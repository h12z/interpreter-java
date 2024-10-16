package me.h12z;


import java.util.List;

import me.h12z.interpreter.token.Token;
import me.h12z.interpreter.token.Tokenizer;

/**
 * Main
 */
public class Main {

  public static void main(String[] args) {
    
    List<Token> tokens = Tokenizer.tokenizeString("func main(string args[])");

    for (int i = 0; i < tokens.size(); i++) {
      
      System.out.println(tokens.get(i).type + ": " + tokens.get(i).value);

    }

  }

}
