package com.simbirsoft.sailserver.controller;

import com.simbirsoft.sailserver.entity.Order;
import com.simbirsoft.sailserver.entity.Product;
import com.simbirsoft.sailserver.entity.ProductCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simbirsoft.sailserver.dao.PostgresDAO;

@Controller
public class TestController {

    @Autowired
    PostgresDAO postgresDAO;

    @RequestMapping(value = "/getAllCategories" , method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getAllCategories() {
        List<String> response = postgresDAO.getAllCategories();
        return response;
    }
    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Product> getAllProducts() {
        List<Product> response = postgresDAO.getAllProducts();
        return response;
    }
    @RequestMapping(value = "/getAllOrders", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Order> getAllOrders() {
        List<Order> response = postgresDAO.getAllOrders();
        return response;
    }
    @RequestMapping(value = "/getAllAddresses", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getAllAddresses() {
        List<String> response = postgresDAO.getAllAddresses();
        return response;
    }
    @RequestMapping(value = "/getCategoriesByAddress", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getCategoriesByAddress(String address) {
        List<String> response = postgresDAO.getCategoriesByAddress(address);
        return response;
    }

    @RequestMapping(value = "/getCategoriesByAddressList", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getCategoriesByAddressList(String[] addressList) {
        List<String> response = postgresDAO.getCategoriesByAddressList(Arrays.asList(addressList));
        return response;
    }

    @RequestMapping(value = "/getAddressesByCategoryList", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getAddressesByCategoryList(String[] categoryList) {
        List<String> response = postgresDAO.getAddressesByCategoryList(Arrays.asList(categoryList));
        return response;
    }

    @RequestMapping(value = "/getSumByAddressListAndCategoryList", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Double getSumByAddressListAndCategoryList(String[] addresses, String[] categories) {
        List<String> addressList = addresses == null ? getAllAddresses() : Arrays.asList(addresses);
        List<String> categoryList = categories == null ? getAllCategories() : Arrays.asList(categories);
        Double response = postgresDAO.getSumByAddressListAndCategoryList(addressList, categoryList);
        return response;
    }
}
