package com.simbirsoft.sailserver.dao.rowmapper;

import com.simbirsoft.sailserver.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setProductId((rs.getInt("productid")));
        product.setProductName((rs.getString("productname")));
        product.setCategoryId((rs.getInt("categoryid")));
        product.setPrice((rs.getDouble("price")));
        return product;
    }
}
