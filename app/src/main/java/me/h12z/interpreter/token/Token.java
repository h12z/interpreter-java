package me.h12z.interpreter.token;

/**
 * Token
 */
public class Token {

  public Token(String value, TokenType type) {

    this.value = value;
    this.type = type;

  }

  public String value;
  public TokenType type;

}
