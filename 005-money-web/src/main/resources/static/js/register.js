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

//注册协议确认
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

	//手机号校验
	$("#phone").on("blur",function (){
		var phone = $("#phone").val().trim();
		if ("" == phone){
			showError("phone", "请输入手机号码");
		} else if(!/^1[1-9]\d{9}$/.test(phone)){
			showError("phone", "请输入正确的手机号码");
		} else{

			$.ajax({
				url: "../checkPhone",
				type:"post",
				data:{
					phone:phone
				},
				success:function (result){
					if (result.code != 200){
						showError("phone", result.message);
						return;
					}

					showSuccess("phone")
				}
			})
		}
	});

	//验证登录密码
	$("#loginPassword").on("blur", function (){
		var password = $("#loginPassword").val().trim();
		if ("" == password){
			showError("loginPassword", "请输入密码");
		}else if(!/^[0-9a-zA-Z]+$/.test(password)) {
			showError("loginPassword", "密码只能使用数字和大小写英文字母");
		}else if(!/^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*/.test(password)) {
			showError("loginPassword", "密码必须包含英文和数字");
		}else if(password.length < 6 || password.length > 20) {
			showError("loginPassword", "密码长度应该6到20位");
		} else {
			showSuccess("loginPassword");
		}
	});

	//获取短信验证码
	$("#messageCodeBtn").click(function (){
		var phone = $("#phone").val().trim();
		//判断是否在倒计时
		if ($("#messageCodeBtn").hasClass("on")){
			return;
		}
		//判断手机号是否通过验证
		$("#phone").blur();
		var phoneErrText = $("#phoneErr").text();
		if ("" != phoneErrText){
			return;
		}
		//判断密码是否通过验证
		$("#loginPassword").blur();
		var loginPasswordErrText = $("#loginPasswordErr").text();
		if ("" != loginPasswordErrText){
			return;
		}

		//发送短信验证请求
		$.ajax({
			url:"../code",
			type:"post",
			data:{
				phone:phone
			},
			success:function (result){
				if (result.code != 200){
					showError("messageCode", result.message);
					return;
				}

				//短信发送成功，60秒内按钮不可再使用
				$.leftTime(60, function (d){
					if (d.status){
						$("#messageCodeBtn").addClass("on");
						$("#messageCodeBtn").html((d.s == "00" ? "60" : d.s) + "秒后获取");
					}else {
						$("#messageCodeBtn").removeClass("on");
						$("#messageCodeBtn").html("获取验证码");
					}
				});

			}
		})
	});

	//验证短信验证码
	$("#messageCode").on("blur",function () {
		var messageCode = $.trim($("#messageCode").val());
		if ("" == messageCode) {
			showError("messageCode", "请输入短信验证码");
		} else {
			showSuccess("messageCode");
		}
	});

	//注册
	$("#btnRegist").click(function (){

		//触发三个文本框验证
		$("#phone").blur();
		$("loginPassword").blur();
		$("messageCode").blur();
		var errTexts = $("div[id$='Err']").text();
		if (!"" == errTexts){
			return;
		}

		var phone = $("#phone").val().trim();
		var password = $("#loginPassword").val().trim();
		var code = $("#messageCode").val();

		$.ajax({
			url:"../register",
			type:"post",
			data:{
				phone:phone,
				password:$.md5(password),
				code:code
			},
			success:function (result){

				if (result.code != 200){
					$("#loginPassword").val("");
					showError("messageCode", result.message);
					return;
				}

				window.location.href= "realName";
			}
		})
	});
});
