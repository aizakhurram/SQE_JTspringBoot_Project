package com.jtspringproject.JtSpringProject.services;

import com.jtspringproject.JtSpringProject.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class userServiceTest {

    @Mock
    private com.jtspringproject.JtSpringProject.dao.userDao userDao;

    @InjectMocks
    private userService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        List<User> userList = new ArrayList<>();

        when(userDao.getAllUser()).thenReturn(userList);

        List<User> retrievedUsers = userService.getUsers();

        assertEquals(userList.size(), retrievedUsers.size());
    }
    @Test
    void testAddUser() {
        // Create a sample user for testing
        User sampleUser = new User();
        User user = new User();
        user.setUsername("lisa");
        user.setPassword("userPass");
        user.setAddress("146 Lahore, Valencia");
        user.setEmail("testuser@gmail.com");
        when(userService.checkUserExists("lisa")).thenReturn(true);
       // when(userService.addUser(user)).thenReturn(user);

        // Mocking the behavior of userDao's saveUser method
        when(userDao.saveUser(user)).thenReturn(user);

        // Call the service method to add a user
        User addedUser = userService.addUser(user);

        // Assert that the added user matches the expected user
        assertEquals(user.getUsername(), addedUser.getUsername());

    }
}