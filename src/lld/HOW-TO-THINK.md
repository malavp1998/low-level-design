# How to think about any LLD problem

Read this before starting a new problem. It is about **how to think**, not about
any one question.

---

## The one big idea

> **LLD is not about patterns. It is about deciding who does what.**

Imagine you are opening a restaurant and hiring staff. You would never hire one
person to take orders, cook, serve, wash dishes **and** handle the cash. You
hire a waiter, a chef, a cashier. Each has **one job**, and they talk to each
other.

That is the entire skill. **Classes are your staff.** Designing is hiring them
and deciding who talks to whom.

Everything below is just a method for doing that hiring well.

---

## Before you write a single line: 4 questions

### Q1. "What is the story?" — say it in one sentence

Explain the problem to yourself like you would explain it to a friend who knows
nothing about code.

> *"A customer picks a movie and a seat, pays, and gets a ticket."*

If you cannot say it in one sentence, you do not understand the problem yet.
**Do not code until you can.**

### Q2. "Who are the players?" — find the nouns

Read your sentence and underline every noun:

> *"A **customer** picks a **movie** and a **seat**, pays, and gets a **ticket**."*

Customer, Movie, Seat, Ticket → your first classes. It really is that
mechanical. Not every noun survives, but this gets you 80% of the way in
60 seconds.

### Q3. "What do they do?" — find the verbs

Underline the verbs: *picks, pays, gets*. Those are your methods.

Then ask the important part: **whose method is it?** `pay()` — does the
`Customer` pay, or does a `BookingService` take payment? There is no single
right answer, but *asking the question* is the job.

### Q4. "What will change next month?" ← the most important one

Think like a product manager. What will someone ask you to change?

- *"Now we also accept UPI."*
- *"Weekend tickets cost more."*
- *"Premium members skip the queue."*

**Whatever changes → that becomes an interface.**

This is where patterns actually come from. Not from memorising a catalogue.

---

## The rule that makes patterns click

> ### Never ask "which pattern should I use?"
> ### Ask "what is the thing that varies?"

Patterns are the **answer**, not the question. If you start by hunting for a
pattern to apply, you will force one in where it does not belong — and an
interviewer spots that immediately.

Do it the other way round:

| You notice… | You reach for |
|---|---|
| "The *way* we do this job might change" (payment, pricing, win rule) | **Strategy** |
| "When X happens, several people need to know" | **Observer** |
| "The same action behaves differently depending on a status" | **State** |
| "We need undo / retry / a history of actions" | **Command** |
| "Creating this thing needs a big if-else on a type" | **Factory** |

In Tic Tac Toe you did not use Strategy because Strategy is famous. You used it
because **the winning rule is the thing that could change**. That is the whole
reasoning, and it is the sentence to say out loud.

**And if nothing varies — use no pattern.** A plain class is the right answer
more often than people admit. Naming a pattern you did not need is a worse
signal than missing one.

---

## The 6 steps, in order

**1. Repeat the requirements back** *(2 min)*
In your own words. Ask 2–3 questions: *"One floor or many?" "Can a booking be
cancelled?"* Interviewers **want** this. Silence looks like you did not think.

**2. List the nouns** *(3 min)*
Your candidate classes.

**3. Give each class one job** *(5 min)*
Write a one-line description for each. **If the description needs the word
"and", split it.**
> *"Board stores the grid **and** decides the winner"* → two classes.

**4. Circle what varies** *(3 min)*
Those become interfaces. Usually 1–3 in any problem.

**5. Draw arrows on paper** *(5 min)*
Who holds whom. `Game` holds a `Board`. `Board` holds cells. **Do this before
typing.** Ten minutes on paper saves an hour of refactoring.

**6. Code bottom-up, `main` last** *(rest)*
Smallest class first, run something after each one. Your `main` is written last
and stays dumb: build objects, call methods, print. Nothing else.

---

## 4 questions to keep asking *while* you code

**"Should this class know that?"**
`Board` does not need to know a player is called "Piyyush". `ParkingSpot` does
not need to know about money. When a class knows something it does not need, it
is the wrong class.

**"If I change this, what else breaks?"**
Good design: change pricing → touch one file. Bad design: change pricing →
touch four.

**"Can I test this without a keyboard?"**
If checking your fee logic means typing input and waiting 3 hours, the design is
wrong. That is why time gets passed **in** as a parameter instead of being read
inside with `now()`.

**"Where would a `println` go?"**
Only in `main`. If a business class is printing, it is doing the UI's job — and
you can never reuse it in a web app.

---

## A worked example, start to finish

**Problem: a library book system.**

**Story:** *"A member borrows a book and returns it later, paying a fine if it
is late."*

**Nouns:** Member, Book, Library, Loan, Fine.

**Verbs:** borrow, return, calculateFine.

**One job each:**

| Class | Its one job |
|---|---|
| `Book` | title, author, is it available |
| `Member` | name, id, how many books they hold |
| `Loan` | this member, this book, borrowed on this date |
| `Library` | the boss: `borrow()`, `returnBook()` |

**What varies?** The **fine rule**. Today ₹5/day. Tomorrow: free for students,
double for bestsellers.
→ `FineStrategy` interface. **Strategy pattern** — and now I can say exactly why.

**Anything else vary?** *"Notify a member when a reserved book comes back"* →
one-to-many → **Observer**. But **only if the requirement exists.** I do not add
it speculatively.

**Arrows:** `Library` holds books, members, loans, and one `FineStrategy`.
`Loan` points at a `Book` and a `Member`.

Total: about 10 minutes, no code. **Now** I start typing — and I know exactly
what to type.

---

## The mistakes to avoid

| Mistake | What to do instead |
|---|---|
| Coding immediately | Spend 10 minutes on nouns and paper first |
| One giant `Main` doing everything | `main` only builds, calls, prints |
| Forcing in 5 patterns to look smart | Use 1–2, and explain *why* |
| Building for imaginary needs ("what if it goes to Mars?") | Build the requirements given; mention extensions verbally |
| Going silent while thinking | Narrate: *"the win rule could change, so I will put it behind an interface"* |
| Perfect names, zero working code | A running solution with average names beats a beautiful half-solution |

---

## Your 45-minute clock

```
0–5 min     understand + ask questions
5–15 min    nouns, one-job-each, circle what varies, draw arrows
15–40 min   code bottom-up, smallest class first
40–45 min   a main that proves it works
```

**A third of the time is spent not coding.** That is not wasted — that *is* the
interview.

---

## If you remember only three things

1. **Classes are staff members. Give each one job.**
2. **Find what changes. That becomes an interface.** Patterns follow from this —
   never the reverse.
3. **Draw it on paper before you type.**

You already did all three on Tic Tac Toe — `Board` stores, `Game` runs turns,
`WinningStrategy` decides. Parking Lot is the same thinking, just more staff.

---

**See also:** [`../../LLD-DESIGN-PATTERNS.md`](../../LLD-DESIGN-PATTERNS.md) for
the pattern cheat sheet · [`README.md`](README.md) for the problem roadmap.
