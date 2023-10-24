package com.expensys.model.response;

import com.expensys.model.enums.Category;

public class ReportInfo {
    private Category mainCategory;
    private String subCategory;  // Ignore in response when null
    private Double spent;
    private String user;

    ReportInfo(){}

    public Category getMainCategory() {
        return mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public Double getSpent() {
        return spent;
    }

    public String getUser() {
        return user;
    }

    public void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static class Builder {
        private ReportInfo reportInfo;

        public Builder() {
            this.reportInfo = new ReportInfo();
        }

        public Builder mainCategory(Category mainCategory) {
            reportInfo.setMainCategory(mainCategory);
            return this;
        }

        public Builder subCategory(String subCategory) {
            reportInfo.setSubCategory(subCategory);
            return this;
        }

        public Builder spent(Double spent) {
            reportInfo.setSpent(spent);
            return this;
        }

        public Builder user(String user) {
            reportInfo.setUser(user);
            return this;
        }

        public ReportInfo build() {
            return reportInfo;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "ReportInfo{" +
                "mainCategory=" + mainCategory +
                ", subCategory='" + subCategory + '\'' +
                ", spent=" + spent +
                ", user='" + user + '\'' +
                '}';
    }
}
