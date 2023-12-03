package com.jtspringproject.JtSpringProject.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jtspringproject.JtSpringProject.dao.categoryDao;
import com.jtspringproject.JtSpringProject.models.Category;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {categoryService.class})
@ExtendWith(SpringExtension.class)
public class categoryServiceTest {
    @MockBean
    private categoryDao categoryDao;

    @Autowired
    private categoryService categoryService;

    /**
     * Method under test: {@link categoryService#addCategory(String)}
     */
    @Test
    void testAddCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        when(categoryDao.addCategory(Mockito.<String>any())).thenReturn(category);
        Category actualAddCategoryResult = categoryService.addCategory("Name");
        verify(categoryDao).addCategory(Mockito.<String>any());
        assertSame(category, actualAddCategoryResult);
    }

    /**
     * Method under test: {@link categoryService#getCategories()}
     */
    @Test
    void testGetCategories() {
        ArrayList<Category> categoryList = new ArrayList<>();
        when(categoryDao.getCategories()).thenReturn(categoryList);
        List<Category> actualCategories = categoryService.getCategories();
        verify(categoryDao).getCategories();
        assertTrue(actualCategories.isEmpty());
        assertSame(categoryList, actualCategories);
    }

    /**
     * Method under test:  {@link categoryService#deleteCategory(int)}
     */
    @Test
    void testDeleteCategory() {
        when(categoryDao.deletCategory(anyInt())).thenReturn(true);
        Boolean actualDeleteCategoryResult = categoryService.deleteCategory(1);
        verify(categoryDao).deletCategory(anyInt());
        assertTrue(actualDeleteCategoryResult);
    }

    /**
     * Method under test:  {@link categoryService#deleteCategory(int)}
     */
    @Test
    void testDeleteCategory2() {
        when(categoryDao.deletCategory(anyInt())).thenReturn(false);
        Boolean actualDeleteCategoryResult = categoryService.deleteCategory(1);
        verify(categoryDao).deletCategory(anyInt());
        assertFalse(actualDeleteCategoryResult);
    }

    /**
     * Method under test: {@link categoryService#updateCategory(int, String)}
     */
    @Test
    void testUpdateCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        when(categoryDao.updateCategory(anyInt(), Mockito.<String>any())).thenReturn(category);
        Category actualUpdateCategoryResult = categoryService.updateCategory(1, "Name");
        verify(categoryDao).updateCategory(anyInt(), Mockito.<String>any());
        assertSame(category, actualUpdateCategoryResult);
    }

    /**
     * Method under test: {@link categoryService#getCategory(int)}
     */
    @Test
    void testGetCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        when(categoryDao.getCategory(anyInt())).thenReturn(category);
        Category actualCategory = categoryService.getCategory(1);
        verify(categoryDao).getCategory(anyInt());
        assertSame(category, actualCategory);
    }
}
