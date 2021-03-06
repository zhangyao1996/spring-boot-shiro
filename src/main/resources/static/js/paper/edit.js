layui.use([ 'form', 'laydate', 'jquery' ], function() {
	var form = layui.form;
	var laydate = layui.laydate;
	var $ = layui.jquery;

	// 验证规则
	form.verify({
		title : [/^[^\s]*$/, '不能有空格'],
		author : [/^[^\s]*$/, '不能有空格']
	});
	

	

	
	
	form.on('submit(edit)', function(data) {
		// console.log(data.field);

		var id = $("#id").val();
		var typeid = $("#typeid").val();
		var title = $("#title").val();
		var author = $("#author").val();
		var status = $("input[name='status']:checked").val();
		var content = $("#content").val();
		if(status){
			status=1;
		}else {
			status=0;
		}
		
		// 重置表单

		$('#clear').click(function() {
			form.val('form', {
				"username" : "",
				"password" : "",
				"realname" : "",
				"phonenumber" : "",
				"email" : "",
				"sex" : '',
				"birthday" : "",
				"roleId" :""
			});
		});
		console.log("title"+title+"author"+author+"status"+status+"content"+content+"typeid"+typeid);
		
			$.ajax({
				url : '../../paper/edit',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify({
				id : id,
				title : title,
				author : author,
				status : status,
				content : content,
				typeId : typeid
			}),
				success : function(data) {
					if (data.result == true) {
						layer.msg(data.msg, {
							icon : 1,
							time : 2000
						},function() {
									parent.location.reload(); // 父页面刷新
									var index = parent.layer
											.getFrameIndex(window.name); // 获取窗口索引
									parent.layer.close(index);
								});

					} else {
						layer.msg(data.msg, {
							icon : 2
						})

					}
				}
			})
			return false;
		
	})

});