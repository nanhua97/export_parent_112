<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
    <script src="../plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<script>
    function cancleById() {
        var idNodes = document.getElementsByName("ids")
        var ids = [];
        for(var i=0;i<idNodes.length;i++){
            if(idNodes[i].checked){
                ids.push(idNodes[i].value)
            }
        }
        if(ids.length<=0){
            alert("请勾选待回退的客户信息")
        }else {
            if(confirm("是否确定将客户信息记录回退给收集员？")) {
                location.href="/puhui/caller/change.do?ids="+ids+"&status="+7;
            }
        }
    }
    function submitToManager() {

        var idNodes = document.getElementsByName("ids")
        var ids = [];
        for(var i=0;i<idNodes.length;i++){
            if(idNodes[i].checked){
                ids.push(idNodes[i].value)
            }
        }
        if(ids.length<=0){
            alert("请勾选意向客户")
        }else {
            if(confirm("您是否确定提交？")) {
                location.href="/puhui/caller/change.do?ids="+ids+"&status="+4;
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
            <small>外呼管理</small>
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
                <h3 class="box-title">客户信息列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">

                    <!--工具栏-->
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="回退给收集员" onclick='cancleById()'><i class="fa fa-trash-o"></i> 回退</button>
                                <button type="button" class="btn btn-default" title="提交给客户经理" onclick='submitToManager()'><i class="fa fa-trash-o"></i> 提交</button>
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
                            <th class="text-center">外呼情况</th>
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
                                    <c:if test="${item.status==2}">
                                        <button type="button" class="btn bg-olive btn-xs" onclick='location.href="/puhui/caller/change.do?ids=${item.id}&status=3"'>
                                            未外呼
                                        </button>
                                    </c:if>
                                    <c:if test="${item.status==3}">
                                        <font color="green">外呼(无意向客户)</font>
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