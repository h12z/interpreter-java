package me.h12z.interpreter.ast;

import java.util.HashMap;
import java.util.List;

/**
 * Func
 */
public class Func {

  HClass parent;
  List<HType<?>> types;
  List<Container> containers;
  List<Action> actions;

}
