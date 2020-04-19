package com.itheima.web.controller.syslog;

import com.github.pagehelper.PageInfo;
import com.itheima.service.syslog.SysLogService;
import com.itheima.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/system/log")
public class SyslogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;
    /**
     * 日志查询
     * @return
     */
    @RequestMapping(value = "/list",name = "日志管理")
    public String list(@RequestParam(defaultValue = "1") Integer page ,@RequestParam(defaultValue = "10") Integer size){
        //根据企业id查询当前企业的日志信息
        PageInfo pageInfo = sysLogService.findAll(companyId , page , size);
        request.setAttribute("page" , pageInfo);
        return "system/log/log-list";
    }
}
