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
</head>
<body>
<div id="frameContent">
    <!-- 内容头部 -->
    <section class="content-header" class="content-wrapper" style="margin-left:0px;">
        <h1>
            客户管理
            <small>客户表单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">客户管理</a></li>
            <li class="active">客户表单</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">客户信息</div>
            <form id="editForm" action="/puhui/collector/edit.do" method="post">
                <input type="hidden" name="id" value="${consumer.id}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">名字</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="名字" name="name" value="${consumer.name}">
                    </div>

                    <div class="col-md-2 title">性别</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio"  checked name="gender" value="true">男</label></div>
                            <div class="radio"><label><input type="radio" name="gender" value="false">女</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">年龄</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="年龄" name="age" value="${consumer.age}">
                    </div>

                    <div class="col-md-2 title">电话</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="电话" name="phone" value="${consumer.phone}">
                    </div>

                    <div class="col-md-2 title">邮箱</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="邮箱" name="email" value="${consumer.email}">
                    </div>

                    <div class="col-md-2 title">职业</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="职业" name="prefessor" value="${consumer.prefessor}">
                    </div>

                    <div class="col-md-2 title rowHeight2x">备注</div>
                    <div class="col-md-10 data rowHeight2x">
                        <textarea class="form-control" rows="3" name="message">${consumer.message}</textarea>
                    </div>
                </div>
            </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

</div>
<!-- 内容区域 /-->
</body>

</html>