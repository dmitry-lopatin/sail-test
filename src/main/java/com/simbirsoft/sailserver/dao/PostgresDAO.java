package com.simbirsoft.sailserver.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.simbirsoft.sailserver.dao.rowmapper.CategoryRowMapper;
import com.simbirsoft.sailserver.dao.rowmapper.OrderRowMapper;
import com.simbirsoft.sailserver.dao.rowmapper.ProductRowMapper;
import com.simbirsoft.sailserver.entity.Order;
import com.simbirsoft.sailserver.entity.Product;
import com.simbirsoft.sailserver.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> getAllCategories() {
        String sql = "SELECT categoryname FROM public.category";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM public.product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM public.order";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    public List<String> getAllAddresses() {
        String sql = "SELECT DISTINCT shopaddress FROM public.order";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<String> getCategoriesByAddress(String address) {
        String sql = "SELECT DISTINCT c.categoryname from public.category c\n" +
                "JOIN public.product p " +
                "ON c.categoryid = p.categoryid " +
                "JOIN public.orderdetail od " +
                "ON p.productid = od.productid " +
                "JOIN public.order o " +
                "ON od.orderid = o.orderid WHERE o.shopaddress ='"+address+"'";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<String> getCategoriesByAddressList(List<String> addressList) {
        Set<String> resultSet = new HashSet<>();
        addressList.stream().forEach(address ->
                    resultSet.addAll(jdbcTemplate.queryForList(
                            "SELECT DISTINCT c.categoryname from public.category c\n" +
                                    "JOIN public.product p " +
                                    "ON c.categoryid = p.categoryid " +
                                    "JOIN public.orderdetail od " +
                                    "ON p.productid = od.productid " +
                                    "JOIN public.order o " +
                                    "ON od.orderid = o.orderid WHERE o.shopaddress ='" + address + "'", String.class))
            );
        return new ArrayList<>(resultSet);
    }

    public List<String> getAddressesByCategoryList(List<String> categoryList) {
        Set<String> resultSet = new HashSet<>();
        categoryList.stream().forEach(category ->
                    resultSet.addAll(jdbcTemplate.queryForList(
                            "SELECT DISTINCT o.shopaddress FROM public.order o\n" +
                                    "JOIN public.orderdetail od \n" +
                                    "ON o.orderid = od.orderid\n" +
                                    "JOIN public.product p\n" +
                                    "ON od.productid = p.productid\n" +
                                    "JOIN public.category c\n" +
                                    "ON p.categoryid = c.categoryid WHERE c.categoryname = '"+category+"'", String.class))
                    );
        return new ArrayList<>(resultSet);
    }

    public double getSumByAddressListAndCategoryList(List<String> addressList, List<String> categoryList) {
        double sum = 0;
        List<Double> partialSumList = new ArrayList<>();
        addressList.stream().forEach(address ->
            categoryList.stream().forEach(category ->
                    partialSumList.add(jdbcTemplate.queryForObject(
                            "SELECT sum(od.amount * p.price) subprice FROM public.orderdetail od " +
                                    "JOIN public.product p " +
                                    "ON od.productid = p.productid " +
                                    "JOIN public.order o " +
                                    "ON od.orderid = o.orderid " +
                                    "JOIN public.category c " +
                                    "ON p.categoryid = c.categoryid WHERE o.shopaddress = '"+address+"'AND c.categoryname = '"+category+"'", Double.class))
                    )
        );
        for (Object d : partialSumList) {
            if (d != null) {
                sum += (Double) d;
            }
        }
        return sum;
    }

    @PostConstruct
    public void initDataBase() {
        List<String> queryList = new ArrayList<>();

        queryList.add("CREATE TABLE IF NOT EXISTS public.category ( " +
                "categoryid serial NOT NULL, " +
                "categoryname character varying(15) NOT NULL, " +
                "description character varying(200) NOT NULL, " +
                "CONSTRAINT pk_category PRIMARY KEY (categoryid))");
        queryList.add("INSERT INTO public.category VALUES (1, 'meat', '') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.category VALUES (2, 'bakery', '') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.category VALUES (3, 'dairy', '') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.category VALUES (4, 'species', '') ON CONFLICT DO NOTHING");

        queryList.add("CREATE TABLE IF NOT EXISTS public.product ( " +
                "productid serial NOT NULL, " +
                "productname character varying(15) NOT NULL, " +
                "categoryid integer NOT NULL, " +
                "price numeric(16,4) NOT NULL DEFAULT 0, " +
                "CONSTRAINT pk_product PRIMARY KEY (productid)," +
                "CONSTRAINT fk_product_category FOREIGN KEY (categoryid) REFERENCES public.category (categoryid) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION," +
                "CONSTRAINT chk_product_price CHECK (price >= 0::numeric))");
        queryList.add("INSERT INTO public.product VALUES (1, 'pork', 1, 230) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.product VALUES (2, 'beef', 1, 330) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.product VALUES (3, 'bacon', 1, 500) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.product VALUES (4, 'milk', 3, 35) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.product VALUES (5, 'cheese', 3, 300) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.product VALUES (6, 'bread', 2, 20) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.product VALUES (7, 'cake', 2, 100) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.product VALUES (8, 'chili', 4, 50) ON CONFLICT DO NOTHING");

        queryList.add("CREATE TABLE IF NOT EXISTS public.order (" +
                "orderid serial NOT NULL, " +
                "orderdate date NOT NULL, " +
                "shopaddress character varying(30) NOT NULL, " +
                "CONSTRAINT pk_order PRIMARY KEY (orderid))");
        queryList.add("INSERT INTO public.order VALUES (1, '2017-04-01', 'Pushkina-10') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (2, '2017-04-01', 'Pushkina-10') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (3, '2017-04-01', 'Lenina-46') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (4, '2017-04-03', 'Lenina-46') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (5, '2017-04-03', 'Lenina-46') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (6, '2017-04-04', 'Goncharova-22') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (7, '2017-04-04', 'Lenina-46') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (8, '2017-04-05', 'Goncharova-22') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (9, '2017-04-06', 'Lenina-46') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (10, '2017-04-06', 'Lenina-46') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (11, '2017-04-06', 'Goncharova-22') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (12, '2017-04-06', 'Pushkina-10') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (13, '2017-04-06', 'Lenina-46') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (14, '2017-04-06', 'Pushkina-10') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (15, '2017-04-06', 'Narimanova-1') ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.order VALUES (16, '2017-04-07', 'Baumana-5') ON CONFLICT DO NOTHING");

        queryList.add("CREATE TABLE IF NOT EXISTS public.orderdetail (" +
                "detailid serial NOT NULL, " +
                "orderid integer NOT NULL, " +
                "productid integer NOT NULL, " +
                "amount integer NOT NULL DEFAULT 0, " +
                "CONSTRAINT pk_orderdetail PRIMARY KEY (detailid), " +
                "CONSTRAINT fk_order_id FOREIGN KEY (orderid) REFERENCES public.order (orderid) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION, " +
                "CONSTRAINT fk_product_id FOREIGN KEY (productid) REFERENCES public.product (productid) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION, " +
                "CONSTRAINT chk_amount CHECK (amount >=0::integer))");
        queryList.add("INSERT INTO public.orderdetail VALUES (1, 1, 1, 2) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (2, 1, 4, 1) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (3, 2, 3, 3) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (4, 2, 5, 4) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (5, 2, 4, 2) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (6, 3, 1, 1) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (7, 3, 2, 2) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (8, 3, 6, 3) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (9, 4, 7, 1) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (10, 4, 1, 4) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (11, 5, 2, 3) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (12, 6, 6, 1) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (13, 6, 7, 2) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (14, 7, 1, 3) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (15, 8, 1, 4) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (16, 8, 5, 5) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (17, 8, 7, 1) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (18, 9, 2, 3) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (19, 9, 4, 1) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (20, 10, 5, 3) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (21, 11, 6, 1) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (22, 12, 7, 10) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (23, 12, 6, 1) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (24, 13, 4, 3) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (25, 13, 1, 5) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (26, 14, 2, 2) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (27, 15, 1, 2) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (28, 15, 4, 2) ON CONFLICT DO NOTHING");
        queryList.add("INSERT INTO public.orderdetail VALUES (29, 16, 8, 7) ON CONFLICT DO NOTHING");


        queryList.stream().forEach(query -> jdbcTemplate.update(query));
    }
}
