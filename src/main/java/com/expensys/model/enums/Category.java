package com.expensys.model.enums;

public enum Category {
    MAIN("main"),
    SUB("sub"),
    OUTSIDE_FOOD("outside food"),
    MEDICAL("medical"),
    SHOPPING("shopping"),
    SALON_AND_COSMETICS("salon and cosmetics"),
    TRANSPORT("transport"),
    GROCERIES("groceries"),
    VEGETABLES_FRUITS_DAIRY_AND_MEAT("vegetables, fruits, dairy and meat"),
    LOSE_OF_MONEY("lose of money"),
    ESSENTIAL("essential"),
    EXPENSE("expense"),
    ENTERTAINMENT("entertainment"),
    RENT_AND_OTHER_BILLS("rent and other bills"),
    OTHERS("others");

    private String cat;

    Category(String cat) {
        this.cat = cat;
    }

    public String getCat() {
        return cat;
    }
}
