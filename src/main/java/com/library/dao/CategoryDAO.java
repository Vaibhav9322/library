package com.library.dao;

import com.library.entity.Category;
import com.library.util.FileUtil;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static final String CATEGORIES_FILE = "categories.txt";
    
    public boolean addCategory(Category category) {
        category.setCategoryId(getNextId());
        String line = categoryToString(category);
        FileUtil.appendToFile(CATEGORIES_FILE, line);
        return true;
    }
    
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        List<String> lines = FileUtil.readFile(CATEGORIES_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                categories.add(stringToCategory(line));
            }
        }
        return categories;
    }
    
    public Category getCategoryById(int categoryId) {
        List<String> lines = FileUtil.readFile(CATEGORIES_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Category category = stringToCategory(line);
                if (category.getCategoryId() == categoryId) {
                    return category;
                }
            }
        }
        return null;
    }
    
    private String categoryToString(Category category) {
        return category.getCategoryId() + "|" + category.getCategoryName() + "|" + 
               category.getDescription();
    }
    
    private Category stringToCategory(String line) {
        String[] parts = line.split("\\|");
        Category category = new Category();
        category.setCategoryId(Integer.parseInt(parts[0]));
        category.setCategoryName(parts[1]);
        category.setDescription(parts[2]);
        return category;
    }
    
    private int getNextId() {
        List<String> lines = FileUtil.readFile(CATEGORIES_FILE);
        int maxId = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                if (id > maxId) maxId = id;
            }
        }
        return maxId + 1;
    }
}