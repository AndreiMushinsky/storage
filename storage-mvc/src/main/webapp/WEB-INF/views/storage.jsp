<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Склад</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/tables.css" />">
</head>
<body>
	<table id="storageTable">
		<tr>
			<th>№ п/п</th>
			<th>Артикул</th>
			<th>Кол-во (п.м.)</th>
		</tr>
		<tbody>
			<c:forEach items="${storage.stocks}" var="stock" varStatus="status">
				<tr>
					<td id="index" class="count"><c:out value="${status.index+1}" /></td>
					<td><c:out value="${stock.name}" /></td>
					<td class="count"><c:out value="${stock.amount}" /></td>
				</tr>
			</c:forEach>
			<tr id="total">
				<td colspan="2">ИТОГО:</td>
				<td class="count"><c:out value="${storage.totalAmount}" /></td>
			</tr>
		</tbody>
	</table>
</body>
</html>