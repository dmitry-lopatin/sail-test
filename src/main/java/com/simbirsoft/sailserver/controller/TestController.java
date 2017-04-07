package com.simbirsoft.sailserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simbirsoft.sailserver.entity.DAOResponce;

@Controller
public class TestController {

    @RequestMapping(value = "/getAll" , method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DAOResponce getAllData() {
        DAOResponce daoResponce = new DAOResponce();
        daoResponce.setValue("sdsds");
        daoResponce.setName("sdsdsdsds");
        daoResponce.getOrderlist().add("Test");
        daoResponce.getOrderlist().add("Insert");
        daoResponce.getOrderlist().add("Values");
        return daoResponce;
    }
    @RequestMapping(value = "/getAllDAta" , method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DAOResponce getAllAddresses() {
        DAOResponce responce = new DAOResponce();
        responce.setName("Addresses");
        return responce;
    }
    @RequestMapping(value = "/getCategories" , method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DAOResponce getCategoriesByAdress(String address) {
        DAOResponce responce = new DAOResponce();
        responce.setName("Categories");
        responce.setValue(address);
        return responce;
    }
    @RequestMapping(value = "/getCost" , method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DAOResponce getCost() {
        DAOResponce responce = new DAOResponce();
        responce.setName("Cost");
        return responce;
    }
}
