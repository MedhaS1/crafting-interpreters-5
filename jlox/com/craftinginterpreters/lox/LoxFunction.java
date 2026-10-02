package com.craftinginterpreters.lox;

import java.util.List;

class LoxFunction implements LoxCallable {
  // challenge 10.2: name is null for lambdas
  private final String name;
  private final Expr.Function declaration;
  private final Environment closure;

  LoxFunction(String name, Expr.Function declaration, Environment closure) {
    this.name = name;
    this.declaration = declaration;
    this.closure = closure;
  }

  @Override
  public String toString() {
    if (name == null) return "<fn>";
    return "<fn " + name + ">";
  }

  @Override
  public int arity() {
    return declaration.parameters.size();
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    Environment environment = new Environment(closure);

    // challenge 11.4: params get slots 0, 1, 2... in the same order
    // the resolver gave them, so we just add them in order
    for (int i = 0; i < declaration.parameters.size(); i++) {
      environment.define(arguments.get(i));
    }

    try {
      interpreter.executeBlock(declaration.body, environment);
    } catch (Return returnValue) {
      return returnValue.value;
    }
    return null;
  }
}
