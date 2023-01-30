package com.tibame.cart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.tibame.cart.model.Cart;
import com.tibame.cart.model.ShopProductService;

/**
 * Servlet implementation class ShowCart
 */
@WebServlet("/ShowCart")
public class ShowCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	@Autowired
//	private ShopProductService shopProductService;
	
	//外部Server
//	@Override
//	 public void init() throws ServletException {
//	  ServletContext application = this.getServletContext();
//	  ApplicationContext context = (ApplicationContext)
//	    application.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//	  this.shopProductService = context.getBean("shopProductService", ShopProductService.class);
//	 }
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		Cart cart = (Cart) req.getAttribute("cart");
		HttpSession session = req.getSession();
		ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list"); 
		List<Cart> cartProduct = null;
		if(cart_list != null){
			ShopProductService shopProductService = new ShopProductService();
			cartProduct = shopProductService.getCartProducts(cart_list);
			req.setAttribute("cart_list", cart_list);
			Integer total = shopProductService.getTotalCartPrice(cart_list);
			req.setAttribute("total", total);
		}
		String url = "/front-end/cart/cart.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listAllProductType.jsp
		successView.forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
