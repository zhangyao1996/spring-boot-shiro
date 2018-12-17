layui.use(['form','laydate','jquery'], function(){
  var form = layui.form;
  var laydate=layui.laydate;
  var $=layui.jquery;
  //渲染日期
  laydate.render({
	    elem: '#birthday'
	  });
  		//验证规则
	  form.verify({
		    username:  [/^[a-zA-Z0-9]{4,12}$/, '用户名必须是4-16位的字母与数字']
		    ,password: [/^[a-zA-Z0-9]{4,16}$/, '密码必须是4-16位的字母与数字']
		    ,realname:[/^([\u4e00-\u9fa5]){2,5}$/,'只能是中文姓名']
		  });
  	//添加表单失焦事件
	// 验证表单
	$('#username').blur(function() {
		var username = $("#username").val();
		console.log("用户名"+username);
		$.ajax({
			url : '../../user/checkUser?userName=' + username,
			type : 'post',
			// dataType:'json',
			// data:JSON.stringify({username:username}),
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
	
	 form.on('submit(add)',function (data) {
         // console.log(data.field);
 	   
		 var username = $("#username").val();
		 var password = $("#password").val();
		 var realname = $("#realname").val();
		 var phonenumber = $("#phonenumber").val();
		 var email = $("#email").val();
		 var sex = $("input[name='sex']:checked").val();
		 var birthday = $("#birthday").val();
		 console.log(data);
		 if(checkPassword()){
			 $.ajax({
	             url:'../../user/add',
	             type:'post',
	             dataType:'json',
	             contentType: 'application/json;charset=UTF-8',
	             data:JSON.stringify({
	            	userName:username,
	            	password:password,
	            	fullName:realname,
	            	phonenumber:phonenumber,
	            	email:email,
	            	sex:sex,
	            	birthday:birthday
	             }),
	             success:function (data) {
	                 if (data.result== true){
	                	 layer.msg(data.msg,{
	                		 icon:1,
	                		 time:2000
	                	 },function(){
	                		 parent.location.reload(); // 父页面刷新
		                     var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		                     parent.layer.close(index);
	                	 });
	                	 
	                 }else{
	                 	layer.msg(data.msg,{
	                 		icon:2
	                 	})
	                 		
	                 }
	             }
	         })
	         return false;
		 }
     })
  
});