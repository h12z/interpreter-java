package me.h12z.interpreter.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtils {

  public static String read(File file) throws FileNotFoundException {

    Scanner scanner = new Scanner(file);

    StringBuilder result = new StringBuilder();

    while (scanner.hasNext()) {

      result.append(scanner.nextLine());

    }

    return result.toString();

  }

}