# State Design Pattern (Behavioral)

> Example implemented here: **E-commerce Order Lifecycle**
> `PLACED → SHIPPED → DELIVERED`, with `CANCELLED` as a side exit.

---

## 1. What is it? (in simple words)

An object behaves **differently depending on what state it is currently in**.

Instead of writing `if (status == ...) else if (status == ...)` inside every
method, we make **each state its own class**. The main object (called the
*context*) does not decide anything by itself — it just **forwards the call to
its current state object**. That state object does the work and then **switches
the context to the next state**.

Simple analogy — a **mobile phone**:
pressing the *volume up* button does different things in *Silent mode*,
*Normal mode* and *Airplane mode*. Same button, different behaviour, because
the phone's *mode* (state) changed.

**Official (GoF) definition:**
> Allow an object to alter its behavior when its internal state changes.
> The object will appear to change its class.

---

## 2. The problem it solves

Look at [`Order.java`](Order.java) — that is the **"before"** version:

```java
public void cancel() {
    if (status.equals("PLACED"))         { status = "CANCELLED"; }
    else if (status.equals("SHIPPED"))   { System.out.println("Cannot cancel, already shipped"); }
    else if (status.equals("DELIVERED")) { System.out.println("Cannot cancel, already delivered"); }
}
```

Problems with this:

| Problem | Why it hurts |
|---|---|
| The same `if-else` ladder is repeated in **every** method | `next()`, `cancel()`, `refund()`, `track()`… all repeat it |
| Adding one new status (say `RETURNED`) | You must edit **every single method** |
| Business rules are scattered | The rules for "SHIPPED" live in 5 different methods |
| Breaks Open/Closed Principle | You keep *modifying* old code instead of *adding* new code |

**After** applying State — all rules for one state live in **one file**:

```java
// ShipedState.java  -> everything about SHIPPED is here, nowhere else
public void nextStatus(OrderContext ctx) { ctx.setState(new DeliveredState()); }
public void cancel(OrderContext ctx)     { System.out.println("Cannot cancel, already shipped"); }
```

Adding `RETURNED` now = create **one new file**. Nothing else changes.

---

## 3. The parts (roles) — mapped to this folder

| Role | File here | Job |
|---|---|---|
| **State** (interface) | [`OrderState.java`](OrderState.java) | Declares the actions every state must answer: `nextStatus()`, `cancel()`, `getStatus()` |
| **Concrete States** | [`PlacedState.java`](PlacedState.java), [`ShipedState.java`](ShipedState.java), [`DeliveredState.java`](DeliveredState.java), [`CancelledState.java`](CancelledState.java) | Each one implements the behaviour **for that state only**, and decides the next state |
| **Context** | [`OrderContext.java`](OrderContext.java) | Holds the current state and **delegates** every call to it. Has `setState()` so states can move it forward |
| **Client** | [`StateMain.java`](StateMain.java) | Just calls `order.next()` / `order.cancel()` — knows nothing about the state classes |
| **Bad version (for comparison)** | [`Order.java`](Order.java) | The `if-else` spaghetti this pattern replaces |

**Key point:** the state classes receive the context (`OrderContext ctx`) as a
parameter — that is how they are able to call `ctx.setState(new ShipedState())`
and move the object to the next state.

---

## 4. The flow

```
                 cancel()
   ┌──────────────────────────────────┐
   │                                  ▼
┌────────┐  next()  ┌─────────┐  next()  ┌───────────┐   ┌───────────┐
│ PLACED │ ───────► │ SHIPPED │ ───────► │ DELIVERED │   │ CANCELLED │
└────────┘          └─────────┘          └───────────┘   └───────────┘
                         │                     │               │
                    cancel() ✗            cancel() ✗      next() ✗
                  "already shipped"    "already delivered"  "cancelled"
```

Runtime call chain:

```
client            context                    current state
──────            ───────                    ─────────────
order.next()  ──► state.nextStatus(this) ──► prints + ctx.setState(next)
```

The context never knows *which* state it is in — it only knows it has *a* state.

---

## 5. Where to use it (when to pick this pattern)

Use State when **all or most** of these are true:

1. **The object has a clear, named set of states** — `PLACED / SHIPPED / DELIVERED`, `DRAFT / PUBLISHED`, `IDLE / RUNNING`.
2. **The same method does different things** depending on the current state.
3. **The same `if-else` / `switch` on a status field is repeated** across several methods.
4. **Transitions have rules** — you can go `PLACED → CANCELLED`, but not `DELIVERED → CANCELLED`.
5. **New states get added over time** and you are tired of editing every method.
6. **Invalid actions must be handled gracefully**, not crash (e.g. "Cannot cancel, already shipped").

### When NOT to use it

- Only **2 states and 1 method** → a `boolean` or a simple `enum` is cleaner.
- The status only changes **data**, not **behaviour** (e.g. just a label on a screen).
- The states never transition — nothing is actually moving.
- You do not want ~5 extra classes for something a 3-line `if` solves.

---

## 6. How to recognize this pattern

### A) Recognizing that you *should* apply it (code smells)

```java
if (status.equals("PLACED"))         { ... }
else if (status.equals("SHIPPED"))   { ... }
else if (status.equals("DELIVERED")) { ... }
```

