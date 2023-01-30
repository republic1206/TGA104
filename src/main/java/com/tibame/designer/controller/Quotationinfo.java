package com.tibame.designer.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/Quotationinfo")
public class Quotationinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/pdf");
		ServletOutputStream out = res.getOutputStream();
		try {
			Statement stmt = con.createStatement();
			String orderNo = req.getParameter("orderNo").trim();
			ResultSet rs = stmt.executeQuery("select quotationAtt from DesignerOrder where orderNO=" + orderNo);

			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("quotationAtt"));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {
				// res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			// System.out.println(e);
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req, res);
	}
	
	
	public void init() throws ServletException {
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestMatDesign");
			con = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
