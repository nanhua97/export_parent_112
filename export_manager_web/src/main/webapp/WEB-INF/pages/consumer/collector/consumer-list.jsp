<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Saas-普惠金融</title>
    <meta name="description" content="Saas-普惠金融">
    <meta name="keywords" content="Saas-普惠金融">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
    <script src="../plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<script>
    function deleteById() {
        var id = getCheckId();//动态获得当前选中的id值
        if(id) {
            if(confirm("你确认要删除此条记录吗？")) {
                location.href="/puhui/collector/delete.do?id="+id;
            }
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }
    function submitToCall() {

            var idNodes = document.getElementsByName("ids")
            var ids = [];
            for(var i=0;i<idNodes.length;i++){
                if(idNodes[i].checked){
                    ids.push(idNodes[i].value)
                }
            }
            if(ids.length<=0){
                alert("请勾选待处理的记录")
            }else {
                if(confirm("您是否确定提交？")) {
                    location.href="/puhui/collector/submit.do?ids="+ids;
                }
            }
    }
    function search() {
        location.href="/puhui/collector/search.do?kw="+$("#searchInput").val;
    }
    function checkAll() {
        var idNodes = document.getElementsByName("ids");
        var node = document.getElementById("checkAll");
        if(!node.checked){
            for(var i=0;i<idNodes.length;i++){
                idNodes[i].checked=true;
            }
        }else {
            for(var i=0;i<idNodes.length;i++){
                idNodes[i].checked=!idNodes[i].checked;
            }
        }

    }
</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            普惠金融
            <small>数据上传</small>
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
                <h3 class="box-title">列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">

                    <!--工具栏-->
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="新建" onclick='location.href="/puhui/collector/toAdd.do"'><i class="fa fa-file-o"></i> 新建</button>
                                <button type="button" class="btn btn-default" title="删除" onclick='deleteById()'><i class="fa fa-trash-o"></i> 删除</button>
                                <button type="button" class="btn btn-default" title="提交" onclick='submitToCall()'><i class="fa fa-trash-o"></i> 提交</button>
                                <button type="button" class="btn btn-default" title="刷新" onclick="window.location.reload();"><i class="fa fa-refresh"></i> 刷新</button>
                            </div>
                        </div>
                    </div>
                    <div class="box-tools pull-right">
                        <div class="has-feedback">
                            <c:if test="${kw!=null}">
                                <input type="text" id="searchInput" value="${kw}" class="form-control input-sm" placeholder="搜索">
                            </c:if>
                            <c:if test="${kw==null}">
                                <input type="text" id="searchInput" class="form-control input-sm" placeholder="搜索">
                            </c:if>
                            <span class="glyphicon glyphicon-search form-control-feedback" onclick="search()"></span>
                        </div>
                    </div>
                    <!--工具栏/-->

                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="" style="padding-right:0px;">
                                <input id="checkAll" type="checkbox" onclick="checkAll()">
                            </th>
                            <th class="sorting">名字</th>
                            <th class="sorting">性别</th>
                            <th class="sorting">年龄</th>
                            <th class="sorting">电话</th>
                            <th class="sorting">邮箱</th>
                            <th class="sorting">职业</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="item">
                            <tr>
                                <td><input name="ids" value="${item.id}" type="checkbox"></td>
                                <td>${item.name}</td>
                                <td>${item.gender?"男":"女"}</td>
                                <td>${item.age}</td>
                                <td>${item.phone}</td>
                                <td>${item.email}</td>
                                <td>${item.prefessor}</td>
                                <td class="text-center">
                                    <c:if test="${item.status==1}">
                                        <button type="button" class="btn bg-olive btn-xs" onclick='location.href="/puhui/collector/toUpdate.do?id=${item.id}"'>编辑</button>
                                    </c:if>
                                    <c:if test="${item.status==7}">
                                        <button type="button" class="btn bg-olive btn-xs" style="background-color: red" onclick='location.href="/puhui/collector/toUpdate.do?id=${item.id}"'>编辑</button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">

                <div class="box-footer">
                    <jsp:include page="../../common/page.jsp">
                        <jsp:param value="${ctx}/puhui/collector/list.do" name="pageUrl"/>
                    </jsp:include>
                </div>
                <!-- /.box-footer-->

            </div>
        </div>
    </section>
</div>
</body>

</html>