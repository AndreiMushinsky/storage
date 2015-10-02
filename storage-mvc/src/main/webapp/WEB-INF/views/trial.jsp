<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Склад</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/tables.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/error.css" />">
<script type="text/javascript" src="<c:url value="/resources/script/date.js" />"></script>
</head>
<body>
	<sf:form method="POST" commandName="timePeriod" onsubmit="return checkTimePeriod(this);">
	Период с:<sf:input path="fromDate" class="date" />
	по:<sf:input path="toDate" class="date" />
	<input type="submit" value="Показать"/>
	<span id="errorMessage"></span>
	</sf:form>
	<table id="trialTable">
		<tr>
			<th>№ п/п</th>
			<th>Артикул</th>
			<th>Сальдо на <fmt:formatDate value="${trialBalance.timePeriod.fromDate}" pattern="dd.MM.yyyy"/> (п.м.)</th>
			<th>Оборот по дебету (п.м.)</th>
			<th>Оборот по кредиту (п.м.)</th>
			<th>Сальдо на <fmt:formatDate value="${trialBalance.timePeriod.toDate}" pattern="dd.MM.yyyy"/> (п.м.)</th>
		</tr>
		<tbody>
			<c:forEach items="${trialBalance.movements}" var="movement"
				varStatus="status">
				<tr>
					<td id="index" class="count"><c:out value="${status.index+1}" /></td>
					<td><c:out value="${movement.name}" /></td>
					<td class="count"><c:out value="${movement.startBalance}" /></td>
					<td class="count"><c:out value="${movement.drMovement}" /></td>
					<td class="count"><c:out value="${movement.crMovement}" /></td>
					<td class="count"><c:out value="${movement.endBalance}" /></td>
				</tr>
			</c:forEach>
			<tr id="total">
				<td colspan="2">ИТОГО:</td>
				<td class="count"><c:out
						value="${trialBalance.totalMovement.startBalance}" /></td>
				<td class="count"><c:out
						value="${trialBalance.totalMovement.drMovement}" /></td>
				<td class="count"><c:out
						value="${trialBalance.totalMovement.crMovement}" /></td>
				<td class="count"><c:out
						value="${trialBalance.totalMovement.endBalance}" /></td>
			</tr>
		</tbody>
	</table>
</body>
</html>