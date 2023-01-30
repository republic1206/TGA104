package com.tibame.productorderdetail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.tibame.cart.model.ShopProductService;
import com.tibame.productorder.model.ProductOrderJDBCDAO;
import com.tibame.productorderdetail.model.ProductOrderDetailVO;


@WebServlet("/BackendOrderDetail")
public class BackendOrderDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	@Autowired
//	private ProductOrderJDBCDAO productOrderJDBCDAO;   

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		req.setCharacterEncoding("UTF-8");
		try(PrintWriter out = res.getWriter()){
			Integer orderNo = Integer.valueOf(req.getParameter("productOrderNo"));
			if(orderNo!=null) {
				ProductOrderJDBCDAO productOrderJDBCDAO = new ProductOrderJDBCDAO();
				List<ProductOrderDetailVO> ordersDetail = productOrderJDBCDAO.findOrdersById(orderNo);
				req.setAttribute("ordersDetail", ordersDetail);
				req.getRequestDispatcher("back-end/productOrder/listProductOrderDetail.jsp").forward(req, res);
//				for (ProductOrderDetailVO aProductOrderVO : list) {
//					out.print(aProductOrderVO.getOrderNo() + ",");
//					out.print(aProductOrderVO.getProductNo() + ",");
//					out.print(aProductOrderVO.getQty() + ",");
//					out.print(aProductOrderVO.getPrice() + ",");
//					out.println();
//				}
//				req.setAttribute("ordersDetail", list);
//				req.getRequestDispatcher("/front-end/order/showOrdersDetail.jsp").forward(req, res);
//			res.sendRedirect("http://localhost:8081/TGA104G4/front-end/order/showOrderDetail.jsp");
		}
//		res.sendRedirect("http://localhost:8081/TGA104G4/front-end/order/showOrderDetail.jsp");
	}catch (Exception e) {
		e.printStackTrace();
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
