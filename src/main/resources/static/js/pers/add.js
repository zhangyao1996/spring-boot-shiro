layui.use([ 'form', 'laydate', 'jquery' ], function() {
	var form = layui.form;
	var laydate = layui.laydate;
	var $ = layui.jquery;

	/*
	 * form.on('select(roleId)', function(data){ $("#roleId").val(data.value)
	 * });
	 */

	form.on('submit(add)', function(data) {
		// console.log(data.field);

		var parentid = $("#parentid").val();
		var resname = $("#resname").val();
		var permission = $("#permission").val();
		var restype = $("input[name='restype']:checked").val();
		var url = $("#url").val();

		$.ajax({
			url : '../../pers/add',
			type : 'post',
			dataType : 'json',
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify({
				parentId : parentid,
				resName : resname,
				resType : restype,
				permission : permission,
				url : url,
			}),
			success : function(data) {
				if (data.result == true) {
					layer.msg(data.msg, {
						icon : 1,
						time : 2000
					}, function() {
						parent.location.reload(); // 父页面刷新
						var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
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