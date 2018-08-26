package com.company.Calculator;

import junit.framework.TestCase;

public class CalculatorTest extends TestCase {
    private Calculator calculator = Calculator.instance;

    public void testCorrectResult_WhenNoOperation(){
        String expression1 = "5";
        String expression2 = "4.6 + 2.3i";
        String expression3 = "(4, 3 - 2i, 9.8i)";

        assertEquals("5.0", calculator.calculate(expression1));
        assertEquals("4.6 + 2.3i", calculator.calculate(expression2));
        assertEquals("(4.0, 3.0 - 2.0i, 9.8i)", calculator.calculate(expression3));
    }

    public void testNegativeValue_WhenMinusAtBegin(){
        String expression1 = "-6.7";
        String expression2 = "-2 - 4i";
        String expression3 = "-(1.0, -1.5i, 100)";

        assertEquals("-6.7", calculator.calculate(expression1));
        assertEquals("-2.0 - 4.0i", calculator.calculate(expression2));
        assertEquals("(-1.0, 1.5i, -100.0)", calculator.calculate(expression3));
    }

    public void testCorrectResult_SimpleOperations(){
        String expression1 = "2 + 7";
        String expression2 = "-2 - 4i - 6 + 5i";
        String expression3 = "(7, 7i, 4 - 8i) + (-2, 5, 6i, 8.5)";

        assertEquals("9.0", calculator.calculate(expression1));
        assertEquals("-8.0 + i", calculator.calculate(expression2));
        assertEquals("(5.0, 5.0 + 7.0i, 4.0 - 2.0i, 8.5)", calculator.calculate(expression3));
    }

    public void testCorrectResult_Multiplying(){
        String expression1 = "20 - 7 * 2";
        String expression2 = "-2 - 4i * 6 + 5i";
        String expression3 = "(2, 1 + i, 2.5i)*(3 - 6i)";

        assertEquals("6.0", calculator.calculate(expression1));
        assertEquals("-2.0 - 19.0i", calculator.calculate(expression2));
        assertEquals("(6.0 - 12.0i, 9.0 - 3.0i, 15.0 + 7.5i)", calculator.calculate(expression3));
    }

    public void testCorrectResult_Scopes(){
        String expression1 = "(20 - 7) * 2";
        String expression2 = "(-2 - 4i) * (6 + 5i)";
        String expression3 = "(-4, 6, 7i) + (-(-4, 6, 7i))";

        assertEquals("26.0", calculator.calculate(expression1));
        assertEquals("8.0 - 34.0i", calculator.calculate(expression2));
        assertEquals("0.0", calculator.calculate(expression3));
    }

    public void testCorrectResult_Division(){
        String expression1 = "20 - 7 / 2";
        String expression2 = "(-4 - 2i) / (1 + 2i)";
        String expression3 = "(-4, 6, 7i) / (-0.5)";

        assertEquals("16.5", calculator.calculate(expression1));
        assertEquals("-1.6 + 1.2i", calculator.calculate(expression2));
        assertEquals("(8.0, -12.0, -14.0i)", calculator.calculate(expression3));
    }
}