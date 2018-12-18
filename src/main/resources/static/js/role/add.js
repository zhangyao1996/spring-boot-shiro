//定义树
var xtree = null;
(function() {
	confirm();
	initTree();
})();

function initTree() {
	layui.use([ 'form' ], function() {
		var form = layui.form;
		var $ = layui.$;

		$.get("../../role/getAll", function(nodes) {
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

function confirm() {
	layui.use([ 'form' ], function() {
		var form = layui.form;
		var $ = layui.$;
		form.on('submit(add)', function(data) {

			var rolename = $("#rolename").val();
			console.log(rolename);
			var allNodes=xtree.GetAllCheckBox();
			var nodes="";
			for(var i=0;i<allNodes.length;i++){
				if(allNodes[i].checked){
					nodes+=allNodes[i].value+",";
				}
			}
			console.log(nodes);
			$.ajax({
				url : '../../role/add',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify({
					roleName : rolename,
					nodes : nodes
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
		})
	})
}

// var roleId = HXcommon.getUrlParam('roleId');
/*
 * $("#submit1").click(function(){ var role = {}; //role.roleId =
 * $("#roleId").val(); role.roleName = $("#roleName").val(); role.description =
 * $("#description").val(); role.roleAsign = $("#roleAsign").val();
 * 
 * role.nodes="";
 *  // 获取被选中的结点 var oCks = xtree1.GetAllCheckBox(); for (var i = 0; i <
 * oCks.length; i++) { if (oCks[i].checked == true) { role.nodes +=
 * oCks[i].value + ","; } } console.log(role.nodes);
 * HXcommon.ajaxController("/sys/role/add","POST",role,function(json){
 * console.log(json); if(json.code == 200){ if(json.data==true){
 * parent.HXcommon.layer.alert('添加成功', function() { parent.location.reload(); //
 * 父页面刷新 HXcommon.layer.closeLayer(); }); }else{
 * parent.HXcommon.layer.alert('数据有误:角色名,权限名为空或已存在'); } }else{
 * parent.HXcommon.layer.alert('请选择权限路径'); } }); }); //重置操作
 * $("#reset1").click(function(){ //HXcommon.layer.alert('as');
 * document.getElementById("addRoleForm").reset(); });
 */

