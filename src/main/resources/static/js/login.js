
  //隐藏text block，显示password block
    function hideShowPsw(){
            if (password.type == "password") {
                    password.type = "text";
                    eye.className='fa fa-eye-slash'
            }else {
                    password.type = "password";
                    eye.className='fa fa-eye'
            }
        }

layui.use([ 'form', 'layer', 'jquery' ], function() {
	// 操作对象
	var layer = layui.layer
	var form = layui.form;
	var $ = layui.jquery;
	//密码显示
	var eye = document.getElementById("eye");
    var password = document.getElementById("password");
	
	form.on('submit(login)', function(data) {
		// console.log(data.field);
		var username = $("#username").val(); // 用户名
		var password = $("#password").val(); // 密码
		// layer.alert(username);
		$.ajax({
			url : '../../login',
			type : 'post',
			dataType : 'json',
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify({
				userName : username,
				password : password
			}),
			success : function(data) {
				if (data.msg == "success") {
					// layer.alert("登陆成功");
				
					location.href = "../../index";
				} else {
					//layer.alert("登陆失败");
					if (data.msg == "账号不存在！") {
						layer.tips('账号不存在！', '#username', {
							tips : [ 2, '#FF3030' ],
							time : 2000
						});
					} else if (data.msg == "账号未启用！") {
						layer.tips('账号未启用！', '#password', {
							tips : [ 2, '#FF3030' ],
							time : 2000
						});
					}else if (data.msg == "密码错误！") {
						layer.tips('密码错误！', '#password', {
							tips : [ 2, '#FF3030' ],
							time : 2000
						});
					}else if (data.msg == "未知错误！") {
						layer.tips('未知错误！', '#password', {
							tips : [ 2, '#FF3030' ],
							time : 2000
						});
					}
				}
			}
		})
		return false;
	})

});
