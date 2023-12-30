package com.expensys.constant;

import com.expensys.model.enums.Category;

import java.util.*;

public class CategoryMappings {
    public static final Set<Category> MAIN_CATEGORIES;
    public static final Set<Category> SUB_CATEGORIES;
    public static final Map<Category, Category> SUB_TO_MAIN_CATEGORY_MAPPINGS;

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
        subCategories.add(Category.VEGETABLES_FRUITS_DAIRY_AND_MEAT);
        subCategories.add(Category.MEDICAL);
        subCategories.add(Category.OUTSIDE_FOOD);
        subCategories.add(Category.LOSE_OF_MONEY);
        subCategories.add(Category.SALON_AND_COSMETICS);
        subCategories.add(Category.TRANSPORT);
        subCategories.add(Category.SHOPPING);
        subCategories.add(Category.ENTERTAINMENT);
        subCategories.add(Category.INVESTMENTS);
        subCategories.add(Category.RENT_AND_OTHER_BILLS);
        subCategories.add(Category.OTHERS);
        SUB_CATEGORIES = Collections.unmodifiableSet(subCategories);

        Map<Category, Category> subToMainCategoryMappings = new HashMap<>();
        subToMainCategoryMappings.put(Category.GROCERIES, Category.ESSENTIAL);
        subToMainCategoryMappings.put(Category.VEGETABLES_FRUITS_DAIRY_AND_MEAT, Category.ESSENTIAL);
        subToMainCategoryMappings.put(Category.MEDICAL, Category.ESSENTIAL);
        subToMainCategoryMappings.put(Category.SALON_AND_COSMETICS, Category.EXPENSE);
        subToMainCategoryMappings.put(Category.TRANSPORT, Category.EXPENSE);
        subToMainCategoryMappings.put(Category.SHOPPING, Category.EXPENSE);
        subToMainCategoryMappings.put(Category.OUTSIDE_FOOD, Category.EXPENSE);
        subToMainCategoryMappings.put(Category.LOSE_OF_MONEY, Category.LOSE_OF_MONEY);
        subToMainCategoryMappings.put(Category.ESSENTIAL, Category.ESSENTIAL);
        subToMainCategoryMappings.put(Category.EXPENSE, Category.EXPENSE);
        subToMainCategoryMappings.put(Category.ENTERTAINMENT, Category.EXPENSE);
        subToMainCategoryMappings.put(Category.RENT_AND_OTHER_BILLS, Category.EXPENSE);
        subToMainCategoryMappings.put(Category.INVESTMENTS, Category.ESSENTIAL);
        subToMainCategoryMappings.put(Category.OTHERS, Category.EXPENSE);
        SUB_TO_MAIN_CATEGORY_MAPPINGS = Collections.unmodifiableMap(subToMainCategoryMappings);
    }
}
