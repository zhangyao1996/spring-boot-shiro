layui.use(['form','laydate','jquery'], function(){
  var form = layui.form;
  var laydate=layui.laydate;
  var $=layui.jquery;

  		//验证规则
	  form.verify({
		    username:  [/^[a-zA-Z0-9]{4,12}$/, '用户名必须是4-16位的字母与数字']
		    ,password: [/^[a-zA-Z0-9]{4,16}$/, '密码必须是4-16位的字母与数字']
		    ,fullname:[/^([\u4e00-\u9fa5]){2,5}$/,'只能是中文姓名']
		  });
  	//添加表单失焦事件
	// 验证表单
	$('#username').blur(function() {
		var username = $("#username").val();
		console.log("用户名"+username);
		$.ajax({
			url : '../../register/checkUser?userName=' + username,
			type : 'post',
			// 验证用户名是否可用
			success : function(data) {
				if (data.result == false) {
					layer.tips('用户名已存在！', '#username', {
						tips : [ 2, '#FF3030' ],
						time : 2000
					});
				}
			}
		})

	});
  
	function checkPassword() {
		if ($('#password').val() != $('#repassword').val()) {
			layer.tips('两次输入的密码不一致', '#repassword', {
				tips : [ 2, '#FF3030' ],
				time : 2000
			});
			return false;
		} else {
			console.log("密码一致");
			return true;
		}

	}
	
	 form.on('submit(register)',function (data) {
         // console.log(data.field);
 	   
		 var username = $("#username").val();
		 var password = $("#password").val();
		 var fullname = $("#fullname").val();
		 
		 console.log(data);
		 if(checkPassword()){
			 $.ajax({
	             url:'../../register/create',
	             type:'post',
	             dataType:'json',
	             contentType: 'application/json;charset=UTF-8',
	             data:JSON.stringify({
	            	userName:username,
	            	password:password,
	            	fullName:fullname,
	             }),
	             success:function (data) {
	                 if (data.result== true){
	                	 location.href = "../../login";
	                	 layer.alert("注册成功");
	                 }else{
	                	 if(data.msg=="exist"){
	                		 layer.alert("注册失败,用户存在");
	                	 }else {
	                		 layer.alert("注册失败,未知错误");
						}
	                 	
	                 }
	             }
	         })
	         return false;
		 }
     })
  
});