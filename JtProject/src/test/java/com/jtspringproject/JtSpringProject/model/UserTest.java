package com.jtspringproject.JtSpringProject.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#setAddress(String)}
     *   <li>{@link User#setEmail(String)}
     *   <li>{@link User#setId(int)}
     *   <li>{@link User#setPassword(String)}
     *   <li>{@link User#setRole(String)}
     *   <li>{@link User#setUsername(String)}
     *   <li>{@link User#getAddress()}
     *   <li>{@link User#getEmail()}
     *   <li>{@link User#getId()}
     *   <li>{@link User#getPassword()}
     *   <li>{@link User#getRole()}
     *   <li>{@link User#getUsername()}
     * </ul>
     */
    @Test
    void testSetAddress() {
        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("janedoe");
        String actualAddress = user.getAddress();
        String actualEmail = user.getEmail();
        int actualId = user.getId();
        String actualPassword = user.getPassword();
        String actualRole = user.getRole();
        assertEquals("42 Main St", actualAddress);
        assertEquals("Role", actualRole);
        assertEquals("iloveyou", actualPassword);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals("janedoe", user.getUsername());
        assertEquals(1, actualId);
    }
}
