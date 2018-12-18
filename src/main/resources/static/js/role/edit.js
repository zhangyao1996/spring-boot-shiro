var xtree = null;
(function() {
	confirm();
	initTree();
})();
function confirm() {
	layui.use([ 'form' ], function() {
		var form = layui.form;
		var $ = layui.$;
		form.on('submit(edit)', function(data) {

			var rolename = $("#rolename").val();
			var roleid = $("#id").val();
			console.log(rolename);
			var allNodes = xtree.GetAllCheckBox();
			var nodes = "";
			for (var i = 0; i < allNodes.length; i++) {
				if (allNodes[i].checked) {
					nodes += allNodes[i].value + ",";
				}
			}
			console.log(nodes);
			$.ajax({
				url : '../../role/edit',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify({
					roleId : roleid,
					roleName : rolename,
					nodes : nodes
				}),
				success : function(data) {
					if (data.result == true) {
						layer.msg(data.msg, {
							icon : 1,
							time : 2000
						},
								function() {
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
		})
	})
}



function initTree() {
	layui.use([ 'form' ], function() {
		var form = layui.form;
		var $ = layui.$;
		var roleId = $("#id").val();

		$('#clear').click(function() {
			var allNodes = xtree.GetAllCheckBox();
		
			for (var i = 0; i < allNodes.length; i++) {
				allNodes[i].checked = false;
			}
			form.val('form', {
				"rolename" : ""

			});
		});

		$.get("../../role/getPermissions?roleId=" + roleId, function(nodes) {
			console.log(nodes);
			xtree = new layuiXtree({
				elem : 'xtree' // 必填
				,
				form : form // 必填
				,
				data : nodes// 必填
				,
				isopen : true// 加载完毕后的展开状态，默认值：true
				,
				ckall : false // 启用全选功能，默认值：false
				,
				ckallback : function() {
				} // 全选框状态改变后执行的回调函数
				,
				color : { // 三种图标颜色，独立配色，更改几个都可以
					open : "#EE9A00" // 节点图标打开的颜色
					,
					close : "#EEC591" // 节点图标关闭的颜色
					,
					end : "#828282" // 末级节点图标的颜色
				},
				icon : { // 图标样式 （选填）
					open : "&#xe625" // 节点打开的图标（使用layui的图标，这里只填入图标代号即可）
					,
					close : "&#xe623" // 节点关闭的图标
					,
					end : "" // 末尾节点的图标
				}

			});
		});

	});

}
