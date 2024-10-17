package me.h12z.interpreter.ast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.h12z.interpreter.token.Token;
import me.h12z.interpreter.token.TokenType;

import java.util.List;
import java.util.Stack;

public class AST {

  private static List<Token> tokens = null;
  private static int pos = 0;

  private static Token getCurrent() {

    return tokens.get(pos);

  }

  private static void consumeToken() {

    pos++;

  }

  private static boolean match(TokenType type) {

    return getCurrent().type.equals(type);

  }

  public static JsonObject toAST(List<Token> _tokens) {

    tokens = _tokens;

    JsonObject result = new JsonObject();

    List<Token> currentLine = new Stack<>();

    int indent = 0;

    tokens.forEach(token -> {

      if(token.type.equals(TokenType.SEMI)) {

        Token first = currentLine.getFirst();

        switch (first.type) {

          case KEYWORD -> {

            switch (first.value) {

              case "class" -> {

                if(indent != 0) {

                  System.err.println("Sorry but you can't define classes inside methods or other classes");
                  System.exit(0);

                } else {

                  JsonArray clazz = new JsonArray();


                }

              }

            }

          }

        }

      } else {

        currentLine.add(token);

      }

    });

    return result;

  }

}