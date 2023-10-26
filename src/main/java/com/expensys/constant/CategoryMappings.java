package com.expensys.constant;

import com.expensys.model.enums.Category;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CategoryMappings {
    public static final Set<Category> MAIN_CATEGORIES;
    public static final Set<Category> SUB_CATEGORIES;

    private CategoryMappings(){}

    static {
        Set<Category> mainCategories = new HashSet<>();
        mainCategories.add(Category.ESSENTIAL);
        mainCategories.add(Category.EXPENSE);
        mainCategories.add(Category.OTHERS);
        mainCategories.add(Category.LOSE_OF_MONEY);
        MAIN_CATEGORIES = Collections.unmodifiableSet(mainCategories);

        Set<Category> subCategories = new HashSet<>();
        subCategories.add(Category.GROCERIES);
        subCategories.add(Category.VEGETABLES_AND_FRUITS);
        subCategories.add(Category.MEDICAL);
        subCategories.add(Category.OUTSIDE_FOOD);
        subCategories.add(Category.LOSE_OF_MONEY);
        subCategories.add(Category.SALON);
        subCategories.add(Category.TRANSPORT);
        subCategories.add(Category.SHOPPING);
        subCategories.add(Category.OTHERS);
        SUB_CATEGORIES = Collections.unmodifiableSet(subCategories);
    }
}
