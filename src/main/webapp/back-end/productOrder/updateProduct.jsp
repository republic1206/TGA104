<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tibame.product.model.*"%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>�ӫ~��ƭק� - update_product_input.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>�ӫ~��ƭק� - update_product_input.jsp</h3>
		 <h4><a href="/TGA104_G4/back-end/product/selectProduct_page.jsp"><img src="/TGA104G4/back-end/product/images/back.png" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<h3>��ƭק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="/TGA104_G4/UpdateProductServlet" name="form1">
<table>

    <tr>		
		<td>�ӫ~�s��:<font color=red><b>*</b></font></td>
		<td><input type="hidden" name="productTypeNo" value ="${productVO.productNo}" size="45" readonly="readonly"/>
		${productVO.productNo}
		</td>
	</tr>

	<tr>
		<td>�ӫ~���O�s��:</td>
		<td><input type="TEXT" name="productTypeNo" size="45" value="${productVO.productTypeNo}" /></td>
	</tr>
	
	<tr>
		<td>�ӫ~�W��:</td>
		<td><input type="TEXT" name="productName" size="45" value="${productVO.productName}" /></td>
	</tr>
	
	<tr>
		<td>�ӫ~�w�s�q:</td>
		<td><input type="TEXT" name="stock" size="45" value="${productVO.stock}" /></td>
	</tr>
	<tr>
		<td>�ӫ~���:</td>
		<td><input type="TEXT" name="price" size="45" value="${productVO.price}" /></td>
	</tr>
	<tr>
		<td>�ӫ~�y�z:</td>
		<td><input type="TEXT" name="productDescription" size="45" value="${productVO.productDescription}" /></td>
	</tr>
	<tr>
		<td>�ӫ~���A:</td>
		<td><input type="TEXT" name="productStatus" size="45" value="${productVO.productStatus}" /></td>
	</tr>
	<tr>
		<td>�޲z���s��:</td>
		<td><input type="hidden" name="adminNo" value ="${productVO.adminNo}" size="45" readonly="readonly"/>
		${productVO.adminNo}
	</tr>



</table>
<br>
<input type="hidden" name="action" value="updateProduct">
<input type="hidden" name="productNo" value="${productVO.productNo}">
<input type="submit" value="�e�X�ק�"></FORM>
</body>


</html>