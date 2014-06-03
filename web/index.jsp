<%--
  Created by IntelliJ IDEA.
  User: lw
  Date: 14-5-1
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jquery.cookie.js_demo</title>

    <script type="text/javascript" src="public_file/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="public_file/js/jquery.cookie.js"></script>
</head>
<script>
    $(function ($) {

        var userName = COOKIE_UTIL.getCookie("userName");
        var userPswd;
        //页面初始化判断是否有cookie记录
        if (userName != null) {
            userPswd = COOKIE_UTIL.getCookie("userPswd");
            $("#userName").val(userName);
            $("#userPswd").val(userPswd);
            $("#isSavaCookie").attr("checked", "checked");
        }

        //注册登录按钮事件
        $("#login").click(function () {

            userName = $("#userName").val();
            userPswd = $("#userPswd").val();
            var isSavaCookie = $("#isSavaCookie").attr("checked");

            //如果勾选保存密码则保存cookie，否则删除
            if (isSavaCookie == undefined) {

                COOKIE_UTIL.delCookie("userName");
                COOKIE_UTIL.delCookie("userPswd");
            } else {

                COOKIE_UTIL.savaCookie("userName", userName, {expires: 7, path: '/', domain: 'jquery.com', secure: true});
                COOKIE_UTIL.savaCookie("userPswd", userPswd, {expires: 7, path: '/', domain: 'jquery.com', secure: true});
            }
            login();
        });

        //登录事件
        function login() {
            //code......
            //alert("login...");
        }
    });

    //对cookie 进行保存-获取-删除
    var COOKIE_UTIL = new function () {
        this.savaCookie = function (the_cookie, the_value, option) {
            $.cookie(the_cookie, the_value, option);
        }
        this.getCookie = function (the_cookie) {
            return $.cookie(the_cookie);
        }
        this.delCookie = function (the_cookie) {
            $.cookie(the_cookie, null);
        }
    };
</script>
<body>

<div style="text-align: center">
    <br><br>
    userName:<input type="text" id="userName"><br>
    userPswd:<input type="password" id="userPswd"><br>
    <input type="checkbox" id="isSavaCookie">保存密码<br>
    <input type="button" id="login" value="Login">

</div>
</body>
</html>