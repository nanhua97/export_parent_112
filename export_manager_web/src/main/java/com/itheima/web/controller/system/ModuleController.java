package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.service.system.DeptService;
import com.itheima.service.system.ModuleService;
import com.itheima.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;


    /**
     * 模块列表查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", name = "模块列表查询")
    //Require 必须 的权限
    //@RequiresPermissions("模块管理")
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        PageInfo pageInfo = moduleService.findAll(page, size);
        request.setAttribute("page" , pageInfo);
        return "system/module/module-list";
    }



    @Autowired
    private DeptService deptService;

    /**
     * 模块跳转新增
     * @return
     */
    @RequestMapping(value = "/toAdd" , name = "模块跳转新增")
    public String toAdd(){
        //查询所有的模块数据 构建下拉框
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus" , moduleList);
        return "system/module/module-add";
    }


    /**
     * 增加和修改的方法
     * @return
     */
    @RequestMapping(value = "/edit" , name = "模块新增或修改")
    public String edit(Module module){
        if(StringUtils.isEmpty(module.getId())){
            moduleService.save(module);
        }else{
            moduleService.update(module);
        }


        return "redirect:/system/module/list.do";
    }

    /**
     * 跳转模块修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "跳转模块修改页面")
    public String toUpdate(String id){
        //查询原有数据
        Module module = moduleService.findById(id);
        request.setAttribute("module" ,module);

        //查询所有的模块数据 构建下拉框
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus" , moduleList);
        return "system/module/module-update";
    }

    /**
     * 删除模块
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete" , name = "删除模块")
    public String delete(String id){
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }

    @RequestMapping("/changeModule")
    @ResponseBody
    public List<Module> changeModule(Integer ctype){
        System.out.println(ctype);
        //根据ctype查询数据库得到指定的菜单信息
        List<Module> moduleList = moduleService.findByCtype(ctype);
        return moduleList;
    }
}
