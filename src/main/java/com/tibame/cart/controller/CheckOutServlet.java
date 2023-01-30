package com.tibame.cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.tibame.cart.model.Cart;
import com.tibame.cart.model.ShopProductService;
import com.tibame.cart.model.User;
import com.tibame.member.model.MemberVO;
import com.tibame.productorder.model.ProductOrderJDBCDAO;
import com.tibame.productorder.model.ProductOrderVO;
import com.tibame.productorderdetail.model.ProductOrderDetailVO;

/**
 * Servlet implementation class CkeckOutServlet
 */
@WebServlet("/CheckOutServlet")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	@Autowired
//	private ProductOrderVO productOrderVO;
//
//	@Autowired
//	private ProductOrderJDBCDAO productOrderJDBCDAO;
//	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		try (PrintWriter out = res.getWriter()) {
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
//			Date date = new Date();
//			// retrive all cart products
//			ArrayList<Cart> cart_list = (ArrayList<Cart>) req.getSession().getAttribute("cart_list");
//			// user authentication
//			User auth = (User) req.getSession().getAttribute("auth");
////			System.out.println(cart_list);
//			int i = 0;
//			// check auth and cart list
//			if (cart_list != null && auth != null) {
//
//				//原先寫法
//				for (Cart c : cart_list) {
//					// prepare the order object
//					ProductOrderVO productOrderVO = new ProductOrderVO();
//					productOrderVO.setProductNo(c.getProductNo());
//					productOrderVO.setMemberNo(auth.getUserNo());
//					productOrderVO.setQuantity(c.getQuantity());
//					productOrderVO.setPaidDate(formatter.format(date));
//					// instantiate the JDBCDAO class
//					ProductOrderJDBCDAO productOrderJDBCDAO = new ProductOrderJDBCDAO();
//					// calling the insertOrder method
//					boolean result = productOrderJDBCDAO.insertOrder(productOrderVO);
//					if (!result) {
//						break;
//					}
//				}
//				res.sendRedirect("http://localhost:8081/TGA104G4/front-end/order/order.jsp"); // order.jsp
//			}else {
//				if (auth == null) {
//					res.sendRedirect("http://localhost:8081/TGA104G4/front-end/cart/login.jsp"); // login.jsp
//				}else {
//					res.sendRedirect("http://localhost:8081/TGA104G4/front-end/cart/cart.jsp"); // cart.jsp
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(req, res);
//	}
	
