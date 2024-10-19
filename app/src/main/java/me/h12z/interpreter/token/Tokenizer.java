package me.h12z.interpreter.token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.*;

import me.h12z.interpreter.Variables;

/**
 * Tokenizer
 */
public class Tokenizer {

  public static List<Token> tokenizeString(String input) {
    List<Token> tokens = new ArrayList<>();

    Pattern pattern = Pattern.compile("(\\\".*\\\")|(\\\'.*\\\')|(\\bint|char|float|string|boolean|class|func|return|while|if|else|ifnot|break|continue|end\\b)|(([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\()|(\\b([a-zA-Z_][a-zA-Z0-9_]*)\\b)|(\\(|\\)|\\[|\\])|([+\\-*\\/])|(==|<=|>=|!=)|(\\=)|(\\d+\\.\\d+)|(\\d+)|(\\s*\\;\\s*)|(\\s*\\.\\s*)|(/s*\\,/s*)");

    Matcher matcher = pattern.matcher(input);

    while (matcher.find()) {

      if (matcher.group(1) != null) {
        // Match STRING
        tokens.add(new Token(matcher.group(1), TokenType.STRING));
      } else if (matcher.group(2) != null) {
        // Match CHAR
        tokens.add(new Token(matcher.group(2), TokenType.CHAR));
      } else if (matcher.group(3) != null) {
        // Match KEYWORD
        tokens.add(new Token(matcher.group(3), TokenType.KEYWORD));
      } else if (matcher.group(4) != null) {
        // Match FUNCTION_NAME
        tokens.add(new Token(matcher.group(4).replace("(", ""), TokenType.FUNCTION_NAME));
        tokens.add(new Token("(", TokenType.PAREN));
      } else if (matcher.group(6) != null) {
        // Match NAME
        tokens.add(new Token(matcher.group(6), TokenType.NAME));
      } else if (matcher.group(8) != null) {
        // Match PARENTHESIS (includes both () and [])
        tokens.add(new Token(matcher.group(8), TokenType.PAREN));
      } else if (matcher.group(9) != null) {
        // Match OPERATOR
        tokens.add(new Token(matcher.group(9), TokenType.MATH_OP));
      } else if (matcher.group(10) != null) {
        // Match COMPARER
        tokens.add(new Token(matcher.group(10), TokenType.COMPARER));
      } else if (matcher.group(11) != null) {
        // Match SET
        tokens.add(new Token(matcher.group(11), TokenType.SET));
      } else if (matcher.group(12) != null) {
        // Match FLOAT
        tokens.add(new Token(matcher.group(12), TokenType.FLOAT));
      } else if (matcher.group(13) != null) {
        // Match INTEGER
        tokens.add(new Token(matcher.group(13), TokenType.INT));
      } else if (matcher.group(15) != null) {
        // Match DOT
        tokens.add(new Token(matcher.group(15), TokenType.DOT));
      } else if (matcher.group(14) != null) {
        // Match SEMI
        tokens.add(new Token(matcher.group(14), TokenType.SEMI));
      } else if (matcher.group(16) != null) {
        // Match SEMI
        tokens.add(new Token(matcher.group(14), TokenType.COMMA));
      }

    }

    return tokens;

  }

}
