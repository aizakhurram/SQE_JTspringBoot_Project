package com.jtspringproject.JtSpringProject.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CategoryTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Category#setId(int)}
     *   <li>{@link Category#setName(String)}
     *   <li>{@link Category#getId()}
     *   <li>{@link Category#getName()}
     * </ul>
     */
    @Test
    void testSetId() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        int actualId = category.getId();
        assertEquals("Name", category.getName());
        assertEquals(1, actualId);
    }
}
