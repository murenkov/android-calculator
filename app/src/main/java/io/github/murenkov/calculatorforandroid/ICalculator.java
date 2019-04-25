package io.github.murenkov.calculatorforandroid;

public interface ICalculator {

    boolean isBracketsBalanced(String inputString);

    String deleteSpaces(String inputString, int flag);

    String convertToPostfix(String inputStatement);

    double calculatePostfix(String inputStatement);

    int operationPrecedence(String operator);

    boolean isRightAssociative(String operator);
}
