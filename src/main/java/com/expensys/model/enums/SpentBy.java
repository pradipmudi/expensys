package com.expensys.model.enums;

public enum SpentBy {
    ALL("all"),
    USER("user");

    private String spentBy;

    SpentBy(String spentBy) {
        this.spentBy = spentBy;
    }

    public String getSpentBy() {
        return spentBy;
    }
}
