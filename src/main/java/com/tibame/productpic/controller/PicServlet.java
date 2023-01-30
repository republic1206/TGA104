package com.tibame.productpic.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tibame.product.model.ProductService;
import com.tibame.productpic.model.ProductPicJDBCDAO;
import com.tibame.productpic.model.ProductPicService;
import com.tibame.productpic.model.ProductPicVO;

@WebServlet("/PicServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class PicServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("addPic".equals(action)) {

			byte[] pic = req.getPart("photo").getInputStream().readAllBytes();
			ProductPicJDBCDAO dao = new ProductPicJDBCDAO();
			ProductPicVO productPicVO = new ProductPicVO();
			
			
			String str = req.getParameter("productNo");
			Integer productNo = Integer.valueOf(str);
			ProductService productSvc = new ProductService();
			

			productPicVO.setProductNo(productNo);
			productPicVO.setPic(pic);
			dao.insert(productPicVO);
			List<ProductPicVO> list = dao.getAll();
			req.setAttribute("list", list);
//			req.setAttribute("productPicVO", productPicVO);
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/productpic/listAllPic.jsp"); // 以集合物件為判斷式，如果有加入任何錯誤訊息，就forward回出發頁面
			failureView.forward(req, res);
			return;// 程式中斷
		}
		
		if ("getProductPic_For_Update".equals(action)) { // 來自selectProduct_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer productPicNo = Integer.valueOf(req.getParameter("productPicNo"));

			/*************************** 2.開始查詢資料 ****************************************/
			ProductPicService productPicSvc = new ProductPicService();
			ProductPicVO productPicVO = productPicSvc.getOnePic(productPicNo);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("productPicVO", productPicVO); // 資料庫取出的productTypeVO物件,存入req
			String url = "/back-end/productpic/updatePic.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_productType_input.jsp
			successView.forward(req, res);
		}

		if ("updatePic".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer productPicNo = Integer.valueOf(req.getParameter("productPicNo").trim());
			Integer productNo = Integer.valueOf(req.getParameter("productNo").trim());
			byte[] pic = req.getPart("pic").getInputStream().readAllBytes();

			ProductPicVO productPicVO = new ProductPicVO();
			productPicVO.setProductPicNo(productPicNo);
			productPicVO.setProductNo(productNo);
			productPicVO.setPic(pic);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("productPicVO", productPicVO); // 含有輸入格式錯誤的productTypeVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/producttype/updatePic.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			ProductPicService productPicSvc = new ProductPicService();
			productPicVO = productPicSvc.updateProductPic(productPicNo, productNo, pic);
			List<ProductPicVO> list = productPicSvc.getAll();
			req.setAttribute("list", list);
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("productPicVO", productPicVO); // 資料庫update成功後,正確的productTypeVO物件,存入req
			String url = "/back-end/productpic/listAllPic.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交selectProduct_page.jsp
			successView.forward(req, res);
		}
	}	

}
