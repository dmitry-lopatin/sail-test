package com.simbirsoft.sailserver.dao.rowmapper;

import com.simbirsoft.sailserver.entity.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setOrderId((rs.getInt("orderid")));
        order.setOrderDate((rs.getDate("orderdate")));
        order.setOrderAddress((rs.getString("shopaddress")));
        return order;
    }
}
