package com.itheima.web.controller.consumer;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.consumer.ConsumerInfo;
import com.itheima.domain.consumer.ConsumerTemplate;
import com.itheima.service.consumer.ConsumerService;
import com.itheima.web.base.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@Controller
@RequestMapping("/puhui/collector")
public class ConsumerController extends BaseController {
    @Autowired
    private ConsumerService consumerService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer status , @RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "3") Integer size){
        PageInfo<ConsumerInfo> consumerInfoPageInfo = consumerService.selectByStatus(status, page, size);
        request.setAttribute("page" , consumerInfoPageInfo);
        request.setAttribute("kw",null);
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
        if(StringUtils.isEmpty(consumerInfo.getId())){//判断id是否存在
            consumerInfo.setCreateBy(loginUser.getId());
            consumerInfo.setUpdateBy(loginUser.getId());
            //调用service开始保存数据
            consumerService.save(consumerInfo);
        }else{
            if (!StringUtils.isEmpty(consumerInfo.getPhone())){
                List<ConsumerInfo> consumerInfos = consumerService.selectByPhone(consumerInfo.getPhone());
                if (consumerInfo!=null && consumerInfos.size()>0){
                    consumerInfo.setId(consumerInfos.get(0).getId());
                    consumerInfo.setUpdateBy(loginUser.getId());
                    consumerService.update(consumerInfo);
                }
            }

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

    @RequestMapping("/upload")
    public String upload(){
        return "consumer/collector/consumer-upload";
    }

    @RequestMapping("/add")
    public String addTemplate(MultipartFile excel){
        System.out.println("success");

        ArrayList<ConsumerTemplate> defeats = new ArrayList<>();
        ArrayList<ConsumerTemplate> success = new ArrayList<>();
        ArrayList<ConsumerTemplate> updates = new ArrayList<>();
        try {
            List<ConsumerTemplate> consumerTemplates = EasyExcel.read(excel.getInputStream()).head(ConsumerTemplate.class).sheet().doReadSync();
            for (ConsumerTemplate consumerTemplate : consumerTemplates) {

                ConsumerInfo consumerInfo = new ConsumerInfo();

                BeanUtils.copyProperties(consumerTemplate,consumerInfo);

                boolean gender = "男".equals(consumerTemplate.getGender())?true:false;

                consumerInfo.setGender(gender);

                if (StringUtils.isEmpty(consumerInfo.getPhone())){
                    defeats.add(consumerTemplate);
                }else {
                    List<ConsumerInfo> consumerInfos = consumerService.selectByPhone(consumerInfo.getPhone());
                    if (consumerInfo!=null && consumerInfos.size()>0){
                        consumerInfo.setId(consumerInfos.get(0).getId());
                        consumerInfo.setUpdateBy(loginUser.getId());
                        if (consumerInfo.getStatus()!=null && consumerInfo.getStatus()==1){
                            Integer update = consumerService.update(consumerInfo);
                            if (update!=1){
                                defeats.add(consumerTemplate);
                            }else {
                                updates.add(consumerTemplate);
                            }
                        }else {
                            defeats.add(consumerTemplate);
                        }

                    }else {
                        consumerInfo.setCreateBy(loginUser.getId());
                        consumerInfo.setUpdateBy(loginUser.getId());
                        Integer save = consumerService.save(consumerInfo);
                        if (save!=1){
                            defeats.add(consumerTemplate);
                        }else {
                            success.add(consumerTemplate);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("success",success);
        request.setAttribute("defeats",defeats);
        request.setAttribute("updates",updates);
        System.out.println("return");
        return "consumer/collector/consumer-after-upload";
    }

    @RequestMapping("/submit")
    public String submit(@RequestParam("ids") List<String> ids){

        for (String id : ids) {
            consumerService.updateStatus(id,2);
        }
        return "redirect:/puhui/collector/list.do";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer status , @RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "3") Integer size,@RequestParam("kw") String kw){

        PageInfo<ConsumerInfo> consumerInfoPageInfo = consumerService.selectByKw(status, page, size, kw);

        request.setAttribute("page" , consumerInfoPageInfo);

        request.setAttribute("kw",kw);

        return "redirect:/puhui/collector/list.do";

    }

}
