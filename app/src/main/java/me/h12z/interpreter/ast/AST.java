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
  private static final JsonObject result = new JsonObject();

  private static Token getCurrent() {

    return tokens.get(pos);

  }

  private static void consumeToken() {

    pos++;

  }

  public static JsonObject toAST(List<Token> _tokens) {

    tokens = _tokens;

    int indent = 0;

    String currentPath = "";

    for (pos = 0; pos < tokens.size(); pos++) {

      switch (getCurrent().type) {

        case KEYWORD -> {

          switch (getCurrent().value) {

            case "class" -> {

              if (indent != 0) {

                System.err.println("Sorry but classes can't be initialized inside of methods...");
                System.exit(0);

              }

              String name;

              consumeToken();

              if (getCurrent().type != TokenType.NAME) {

                System.err.println("Sorry but classes require a name...");
                System.exit(0);

              }

              name = getCurrent().value;

              indent++;

              result.add("mainFile", new JsonArray());

              JsonObject clazz = new JsonObject();
              clazz.addProperty("type", "class");
              clazz.addProperty("name", name);
              clazz.add("content", new JsonArray());

              result.getAsJsonArray("mainFile").add(clazz);

              currentPath = Integer.toString(result.getAsJsonArray("mainFile").size() - 1);

            }

            case "func" -> {

              String[] path = currentPath.split("\\.");
              if (path.length == 2) {

                System.err.println("Sorry but functions cannot be initialized within functions...");
                System.exit(0);

              }

              consumeToken();

              if (getCurrent().type != TokenType.NAME) {

                System.err.println("Sorry but classes require a name...");
                System.exit(0);

              }

              String name = getCurrent().value;

              JsonObject func = new JsonObject();
              func.addProperty("type", "func");
              func.addProperty("name", name);

              consumeToken();

              while (getCurrent().type.equals(TokenType.KEYWORD)) {

                String type = getCurrent().value;

                consumeToken();

                if (!getCurrent().type.equals(TokenType.NAME)) {
                  System.err.println("Sorry but variables have to be assigned names...");
                  System.exit(0);
                }

                String varname = getCurrent().value;

                consumeToken();

                String array = "";

                while (getCurrent().type.equals(TokenType.PAREN) && getCurrent().value.equals("[")) {

                  array += "[]";
                  consumeToken();
                  consumeToken();

                }

                if (!func.has("args")) {
                  func.add("args", new JsonArray());
                }

                JsonObject arg = new JsonObject();
                arg.addProperty("type", type + array);
                arg.addProperty("name", varname);

                func.getAsJsonArray("args").add(arg);

                if (getCurrent().type.equals(TokenType.COMMA)) consumeToken();
                else break;

              }

              consumeToken(); // consumes )
              consumeToken(); // consumes ;

              func.add("content", new JsonArray());

              if (!result.has("mainFile")) result.add("mainFile", new JsonArray());

              if (path.length == 0) {

                result.getAsJsonArray("mainFile").add(func);

                currentPath = Integer.toString(result.getAsJsonArray("mainFile").size() - 1);

              } else {

                int index = Integer.parseInt(currentPath.split("\\.")[0]);

                result.getAsJsonArray("mainFile").get(index).getAsJsonArray().add(func);
                currentPath += "." + result.getAsJsonArray("mainFile").get(index).getAsJsonArray().size();

              }

              indent++;

            }

            case "int", "float", "char", "string", "boolean" -> {

              String vartype = getCurrent().value;

              consumeToken();

              if (!getCurrent().type.equals(TokenType.NAME)) {
                System.err.println("Sorry but variables have to be assigned names...");
                System.exit(0);
              }

              String name = getCurrent().value;
              String value = "";

              consumeToken();

              if (getCurrent().type.equals(TokenType.SET)) {
                consumeToken();
                while (!getCurrent().type.equals(TokenType.SEMI)) {
                  value += getCurrent().value;
                }
                consumeToken();
              } else {
                consumeToken();
              }

              JsonObject integer = new JsonObject();

              integer.addProperty("name", name);
              integer.addProperty("type", vartype);
              integer.addProperty("value", value);

              String[] path = currentPath.split("\\.");

              JsonArray all;

              JsonArray current = result.getAsJsonArray("mainFile");

              for (int j = 0; j < path.length; j++) {

                current = current.get(Integer.parseInt(path[j])).getAsJsonArray();

              }

              current.add(integer);

              all = current;

              for (int i = path.length - 1; i > 0; i--) {

                current = result.getAsJsonArray("mainFile");

                for (int j = 0; j < path.length - i - 1; j++) {

                  current = current.get(Integer.parseInt(path[j])).getAsJsonArray();

                }

                current.set(i, all);

                all = current;

              }

              System.out.println(current);

              result.remove("mainFile");
              result.add("mainFile", all);

            }

            case "break" -> {

              String value = "";

              consumeToken();
              while (!getCurrent().type.equals(TokenType.SEMI)) {
                value += getCurrent().value;
              }

              consumeToken();

              JsonObject _return = new JsonObject();

              _return.addProperty("type", "return");
              _return.addProperty("name", "return");
              _return.addProperty("value", value);

              String[] path = currentPath.split("\\.");

              JsonArray all;

              JsonArray current = result.getAsJsonArray("mainFile");

              for (int j = 0; j < path.length; j++) {

                current = current.get(Integer.parseInt(path[j])).getAsJsonArray();

              }

              current.add(_return);

              all = current;

              for (int i = path.length - 1; i > 0; i--) {

                current = result.getAsJsonArray("mainFile");

                for (int j = 0; j < path.length - i - 1; j++) {

                  current = current.get(Integer.parseInt(path[j])).getAsJsonArray();

                }

                current.set(i, all);

                all = current;

              }

              System.out.println(current);

              result.remove("mainFile");
              result.add("mainFile", all);

            }

          }

        }

      }

    }

    return result;

  }

}
