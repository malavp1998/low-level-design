# LLD Practice — machine coding problems

Patterns are done. Now build actual systems. **Write the code yourself.**

> **New to this? Read [`HOW-TO-THINK.md`](HOW-TO-THINK.md) first** — how to
> approach any LLD problem, and where design patterns actually come from.

## The order to do them

| # | Problem | Why this one | Patterns you'll use |
|---|---|---|---|
| 1 | **Tic Tac Toe** ← start here | Smallest real LLD. Teaches class splitting. | Strategy |
| 2 | Parking Lot | The most-asked LLD question, ever. | Strategy, Factory, Singleton |
| 3 | Vending Machine / ATM | Pure lifecycle problem. | **State**, Strategy |
| 4 | Splitwise | First one with real logic, not just objects. | Strategy, Observer |
| 5 | BookMyShow | Concurrency + seat locking enters the picture. | Singleton, Observer, Factory |
| 6 | Notification / Logger system | Small, and interviewers love it as a warm-up. | Observer, Chain of Responsibility |

## How to attack any LLD problem (45 min)

1. **Requirements** (5 min) — say them out loud, ask 2–3 clarifying questions.
2. **Nouns → classes** — "player", "board", "move" become classes.
3. **Verbs → methods** — "place a mark", "check winner".
4. **Find the thing that varies** → that becomes an interface (Strategy / State).
5. **Code the skeleton**, then fill it.
6. **A `main` that proves it works.**

**Rule of thumb:** one class = one job. If a class name has "and" in its
description, split it.

## Run

```bash
./run.sh tictactoe
```

---

### 1. Tic Tac Toe — [`tictactoe/`](tictactoe/)

**Start file:** [`tictactoe/TicTacToeMain.java`](tictactoe/TicTacToeMain.java) — the driver is
already written, so you only fill the TODOs and keep re-running until it works.

Fill them in this order:

1. [`Board.java`](tictactoe/Board.java) — the grid: fill with EMPTY, validate, place, isFull, print
2. [`RowColumnDiagonalStrategy.java`](tictactoe/RowColumnDiagonalStrategy.java) — rows, columns, 2 diagonals
3. [`Game.java`](tictactoe/Game.java) — `makeMove()`: validate → place → check win → check draw → switch turn

Already done for you: `Symbol`, `GameStatus`, `Player`, `WinningStrategy`.

**Target output** once finished — X wins on the top row:

```
X X X
- O -
- O -
Status: WIN
Winner: Piyush
```

**Once it works, try these follow-ups** (this is what interviewers ask next):
- make it an **N x N** board — does anything break?
- add an `UndoMove` — *(hint: Command pattern)*
- add a `BotPlayer` that picks a random empty cell — *(hint: Strategy again)*
