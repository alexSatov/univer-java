package com.company.Calculator;

import com.company.BracketsChecker.BracketsChecker;
import com.company.Lexer.JavaParser;
import com.company.Lexer.LanguageParser;
import com.company.Lexer.Token;
import com.company.Vector.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Calculator {
    public static Calculator instance = new Calculator();

    private static LanguageParser inputStringParser = JavaParser.parser;
    private static BracketsChecker bracketsChecker = BracketsChecker.instance;
    private static TokenTranslator tokenTranslator = new TokenTranslator();
    private HashMap<String, Integer> operationPriority = new HashMap<>();

    public String calculate(String expression){
        checkOnCorrectBrackets(expression);
        ArrayList<Token> expTokens = inputStringParser.parseProgram(expression);
        ArrayList<Token> mathTokens = tokenTranslator.translateToMathTokens(expTokens);
        return rpnCalculating(mathTokens);
    }

    private String rpnCalculating(ArrayList<Token> mathTokens){
        Stack<ComplexVectorPair> operators = new Stack<>();
        Stack<String> operations = new Stack<>();

        for(Token mathToken : mathTokens){
            switch (mathToken.type){
                case MathOperator:
                    pushOperationToStack(mathToken.value, operations, operators);
                    continue;
                case SpecSymbol:
                    pushOperationToStack(mathToken.value, operations, operators);
                    continue;
                case Number:
                    ComplexVectorPair pair1 = new ComplexVectorPair(new Complex(mathToken.value),
                            new Vector(new Complex(0, 0)));
                    operators.push(pair1);
                    continue;
                case Imaginary:
                    ComplexVectorPair pair2 = new ComplexVectorPair(new Complex(mathToken.value),
                            new Vector(new Complex(0, 0)));
                    operators.push(pair2);
                    continue;
                case Vector:
                    ComplexVectorPair pair3 = new ComplexVectorPair(new Complex(0, 0),
                            new Vector(mathToken.value));
                    operators.push(pair3);
            }
        }
        while (!operations.isEmpty()){
            String operation = operations.pop();
            operate(operation, operators);
        }
        return operators.pop().toString();
    }

    private void pushOperationToStack(String operation, Stack<String> operations, Stack<ComplexVectorPair> operators){
        if (operations.isEmpty()){
            operations.push(operation);
            return;
        }

        Integer priority = operationPriority.get(operation);

        if (operation.equals("(")) operations.push(operation);
        else if (operation.equals(")")){
            operation = operations.pop();
            while (!operation.equals("(")) {
                operate(operation, operators);
                operation = operations.pop();
            }
        }
        else {
            if (priority > operationPriority.get(operations.peek())){
                operations.push(operation);
                return;
            }
            String cache = operation;
            while (!operations.isEmpty() && priority <= operationPriority.get(operations.peek())){
                operation = operations.pop();
                operate(operation, operators);
            }
            operations.push(cache);
        }
    }

    private void operate(String operation, Stack<ComplexVectorPair> operators){
        ComplexVectorPair rightOperator = operators.pop();
        ComplexVectorPair leftOperator = operators.pop();
        ComplexVectorPair result;

        switch (operation) {
            case "+":
                result = leftOperator.add(rightOperator);
                break;
            case "-":
                result = leftOperator.subtract(rightOperator);
                break;
            case "*":
                result = leftOperator.multiply(rightOperator);
                break;
            case "/":
                result = leftOperator.divide(rightOperator);
                break;
            default:
                result = null;
        }
        operators.push(result);
    }

    private void checkOnCorrectBrackets(String inputString){
        if (!bracketsChecker.isCorrectBracketsSequence(inputString))
            throw new IllegalArgumentException("Incorrect string!");
    }

    private Calculator() {
        operationPriority.put("(", 0);
        operationPriority.put(")", 1);
        operationPriority.put("+", 2);
        operationPriority.put("-", 2);
        operationPriority.put("*", 3);
        operationPriority.put("/", 3);
    }
}
