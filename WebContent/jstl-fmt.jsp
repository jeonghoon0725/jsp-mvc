<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<c:set var = "current" value = "<%= new java.util.Date() %>" />
dateStyleshort : <fmt:formatDate value="${current }" type="date" dateStyle="short"/><br/> 
dateStylemedium : <fmt:formatDate value="${current }" type="date" dateStyle="medium"/><br/> 
dateStyledefault : <fmt:formatDate value="${current }" type="date" /><br/> 
dateStylelong : <fmt:formatDate value="${current }" type="date" dateStyle="long"/><br/> 
dateStylefull : <fmt:formatDate value="${current }" type="date" dateStyle="full"/><br/>
timeStyleshort : <fmt:formatDate value="${current }" type="time" timeStyle="short"/><br/> 
timeStylemedium : <fmt:formatDate value="${current }" type="time" timeStyle="medium"/><br/> 
timeStyledefault : <fmt:formatDate value="${current }" type="time" /><br/> 
timeStylelong : <fmt:formatDate value="${current }" type="time" timeStyle="long"/><br/> 
timeStylefull : <fmt:formatDate value="${current }" type="time" timeStyle="full"/><br/>
type both : <fmt:formatDate value="${current }" type="both" dateStyle="long" timeStyle="full"/><br/> 
pattern : <fmt:formatDate value="${current }" pattern="yyyy년MM월dd일, hh시:mm분:ss초a z"/><br/>
<!-- fmt:formatDate 날짜를 문자열로 포맷팅 -->
</body>
</html>