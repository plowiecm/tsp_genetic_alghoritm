package com.company;

public class InvalidInputException extends Throwable {
    public InvalidInputException(String wrongDistanceInGivenFile) {
        super("Invalid input in file, exception message: " + wrongDistanceInGivenFile);
    }
}
