package me.h12z.interpreter.token;

import java.util.List;
import java.util.Stack;

import me.h12z.interpreter.Variables;

/**
 * Tokenizer
 */
public class Tokenizer {

  public static List<Token> tokenize(String str) {

    List<Token> tokens = new Stack<>();

    String[] lines = str.split(";");

    for(String line : lines) {

      tokens.addAll(analyzeLine(line));

    }

    return tokens;

  }

  private static List<Token> analyzeLine(String line) {

    List<Token> tokens = new Stack<>();



  }

}
