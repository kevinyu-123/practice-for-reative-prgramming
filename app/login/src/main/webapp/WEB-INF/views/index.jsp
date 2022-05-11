<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Index Page</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

</head>
<body>
    <c:choose>
        <c:when test="${userInfo == null}">
            <button onclick="location.href='login'">로그인</button>
            <br>
            <button onclick="location.href='register'">회원가입</button>
        </c:when>
        <c:otherwise>
            <label>로그인 성공</label>
            <label>${userInfo.nickname}님 환영합니다.</label>
            <button id="btn-logout" onclick="location.href='/logout?email=${userInfo.email}'">로그아웃</button>
        </c:otherwise>
    </c:choose>
    <script src="/js/user.js"></script>
</body>
</html>
