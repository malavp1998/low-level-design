# LLD Design Patterns — Quick Notes (Behavioral)

Short revision sheet for the 4 patterns in this repo. Read this before an LLD round.

| Pattern | One line | Trick word |
|---|---|---|
| **Strategy** | Many ways to do the **same job**, swap at runtime | **HOW** |
| **Observer** | One changes → **many** get told | **NOTIFY** |
| **State** | Same method behaves differently as the object **moves through a lifecycle** | **WHEN / status** |
| **Command** | Wrap a request into an **object** so you can queue, log, undo it | **WHAT (as object)** |

---

## 1. Strategy

**What:** Put each algorithm in its own class behind one interface. The client picks which one to use.

**Real life:** Google Maps route — car / walk / bike. Same "find route", different method.

**Code shape** — `src/behavioral/strategy/`
```java
interface PaymentStrategy { void pay(int amount); }
class UpiPayment implements PaymentStrategy { ... }
cart.setPayment(new UpiPayment());   // client chooses
cart.checkout();                     // context delegates
```

**Spot it:** `if (type == "UPI") ... else if (type == "CARD")` — and the **caller knows** which one it wants.

**LLD questions:** payment gateway · ride fare calculator (normal/surge/night) · sorting or discount rules · file compression (zip/rar) · notification channel picked by user.

**Trick:** *Strategy = you choose the tool.*

---

## 2. Observer

**What:** A subject keeps a list of observers. On change, it loops and calls `update()` on all of them. Subject does not know who they are.

**Real life:** YouTube channel uploads → all subscribers get a bell notification.

**Code shape** — `src/behavioral/observer/`
```java
interface Subscriber { void update(String video); }
channel.subscribe(new EmailSubscriber());
channel.upload("video");   // notifies everyone in the list
```

**Spot it:** words like *notify, subscribe, publish, listener, event, broadcast* — **1 → many**.

**LLD questions:** notification system · stock price / cricket score alerts · chat room message broadcast · food delivery order tracking · logging & analytics listeners.

**Trick:** *Observer = newspaper subscription.*

---

## 3. State

**What:** One class per state. The object (context) forwards the call to its current state, and that state decides the **next** state.

**Real life:** Order: PLACED → SHIPPED → DELIVERED. `cancel()` works in PLACED, fails in SHIPPED.

**Code shape** — `src/behavioral/state/`
```java
interface OrderState { void next(OrderContext c); void cancel(OrderContext c); }
class PlacedState implements OrderState {
    public void next(OrderContext c) { c.setState(new ShippedState()); }  // state moves itself
}
```

**Spot it:** a `status` / `mode` field, and the **same if-else ladder repeated in many methods**.

**LLD questions:** order tracking · ATM / vending machine · elevator · Uber ride lifecycle · Jira ticket or leave-approval workflow · traffic signal.

**Trick:** *State = the object changes its own gear.*

---

## 4. Command

**What:** Turn a request into an object holding *receiver + action*. The invoker just calls `execute()` — it has no idea what the command does.

**Real life:** TV remote — each button holds a command object. Rebind a button, remote code never changes.

**Code shape** — `src/behavioral/command/`
```java
interface Command { void execute(); void undo(); }
class LightOnCommand implements Command {
    Light light;
    public void execute() { light.on(); }
    public void undo()    { light.off(); }
}
remote.setButton(new LightOnCommand(light));
remote.press();      // invoker knows nothing
```

**Spot it:** you need **undo / redo / queue / retry / schedule / log** of actions.

**LLD questions:** text editor undo-redo · smart home remote · task scheduler or job queue · restaurant order (waiter → kitchen) · transaction rollback · game move history.

**Trick:** *Command = an order slip you can keep, queue and tear up.*

---

## The confusing pair: Strategy vs State

Same structure (interface + impls + context). Only the **intent** differs.

| | Strategy | State |
|---|---|---|
| Who picks the object | **Client** — `cart.pay(new UpiPayment())` | **The object itself** — `ctx.setState(...)` |
| Do impls know each other | No | Yes — `PlacedState` creates `ShippedState` |
| How often it changes | Set once | Keeps changing through a lifecycle |

**One-liner for the interview:** *"Strategy is interchangeable algorithms chosen from outside; State is a lifecycle where the object switches itself."*

---

## Pick-the-pattern in 10 seconds

| The requirement says… | Use |
|---|---|
| "user can choose the method / algorithm" | Strategy |
| "when X happens, inform A, B and C" | Observer |
| "valid only in certain status / stage" | State |
| "support undo, retry, queue, history" | Command |

**Full sentence to remember:**
> **HOW** to do it → Strategy · **TELL** everyone → Observer · **WHEN** in the lifecycle → State · **WHAT** as an object → Command.

---

## Interview tip

Never just name the pattern. Say it in this order:

1. **Problem** — "otherwise every new payment mode means editing the cart class"
2. **Pattern + role mapping** — "PaymentStrategy is the interface, ShoppingCart is the context"
3. **Benefit** — "adding a new one = one new class, no old code touched (Open/Closed)"

Deeper write-up for State: [`src/behavioral/state/README.md`](src/behavioral/state/README.md)
