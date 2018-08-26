package com.company.BracketsChecker;

public class BracketsChecker {
    public static BracketsChecker instance = new BracketsChecker();

    public boolean isCorrectBracketsSequence(String inputString){
        char[] stack = new char[inputString.length()];
        int j = 0;
        boolean result = true;
        for (int i = 0; i < inputString.length(); i++)
        {
            if (inputString.charAt(i) == '(' || inputString.charAt(i) == '{' || inputString.charAt(i) == '[')
            {
                stack[j] = inputString.charAt(i);
                j++;
            }

            if (inputString.charAt(i) == ')' || inputString.charAt(i) == '}' || inputString.charAt(i) == ']')
            {
                if (!equalBrackets(stack[j-1], inputString.charAt(i))) {
                    result = false;
                    break;
                }
                else j--;
            }
        }
        return result;
    }

    private boolean equalBrackets(char openBracket, char closeBracket) {
        return openBracket == '(' && closeBracket == ')'
                || openBracket == '{' && closeBracket == '}'
                || openBracket == '[' && closeBracket == ']';
    }

    private BracketsChecker() {}
}
