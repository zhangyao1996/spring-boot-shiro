layui.use([ 'form', 'layer', 'jquery' ], function() {
	// 操作对象
	var layer=layui.layer;
	var $=layui.jquery;
	var id=$("#id").val();
	layer.confirm('真的删除么?', {
		skin : 'layui-layer-lan',
		closeBtn : 0,
		anim : 4
	// 动画类型
	}, function(index) {
		delUser(id);
		layer.close(index);
	});
	// 删除用户信息
	function delUser(id) {
		$.ajax({
			url : "../../user/del?userId=" + id,
			type : "delete",
			success : function(data) {
				if (data.result == true) {
					layer.msg(data.msg, {
						icon : 1,
						time : 2000
					}, function() {
						parent.location.href="../../logout" ;
					});
				} else {
					layer.msg(data.msg, {
						icon : 2
					})
				}
			}
		});
	}

});