#!/bin/bash
# Usage: ./run.sh strategy | observer | state | command | tictactoe | all
set -e
cd "$(dirname "$0")"

compile() {
  echo "==> compiling..."
  javac -d out $(find src -name "*.java")
}

run_one() {
  echo ""
  echo "=========== $1 ==========="
  case $1 in
    strategy) java -cp out behavioral.strategy.StrategyMain ;;
    observer) java -cp out behavioral.observer.ObserverMain ;;
    state)    java -cp out behavioral.state.StateMain ;;
    command)  java -cp out behavioral.command.CommandMain ;;
    tictactoe) java -cp out lld.tictactoe.TicTacToeMain ;;
    *) echo "unknown: $1 (use strategy|observer|state|command|tictactoe|all)"; exit 1 ;;
  esac
}

compile
if [ "$1" == "all" ] || [ -z "$1" ]; then
  for p in strategy observer state command; do run_one $p; done
else
  run_one "$1"
fi
