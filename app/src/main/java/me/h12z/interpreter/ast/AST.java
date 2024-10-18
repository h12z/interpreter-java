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

  public static JsonObject toAST(List<Token> _tokens) {

    tokens = _tokens;

    JsonObject result = new JsonObject();

    int indent = 0;

    String currentPath = "";

    for(pos = 0; pos < tokens.size(); pos++) {

      switch (getCurrent().type) {

        case KEYWORD -> {

          switch (getCurrent().value) {

            case "class" -> {

              if(indent != 0) {

                System.err.println("Sorry but classes can't be initialized inside of methods...");
                System.exit(0);

              }

              String name = "";

              consumeToken();

              if(getCurrent().type != TokenType.NAME) {

                System.err.println("Sorry but classes require a name...");
                System.exit(0);

              }

              name = getCurrent().value;

              indent++;

              result.add(name, new JsonObject());

              currentPath = "class/" + name;

            }

            case "func" -> {

              if(currentPath.startsWith("func/")) {

                System.err.println("Sorry but functions cannot be initialized within functions...");
                System.exit(0);

              }

              String[] path = currentPath.split(".");
              if(path.length == 2) {

                System.err.println("Sorry but functions cannot be initialized within functions...");
                System.exit(0);

              }

              consumeToken();

              if(getCurrent().type != TokenType.NAME) {

                System.err.println("Sorry but classes require a name...");
                System.exit(0);

              }

              String name = getCurrent().value;

              if(path.length == 0) {

                result.add(name, new JsonObject());
                currentPath = "func/" + name;
                indent++;

              } else {

                result.getAsJsonObject(currentPath.replace("class/", "")).add(name, new JsonObject());
                currentPath += ".func/" + name;
                indent++;

              }

            }

            case "int" -> {

              

            }

          }

        }

      }

    }

    return result;

  }

}