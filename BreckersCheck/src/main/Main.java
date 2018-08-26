package main;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class Main {
    public static String ReadLine() {
        try {
            return new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            return new String();
        }
    }

    public  static boolean equalBrackets(char openBrecket, char closeBrecket)
    {
        if (openBrecket == '(' && closeBrecket == ')'
                || openBrecket == '{' && closeBrecket == '}'
                || openBrecket == '[' && closeBrecket == ']')
            return true;
        else
            return false;
    }

    public static void main(String[] args)
    {
        String inputLine = ReadLine();
        char[] stack = new char[inputLine.length()];
        int j = 0;
        boolean error = false;
        for (int i = 0; i < inputLine.length(); i++)
        {
            if (inputLine.charAt(i) == '(' || inputLine.charAt(i) == '{' || inputLine.charAt(i) == '[')
            {
                stack[j] = inputLine.charAt(i);
                j++;
            }

            if (inputLine.charAt(i) == ')' || inputLine.charAt(i) == '}' || inputLine.charAt(i) == ']')
            {
                if (!equalBrackets(stack[j-1], inputLine.charAt(i))) {
                    System.out.print("Error");
                    error = true;
                    break;
                }
                else
                    j--;
            }
        }
        if (!error)
            if (stack[0] == 0)
                System.out.print("Correct");
            else
                System.out.print("No breckers here");
    }
}
