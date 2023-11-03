package com.expensys.model.response;

import com.expensys.model.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class ReportInfo {
    private Category subCategory;
    private Category mainCategory;  // Ignore in response when null
    private Double spent;
    private String spentBy;

    ReportInfo(){}

    public Category getSubCategory() {
        return subCategory;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public Double getSpent() {
        return spent;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public void setSubCategory(Category subCategory) {
        this.subCategory = subCategory;
    }

    public void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }

    @JsonIgnore
    public String getMainCategorySpentByKey(){ return mainCategory +"_"+spentBy;}
    @JsonIgnore
    public String getSubCategorySpentByKey(){ return subCategory +"_"+spentBy;}

    public static class Builder {
        private ReportInfo reportInfo;

        public Builder() {
            this.reportInfo = new ReportInfo();
        }

        public Builder subCategory(Category mainCategory) {
            reportInfo.setSubCategory(mainCategory);
            return this;
        }

        public Builder mainCategory(Category subCategory) {
            reportInfo.setMainCategory(subCategory);
            return this;
        }

        public Builder spent(Double spent) {
            reportInfo.setSpent(spent);
            return this;
        }

        public Builder spentBy(String spentBy) {
            reportInfo.setSpentBy(spentBy.toUpperCase());
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportInfo)) return false;
        ReportInfo that = (ReportInfo) o;
        return mainCategory == that.mainCategory && spentBy.equalsIgnoreCase(that.spentBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainCategory, spentBy);
    }

    @Override
    public String toString() {
        return "ReportInfo{" +
                "mainCategory=" + subCategory +
                ", subCategory='" + mainCategory + '\'' +
                ", spent=" + spent +
                ", spentBy='" + spentBy + '\'' +
                '}';
    }
}
