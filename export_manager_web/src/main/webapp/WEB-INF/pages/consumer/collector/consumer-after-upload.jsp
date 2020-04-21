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
    function deleteById() {
        var id = getCheckId();//动态获得当前选中的id值
        if(id) {
            if(confirm("你确认要删除此条记录吗？")) {
                location.href="/company/delete.do?id="+id;
            }
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
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
                <h3 class="box-title">上传成功记录,共${success.size()}条</h3>
            </div>

            <div class="box-body">
                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="sorting">名字</th>
                            <th class="sorting">性别</th>
                            <th class="sorting">年龄</th>
                            <th class="sorting">电话</th>
                            <th class="sorting">邮箱</th>
                            <th class="sorting">职业</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${success}" var="item">
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.gender}</td>
                                <td>${item.age}</td>
                                <td>${item.phone}</td>
                                <td>${item.email}</td>
                                <td>${item.prefessor}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">上传失败记录,共${defeats.size()}条</h3>
            </div>

            <div class="box-body">
                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="sorting">名字</th>
                            <th class="sorting">性别</th>
                            <th class="sorting">年龄</th>
                            <th class="sorting">电话</th>
                            <th class="sorting">邮箱</th>
                            <th class="sorting">职业</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${defeats}" var="item">
                            <tr>
                                <td>${item.name}</td>
                                <td>${item.gender}</td>
                                <td>${item.age}</td>
                                <td>${item.phone}</td>
                                <td>${item.email}</td>
                                <td>${item.prefessor}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">数据更新记录,共${updates.size()}条</h3>
            </div>

            <div class="box-body">
                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="" style="padding-right:0px;"></th>
                            <th class="sorting">名字</th>
                            <th class="sorting">性别</th>
                            <th class="sorting">年龄</th>
                            <th class="sorting">电话</th>
                            <th class="sorting">邮箱</th>
                            <th class="sorting">职业</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${update}" var="item">
                            <tr>
                                <td><input name="ids" value="${item.id}" type="checkbox"></td>
                                <td>${item.name}</td>
                                <td>${item.gender}</td>
                                <td>${item.age}</td>
                                <td>${item.phone}</td>
                                <td>${item.email}</td>
                                <td>${item.prefessor}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</div>
</body>

</html>