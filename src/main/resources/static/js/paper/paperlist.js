layui.use('table',function() {
					var table = layui.table;
					var laypage = layui.laypage;

						
					// table.render()方法返回一个对象：var tableIns =
					// table.render(options)，可用于对当前表格进行“重载”等操作
					table.render({
								skin : 'line', // 行边框风格
								// ,even: true //开启隔行背景
								// ,size: 'sm' //小尺寸的表格
								elem : '#demo', // 表格id
								url : '../../paper/findPaper',
								title : '文章列表',
								toolbar : "#toolbarDemo", // 表格外操作按钮
								page : true,
								limits : [ 10, 20, 50, 100 ],
								id : 'tableDate',
								cellMinWidth : 60,
								height : 'full-210',
								cols : [ [
										{
											type : 'checkbox',
											fixed : 'left'
										},
										{
											field : 'id',
											title : '文章ID',
											sort : true
										},
										{
											templet:'<div>{{d.paperType.typeName}}</div>',
											title : '文章标签'
											
										},
										{
											field : 'title',
											title : '文章标题'
										},
										{
											field : 'author',
											title : '作者'
										},
										{
											field : 'uploadTime',
											title : '上传时间',
											sort : true
										},
										{
											field : 'status',
											title : '发布状态',

											templet : function(d) {
												if (d.status == 0) {
													return '<button class="layui-btn layui-btn-xs layui-btn-primary">待发布</button>';
												} else {
													return '<button class="layui-btn layui-btn-xs">已发布</button>';
												}
											}
										}, {
											fixed : 'right',
											title : '操作',
											width : 180,
											align : 'center',
											toolbar : '#barDemo'
										} ] ]
							});
				
					
					
					// 获取搜索框信息
					var $ = layui.$, active = {
						search : function() {
							var author=$('#author').val();
							var title=$('#title').val();
							var typename=$('#typename').val();
							console.log("1"+author+"2"+title+"3"+typename);
							table.reload('tableDate', {
								// 从第 1 页开始
								page : {
									curr : 1
								}
								// 设定异步数据接口的额外参数
								,
								where : {
									'author' : author,
									'title' : title,
									'typename' : typename,
								}
							});
						}
					};

					// 点击搜索
					$('.search .layui-btn').on('click', function() {
						var type = $(this).data('type');
						active[type] ? active[type].call(this) : '';
					});
					
					//点击刷新
					// 点击搜索
					$('.search .layui-btn-warm').on('click', function() {
						location.reload();
					});

					// 头工具栏事件
					table.on('toolbar(test)', function(obj) {
						var checkStatus = table.checkStatus(obj.config.id);
						switch (obj.event) {
						case 'getCheckData':
							var data = checkStatus.data;
							var ids = "";
							if (data.length > 0) {
								for (var i = 0; i < data.length; i++) {
									ids += data[i].id + ",";
								}
								layer.confirm('是否删除这' + data.length + '条数据', {
									icon : 3,
									title : '提示'
								}, function(index) {

									deletePaperIds(ids);
								});
							} else {
								layer.alert("请选择要删除的数据");
							}
							break;
						case 'add':
							addPaper();
							break;
						}
						;
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
								delPaper(data.id);
								layer.close(index);
							});
						} else if (obj.event == 'edit') {
							editPaper(data.id);
						}
					});

					// 新增文章信息
					function addPaper() {
						layer.open({
							area : [ '493px', '424px' ], // 宽高
							title : '添加文章',
							type : 2,
							fix : false, // 不固定
							maxmin : true,
							content : '../../paper/add'
						});
					}

					// 修改文章信息
					function editPaper(id) {
						layer.open({
							area : [ '493px', '424px' ], // 宽高
							title : '修改文章',
							type : 2,
							fix : false, // 不固定
							maxmin : true,
							content : '../../paper/edit?id=' + id,
						});
					}

					// 删除文章信息
					function delPaper(id) {
						$.ajax({
							url : "../../paper/del?id=" + id,
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

					// 批量删除文章信息
					function deletePaperIds(ids) {
						$.ajax({
							url : "../../paper/deletePaperIds?ids=" + ids,
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
