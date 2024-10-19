package me.h12z;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import me.h12z.interpreter.Interpreter;
import me.h12z.interpreter.token.Token;
import me.h12z.interpreter.token.Tokenizer;

/**
 * Main
 */
public class Main {

  public static void main(String[] args) {

    try {
      Interpreter.run(new File(args[0]));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

  }

}
