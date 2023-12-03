package com.jtspringproject.JtSpringProject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jtspringproject.JtSpringProject.dao.productDao;
import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {productService.class})
@ExtendWith(SpringExtension.class)
public class productServiceTest {
    @MockBean
    private productDao productDao;

    @Autowired
    private productService productService;

    /**
     * Method under test: {@link productService#getProducts()}
     */
    @Test
    void testGetProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productDao.getProducts()).thenReturn(productList);
        List<Product> actualProducts = productService.getProducts();
        verify(productDao).getProducts();
        assertTrue(actualProducts.isEmpty());
        assertSame(productList, actualProducts);
    }

    /**
     * Method under test: {@link productService#addProduct(Product)}
     */
    @Test
    void testAddProduct() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("Image");
        product.setName("Name");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);
        when(productDao.addProduct(Mockito.<Product>any())).thenReturn(product);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");

        Product product2 = new Product();
        product2.setCategory(category2);
        product2.setDescription("The characteristics of someone or something");
        product2.setId(1);
        product2.setImage("Image");
        product2.setName("Name");
        product2.setPrice(1);
        product2.setQuantity(1);
        product2.setWeight(3);
        Product actualAddProductResult = productService.addProduct(product2);
        verify(productDao).addProduct(Mockito.<Product>any());
        assertSame(product, actualAddProductResult);
    }

    /**
     * Method under test: {@link productService#getProduct(int)}
     */
    @Test
    void testGetProduct() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("Image");
        product.setName("Name");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);
        when(productDao.getProduct(anyInt())).thenReturn(product);
        Product actualProduct = productService.getProduct(1);
        verify(productDao).getProduct(anyInt());
        assertSame(product, actualProduct);
    }

    /**
     * Method under test: {@link productService#updateProduct(int, Product)}
     */
    @Test
    void testUpdateProduct() {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("Image");
        product.setName("Name");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);
        when(productDao.updateProduct(Mockito.<Product>any())).thenReturn(product);

        Category category2 = new Category();
        category2.setId(1);
        category2.setName("Name");

        Product product2 = new Product();
        product2.setCategory(category2);
        product2.setDescription("The characteristics of someone or something");
        product2.setId(1);
        product2.setImage("Image");
        product2.setName("Name");
        product2.setPrice(1);
        product2.setQuantity(1);
        product2.setWeight(3);
        Product actualUpdateProductResult = productService.updateProduct(1, product2);
        verify(productDao).updateProduct(Mockito.<Product>any());
        assertEquals(1, product2.getId());
        assertSame(product, actualUpdateProductResult);
    }

    /**
     * Method under test:  {@link productService#deleteProduct(int)}
     */
    @Test
    void testDeleteProduct() {
        when(productDao.deletProduct(anyInt())).thenReturn(true);
        boolean actualDeleteProductResult = productService.deleteProduct(1);
        verify(productDao).deletProduct(anyInt());
        assertTrue(actualDeleteProductResult);
    }

    /**
     * Method under test:  {@link productService#deleteProduct(int)}
     */
    @Test
    void testDeleteProduct2() {
        when(productDao.deletProduct(anyInt())).thenReturn(false);
        boolean actualDeleteProductResult = productService.deleteProduct(1);
        verify(productDao).deletProduct(anyInt());
        assertFalse(actualDeleteProductResult);
    }
}
