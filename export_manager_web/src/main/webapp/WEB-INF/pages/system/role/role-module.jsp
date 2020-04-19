<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <base href="${ctx}/">
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
    <link rel="stylesheet" href="plugins/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="plugins/ztree/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="plugins/ztree/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="plugins/ztree/js/jquery.ztree.excheck-3.5.js"></script>

    <SCRIPT type="text/javascript">
        /*  <!--  支持ie //-->*/
        var setting = {//设置 可以设置自己需要的属性参数
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var zNodes ; /*[数据需要从数据库查询出来
            { id:11, pId:1, name:"随意勾选 1-1"},
            { id:111, pId:11, name:"随意勾选 1-1-1"},
            { id:112, pId:11, name:"随意勾选 1-1-2"},
            { id:12, pId:1, name:"随意勾选 1-2", open:true},
            { id:121, pId:12, name:"随意勾选 1-2-1"},
            { id:122, pId:12, name:"随意勾选 1-2-2"},
            { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
            { id:21, pId:2, name:"随意勾选 2-1"},
            { id:22, pId:2, name:"随意勾选 2-2", open:true},
            { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
            { id:222, pId:22, name:"随意勾选 2-2-2"},
            { id:23, pId:2, name:"随意勾选 2-3"},
            { id:1, pId:0, name:"随意勾选 1", open:true},
            { id:3333333, pId:1234567, name:"随意勾选 333" , open:true},
            { id:123, pId:3333333, name:"随意勾选 二级菜单"}
        ];*/
        var treeObject ;
        //$(function(){}) 页面加载    $(document).ready(function(){} 页面加载
        $(document).ready(function(){

            //页面加载完成后 可以使用ajax获得服务器传输的数据 动态构建树结构
            $.get("/system/role/initTree.do?roleid=${role.id}", function(data){
                console.log(data);//打印日志
                //初始化树结构
                //参数1: 动态的获得某个ul  动态渲染成 树结构
                //参数2: setting需要的特殊参数 参考api cn
                //参数3: 数据节点
                treeObject = $.fn.zTree.init($("#treeDemo"), setting, data);
                treeObject.expandAll(true);//展开所有节点
            });

        });

    </SCRIPT>
    <script>
        //点击保存操作
        function submitCheckedNodes(){
            //1.数据 模块的ids  角色的id 获得树结构中的被选的模块数据
            //treeObject 树对象
            //var selectNodes = treeObject.getSelectedNodes();
            //console.log(selectNodes);
            var checkedNodes = treeObject.getCheckedNodes(true); //getCheckedNodes(true) 获得被选中的复选框
            // <input type="hidden" id="moduleIds" name="moduleIds" value=""/> 放在表单中提交
            var moduleIds= "";
            for(var i = 0 ;i< checkedNodes.length; i ++){
                //console.log(checkedNodes[i].id);
                moduleIds += checkedNodes[i].id +",";//在后台需要切割字符串
            }

            if(moduleIds.length>0){
                //在页面上 截取字符串
                moduleIds = moduleIds.substring(0 , moduleIds.length-1) ;
                document.getElementById("moduleIds").value=moduleIds;
            }

            //2.如何提交
            document.icform.submit();
        }
    </script>
</head>

<body style="overflow: visible;">
<div id="frameContent" class="content-wrapper" style="margin-left:0px;height: 1200px" >
    <section class="content-header">
        <h1>
            菜单管理
            <small>菜单列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">角色 [${role.name}] 权限列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--工具栏-->
                    <div class="box-tools text-left">
                        <button type="button" class="btn bg-maroon" onclick="submitCheckedNodes();">保存</button>
                        <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
                    </div>
                    <!--工具栏/-->
                    <!-- 树菜单 -->
                    <form name="icform" method="post" action="/system/role/updateRoleModule.do">
                        <input type="hidden" name="roleid" value="${role.id}"/>
                        <input type="hidden" id="moduleIds" name="moduleIds" value=""/>
                        <div class="content_wrap">
                            <div class="zTreeDemoBackground left" style="overflow: visible">
                                <ul id="treeDemo" class="ztree"></ul>
                            </div>
                        </div>

                    </form>
                    <!-- 树菜单 /-->

                </div>
                <!-- /数据表格 -->

            </div>
            <!-- /.box-body -->
        </div>
    </section>
</div>
</body>
</html>