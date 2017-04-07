package com.simbirsoft.sailserver.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.tree.RowMapper;

import com.simbirsoft.sailserver.entity.DAOResponce;

public class ResRowMapper implements org.springframework.jdbc.core.RowMapper<DAOResponce> {

    public DAOResponce mapRow(ResultSet rs, int rowNum) throws SQLException {
        DAOResponce responce = new DAOResponce();
        responce.setName(String.valueOf(rs.getInt("id")));
        responce.setValue(rs.getString("type"));
        return responce;
    }
}
