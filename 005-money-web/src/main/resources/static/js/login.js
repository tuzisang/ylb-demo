var referrer = "";//登录后返回页面
referrer = document.referrer;
if (!referrer) {
    try {
        if (window.opener) {
            // IE下如果跨域则抛出权限异常，Safari和Chrome下window.opener.location没有任何属性
            referrer = window.opener.location.href;
        }
    } catch (e) {
    }
}

//按键盘Enter键即可登录
$(document).keyup(function (event) {
    if (event.keyCode == 13) {
        login();
    }
});

function login() {
    //账号和密码不能为空
    if ($("#phone").val().length == 0) {
        alert("账号不能为空！");
        return;
    }
    if ($("#loginPassword").val().length == 0) {
        alert("密码不能为空！");
        return;
    }

    $.ajax({
        url: "../page/login",
        type: "post",
        data: {
            phone: $("#phone").val(),
            loginPassword: $.md5($("#loginPassword").val()),
            captcha: $("#captcha").val()
        },
        success: function (result) {
            if (result.code == 200) {
                alert(result.message);
                window.location.href = "../../";
            } else {
                alert(result.message);
            }
        }
    });

}

//自定义代码
$(function () {
    //刷新验证码
    $("#imgCode").click(function () {
        $(this).attr("src", "../login-captcha?t=" + new Date().getTime());
    });
    $("#loginId").click(function () {
        login();
    });

});