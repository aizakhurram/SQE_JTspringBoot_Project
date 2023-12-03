//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.jtspringproject.JtSpringProject.model;

import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {
    ProductTest() {
    }

    @Test
    void testSetCategory() {
        Product product = new Product();
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        product.setCategory(category);
        product.setDescription("some description");
        product.setId(1);
        product.setImage("Image");
        product.setName("Name");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);
        Category actualCategory = product.getCategory();
        String actualDescription = product.getDescription();
        int actualId = product.getId();
        String actualImage = product.getImage();
        String actualName = product.getName();
        int actualPrice = product.getPrice();
        int actualQuantity = product.getQuantity();
        Assertions.assertEquals("Image", actualImage);
        Assertions.assertEquals("Name", actualName);
        Assertions.assertEquals("some description", actualDescription);
        Assertions.assertEquals(1, actualId);
        Assertions.assertEquals(1, actualPrice);
        Assertions.assertEquals(1, actualQuantity);
        Assertions.assertEquals(3, product.getWeight());
        Assertions.assertSame(category, actualCategory);
    }
    @Test
    void testSetID(){
        Product p= new Product();
        p.setId(1);
        int id=p.getId();
        Assertions.assertEquals(1,id);
    }
    @Test
    void testSetName(){
        Product p= new Product();
        p.setName("product");
        String name=p.getName();
        Assertions.assertEquals("product",name);
    }
}
