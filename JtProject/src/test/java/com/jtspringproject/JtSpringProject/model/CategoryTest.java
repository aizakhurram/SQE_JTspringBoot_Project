package com.jtspringproject.JtSpringProject.model;

import com.jtspringproject.JtSpringProject.models.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {
    CategoryTest() {
    }

    @Test
    void testSetId() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        int actualId = category.getId();
        Assertions.assertEquals("Name", category.getName());
        Assertions.assertEquals(1, actualId);
    }
     @Test
    void testSetName() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        String actualName=category.getName();
        Assertions.assertEquals("Name",actualName);
        Assertions.assertEquals(1, category.getId());
    }
}
