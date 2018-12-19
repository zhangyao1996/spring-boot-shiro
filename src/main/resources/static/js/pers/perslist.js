 /*使用模块加载的方式 加载文件*/
    layui.config({
        base: '../../lib/layui/css/modules/'
    }).extend({
        treetable: 'treetable-lay/treetable'
    }).use(['layer', 'table', 'treetable'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var layer = layui.layer;
        var treetable = layui.treetable;

        // 渲染表格
        var renderTable = function () {
            layer.load(2);
            treetable.render({
                treeColIndex: 1,//树形图标显示在第几列
                treeSpid: 0,//最上级的父级id
                treeIdName: 'id',//id字段的名称
                treePidName: 'parentId',//pid字段的名称
                treeDefaultClose: false,//是否默认折叠
                treeLinkage: true,//父级展开时是否自动展开所有子级
                elem: '#permissionTable',
                url: '../../pers/perstable',
                page: false,
                height: 400,
                cols: [[
                    {type: 'numbers', title: '编号'},
                    {field: 'resName', title: '权限名称'},
                    {field: 'url', title: '权限路径'},
                    {field: 'permission', title: '权限简介'},
                    {field: 'resType', title: '权限类型',
                    	templet: function(d){
                    		if(d.resType=="menu"){
                    			return '菜单';
                    		}else{
                    			return '按钮';
                    		}
                        }	
                    },
                    {templet: '#auth-state', width: 180, align: 'center',title: '操作'}
                ]],
                done: function () {
                    layer.closeAll('loading');
                }
            });
        };

        renderTable();
        
		//触发三个button按钮
        $('#btn-expand').click(function () {
            treetable.expandAll('#permissionTable');
        });

        $('#btn-fold').click(function () {
            treetable.foldAll('#permissionTable');
        });

        $('#btn-refresh').click(function () {
            renderTable();
        });
		
        $('#btn-add').click(function () {
        	addPermission();
        });
        
        //添加一级菜单
        function addPermission(){
			layer.open({
				area : [ '493px','424px' ], // 宽高
				title:'增加权限菜单',
				type : 2,
				fix : false, // 不固定
				maxmin : true,
				content : '../../pers/add?id=' + 0,
			});
		}
        
        //监听工具条
        table.on('tool(permissionTable)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
			if(data.resName!=null){
				if (layEvent === 'del') {
					layer.confirm('真的删除'+data.resName+'吗？', {
						skin : 'layui-layer-lan',
						closeBtn : 0,
						anim : 4
					// 动画类型
					}, function(index) {
						deletePers(data.id);
						layer.close(index);
					});
	            } else if (layEvent === 'edit') {
	                editPers(data.id);
	            } else if (layEvent === 'add'){
	            	 //layer.msg('增加' + data.id);
	            	 addPers(data.id);
				}
			}
        });
        
        
        // 删除权限信息
		function deletePers(id) {
			$.ajax({
				url : "../../pers/del?id=" + id,
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
		
		// 修改用户信息
		function editPers(id) {
			layer.open({
				area : [ '493px','424px' ], // 宽高
				title:'修改用户',
				type : 2,
				fix : false, // 不固定
				maxmin : true,
				content : '../../pers/edit?id=' + id,
			});
		}
		
		function addPermission(){
			layer.open({
				area : [ '493px','424px' ], // 宽高
				title:'增加权限菜单',
				type : 2,
				fix : false, // 不固定
				maxmin : true,
				content : '../../pers/add?id=' + 0,
			});
		}
		
		function addPers(id) {
			layer.open({
				area : [ '493px','424px' ], // 宽高
				title:'增加权限菜单',
				type : 2,
				fix : false, // 不固定
				maxmin : true,
				content : '../../pers/add?id=' + id,
			});
		}
		
    });
