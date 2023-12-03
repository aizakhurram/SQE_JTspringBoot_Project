package com.jtspringproject.JtSpringProject.services;

import com.jtspringproject.JtSpringProject.dao.cartDao;
import com.jtspringproject.JtSpringProject.models.Cart;
import com.jtspringproject.JtSpringProject.models.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
        classes = {cartService.class}
)
@ExtendWith({SpringExtension.class})
class cartServiceTest {
    @MockBean
    private cartDao cartDao;
    @Autowired
    private cartService cartService;

    cartServiceTest() {
    }

    @Test
    void testAddCart() {
        User customer = new User();
        customer.setAddress("42 Main St");
        customer.setEmail("abc@gmail.com");
        customer.setId(1);
        customer.setPassword("abc");
        customer.setRole("Role");
        customer.setUsername("janedoe");
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setId(1);
        cart.setProducts(new ArrayList());
        Mockito.when(this.cartDao.addCart((Cart)Mockito.any())).thenReturn(cart);
        User customer2 = new User();
        customer2.setAddress("42 Main St");
        customer2.setEmail("abc@gmail.com");
        customer2.setId(1);
        customer2.setPassword("pass");
        customer2.setRole("Role");
        customer2.setUsername("janedoe");
        Cart cart2 = new Cart();
        cart2.setCustomer(customer2);
        cart2.setId(1);
        cart2.setProducts(new ArrayList());
        Cart actualAddCartResult = this.cartService.addCart(cart2);
        ((cartDao)Mockito.verify(this.cartDao)).addCart((Cart)Mockito.any());
        Assertions.assertSame(cart, actualAddCartResult);
    }



    @Test
    void testGetCarts() {
        ArrayList<Cart> cartList = new ArrayList();
        Mockito.when(this.cartDao.getCarts()).thenReturn(cartList);
        List<Cart> actualCarts = this.cartService.getCarts();
        ((cartDao)Mockito.verify(this.cartDao)).getCarts();
        Assertions.assertTrue(actualCarts.isEmpty());
        Assertions.assertSame(cartList, actualCarts);
    }

    @Test
    void testUpdateCart() {
        ((cartDao)Mockito.doNothing().when(this.cartDao)).updateCart((Cart)Mockito.any());
        User customer = new User();
        customer.setAddress("42 Main St");
        customer.setEmail("abc@gmail.com");
        customer.setId(1);
        customer.setPassword("hello");
        customer.setRole("Role");
        customer.setUsername("abc");
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setId(1);
        cart.setProducts(new ArrayList());
        this.cartService.updateCart(cart);
        ((cartDao)Mockito.verify(this.cartDao)).updateCart((Cart)Mockito.any());
    }

    @Test
    void testDeleteCart() {
        ((cartDao)Mockito.doNothing().when(this.cartDao)).deleteCart((Cart)Mockito.any());
        User customer = new User();
        customer.setAddress("42 Main St");
        customer.setEmail("abc@gmail.com");
        customer.setId(1);
        customer.setPassword("hello");
        customer.setRole("Role");
        customer.setUsername("abc");
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setId(1);
        cart.setProducts(new ArrayList());
        this.cartService.deleteCart(cart);
        ((cartDao)Mockito.verify(this.cartDao)).deleteCart((Cart)Mockito.any());
    }
