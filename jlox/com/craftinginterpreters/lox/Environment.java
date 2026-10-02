package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;

// challenge 11.4: local variables are stored in a list and looked up by
// index instead of by name. globals are kept in a map in the Interpreter.
class Environment {
  final Environment enclosing;
  private final List<Object> values = new ArrayList<>();

  Environment() {
    enclosing = null;
  }

  Environment(Environment enclosing) {
    this.enclosing = enclosing;
  }

  // variables get added in the same order the resolver declared them,
  // so the position in the list matches the slot the resolver picked
  void define(Object value) {
    values.add(value);
  }

  Environment ancestor(int distance) {
    Environment environment = this;
    for (int i = 0; i < distance; i++) {
      environment = environment.enclosing;
    }
    return environment;
  }

  Object getAt(int distance, int slot) {
    return ancestor(distance).values.get(slot);
  }

  void assignAt(int distance, int slot, Object value) {
    ancestor(distance).values.set(slot, value);
  }
}
