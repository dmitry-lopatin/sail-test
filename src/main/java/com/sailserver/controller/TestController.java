package com.sailserver.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sailserver.dao.PostgresDAO;

@Controller
public class TestController {

    @Autowired
    private PostgresDAO postgresDAO;

    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getAllCategories() {
        return postgresDAO.getAllCategories();
    }

    @RequestMapping(value = "/getAllAddresses", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getAllAddresses() {
        return postgresDAO.getAllAddresses();
    }

    @RequestMapping(value = "/getCategoriesByAddressList", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getCategoriesByAddressList(String[] addresses) {
        return postgresDAO.getCategoriesByAddressList(Arrays.asList(addresses));
    }

    @RequestMapping(value = "/getSumByAddressesAndCategories", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Double getSumByAddressListAndCategoryList(String[] addresses, String[] categories) {
        List<String> addressList = addresses == null ? getAllAddresses() : Arrays.asList(addresses);
        List<String> categoryList = categories == null ? getAllCategories() : Arrays.asList(categories);
        return postgresDAO.getSumByAddressListAndCategoryList(addressList, categoryList);
    }
}
