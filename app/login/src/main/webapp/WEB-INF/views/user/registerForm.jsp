
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>register</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
<div class="container">
        <form>
            <div >
                <label>nickname: </label>
                <input type="text" placeholder="nickname" id="nickname">
            </div>
            <div >
                <label>Password:</label>
                <input type="password" placeholder="Enter password" id="password">
            </div>
            <div>
                <label>Email address:</label>
                <input type="text" placeholder="Enter email" id="email">
                <br>
                <button id="btn-check-email" type="button">이메일 중복 확인</button>
                <span class="chk"></span>
                <br>
                <button id="btn-email-auth" type="button" disabled>이메일 인증</button>
            </div>

        </form>
        <button id="btn-save" class="btn btn-primary">회원가입</button>
    </div>

    <br/>
</body>
<script src="/js/user.js"></script>
</html>