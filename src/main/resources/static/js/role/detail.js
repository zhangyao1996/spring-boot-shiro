//递归设置nodes中key-value
function disabled(nodes){
	for(var i=0;i<nodes.length;i++){
		nodes[i]["disabled"]=true;
		if(nodes[i].data.length>0){
			disabled(nodes[i].data);
		}
	}	
	return nodes;
}


layui.use([ 'form' ], function() {
	var form = layui.form;
	var $ = layui.$;
	var xtree;
	var roleId = $("#id").val();

	$.get("../../role/getPermissions?roleId="+roleId, function(nodes) {
		console.log(nodes);
		disabled(nodes)
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
			}
			, icon: {                 //图标样式 （选填）
                open: "&#xe625"      //节点打开的图标（使用layui的图标，这里只填入图标代号即可）
                , close: "&#xe623"   //节点关闭的图标
                , end: ""     //末尾节点的图标
            }

		});
	});

});
