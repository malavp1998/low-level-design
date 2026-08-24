# Parking Lot — Problem Statement

Design and implement a parking lot system. **No starter code — you design every
class.** This page is the spec plus hints on where patterns fit and why.

---

## 1. The scenario

A parking lot has several floors. Each floor has a number of parking spots, and
spots come in different sizes. A vehicle drives up to the gate, gets a spot and
a ticket. When it leaves, it shows the ticket and pays for the time it stayed.

That's it. Everything below is that sentence made precise.

---

## 2. Core requirements — build these first

**R1 — Structure**
The lot has multiple floors. Each floor has multiple spots. Every spot has a
size: `SMALL`, `MEDIUM` or `LARGE`, and an id you can print (e.g. `F0-M1`).

**R2 — Vehicles and fit**
Three kinds: `BIKE`, `CAR`, `TRUCK`. What fits where:

| Vehicle | Can use |
|---|---|
| BIKE  | SMALL, MEDIUM, LARGE |
| CAR   | MEDIUM, LARGE |
| TRUCK | LARGE only |

A vehicle should get **the smallest spot that fits**, so a bike doesn't waste a
large spot while a truck waits outside.

**R3 — Park**
Given a vehicle, find a free spot that fits and return a **ticket**. The ticket
must remember: which vehicle, which spot, and the entry time.
If nothing fits anywhere → no ticket. Decide how you signal that.

**R4 — Unpark**
Given a ticket, free the spot and return the **fee**.
Pricing: charge per *started* hour — 20 minutes is 1 hour, 61 minutes is 2 hours.
Rates: BIKE 10, CAR 20, TRUCK 40. So a truck parked 2h15m pays 3 × 40 = 120.

**R5 — Availability**
Ask the lot how many spots are currently free for a given vehicle type.

**R6 — Handle the bad cases**
- Lot is full → parking must fail cleanly, not crash.
- Unknown or already-used ticket at exit → fail cleanly.
- Same vehicle parked twice, or a spot double-booked → must be impossible.

### Done when
You can run a `main` that parks a bike, a car and a truck across two floors,
prints the tickets, unparks two of them with the right fees, and rejects a bad
ticket — without a single `if (type == ...)` chain in your top-level class.

---

## 3. Moderate features — add after the core works

Add them **one at a time**, and notice how much existing code you have to edit.
If the answer is "a lot", your design needs work — that's the real lesson here.

- **F1 — A second pricing model.** Flat rate: ₹50 no matter how long.
- **F2 — A second allocation rule.** Instead of nearest floor first, fill the
  top floor first. Or spread cars evenly across floors.
- **F3 — Find my vehicle.** Given a number plate, say which spot it's in.
- **F4 — A display board** at the entrance showing free counts per size, kept
  up to date as cars come and go.
- **F5 — Free first 30 minutes**, then normal hourly pricing.

**The test for F1 and F2: your orchestrator class should need ZERO edits.**
If you have to open it, the pattern isn't doing its job.

### Explicitly out of scope
No database, no UI, no real payment gateway, no login, no reservations, no
threads. Plain Java classes and a `main`. Don't gold-plate.

---

## 4. Where the design patterns go, and why

### Use now

**Strategy — spot allocation**
*The thing that varies:* which free spot you hand out (nearest floor, top floor,
spread the load, keep the ground floor for trucks).
*Why:* F2 asks you to change exactly this rule. Behind an interface, F2 is one
new class. Inline in the lot, F2 means surgery on the class that also handles
tickets and money.

**Strategy — fee calculation**
*The thing that varies:* the price. Hourly, flat, weekend, first-30-free.
*Why:* F1 and F5 are both pure pricing changes. Same reasoning as above.

> **Two interfaces, not one.** "Which spot" and "how much" change for completely
> different reasons — a new pricing scheme has nothing to do with a new
> allocation rule. One interface doing both = one class with two jobs.
> Being able to say this out loud is most of the interview answer.

### Reach for when the feature appears

| Pattern | Where it fits | Why |
|---|---|---|
| **Observer** | F4, the display board | The lot shouldn't know who's watching. Add a logger or an app later without touching it. |
| **Factory** | Creating spots/vehicles from a type | Hides a `switch` behind one call. Only worth it once creation has real logic. |
| **Builder** | Setting up a lot with many floors and spots | When your constructor has 5+ arguments and half are optional. |
| **State** | If a spot grows a lifecycle: `FREE → OCCUPIED → RESERVED → OUT_OF_SERVICE` | Only if each state changes what the *methods do*. With just free/occupied, a boolean is correct. |
| **Singleton** | "There is only one lot" | **Be careful.** It makes testing painful and blocks running two lots. Know it as an option; say the downside before an interviewer does. |

**Don't add a pattern that has no varying thing behind it.** Naming patterns you
didn't need is a bigger red flag than missing one.

---

## 5. Decisions you have to make yourself

No right answer given — but be ready to defend yours.

1. **Are `SpotType` and `VehicleType` the same enum or two?**
   Think about R2: a CAR fits in MEDIUM *or* LARGE. Does one enum still work?
2. **Where does "now" come from?** If your fee code calls `LocalDateTime.now()`
   internally, how do you test a 3-hour stay?
3. **Who validates?** Does the spot refuse a truck, or does the caller check
   first? Pick one and make it consistent everywhere.
4. **What does `park()` return when the lot is full?** `null`, an `Optional`, or
   an exception? "Full" is normal, not exceptional — does that change your answer?
5. **How do you find a ticket at exit?** Scanning every floor is O(n). What
   structure makes it O(1), and what do you give up?
6. **Who owns the spot list — the floor or the lot?** Whichever you choose,
   the other must not be able to modify it.

---

## 6. How to approach it

1. **Write the requirements down** in your own words first. 5 minutes.
2. **Underline the nouns** — those are your candidate classes.
3. **Underline the verbs** — those are your methods.
4. **Circle the things that vary** (R4 pricing, R3 spot choice) — those become
   interfaces.
5. **Draw the classes on paper** with arrows for who-holds-what. Do this before
   typing.
6. **Build bottom-up**: the smallest class first (a single spot), then the
   floor, then the lot. Run something after each one.
7. **Write `main` last**, and keep it dumb — build objects, call methods, print.

**The rule of thumb:** one class, one job. If describing a class needs the word
"and", split it.

---

## 7. Self-check before you call it done

- Could you swap in flat-rate pricing by changing **one line** in `main`?
- Does your top-level lot class contain any loop that searches spots? (It shouldn't.)
- Does anything outside a spot mutate that spot's state directly?
- Can you construct the whole thing in a test and check a fee **without waiting**?
- Can you add a 4th vehicle type without editing more than 2 files?
- Is there a `println` anywhere except `main`?

---

## 8. Follow-ups to expect

1. Two cars arrive at the last free spot at the same instant. What breaks, and
   where is the critical section?
2. Electric spots with a charger, priced differently.
3. Monthly pass holders who park free.
4. Multiple entry and exit gates.
5. 10,000 spots — which of your operations is now too slow?

---

## Run

When you have a driver, `./run.sh parkinglot` expects it at
`lld.parkinglot.ParkingLotMain`. Name it that, or edit [`run.sh`](../../../run.sh).
