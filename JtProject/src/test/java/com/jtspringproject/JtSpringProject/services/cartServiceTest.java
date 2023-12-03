package com.jtspringproject.JtSpringProject.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jtspringproject.JtSpringProject.dao.cartDao;
import com.jtspringproject.JtSpringProject.models.Cart;
import com.jtspringproject.JtSpringProject.models.User;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {cartService.class})
@ExtendWith(SpringExtension.class)
public class cartServiceTest {
    @MockBean
    private cartDao cartDao;

    @Autowired
    private cartService cartService;

    /**
     * Method under test: {@link cartService#addCart(Cart)}
     */
    @Test
    void testAddCart() {
        User customer = new User();
        customer.setAddress("42 Main St");
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setPassword("iloveyou");
        customer.setRole("Role");
        customer.setUsername("janedoe");

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setId(1);
        cart.setProducts(new ArrayList<>());
        when(cartDao.addCart(Mockito.<Cart>any())).thenReturn(cart);

        User customer2 = new User();
        customer2.setAddress("42 Main St");
        customer2.setEmail("jane.doe@example.org");
        customer2.setId(1);
        customer2.setPassword("iloveyou");
        customer2.setRole("Role");
        customer2.setUsername("janedoe");

        Cart cart2 = new Cart();
        cart2.setCustomer(customer2);
        cart2.setId(1);
        cart2.setProducts(new ArrayList<>());
        Cart actualAddCartResult = cartService.addCart(cart2);
        verify(cartDao).addCart(Mockito.<Cart>any());
        assertSame(cart, actualAddCartResult);
    }

    /**
     * Method under test: {@link cartService#getCarts()}
     */
    @Test
    void testGetCarts() {
        ArrayList<Cart> cartList = new ArrayList<>();
        when(cartDao.getCarts()).thenReturn(cartList);
        List<Cart> actualCarts = cartService.getCarts();
        verify(cartDao).getCarts();
        assertTrue(actualCarts.isEmpty());
        assertSame(cartList, actualCarts);
    }

    /**
     * Method under test: {@link cartService#updateCart(Cart)}
     */
    @Test
    void testUpdateCart() {
        doNothing().when(cartDao).updateCart(Mockito.<Cart>any());

        User customer = new User();
        customer.setAddress("42 Main St");
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setPassword("iloveyou");
        customer.setRole("Role");
        customer.setUsername("janedoe");

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setId(1);
        cart.setProducts(new ArrayList<>());
        cartService.updateCart(cart);
        verify(cartDao).updateCart(Mockito.<Cart>any());
    }

    /**
     * Method under test: {@link cartService#deleteCart(Cart)}
     */
    @Test
    void testDeleteCart() {
        doNothing().when(cartDao).deleteCart(Mockito.<Cart>any());

        User customer = new User();
        customer.setAddress("42 Main St");
        customer.setEmail("jane.doe@example.org");
        customer.setId(1);
        customer.setPassword("iloveyou");
        customer.setRole("Role");
        customer.setUsername("janedoe");

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setId(1);
        cart.setProducts(new ArrayList<>());
        cartService.deleteCart(cart);
        verify(cartDao).deleteCart(Mockito.<Cart>any());
    }
}
