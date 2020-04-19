package com.itheima.web.controller.consumer;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.consumer.ConsumerInfo;
import com.itheima.domain.consumer.ConsumerTemplate;
import com.itheima.service.consumer.ConsumerService;
import com.itheima.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/puhui/collector")
public class ConsumerController extends BaseController {
    @Autowired
    private ConsumerService consumerService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer status , @RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "3") Integer size){
        PageInfo<ConsumerInfo> consumerInfoPageInfo = consumerService.selectByStatus(status, page, size);
        request.setAttribute("page" , consumerInfoPageInfo);
        return "consumer/collector/consumer-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "consumer/collector/consumer-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam("id") String id){
        ConsumerInfo consumerInfo = consumerService.selectById(id);
        request.setAttribute("consumer",consumerInfo);
        return "consumer/collector/consumer-update";
    }

    @RequestMapping("/getTemplate")
    public void getTemplate(@RequestParam(defaultValue = "1") Integer status , @RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "3") Integer size){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("普惠金融客户资源表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ConsumerTemplate.class).sheet("模板").doWrite(new ArrayList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/edit")
    public String edit(ConsumerInfo consumerInfo){
        System.out.println(consumerInfo);
        if(StringUtils.isEmpty(consumerInfo.getId())){//判断id是否存在
            consumerInfo.setCreateBy(loginUser.getId());
            consumerInfo.setCreateTime(new Date());
            consumerInfo.setUpdateBy(loginUser.getId());
            consumerInfo.setUpdateTime(new Date());
            //调用service开始保存数据
            consumerService.save(consumerInfo);
        }else{
            consumerInfo.setUpdateBy(loginUser.getId());
            consumerInfo.setUpdateTime(new Date());
            consumerService.update(consumerInfo);
        }
        return "redirect:/puhui/collector/list.do";
    }

    @RequestMapping("/deleteOne")
    public String delete(String id){

        Integer delete = consumerService.delete(id);

        if (delete != 1){
            request.setAttribute("msg","删除出错");
        }

        return "redirect:/puhui/collector/list.do";
    }


}
