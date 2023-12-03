package com.jtspringproject.JtSpringProject.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.jtspringproject.JtSpringProject.models.Cart;
import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;
import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    void testConstructor() {
        Cart actualCart = new Cart();
        User customer = new User();
        customer.setAddress("42 Main St");
        customer.setEmail("abc@gmail.com");
        customer.setId(1);
        customer.setPassword("hello");
        customer.setRole("Role");
        customer.setUsername("abc");
        actualCart.setCustomer(customer);
        actualCart.setId(1);
        ArrayList<Product> products = new ArrayList<>();
        actualCart.setProducts(products);
        User actualCustomer = actualCart.getCustomer();
        int actualId = actualCart.getId();
        assertEquals(1, actualId);
        assertSame(customer, actualCustomer);
        assertSame(products, actualCart.getProducts());
    }

    /**
     {@link Cart#addProduct(Product)}
     */
    @Test
    void testAddProduct() {
        Cart cart = new Cart();

        Category category = new Category();
        category.setId(1);
        category.setName("Name");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("some description");
        product.setId(1);
        product.setImage("Image");
        product.setName("Name");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);
        cart.addProduct(product);
        assertEquals(1, cart.getProducts().size());
    }

    /**
     {@link Cart#removeProduct(Product)}
     */
    @Test
    void testRemoveProduct() {
        Cart cart = new Cart();

        Category category = new Category();
        category.setId(1);
        category.setName("Name");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("Some description");
        product.setId(1);
        product.setImage("Image");
        product.setName("Name");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);
        cart.removeProduct(product);
        assertTrue(cart.getProducts().isEmpty());
    }
}
