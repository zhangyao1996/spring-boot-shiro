layui.use('table', function() {
	var table = layui.table;
	//var laypage = layui.laypage;
	var $=layui.$;
	// table.render()方法返回一个对象：var tableIns =
	// table.render(options)，可用于对当前表格进行“重载”等操作
	table.render({
		skin : [ 'line', 'row' ], // 边框风格
		// even: true, //开启隔行背景
		// ,size: 'sm' //小尺寸的表格
		elem : '#demo', // 表格id
		url : '../../paper/findType',
		title : '文章列表',
		toolbar : "#toolbarDemo", // 表格外操作按钮
		page : false,
		limit:20,
		id : 'tableDate',
		cellMinWidth : 60,
		height : 'full-20',
		cols : [ [ {
			field : 'id',
			title : 'ID',
			sort : true
		}, {
			field : 'typeName',
			title : '分类',
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
			addType();
			break;
		case 'reload':
			location.reload();
			break;
		}
	});

	// 监听行工具事件
	// 绑定查删改事件test对于table中的lay-filter的值
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		if (obj.event == 'del') {
			layer.confirm('真的删除么?', {
				skin : 'layui-layer-lan',
				closeBtn : 0,
				anim : 4
			// 动画类型
			}, function(index) {
				delType(data.id);
				layer.close(index);
			});
		} else if (obj.event == 'edit') {
			editType(data.id);
		}
	});

	// 新增文章信息
	function addType() {
		layer.open({
			area : [ '320px', '190px' ], // 宽高
			title : '添加分类',
			type : 2,
			fix : false, // 不固定
			maxmin : true,
			content : '../../paper/addType'
		});
	}

	// 修改文章信息
	function editType(id) {
		layer.open({
			area : [ '320px', '190px' ], // 宽高
			title : '修改文章',
			type : 2,
			fix : false, // 不固定
			maxmin : true,
			content : '../../paper/editType?id=' + id,
		});
	}

	// 删除文章信息
	function delType(id) {
		$.ajax({
			url : "../../paper/delType?id=" + id,
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
