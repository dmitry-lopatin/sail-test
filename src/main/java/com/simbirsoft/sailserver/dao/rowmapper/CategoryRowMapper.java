package com.simbirsoft.sailserver.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.simbirsoft.sailserver.entity.ProductCategory;

public class CategoryRowMapper implements RowMapper<ProductCategory> {

    public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId((rs.getInt("categoryId")));
        productCategory.setCategoryName((rs.getString("categoryname")));
        productCategory.setDescription((rs.getString("description")));
        return productCategory;
    }
}
