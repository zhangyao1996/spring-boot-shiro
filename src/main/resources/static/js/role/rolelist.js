
layui.use('table', function() {
	var table = layui.table;
	var laypage = layui.laypage;

	// table.render()方法返回一个对象：var tableIns =
	// table.render(options)，可用于对当前表格进行“重载”等操作
	table.render({
		skin : 'line', // 行边框风格
		// ,even: true //开启隔行背景
		// ,size: 'sm' //小尺寸的表格
		elem : '#demo', // 表格id
		url : '../../role/findRoles',
		title : '角色数据表',
		toolbar : "#toolbarDemo", // 表格外操作按钮
		page : false,
		cols : [ [ {
			field : 'roleId',
			title : '编号',
			sort : true
		}, {
			field : 'roleName',
			title : '角色名'
		}, {
			fixed : 'right',
			title : '操作',
			width : 180,
			align : 'center',
			toolbar : '#barDemo'
		} ] ]
	});

	// 头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch (obj.event) {
		case 'add':
			addRole();
			break;
		}
		;
	});

	// 监听行工具事件
	// 绑定查删改事件test对于table中的lay-filter的值
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			detailRole(data.roleId);
		} else if (obj.event == 'del') {
			layer.confirm('真的删除么?', {
				skin : 'layui-layer-lan',
				closeBtn : 0,
				anim : 4
			// 动画类型
			}, function(index) {
				delUser(data.userId);
				layer.close(index);
			});
		} else if (obj.event == 'edit') {
			editUser(data.userId);
		}
	});

	// 查看用户信息
	function detailRole(id) {
		layer.open({
			area : [ '493px', '424px' ], // 宽高
			title : '用户详情',
			type : 2,
			fix : false, // 不固定
			maxmin : true,
			content : '../../role/detail?roleId=' + id,
		});
	}

	// 新增用户信息
	function addRole() {
		layer.open({
			area : [ '493px', '424px' ], // 宽高
			title : '增加角色',
			type : 2,
			fix : false, // 不固定
			maxmin : true,
			content : '../../role/add'
		});
	}

	// 修改用户信息
	function editUser(id) {
		layer.open({
			area : [ '493px', '424px' ], // 宽高
			title : '修改用户',
			type : 2,
			fix : false, // 不固定
			maxmin : true,
			content : '../../user/edit?userId=' + id,
		});
	}

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
						location.reload();
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