//	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		String action = req.getParameter("action");
//		
//		if ("insertReceive".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//			String receiverName = req.getParameter("rname").trim();
//			if (receiverName == null || receiverName.trim().length() == 0) {
//				errorMsgs.add("姓名請勿空白");
//			}
//			
//			String receiverPhone = req.getParameter("rphone").trim();
//			if (receiverPhone == null || receiverPhone.trim().length() == 0) {
//				errorMsgs.add("電話請勿空白");
//			}
//			
//			String receiverAddress = req.getParameter("raddress").trim();
//			if (receiverAddress == null || receiverAddress.trim().length() == 0) {
//				errorMsgs.add("地址請勿空白");
//			}
//			
//
//			ProductOrderVO productOrderVO = new ProductOrderVO();
//			productOrderVO.setReceiverName(receiverName);
//			productOrderVO.setReceiverPhone(receiverPhone);
//			productOrderVO.setReceiverAddress(receiverAddress);
//
//
//			// Send the use back to the form, if there were errors
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/product/addProduct.jsp");
//				failureView.forward(req, res);
//				return;// 程式中斷
//			}
//		}
//	}	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String receiverName = null;
		String receiverPhone = null;
		String receiverAddress = null;
		if ("insertReceive".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			receiverName = req.getParameter("rname").trim();
			String receiverNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
			if (receiverName == null || receiverName.trim().length() == 0) {
				errorMsgs.add("收件人姓名請勿空白");
			} else if(!receiverName.trim().matches(receiverNameReg)) { 
				errorMsgs.add("收件人姓名只能是中、英文字母");
            }
			
			receiverPhone = req.getParameter("rphone").trim();
			String receiverPhoneReg = "^[(0-9)]{2,10}$";
			if (receiverPhone == null || receiverPhone.trim().length() == 0) {
				errorMsgs.add("電話請勿空白");
			} else if(!receiverPhone.trim().matches(receiverPhoneReg)) { 
				errorMsgs.add("收件電話只能是數字");
            }
			
			receiverAddress = req.getParameter("raddress").trim();
			String receiverAddressReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
			if (receiverAddress == null || receiverAddress.trim().length() == 0) {
				errorMsgs.add("地址請勿空白");
			} else if(!receiverAddress.trim().matches(receiverAddressReg)) { 
				errorMsgs.add("收件人地址只能包含中、英文字與數字");
            }
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("ShowOrderDetail");
				failureView.forward(req, res);
				return;
			}
		}
		
		try (PrintWriter out = res.getWriter()) {
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
//		Date date = new Date();
//		java.sql.Date Date = new java.sql.Date(System.currentTimeMillis()); //年月日
		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp Date = new java.sql.Timestamp(calendar.getTimeInMillis()); //年月日時分秒
		
//		SimpleDateFormat st = new SimpleDateFormat("yyyyMMddHHmmss");
//		String so = st.format(Date);
//		String number = Integer.toString((int)(Math.random()*10000 + 100));
//		String orderNo = so.concat(number);
		HttpSession session = req.getSession();
		MemberVO auth = (MemberVO) session.getAttribute("memberVO");
		if (auth == null) {
			res.sendRedirect("/front-end/cart/login.jsp"); // login.jsp
			return;
		}
		ArrayList<Cart> cart_list = (ArrayList<Cart>) req.getSession().getAttribute("cart_list");
		ProductOrderVO productOrderVO = new ProductOrderVO();
		
		Integer QTY = 0;
		Integer price = 0;
		Integer totalPrice = 0;
		if(cart_list != null) {
			for(int i=0; i<cart_list.size(); i++) {
				price += cart_list.get(i).getPrice() * cart_list.get(i).getQuantity();
				QTY += cart_list.get(i).getQuantity();
			}
		}
		
		Integer orderNo = (int)(Math.random()*10000 + 1000);
		String orderStatus = "未發貨";
		productOrderVO.setTotalQTY(QTY);
		productOrderVO.setTotalAmount(price);
		productOrderVO.setMemberNo(auth.getMemberNo());
//		productOrderVO.setPaidDate(formatter.format(date));
		productOrderVO.setPaidDate(Date);
		productOrderVO.setReceiverName(receiverName);
		productOrderVO.setReceiverPhone(receiverPhone);
		productOrderVO.setReceiverAddress(receiverAddress);
		productOrderVO.setOrderNo(orderNo);
		productOrderVO.setOrderStatus(orderStatus);
		
		List<ProductOrderDetailVO> ordersItems = new ArrayList<ProductOrderDetailVO>();
		for (Cart c : cart_list) {
		ProductOrderDetailVO productOrderDetailVO = new ProductOrderDetailVO();
		productOrderDetailVO.setOrderNo(productOrderVO.getOrderNo());
		productOrderDetailVO.setProductNo(c.getProductNo());
		productOrderDetailVO.setProductName(c.getProductName());
		productOrderDetailVO.setQty(c.getQuantity());
		productOrderDetailVO.setPrice(c.getPrice());
		ordersItems.add(productOrderDetailVO);
//		System.out.println("cart_list" + cart_list.get(0).getProductName());
//		System.out.println("cart_list" + cart_list.get(1).getProductName());
	    }

//		System.out.println("ordersItems" + ordersItems.get(0).getProductName());
//		System.out.println("ordersItems" + ordersItems.get(1).getProductName());
		productOrderVO.setItems(ordersItems);
		ProductOrderJDBCDAO productOrderJDBCDAO = new ProductOrderJDBCDAO();
		productOrderJDBCDAO.addOrders(productOrderVO);
//		System.out.println("ordersItems_2" + ordersItems.get(0).getProductName());
//		System.out.println("ordersItems_2" + ordersItems.get(1).getProductName());
		session.removeAttribute("cart_list");//付款後，清空session中的購物車
//		System.out.println(productOrderVO.getOrderNo());
//		System.out.println("ordersItems_3" + ordersItems.get(0).getProductName());
//		System.out.println("ordersItems_3" + ordersItems.get(1).getProductName());
		res.sendRedirect("SelectOrder"); // order.jsp
		} catch (Exception e) {
		e.printStackTrace();
	}
		//購物明細
		
	}


}
