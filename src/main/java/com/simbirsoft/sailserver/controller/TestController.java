package com.simbirsoft.sailserver.controller;

import javafx.geometry.Pos;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simbirsoft.sailserver.dao.PostgresDAO;
import com.simbirsoft.sailserver.entity.DAOResponce;

@Controller
public class TestController {

    @RequestMapping(value = "/getAll" , method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<DAOResponce> getAllData() {
        PostgresDAO postgresDAO= new PostgresDAO();
        List<DAOResponce> responces = postgresDAO.getAllData();
        return responces;
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
