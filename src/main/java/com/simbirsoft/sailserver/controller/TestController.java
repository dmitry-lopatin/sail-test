package com.simbirsoft.sailserver.controller;

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

    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getAllCategories() {
        List<String> response = postgresDAO.getAllCategories();
        return response;
    }

    @RequestMapping(value = "/getAllAddresses", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getAllAddresses() {
        List<String> response = postgresDAO.getAllAddresses();
        return response;
    }

    @RequestMapping(value = "/getCategoriesByAddressList", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getCategoriesByAddressList(String[] addresses) {
        List<String> response = postgresDAO.getCategoriesByAddressList(Arrays.asList(addresses));
        return response;
    }

    @RequestMapping(value = "/getAddressesByCategoryList", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getAddressesByCategoryList(String[] categories) {
        List<String> response = postgresDAO.getAddressesByCategoryList(Arrays.asList(categories));
        return response;
    }

    @RequestMapping(value = "/getSumByAddressesAndCategories", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Double getSumByAddressListAndCategoryList(String[] addresses, String[] categories) {
        List<String> addressList = addresses == null ? getAllAddresses() : Arrays.asList(addresses);
        List<String> categoryList = categories == null ? getAllCategories() : Arrays.asList(categories);
        Double response = postgresDAO.getSumByAddressListAndCategoryList(addressList, categoryList);
        return response;
    }
}
