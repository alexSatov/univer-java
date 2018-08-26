package com.company;

import com.company.Calculator.Calculator;

public class Main {

    public static void main(String[] args) {
        String expression1 = "(1/2,i/2,i*3) + (1/2+i/2, 4i/(i-i+1-1+i), i*i*i)";
        String expression2 = "-((2-3) + (((2 *4))))";
        String expression3 = "(1 + (1, 1)) * (1, 1)";
        Calculator calculator = Calculator.instance;
        System.out.println(calculator.calculate(expression1));
        System.out.println(calculator.calculate(expression2));
        System.out.println(calculator.calculate(expression3));
    }
}
