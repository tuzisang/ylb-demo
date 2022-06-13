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
});
