package com.expensys.model.enums;

public enum Category {
    MAIN("main"),
    SUB("sub"),
    OUTSIDE_FOOD("outside food"),
    MEDICAL("medical"),
    SHOPPING("shopping"),
    SALON("salon"),
    TRANSPORT("transport"),
    GROCERIES("groceries"),
    VEGETABLES_AND_FRUITS("vegetables and fruits"),
    LOSE_OF_MONEY("lose of money"),
    ESSENTIALS("essentials"),
    EXPENSES("expenses"),
    OTHERS("others");

    private String cat;

    Category(String cat) {
        this.cat = cat;
    }

    public String getCat() {
        return cat;
    }
}
