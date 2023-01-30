package com.tibame.productorderdetail.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tibame.cart.model.User;
import com.tibame.member.model.MemberVO;
import com.tibame.productorderdetail.model.ProductOrderDetailVO;


@WebServlet("/ShowFinalDetail")
public class ShowFinalDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		//先確認是否已登入
		MemberVO auth = (MemberVO)req.getSession().getAttribute("memberVO");
		List<ProductOrderDetailVO> orders = (List<ProductOrderDetailVO>)req.getAttribute("ordersDetail");
		if(auth!=null){
			req.setAttribute("auth", auth);
			req.setAttribute("orders", orders);
			req.getAttribute("productOrderVO");
			String url = "/front-end/order/showOrderDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
		}
		else{
//			res.sendRedirect("http://localhost:8080/TGA104_G4/front-end/cart/login.jsp");
			String url = "/front-end/cart/login.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}

}
