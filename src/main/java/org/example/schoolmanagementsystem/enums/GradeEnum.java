package org.example.schoolmanagementsystem.enums;

public enum GradeEnum {
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private final int value;

    GradeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