- A field named `status`, `state`, `mode`, `phase`, `stage`, `type`.
- The **same** `if-else` / `switch` ladder appears in **method after method**.
- Adding a new status forces you to **hunt through the whole class**.
- Lots of guard lines like *"cannot do X because we are in Y"*.

**One-line test:**
> *"Does the same method call behave differently based on a status field, and does that status change over time?"*
> If yes → **State**.

### B) Recognizing it in code that already uses it

- An interface named `*State` (`OrderState`, `DoorState`, `ConnectionState`).
- Every method of that interface takes the **context** as a parameter.
- A context class whose methods are all **one-liners that delegate**:
  ```java
  public void next() { state.nextStatus(this); }
  ```
- State classes calling `context.setState(new SomethingState())` — **the states
  drive the transitions**, not one big central `switch`.
- Class names that are **nouns describing a situation**: `PlacedState`, `IdleState`, `LockedState`.

---

## 7. Real-world use cases

### 1. E-commerce order tracking (implemented here)
Amazon / Flipkart order: `PLACED → SHIPPED → DELIVERED`, plus `CANCELLED` and later `RETURNED`.

| Action | PLACED | SHIPPED | DELIVERED |
|---|---|---|---|
| `next()` | ship it | deliver it | ✗ already delivered |
| `cancel()` | cancel it | ✗ already shipped | ✗ already delivered |

### 2. ATM / Vending machine
States: `Idle → HasCard → PinEntered → DispensingCash → OutOfService`.
Pressing *withdraw* in `Idle` says *"Insert card first"*; in `PinEntered` it
actually gives money. A perfect fit, because every button is invalid in most states.

### 3. Ride booking app (Uber / Ola)
States: `REQUESTED → DRIVER_ASSIGNED → ARRIVED → IN_RIDE → COMPLETED`, plus `CANCELLED`.
The **Cancel** button is free in `REQUESTED`, charges a fee in `DRIVER_ASSIGNED`,
and is **disabled** in `IN_RIDE`. Same button, three behaviours.

### 4. Document / ticket workflow (CMS, Jira ticket, leave approval)
States: `DRAFT → IN_REVIEW → APPROVED → PUBLISHED → ARCHIVED`.
`edit()` is allowed in `DRAFT`, blocked in `IN_REVIEW`, and creates a new
version in `PUBLISHED`. `publish()` only works from `APPROVED`.

### 5. Media player (YouTube / Spotify)
States: `Stopped → Playing → Paused → Buffering`.
The **same play/pause button**: starts the song in `Stopped`, pauses in
`Playing`, resumes in `Paused`, and does nothing in `Buffering`.

### 6. Traffic signal / elevator door (classic textbook examples)
`RED → GREEN → YELLOW → RED`, and `Closed → Opening → Open → Closing`.
Pressing *open* while `Closing` reverses the door; while `Open` it does nothing.

---

## 8. State vs Strategy (they look identical — the intent differs)

Both live in this repo, both use "interface + implementations + context", so it
is a very common interview question.

| | **Strategy** | **State** |
|---|---|---|
| Question it answers | *How* should I do this job? | *What* should happen right now? |
| Who chooses | The **client** picks it (`cart.pay(new UpiPayment())`) | The **object itself** switches (`ctx.setState(...)`) |
| Do implementations know each other? | **No** — `UpiPayment` never mentions `CardPayment` | **Yes** — `PlacedState` creates `ShipedState` |
| Changes over time? | Usually set once | Changes constantly during the object's life |
| Example | Payment method, sorting algorithm, compression | Order status, media player, ATM |

**Memory hook:** Strategy = *interchangeable algorithms*. State = *a lifecycle*.

---

## 9. Trade-offs

**Pros**
- Removes big `if-else` / `switch` blocks.
- All rules for one state sit in **one file** (Single Responsibility).
- Adding a new state = adding a new class (Open/Closed).
- Illegal transitions become **unreachable**, not just guarded against.

**Cons**
- More classes — overkill for 2–3 simple states.
- The transition map is **spread across files**, so you must open several classes
  to see the whole picture (draw a diagram, like section 4).
- Creating `new ShipedState()` on every transition makes small garbage objects —
  if states hold no data, reuse **singletons** instead.

---

## 10. Run it

```bash
./run.sh state
```

Or manually from the project root:

```bash
javac -d out $(find src -name "*.java")
java -cp out behavioral.state.StateMain
```

Expected output:

```
PLACED
Shipping the order...
Cannot cancel, already shipped
Delivering  the order...
DELIVERED
```

---

## 11. Practice ideas (extend this example)

1. Add a **`RETURNED`** state reachable only from `DELIVERED` — notice you only
   add files, you do not edit old ones.
2. Make the state classes **singletons** (`PlacedState.INSTANCE`) to avoid
   creating new objects on every transition.
3. Add a `PAYMENT_PENDING` state **before** `PLACED`.
4. Keep a **history list** in `OrderContext` so you can print the full journey.
5. Re-write the same thing with an `enum` that has abstract methods — a compact
   Java-specific alternative to the classic class-per-state approach.
