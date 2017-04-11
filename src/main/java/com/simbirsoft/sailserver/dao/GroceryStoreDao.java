package com.simbirsoft.sailserver.dao;

import java.util.List;

public interface GroceryStoreDao {

    List<String> getAddressesByCategoryList(List<String> categoryList);

    List<String> getCategoriesByAddressList(List<String> addressList);

    double getSumByAddressListAndCategoryList(List<String> addressList, List<String> categoryList);
}
