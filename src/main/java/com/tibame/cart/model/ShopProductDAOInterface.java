package com.tibame.cart.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface ShopProductDAOInterface {
	public List<Map<String, Object>> getAll();
	
	public List<Cart> getCartProducts(ArrayList<Cart> cartList);
	
	public Integer getTotalCartPrice(ArrayList<Cart> cartList);
}
