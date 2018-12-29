# 前端
## Layui + Jquery
# 后端
## SpringBoot+mybatis+pagehelper+Shiro

# 总体介绍
+ 登陆界面： 使用shiro权限框架来实现安全管理，用户表中设计的密码生成方式是通过MD5加密并使用username+salt作为新盐值Salt
+ 主界面： 左侧菜单通过thymeleaf模版引擎来展示出来
+ 用户管理界面： 使用Layui-table来展示用户数据，分页功能是Layui-table结合后端的pagehelper。
+ 菜单权限树： 使用Layui-Xtree来完成，只需在后端自定义一个符合他的实体类即可
