package com.jtspringproject.JtSpringProject.services;

import com.jtspringproject.JtSpringProject.models.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class cartServiceTest {
    @Mock
    private com.jtspringproject.JtSpringProject.dao.cartDao DaoMock;
    @InjectMocks
    private cartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCart() {
        Cart sampleCart = new Cart();
        sampleCart.setId(1);
        when(cartService.addCart(sampleCart)).thenReturn(sampleCart);

        Cart addedCart = cartService.addCart(sampleCart);

        assertNotNull(addedCart);
    }
}