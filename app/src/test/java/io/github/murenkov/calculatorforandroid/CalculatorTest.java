package io.github.murenkov.calculatorforandroid;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void isBracketsBalancedTest() throws Exception {
        assertEquals(Calculator.areBracketsBalanced("()()()()()()()"), true);
        assertEquals(Calculator.areBracketsBalanced(")()()()()()("), false);
        assertEquals(Calculator.areBracketsBalanced("(54)()(fuck)()()()()"), true);
        assertEquals(Calculator.areBracketsBalanced("8)"), false);
    }

    @Test
    public void deleteSpacesTest() throws Exception {
        assertEquals(Calculator.deleteAllSpaces("0f  -dfd-c-x0cd-fdf-"), "0f-dfd-c-x0cd-fdf-");
        assertEquals(Calculator.deleteAllSpaces("3 + 4 * 2 / (1 - 5)^2"), "3+4*2/(1-5)^2");
        assertEquals(Calculator.deleteAllSpaces("3 4 2 * 1 5 - 2 ^ / +"), "342*15-2^/+");
        assertEquals(Calculator.deleteAllSpaces("x ^ y / (5 * z) + 10"), "x^y/(5*z)+10");
        assertEquals(Calculator.deleteMultipleSpaces("     "), " ");
        assertEquals(Calculator.deleteMultipleSpaces(""), "");
        assertEquals(Calculator.deleteBorderSpaces(""), "");
    }

    @Test
    public void convertToPostfixTest() throws Exception {
        assertEquals(Calculator.convertToPostfix("1"), "1");
        assertEquals(Calculator.convertToPostfix("+2"), "2");
        assertEquals(Calculator.convertToPostfix("-43"), "0 43 -");
        assertEquals(Calculator.convertToPostfix("2+3"), "2 3 +");
        assertEquals(Calculator.convertToPostfix("3+4*2/(1-5)^2"), "3 4 2 * 1 5 - 2 ^ / +");
        assertEquals(Calculator.convertToPostfix("-(32-23)"), "0 32 23 - -");
        assertEquals(Calculator.convertToPostfix("-(-(-300)-(-21))"), "0 0 0 300 - - 0 21 - - -");
        assertEquals(Calculator.convertToPostfix("-(-3-2)"), "0 0 3 - 2 - -");
        assertEquals(Calculator.convertToPostfix("1^2^3"), "1 2 3 ^ ^");
        assertEquals(Calculator.convertToPostfix("2 ^ (1 / 2)"), "2 1 2 / ^");
        assertEquals(Calculator.convertToPostfix("()"), "");
        assertEquals(Calculator.convertToPostfix("625^(5×10÷100)"), "625 5 10 * 100 / ^");
        assertEquals(Calculator.convertToPostfix("1.5(8+32)"), "1.5 8 32 + *");
        assertEquals(Calculator.convertToPostfix("-(-2)^(-3)"), "0 0 2 - 0 3 - ^ -");
        //assertEquals(Calculator.convertToPostfix("-31*7*(2+74)*sin(pi)+567"), "-31 7 2 74 + pi sin * * * 567 +");

        // assertEquals(Calculator.convertToPostfix("exp(-1/2*x)"), "-1 2 / x * exp");
        // assertEquals(Calculator.convertToPostfix("x^y/(5*z)+10"), "x y ^ 5 z * / 10 +");
    }

    @Test
    public void calculatePostfixTest() throws Exception {
        final double epsilon = 1e-40;
        assertEquals(Calculator.calculatePostfix("1"), 1, epsilon);
        assertEquals(Calculator.calculatePostfix("2"), 2, epsilon);
        assertEquals(Calculator.calculatePostfix("0 43 -"), -43, epsilon);
        assertEquals(Calculator.calculatePostfix("2 3 +"), 5, epsilon);
        assertEquals(Calculator.calculatePostfix("3 4 2 * 1 5 - 2 ^ / +"), 3.5, epsilon);
        assertEquals(Calculator.calculatePostfix("0 32 23 - -"), -9, epsilon);
        assertEquals(Calculator.calculatePostfix("0 0 0 300 - - 0 21 - - -"), -321, epsilon);
        assertEquals(Calculator.calculatePostfix("0 0 3 - 2 - -"), 5, epsilon);
        assertEquals(Calculator.calculatePostfix("1 2 3 ^ ^"), 1, epsilon);
        assertEquals(Calculator.calculatePostfix("2 0.5 ^"), 1.4142135623730950488016887242097, epsilon);
        assertEquals(Calculator.calculatePostfix("1.7976931348623158079372897140530341507993e+308"), 1.7976931348623158079372897140530341507993e+308, epsilon);
        assertEquals(Calculator.calculatePostfix("625 5 10 * 100 / ^"), 25, epsilon);
        assertEquals(Calculator.calculatePostfix("1.5 8 32 + *"), 60, epsilon);
        assertEquals(Calculator.calculatePostfix("0 0 2 - 0 3 - ^ -"), 0.125, epsilon);
        // assertEquals(Calculator.calculatePostfix("-31 7 2 74 + pi sin * * * 567 +"), 567, epsilon);
    }
}
