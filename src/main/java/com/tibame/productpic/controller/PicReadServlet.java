package com.tibame.productpic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.tibame.cart.model.ShopProductService;

@WebServlet("/PicReadServlet")
public class PicReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private DataSource dataSource;
	
	//外部Server
	@Override
	 public void init() throws ServletException {
	  ServletContext application = this.getServletContext();
	  ApplicationContext context = (ApplicationContext)
	    application.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	  this.dataSource = context.getBean("dataSource", DataSource.class);
	 }
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();

			Statement stmt = con.createStatement();
			String id = req.getParameter("productNo").trim();
//				System.out.println("id = " + id);
			ResultSet rs = stmt.executeQuery("select pic from ProductPic where productNo=" + id);
			if (rs.next()) {
				byte[] b = rs.getBytes("pic");
				ByteArrayInputStream in = new ByteArrayInputStream(b);
				byte[] buf = in.readAllBytes();
				out.write(buf);
				in.close();
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}

		}

	}
}
