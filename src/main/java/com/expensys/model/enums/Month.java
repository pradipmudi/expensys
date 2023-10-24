package com.expensys.model.enums;

public enum Month {
    ALL("0"),
    JANUARY("1"),
    FEBRUARY("2"),
    MARCH("3"),
    APRIL("4"),
    MAY("5"),
    JUNE("6"),
    JULY("7"),
    AUGUST("8"),
    SEPTEMBER("9"),
    OCTOBER("10"),
    NOVEMBER("11"),
    DECEMBER("12");

    private String month;
    Month(String month) {
        this.month = month;
    }

    public String getMonthValue() {
        return month;
    }
}
