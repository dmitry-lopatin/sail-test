package com.simbirsoft.sailserver.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.simbirsoft.sailserver.entity.DAOResponce;
import com.sun.tracing.dtrace.Attributes;

@Component("postgresDAO")
public class PostgresDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<DAOResponce> getAllData () {
        String sql = "SELECT * FROM sampledb.logs";

        return jdbcTemplate.query(sql, new ResRowMapper());
    }
}
