package demo.web.controller.consumer;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import demo.domain.consumer.ConsumerInfo;
import demo.domain.consumer.ConsumerTemplate;
import demo.service.consumer.ConsumerService;
import demo.web.base.BaseController;
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

@Controller
@RequestMapping("/puhui")
public class ConsumerController extends BaseController {
    @Autowired
    private ConsumerService consumerService;

    /**
     * 收集专员看到的客户列表
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/collector/list")
    public String collectorList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(7);
        PageInfo<ConsumerInfo> consumerInfoPageInfo = consumerService.selectByStatus(integers, page, size);
        request.setAttribute("page", consumerInfoPageInfo);
        request.setAttribute("kw", null);
        return "consumer/collector/consumer-list";
    }

    /**
     * 外呼专员看到的列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/caller/list")
    public String callerList(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10") Integer size){
        List<Integer> integers = new ArrayList<>();
        integers.add(2);
        integers.add(3);
        PageInfo<ConsumerInfo> consumerInfoPageInfo = consumerService.selectByStatus(integers, page, size);
        request.setAttribute("page" , consumerInfoPageInfo);
        request.setAttribute("kw",null);
        return "consumer/caller/caller-list";
    }
    /**
     * 客户经理看到的列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/manager/list")
    public String managerList(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10") Integer size){
        List<Integer> integers = new ArrayList<>();
        integers.add(4);
        integers.add(6);
        integers.add(8);
        PageInfo<ConsumerInfo> consumerInfoPageInfo = consumerService.selectByStatus(integers, page, size);
        request.setAttribute("page" , consumerInfoPageInfo);
        request.setAttribute("kw",null);
        return "consumer/manager/manager-list";
    }

    /**
     * 客户添加页面
     * @return
     */
    @RequestMapping("/collector/toAdd")
    public String toAdd(){
        return "consumer/collector/consumer-add";
    }

    /**
     * 客户更新页面
     * @param id
     * @return
     */
    @RequestMapping("/collector/toUpdate")
    public String toUpdate(@RequestParam("id") String id){
        ConsumerInfo consumerInfo = consumerService.selectById(id);
        request.setAttribute("consumer",consumerInfo);
        return "consumer/collector/consumer-update";
    }

    /**
     * 获取客户信息上传模板
     * @param status
     * @param page
     * @param size
     */
    @RequestMapping("/collector/getTemplate")
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

    /**
     * 客户信息保存/更新
     * @param consumerInfo
     * @return
     */
    @RequestMapping(value = "/collector/edit")
    public String edit(ConsumerInfo consumerInfo){
        if(StringUtils.isEmpty(consumerInfo.getId())){//判断id是否存在
            consumerInfo.setCreateBy(loginUser.getId());
            consumerInfo.setUpdateBy(loginUser.getId());
            //调用service开始保存数据
            consumerService.save(consumerInfo);
        }else{
            if (!StringUtils.isEmpty(consumerInfo.getPhone())){
                List<Integer> integers = new ArrayList<>();
                integers.add(1);
                integers.add(7);
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

    /**
     * 客户信息删除
     * @param id
     * @return
     */
    @RequestMapping("/collector/deleteOne")
    public String delete(String id){

        Integer delete = consumerService.delete(id);

        if (delete != 1){
            request.setAttribute("msg","删除出错");
        }

        return "redirect:/puhui/collector/list.do";
    }

    /**
     * 客户信息批量上传页面
     * @return
     */
    @RequestMapping("/collector/upload")
    public String upload(){
        return "consumer/collector/consumer-upload";
    }

    /**
     *  客户信息批量功能
     * @param excel
     * @return
     */
    @RequestMapping("/collector/add")
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

                System.out.println(consumerInfo);

                if (StringUtils.isEmpty(consumerInfo.getPhone())){
                    defeats.add(consumerTemplate);
                }else {
                    List<Integer> integers = new ArrayList<>();
                    List<ConsumerInfo> consumerInfos = consumerService.selectByPhone(consumerInfo.getPhone());
                    if (consumerInfo!=null && consumerInfos.size()>0){

                        ConsumerInfo consumerInfo1 = consumerInfos.get(0);

                        consumerInfo.setId(consumerInfo1.getId());
                        consumerInfo.setCreateTime(consumerInfo1.getCreateTime());
                        consumerInfo.setStatus(consumerInfo1.getStatus());
                        consumerInfo.setCreateBy(consumerInfo1.getCreateBy());
                        consumerInfo.setUpdateBy(loginUser.getId());
                        consumerInfo.setUpdateTime(new Date());


                        if (consumerInfo1.getStatus()==1 || consumerInfo1.getStatus()==7){

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
        return "consumer/collector/consumer-after-upload";
    }

    /**
     * 收集专员信息提交
     * @param ids
     * @return
     */
    @RequestMapping("/collector/submit")
    public String collectorSubmit(@RequestParam("ids") List<String> ids){

        for (String id : ids) {
            consumerService.updateStatus(id,2,loginUser.getId());
        }
        return "redirect:/puhui/collector/list.do";
    }

    /**
     * 外呼专员信息提交给客户经理
     * 外呼专员呼不通的客户回退给收集员
     * 外呼专员标记外呼暂无意向的客户
     * @param ids
     * @return
     */
    @RequestMapping("/caller/change")
    public String callerSubmit(@RequestParam("ids") List<String> ids,@RequestParam("status") Integer status){

        for (String id : ids) {
            consumerService.updateStatus(id,status,loginUser.getId());
        }
        return "redirect:/puhui/caller/list.do";
    }

    /**
     * 外呼专员信息提交给客户经理
     * 外呼专员呼不通的客户回退给收集员
     * 外呼专员标记外呼暂无意向的客户
     * @param ids
     * @return
     */
    @RequestMapping("/manager/change")
    public String managerSubmit(@RequestParam("ids") List<String> ids,@RequestParam("status") Integer status){

        for (String id : ids) {
            consumerService.updateStatus(id,status,loginUser.getId());
        }
        return "redirect:/puhui/manager/list.do";
    }

    /**
     * 根据姓名跟手机号进行模糊查询
     * @param status
     * @param page
     * @param size
     * @param kw
     * @return
     */
    @RequestMapping("/collector/search")
    public String search(@RequestParam(defaultValue = "1") Integer status , @RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10") Integer size,@RequestParam("kw") String kw){

        PageInfo<ConsumerInfo> consumerInfoPageInfo = consumerService.selectByKw(status, page, size, kw);

        request.setAttribute("page" , consumerInfoPageInfo);

        request.setAttribute("kw",kw);

        return "redirect:/puhui/collector/list.do";

    }


}
