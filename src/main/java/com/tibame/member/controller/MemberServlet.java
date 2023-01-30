package com.tibame.member.controller;

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
import javax.servlet.http.HttpSession;

import com.tibame.designer.model.DesignerOrderPhaseVO;
import com.tibame.designer.model.DesignerOrderVO;
import com.tibame.designer.service.DesignerOrderPhaseService;
import com.tibame.designer.service.DesignerService;
import com.tibame.member.model.MailService;
import com.tibame.member.model.MemberService;
import com.tibame.member.model.MemberVO;

@WebServlet(value = { "/front-end/member/MemberServlet", "/front-end/member/MemberSignup",
		"/back-end/member/MemberServlet" })
@MultipartConfig
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MemberServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("portfolio_GetByNo".equals(action)) { // 來自/front-end/member/index.jsp點擊設計師資料的請求

			/*************************** 1.接收請求參數 **********************/
			String str = req.getParameter("memberNo");
			Integer memberNo = Integer.valueOf(str);
			/*************************** 2.查詢資料 *****************************************/
			// 查詢訂單資料
			MemberService memberSvc = new MemberService();
			List<DesignerOrderVO> desOrderList = memberSvc.selectbyMemberNo(memberNo);
			
			
			
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			
			req.setAttribute("desOrderList", desOrderList);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/member/memberPorfile.jsp");
			successView.forward(req, res);
		}

		if ("memberSignup".equals(action)) { // 來自/front-end/member/signup.jsp 點擊註冊的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			String memberAccount = req.getParameter("memberAccount");
			if (memberAccount == null || memberAccount.trim().length() == 0) {
				errorMsgs.add("會員帳號(信箱) 請勿空白");
			}
			MemberService checkUsed = new MemberService();
			Boolean used = checkUsed.accountUsed(memberAccount);
			if (used == true) {
				errorMsgs.add("帳號已存在");
			}

//			// Send the use back to the form, if there were errors
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/signup.jsp");
//				failureView.forward(req, res);
//				return; // 程式中斷
//			}

			String memberPassword = req.getParameter("memberPassword");
			String memberPasswordReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (memberPassword == null || memberPassword.trim().length() == 0) {
				errorMsgs.add("會員密碼 請勿空白");
			} else if (!memberPassword.trim().matches(memberPasswordReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("會員密碼: 只能是英文字母、數字和_ , 且長度必需在2到10之間");
			}
			String confirmedPassword = req.getParameter("confirmedPassword");
			if (!confirmedPassword.equals(memberPassword)) {
				errorMsgs.add("密碼與確認密碼不一致 請重新輸入");
			}
			String memberName = req.getParameter("memberName");
			if (memberName == null || memberName.trim().length() == 0) {
				errorMsgs.add("會員名稱 請勿空白");
			}
			String nickName = req.getParameter("nickName");
			if (nickName == null || nickName.trim().length() == 0) {
				errorMsgs.add("會員暱稱 請勿空白");
			}
			String gender = req.getParameter("gender");
			if (gender == null || gender.trim().length() == 0) {
				errorMsgs.add("會員性別 請勿空白");
			}
			String strBirthDate = req.getParameter("birthDate");
			java.sql.Date birthDate = java.sql.Date.valueOf(strBirthDate); // string to java.sql.Date

			String valistr = req.getParameter("valistr");
			HttpSession session = req.getSession();
			session.getAttribute(valistr);
//			String valistrin = (String) session.getAttribute(valistr);
			String valistrin = req.getParameter("valistr");
//			System.out.println("Servlet valistr: " + valistr);
//			System.out.println("Servlet valistrin: " + valistrin);

			if (!valistr.equals(valistrin)) {
				errorMsgs.add("驗證碼錯誤 請重新輸入");
			}

			Boolean activaction = false;

			MemberVO memberVO = new MemberVO();
			memberVO.setMemberAccount(memberAccount);
			memberVO.setMemberPassword(memberPassword);
			memberVO.setMemberName(memberName);
			memberVO.setNickName(nickName);
			memberVO.setGender(gender);
			memberVO.setBirthDate(birthDate);
			memberVO.setActivaction(activaction);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的manager_VO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/signup.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			MemberService memberSvc = new MemberService();
			memberVO = memberSvc.addMember(memberAccount, memberPassword, memberName, nickName, gender, birthDate,
					activaction);
			memberVO = memberSvc.findMemberNo(memberAccount);
			Integer memberNo = memberVO.getMemberNo();
			MailService mail = new MailService();
			String subject = "【會員註冊通知信】";
			String messageText = "<h2>Hello! " + memberName + "</h2>" +"<br> <p> 請點擊以下連結確認信箱帳號無誤</p>" + 
					 "<a href='http://localhost:8081/TGA104_G4/front-end/Activation?memberNo=" + memberNo +"'>確認</a><br>";
			mail.sendMail(memberAccount, subject, messageText);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/member/signupSuccess.jsp");
			successView.forward(req, res);
		}

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("memberNo");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入會員編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer memberno = null;
			try {
				memberno = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("會員編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMember(memberno);
			if (memberVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("memberselect", memberVO); // 資料庫取出的empVO物件,存入req
			String url = "/back-end/member/listOneMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer memberNo = Integer.valueOf(req.getParameter("memberNo"));

			/*************************** 2.開始查詢資料 ****************************************/
			MemberService memberSvc = new MemberService();
			MemberVO memberVO = memberSvc.getOneMember(memberNo);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("memberVO", memberVO); // 資料庫取出的empVO物件,存入req
			String url = "/member/update_Member_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_member_input.jsp
			successView.forward(req, res);
		}

		if ("update".equals(action)) { // 來自update_member_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			Integer memberno = Integer.valueOf(req.getParameter("memberNo").trim());
			String memberAccount = req.getParameter("memberAccount");
			memberAccount = req.getParameter("memberAccount").trim();
			if (memberAccount == null || memberAccount.trim().length() == 0) {
				errorMsgs.add("會員帳號請勿空白");
			}

			String memberPassword = req.getParameter("memberPassword");
			String memberReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (memberPassword == null || memberPassword.trim().length() == 0) {
				errorMsgs.add("會員密碼: 請勿空白");
			} else if (!memberPassword.trim().matches(memberReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("會員密碼: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String memberName = req.getParameter("memberName").trim();
			if (memberName == null || memberName.trim().length() == 0) {
				errorMsgs.add("會員姓名請勿空白");
			}

			String nickName = null;
			try {
				nickName = String.valueOf(req.getParameter("nickName").trim());
			} catch (NumberFormatException e) {
				nickName = "阿豪";
				errorMsgs.add("請填中文字");
			}

			String gender = null;
			try {
				gender = String.valueOf(req.getParameter("gender").trim());
			} catch (NumberFormatException e) {
				gender = "男";
				errorMsgs.add("性別請填中文字");
			}

			java.sql.Date birthDate = null;
			try {
				birthDate = java.sql.Date.valueOf(req.getParameter("birthDate").trim());
			} catch (IllegalArgumentException e) {
				birthDate = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入日期!");
			}
			Boolean activaction = Boolean.valueOf(req.getParameter("activaction").trim());

			MemberVO memberVO = new MemberVO();
			memberVO.setMemberNo(memberno);
			memberVO.setMemberPassword(memberPassword);
			memberVO.setMemberName(memberName);
			memberVO.setNickName(nickName);
			memberVO.setGender(gender);
			memberVO.setBirthDate(birthDate);
			memberVO.setActivaction(activaction);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的memberVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/member/update_member_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			MemberService memberSvc = new MemberService();
			memberVO = memberSvc.updateMember(memberno, memberAccount, memberPassword, memberName, nickName, gender,
					birthDate, activaction);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("memberVO", memberVO); // 資料庫update成功後,正確的的memberVO物件,存入req
			String url = "/member/listOneMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOnemember.jsp
			successView.forward(req, res);
		}

		if ("insert".equals(action)) { // 來自addmember.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			String memberAccount = req.getParameter("memberAccount");
			String memberPassword = req.getParameter("memberPassword");
			String memberReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (memberPassword == null || memberPassword.trim().length() == 0) {
				errorMsgs.add("會員密碼: 請勿空白");
			} else if (!memberPassword.trim().matches(memberReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("會員密碼: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String memberName = req.getParameter("memberName").trim();
			if (memberName == null || memberName.trim().length() == 0) {
				errorMsgs.add("會員姓名請勿空白");
			}

			String nickName = null;
			try {
				nickName = String.valueOf(req.getParameter("nickName").trim());
			} catch (NumberFormatException e) {
				nickName = "小明";
				errorMsgs.add("暱稱請填中文字.");
			}

			String gender = null;
			try {
				gender = String.valueOf(req.getParameter("gender").trim());
			} catch (NumberFormatException e) {
				gender = "女";
				errorMsgs.add("性別請填中文字.");
			}

			java.sql.Date birthdate = null;
			try {
				birthdate = java.sql.Date.valueOf(req.getParameter("birthDate"));
			} catch (IllegalArgumentException e) {
				birthdate = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入日期!");
			}

			Boolean activaction = Boolean.valueOf(req.getParameter("activaction").trim());

			MemberVO memberVO = new MemberVO();
			memberVO.setMemberPassword(memberPassword);
			memberVO.setMemberName(memberName);
			memberVO.setNickName(nickName);
			memberVO.setGender(gender);
			memberVO.setBirthDate(birthdate);
			memberVO.setActivaction(activaction);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
				System.out.println("1=====================================================");
				RequestDispatcher failureView = req.getRequestDispatcher("/member/addMember.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			System.out.println("1.5=====================================================");
			MemberService memberSvc = new MemberService();
			System.out.println("2=====================================================");
			memberVO = memberSvc.addMember(memberAccount, memberPassword, memberName, nickName, gender, birthdate,
					activaction);
			System.out.println("3=====================================================");
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/member/listAllMember.jsp";
			System.out.println("4=====================================================");
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
			System.out.println("5=====================================================");
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer memberno = Integer.valueOf(req.getParameter("memberno"));

			/*************************** 2.開始刪除資料 ***************************************/
			MemberService MemberSvc = new MemberService();
			MemberSvc.deleteMember(memberno);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/member/listAllMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}

		if ("updatemember".equals(action)) { // 來自memberPorfile.jsp的修改請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			/*************************** 1.接收請求參數 ****************************************/
			Integer memberno = Integer.valueOf(req.getParameter("memberNo").trim());
			String memberAccount = req.getParameter("memberAccount");
			if (memberAccount == null || memberAccount.trim().length() == 0) {
				errorMsgs.add("會員帳號請勿空白");
			}

			String memberPassword = req.getParameter("memberPassword");
			String memberReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (memberPassword == null || memberPassword.trim().length() == 0) {
				errorMsgs.add("會員密碼: 請勿空白");
			} else if (!memberPassword.trim().matches(memberReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("會員密碼: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String memberName = req.getParameter("memberName");
			if (memberName == null || memberName.trim().length() == 0) {
				errorMsgs.add("會員姓名請勿空白");
			}

			String nickName = req.getParameter("nickName");
			if (nickName == null || nickName.trim().length() == 0) {
				errorMsgs.add("會員暱稱 請勿空白");
			}
			String gender = req.getParameter("gender");
			if (gender == null || gender.trim().length() == 0) {
				errorMsgs.add("會員性別 請勿空白");
			}

			String strBirthDate = req.getParameter("birthDate");
			java.sql.Date birthDate = java.sql.Date.valueOf(strBirthDate); // string to java.sql.Date

			Boolean activaction = Boolean.valueOf(req.getParameter("activaction").trim());

			MemberVO memberVO = new MemberVO();
			memberVO.setMemberNo(memberno);
			memberVO.setMemberPassword(memberPassword);
			memberVO.setMemberName(memberName);
			memberVO.setNickName(nickName);
			memberVO.setGender(gender);
			memberVO.setBirthDate(birthDate);
			memberVO.setActivaction(activaction);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的memberVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/memberPorfile.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			MemberService memberSvc = new MemberService();
			memberVO = memberSvc.updateMember(memberno, memberAccount, memberPassword, memberName, nickName, gender,
					birthDate, activaction);

			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			req.setAttribute("updateMemberVO", memberVO); // 資料庫update成功後,正確的的memberVO物件,存入req
			String url = "/front-end/member/updateMemberPorfile.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOnemember.jsp
			successView.forward(req, res);

		}
		
		if ("desOrder_GetOne".equals(action)) { // 來自memberPorfile.jsp的案件明細請求
			/*************************** 1.接收請求參數 ****************************************/
			Integer orderNo = Integer.valueOf(req.getParameter("orderNo"));
			Integer memberNo = Integer.valueOf(req.getParameter("memberNo"));
			
			/*************************** 2.開始修改資料 *****************************************/
			MemberService memberSvc = new MemberService();
			DesignerOrderVO findDesignerOrder = memberSvc.findDesignerOrder(orderNo);
			DesignerOrderPhaseService designerOrderPhaseSvc = new DesignerOrderPhaseService();
			List<DesignerOrderVO> desOrderList = memberSvc.selectbyMemberNo(memberNo);
			List<DesignerOrderPhaseVO> designerOrderPhase = designerOrderPhaseSvc.getOrderPhase(orderNo);
			
			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
			
			req.setAttribute("findDesignerOrder", findDesignerOrder);
			req.setAttribute("desOrderList", desOrderList);
			req.setAttribute("designerOrderPhase", designerOrderPhase);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/member/designerOrderDetail.jsp"); 
			successView.forward(req, res);
		}
		
		
		
		
		
	}

}
