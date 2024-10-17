package me.h12z.interpreter;

import me.h12z.interpreter.token.Token;
import me.h12z.interpreter.token.Tokenizer;
import me.h12z.interpreter.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Interpreter {

    public static void run(File file) throws FileNotFoundException {

        String content = FileUtils.read(file);

        List<Token> tokens = Tokenizer.tokenizeString(content);

    }

}