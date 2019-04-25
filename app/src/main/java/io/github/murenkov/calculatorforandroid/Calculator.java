package io.github.murenkov.calculatorforandroid;

import java.util.Stack;

public class Calculator {

    private static final String PATTERN_OPERATION = "[*/+-^%]";

    // TODO: Add support of all kinds of brackets.
    public static boolean areBracketsBalanced(String inputString) {
        Stack<Character> stack = new Stack<>();
        try {
            for (int i = 0; i < inputString.length(); i++) {
                char token = inputString.charAt(i);
                if (token == '(') {
                    stack.push(token);
                } else if (!stack.empty() && token == ')') {
                    stack.pop();
                } else if (stack.empty() && token == ')') {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stack.empty();
    }

    // TODO: Add functions and constants support. (functions pushes onto stack!)
    public static String convertToPostfix(String inputStatement) {
        inputStatement = normalizeStatement(inputStatement);
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder outputStatement = new StringBuilder();
        int i = 0;
        while (i < inputStatement.length()) {
            String token = String.valueOf(inputStatement.charAt(i));
            if (token.matches("[0-9.Ee]")) {
                outputStatement.append(token);
            } else if (token.equals("(")) {
                if (i > 0 && String.valueOf(inputStatement.charAt(i - 1)).matches("[0-9)]")) {
                    outputStatement.append(" ");
                    stack.push(new StringBuilder("*"));
                }
                stack.push(new StringBuilder(token));
            } else if (token.equals(")")) {
                if (!stack.empty()) {
                    while (!stack.peek().toString().equals("(")) {
                        outputStatement.append(" ").append(stack.pop()).append(" ");
                    }
                    stack.pop();
                }
            } else if (token.matches(PATTERN_OPERATION)) {
                while (!stack.empty() && stack.peek().toString().matches(PATTERN_OPERATION) &&
                        ((!isRightAssociative(token) &&
                                operationPrecedence(token) <= operationPrecedence(stack.peek().toString())) ||
                                (isRightAssociative(token) &&
                                        operationPrecedence(token) < operationPrecedence(stack.peek().toString())))) {
                    outputStatement.append(" ").append(stack.pop()).append(" ");
                }
                if (i == 0 || String.valueOf(inputStatement.charAt(i - 1)).matches("[(]")) {
                    if (token.equals("-")) {
                        outputStatement.append("0");
                    }
                    if (token.equals("+")) {
                        i++;
                        continue;
                    }
                }
                stack.push(new StringBuilder(token));
                outputStatement.append(" ");
            }
            // TODO: Else throw exception, that statement is invalid.
            i++;
        }
        while (!stack.empty()) {
            outputStatement.append(" ").append(stack.pop()).append(" ");
        }
        return deleteMultipleSpaces(deleteBorderSpaces(outputStatement.toString()));
    }

    public static String normalizeStatement(String inputString) {
        return inputString.replaceAll("×", "*").replaceAll("÷", "/").replaceAll(",", ".");
    }

    private static boolean isRightAssociative(String operation) {
        return operation.matches("[\\^]");
    }

    // TODO: Add more operations.
    private static int operationPrecedence(String operation) {
        try {
            int precedence;
            switch (operation) {
                case "(":
                    precedence = 0;
                    break;
                case ")":
                    precedence = 1;
                    break;
                case "+":
                case "-":
                    precedence = 2;
                    break;
                case "*":
                case "/":
                    precedence = 3;
                    break;
                case "^":
                    precedence = 4;
                    break;
                default:
                    throw new Exception("Unknown operation" + " \"" + operation + "\"!");
            }
            return precedence;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String deleteMultipleSpaces(String inputString) {
        StringBuilder outputString = new StringBuilder(inputString);
        outputString = new StringBuilder(outputString.toString().replaceAll(" +", " "));
        return outputString.toString();
    }

    public static String deleteBorderSpaces(String inputString) {
        StringBuilder outputString = new StringBuilder(inputString);
        if (!inputString.isEmpty()) {
            int i = 0;
            while (outputString.charAt(i) == ' ') {
                outputString.deleteCharAt(i);
            }
            i = outputString.length() - 1;
            while (outputString.charAt(i) == ' ') {
                outputString.deleteCharAt(i);
                i = outputString.length() - 1;
            }
        } else {
            outputString.append(inputString);
        }
        return outputString.toString();
    }

    public static String deleteAllSpaces(String inputString) {
        StringBuilder outputString = new StringBuilder(inputString);
        for (int i = 0; i < outputString.length(); i++) {
            if (outputString.charAt(i) == ' ') {
                outputString.deleteCharAt(i);
                i--;
            }
        }
        return outputString.toString();
    }

    // TODO: Add functions and constants support.
    public static double calculatePostfix(String inputStatement) {
        Stack<Double> stack = new Stack<>();
        for (String token : inputStatement.split(" ")) {
                /*
                It is already known that the operator takes n arguments.
                If there are fewer than n values on the stack
                    (Error) The user has not input sufficient values in the expression.
                Else, Pop the top n values from the stack.
                Evaluate the operator, with the values as arguments.
                Push the returned results, if any, back onto the stack.
                */
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    stack.push(1 / stack.pop() * stack.pop());
                    break;
                case "^":
                    double a = stack.pop(), b = stack.pop();
                    stack.push(Math.pow(b, a));
                    break;
                default:
                    stack.push(Double.valueOf(token));
            }
        }
        /*if (stack.size() == 1) {
            return stack.peek();
        } else {
            // TODO: Throw the exception.
        }*/
        return stack.peek();
    }
}
