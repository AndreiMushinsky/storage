<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Главная страница</title>
	</head>
	<body>
		<ul>
			<li><a href="<c:url value="/storage" />">Склад</a></li>
			<li><a href="<c:url value="/journal" />">Журнал учета</a></li>
			<li><a href="<c:url value="/trial" />">Оборотно-сальдовая ведомость</a></li>
		</ul>
	</body>
</html>