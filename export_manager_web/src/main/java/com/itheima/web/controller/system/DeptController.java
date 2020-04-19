package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import com.itheima.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;
    /**
     * 分页查询部门列表数据 根据 企业id查询
     * @return
     */
    @RequestMapping(value = "/list", name = "部门列表查询")
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){

        //分页查询部门列表数据
        //select * from pe_dept limit 分页
        //String companyId = "1";//需要登陆后 拿到当前用户 的企业id

        //分页查询
        PageInfo pageInfo = deptService.findAll(page, size , companyId);
        //存入页面
        request.setAttribute("page" , pageInfo);
        return "system/dept/dept-list";
    }

    /**
     * 部门跳转新增
     * @return
     */
    @RequestMapping(value = "/toAdd" , name = "部门跳转新增")
    public String toAdd(){
        //查询所有部门的数据 此处不能分页
        //String companyId = "1";
        //返回的不是分页 使用就是list集合
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "system/dept/dept-add";
    }


    /**
     * 增加和修改的方法
     * @return
     */
    @RequestMapping(value = "/edit" , name = "部门新增或修改")
    public String edit(Dept dept){
        //添加部门的时候 必须添加 企业的名称 和企业的id
        //String companyId = "1";
        //String companyName = "传智播客"; //以后从登陆用户中获得
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        if(StringUtils.isEmpty(dept.getId())){//新增
            deptService.save(dept);
        }else{
            //修改
            deptService.update(dept);
        }
        return "redirect:/system/dept/list.do";
    }

    /**
     * 跳转部门修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "跳转部门修改页面")
    public String toUpdate(String id){
        //String companyId="1";
        //1.回显部门的信息
        Dept dept = deptService.findById(id);
        request.setAttribute("dept" , dept);
        //2.回显上级部门信息
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);

        return "system/dept/dept-update";
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete" , name = "删除部门")
    public String delete(String id){
        deptService.delete(id);
        return "redirect:/system/dept/list.do";
    }
}
