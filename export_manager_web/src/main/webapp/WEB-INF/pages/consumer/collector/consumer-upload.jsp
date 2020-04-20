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
    function getTemplate() {

    }
    function upload(){

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

        <div class="panel panel-default">
            <div class="panel-heading">客户信息上传</div>
            <form id="uploadForm" action="/puhui/collector/add.do" method="post" enctype="multipart/form-data">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">客户信息</div>
                    <div class="col-md-4 data">
                        <input type="file" class="form-control" placeholder="选择数据文件" name="excel">
                    </div>
                </div>
            </form>
        </div>

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="col-md-1"></div>
            <input type="button" value="数据上传" class="btn bg-maroon" onclick='document.getElementById("uploadForm").submit()'>
            <div class="col-md-1"></div>
            <a href="/puhui/collector/getTemplate.do" style="color: black"><button class="btn bg-default">模板下载</button></a>
        </div>
    </section>
</div>
</body>

</html>