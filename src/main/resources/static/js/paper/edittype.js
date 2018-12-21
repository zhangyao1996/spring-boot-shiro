layui.use([ 'form', 'laydate', 'jquery' ], function() {
	var form = layui.form;
	var laydate = layui.laydate;
	var $ = layui.jquery;

	// 验证规则
	form.verify({
		typename : [/^[^\s]*$/, '不能有空格'],
	});
	

	

	
	
	form.on('submit(edit)', function(data) {
		// console.log(data.field);

		
		var typename = $("#typename").val();
		var id = $("#id").val();
	
		
			$.ajax({
				url : '../../paper/editType',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify({
				typeName : typename,
				id:id
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