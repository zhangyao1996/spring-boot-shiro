/*var pageCode = 1;
var pageSize = 2;*/
//productsearch(pageCode, pageSize);

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
			url : '../../user/findByConPage',
			title : '用户数据表',
			toolbar : "#toolbarDemo", // 表格外操作按钮
			page : true,
			limits : [ 10, 20, 50, 100 ],
			id : 'tableDate',
			cellMinWidth : 60,
			height: 'full-210',
			cols : [ [ {
				type : 'checkbox',
				fixed : 'left'
			}, {
				field : 'userId',
				title : '编号',
				sort : true
			}, {
				field : 'userName',
				title : '用户名'
			}, {
				field : 'fullName',
				title : '中文名'
			},{
				fixed : 'right',
				title : '操作',
				width : 180,
				align : 'center',
				toolbar : '#barDemo'
			} ] ]
		});

		// 搜索功能
		var $ = layui.$, active = {
		reload : function() {
			var keyword = $('#keyword');
			table.reload('tableDate', {
				// 从第 1 页开始
				page : {
					curr : 1
				}
				// 设定异步数据接口的额外参数
				,
				where : {
					keyword : keyword.val()
				}
			});
		}
	};

		// 点击搜索
		$('.search .layui-btn').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});

		// 头工具栏事件
		table.on('toolbar(test)', function(obj) {
			var checkStatus = table.checkStatus(obj.config.id);
			switch (obj.event) {
			case 'getCheckData':
				var data = checkStatus.data;
				var ids="";
				if(data.length>0){
					for(var i=0;i<data.length;i++){
						ids+=data[i].userId+",";
					}
					layer.confirm('是否删除这'+data.length+'条数据',{icon: 3,title:'提示'},function(index){
						//window.location.href="../../user/deleteUserIds?ids="+ids+"";
						deleteUserIds(ids);
					});
				}else{
					layer.alert("请选择要删除的数据");
				}
				break;
			case 'add':
				addUser();
				break;
			}
			;
		});

		// 监听行工具事件
		// 绑定查删改事件test对于table中的lay-filter的值
		table.on('tool(test)', function(obj) {
			var data = obj.data;
			if (obj.event  === 'detail') {
				detailUser(data.userId);
			}else if (obj.event == 'del') {
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
		function detailUser(id) {
			layer.open({
				area : [ '493px','424px' ], // 宽高
				title:'用户详情',
				type : 2,
				fix : false, // 不固定
				maxmin : true,
				content : '../../user/detail?userId=' + id,
			});
		}
		
		// 新增用户信息
		function addUser() {
			layer.open({
				area : [ '493px','424px' ], // 宽高
				title:'增加用户',
				type : 2,
				fix : false, // 不固定
				maxmin : true,
				content : '../../user/add'
			});
		}

		// 修改用户信息
		function editUser(id) {
			layer.open({
				area : [ '493px','424px' ], // 宽高
				title:'修改用户',
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
		
		// 批量删除用户信息
		function deleteUserIds(ids) {
			$.ajax({
				url : "../../user/deleteUserIds?ids=" + ids,
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


