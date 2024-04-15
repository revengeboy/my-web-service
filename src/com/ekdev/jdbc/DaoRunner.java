package com.ekdev.jdbc;

import com.ekdev.jdbc.dao.ProductDao;
import com.ekdev.jdbc.dao.RoleDao;
import com.ekdev.jdbc.dao.UsersDao;
import com.ekdev.jdbc.entity.Product;
import com.ekdev.jdbc.entity.Role;
import com.ekdev.jdbc.entity.Users;

import java.math.BigDecimal;

public class DaoRunner {

    public static void main(String[] args) {
//        saveProduct();
//        deleteProduct(2L);
//        saveRole();
        deleteRole(2L);
    }

    private static void saveProduct() {
        var productDao = ProductDao.getInstance();
        var product = new Product();
        product.setName("test");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("test desc");
        product.setRating(5.0);

        var testProduct = productDao.save(product);
        System.out.println(testProduct);
    }

    private static void saveRole() {
        var roleDao = RoleDao.getInstance();
        var role = new Role();
        role.setRole("admin");

        var testRole = roleDao.save(role);
        System.out.println(testRole);
    }

    private static void deleteRole(Long id) {
        var roleDao = RoleDao.getInstance();
        var deleteResult = roleDao.delete(id);
        System.out.println(deleteResult);
    }

    private static void deleteProduct(Long id) {
        var productDao = ProductDao.getInstance();
        var deleteResult = productDao.delete(id);
        System.out.println(deleteResult);
    }
}
