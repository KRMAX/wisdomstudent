<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String contextPath = request.getContextPath();
    String url = request.getRequestURI();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + contextPath + "/";
%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]> <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]> <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]> <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js"> <!--<![endif]-->
<head>
    <meta charset="UTF-8"/>
    <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
    <title>Login and Registration Form with HTML5 and CSS3</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="../favicon.ico">

    <link href='<c:url value="/resources/themes/login/demo-login.css"></c:url>'
          rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/resources/themes/login/style-login.css"></c:url>'
          rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/resources/themes/login/animate-custom-login.css"></c:url>'
          rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=contextPath %>/resources/js/jquery.min.js"></script>
</head>
<body>
<div class="container">

    <header>

        <nav class="codrops-demos">
            <span>欢迎来到沈阳城建学院<strong>数学作业在线系统</strong> ！</span>
        </nav>
    </header>
    <div style="text-align:center;clear:both;">
    </div>
    <section>
        <div id="container_demo">
            <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
            <a class="hiddenanchor" id="toregister"></a>
            <a class="hiddenanchor" id="tologin"></a>
            <div id="wrapper">
                <div id="login" class="animate form">
                    <form id="ff" action="j_spring_security_check" autocomplete="on" method="post">
                        <h1>用 户 登 录</h1>
                        <p>${result }</p>
                        <p>
                            <label for="j_username" class="uname" data-icon="u"> 用户名</label>
                            <input id="j_username" name="j_username" required="required" type="text"
                                   placeholder="在此处输入用户名，默认是学号。。"/>
                        </p>
                        <p>
                            <label for="j_password" class="youpasswd" data-icon="p"> 密码 </label>
                            <input id="j_password" name="j_password" required="required" type="password"
                                   placeholder="在此处输入密码。。"/>
                        </p>
                        <p class="keeplogin">
                            <input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping"/>
                            <label for="loginkeeping">Keep me logged in</label>
                        </p>
                        <p class="login button">
                            <input type="submit" onclick="submitForm()" value="登录"/>
                        </p>
                        <p class="change_link">
                            Not a member yet ?
                            <a href="#toregister" class="to_register">加入我们！</a>
                        </p>
                    </form>
                </div>

                <div id="register" class="animate form">
                    <form action="#" autocomplete="on">
                        <h1> Sign up </h1>
                        <p>
                            <label for="usernamesignup" class="uname" data-icon="u">Your username</label>
                            <input id="usernamesignup" name="usernamesignup" required="required" type="text"
                                   placeholder="mysuperusername690"/>
                        </p>
                        <p>
                            <label for="emailsignup" class="youmail" data-icon="e"> Your email</label>
                            <input id="emailsignup" name="emailsignup" required="required" type="email"
                                   placeholder="mysupermail@mail.com"/>
                        </p>
                        <p>
                            <label for="passwordsignup" class="youpasswd" data-icon="p">Your password </label>
                            <input id="passwordsignup" name="passwordsignup" required="required" type="password"
                                   placeholder="eg. X8df!90EO"/>
                        </p>
                        <p>
                            <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Please confirm your
                                password </label>
                            <input id="passwordsignup_confirm" name="passwordsignup_confirm" required="required"
                                   type="password" placeholder="eg. X8df!90EO"/>
                        </p>
                        <p class="signin button">
                            <input type="submit" value="Sign up"/>
                        </p>
                        <p class="change_link">
                            Already a member ?
                            <a href="#tologin" class="to_register"> Go and log in </a>
                        </p>
                    </form>
                </div>

            </div>
        </div>
    </section>
</div>


</body>
<script>
    function submitForm() {
        $('#ff').form('submit', {
            ajax: false
        });
    }

    function clearForm() {
        $('#ff').form('clear');
    }
</script>
</html>