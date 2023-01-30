package com.tibame.cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.tibame.cart.model.User;
import com.tibame.cart.model.UserJDBCDAO;
import com.tibame.member.model.MemberDAO;
import com.tibame.member.model.MemberVO;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	@Autowired
//	private UserJDBCDAO userJDBCDAO;
//	@Autowired
//	private ShopProductService shopProductService;

	// 外部Servlet
	@Override 
//	public void init() throws ServletException {
//		ServletContext application = this.getServletContext();
//		ApplicationContext context = (ApplicationContext) application
//				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//		this.shopProductService = context.getBean("shopProductService", ShopProductService.class);
//		this.userJDBCDAO = context.getBean("userJDBCDAO", UserJDBCDAO.class);
//	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.sendRedirect("/front-end/cart/login.jsp"); // login.jsp
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = res.getWriter()) {
			String email = req.getParameter("login-email"); // from login.jsp name=login-email
			String password = req.getParameter("login-password"); // from login.jsp name=login-password

			UserJDBCDAO userJDBCDAO = new UserJDBCDAO();
			MemberVO user = userJDBCDAO.userLogin(email, password);
		    
//			MemberDAO memberDAO = new MemberDAO();
//			MemberVO memberVO  = memberDAO.memberLogin(memberVO);

			Cart cart = (Cart) req.getAttribute("cart");
			HttpSession session = req.getSession();
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart_list");
			List<Cart> cartProduct = null;

			// 已加入購物車 & 消費者登入
			if (user != null && cart_list != null) {
				req.getSession().setAttribute("auth", user);
				ShopProductService shopProductService = new ShopProductService();
				cartProduct = shopProductService.getCartProducts(cart_list);
				req.setAttribute("cart_list", cart_list);
				
				Integer total = shopProductService.getTotalCartPrice(cart_list);
				req.setAttribute("total", total);
				session.setAttribute("memberVO", user);
				
				String url = "/ShowOrderDetail";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listAllProductType.jsp
				successView.forward(req, res);
			} else {
				out.println("user login failed");
			}

			// 消費者登入
//			if (user != null) {
//				req.getSession().setAttribute("auth", user);
////				res.sendRedirect("http://localhost:8080/TGA104_G4/front-end/cart/cart.jsp"); // cart.jsp 登入後導到購物區
//				String url = "/ShowOrderDetail";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//			} else {
//				out.println("user login failed");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
