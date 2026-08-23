# Behavioral Design Patterns - Java Practice

Skeletons for the top 4 behavioral patterns. Every class has comments explaining
the pattern, the roles, the example being modelled, and `TODO`s for you to fill in.

## Structure

```
src/behavioral/
  strategy/   PaymentStrategy, CreditCardPayment, UpiPayment, CashPayment,
              ShoppingCart (context), StrategyMain
  observer/   Observer, Subject, Channel (subject), Subscriber, ObserverMain
  state/      VendingMachineState, IdleState, HasMoneyState, DispensingState,
              VendingMachine (context), StateMain
  command/    Command, Light, Fan (receivers), LightOnCommand, LightOffCommand,
              FanOnCommand, RemoteControl (invoker), CommandMain
```

## Run

Easiest:

```bash
./run.sh strategy      # or observer | state | command | all
```

Manual, if you prefer:

```bash
# compile everything once
javac -d out $(find src -name "*.java")

# then run whichever you want
java -cp out behavioral.strategy.StrategyMain
java -cp out behavioral.observer.ObserverMain
java -cp out behavioral.state.StateMain
java -cp out behavioral.command.CommandMain
```

Compile just one package while iterating (faster):

```bash
javac -d out src/behavioral/strategy/*.java && java -cp out behavioral.strategy.StrategyMain
```

Java 25 single-file mode won't work here because the classes are split across
files with packages — use the `javac -d out` form above.

## The examples

| Pattern  | Example              | The varying thing                          |
|----------|----------------------|--------------------------------------------|
| Strategy | Shopping cart payment| HOW you pay: card / UPI / cash             |
| Observer | YouTube channel      | WHO gets notified on upload                |
| State    | Vending machine      | WHAT an action does, per current state     |
| Command  | Universal remote     | WHICH action a button fires (+ undo)       |

## Order to learn

1. **Strategy** — simplest, teaches composition + delegation.
2. **Observer** — one-to-many, teaches loose coupling.
3. **State** — same shape as Strategy, different intent (self-transitions).
4. **Command** — request-as-object, unlocks undo/queue/log.
