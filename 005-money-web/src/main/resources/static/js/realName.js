
//同意实名认证协议
$(function() {
	$("#agree").click(function(){
		var ischeck = document.getElementById("agree").checked;
		if (ischeck) {
			$("#btnRegist").attr("disabled", false);
			$("#btnRegist").removeClass("fail");
		} else {
			$("#btnRegist").attr("disabled","disabled");
			$("#btnRegist").addClass("fail");
		}
	});
});
//打开注册协议弹层
function alertBox(maskid,bosid){
	$("#"+maskid).show();
	$("#"+bosid).show();
}
//关闭注册协议弹层
function closeBox(maskid,bosid){
	$("#"+maskid).hide();
	$("#"+bosid).hide();
}

//错误提示
function showError(id,msg) {
	$("#"+id+"Ok").hide();
	$("#"+id+"Err").html("<i></i><p>"+msg+"</p>");
	$("#"+id+"Err").show();
	$("#"+id).addClass("input-red");
}
//错误隐藏
function hideError(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id).removeClass("input-red");
}
//显示成功
function showSuccess(id) {
	$("#"+id+"Err").hide();
	$("#"+id+"Err").html("");
	$("#"+id+"Ok").show();
	$("#"+id).removeClass("input-red");
}

$(function (){
	//刷新验证码
	$("#imgCode").click(function () {
		$(this).attr("src", "../login-captcha?t=" + new Date().getTime());
	});

	//验证手机号码
	$("#phone").on("blur", function () {
		var phone = $.trim($("#phone").val());
		if (phone == "") {
			showError("phone", "手机号码不能为空");
		} else if (!/^1[1-9]\d{9}$/.test(phone)) {
			showError("phone", "请输入正确的手机号码");
		} else {
			showSuccess("phone");
		}
	});

	//验证真实姓名
	$("#realName").on("blur", function () {
		var code = $("#realName").val();
		if (code == "") {
			showError("realName", "真实姓名不能为空");
		} else {
			showSuccess("realName");
		}
	})

	//验证身份证号码
	$("#idCard").on("blur", function () {
		var idCard = $.trim($("#idCard").val());
		if (idCard == "") {
			showError("idCard", "身份证号码号码不能为空");
		} else if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idCard)) {
			showError("idCard", "请输入正确的身份证号码");
		} else {
			showSuccess("idCard");
		}
	});

	//验证码事件
	$("#captcha").on("blur", function () {
		var code = $("#captcha").val();
		if (code == "") {
			showError("captcha", "图形验证码不能为空");
		} else {
			showSuccess("captcha");
		}
	})

	//实名认证
	$("#btnRegist").on("click",function () {
		$("#phone").blur();
		$("#realName").blur();
		$("#idCard").blur();
		$("#captcha").blur();
		var errorTexts = $("div[id$='Err']").text();

		if (!"" == errorTexts) {
			return;
		}

		var captcha = $.trim($("#captcha").val());
		var phone = $.trim($("#phone").val());
		var realName = $.trim($("#realName").val());
		var idCard = $.trim($("#idCard").val());

		$.ajax({
			url: "/money/loan/verifyRealName",
			type:"post",
			data:{
				"code":captcha,
				"phone":phone,
				"realName":realName,
				"idCard":idCard
			},
			success:function (result) {
				if (result.code == 200) {
					window.location.href = "/money/index";
				} else {
					showError("realName",result.message);
				}
			},
			error:function () {
				showError("realName","实名认证异常,请稍后重试");
			}
		});
	});

})