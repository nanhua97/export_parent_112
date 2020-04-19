package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.Role;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.RoleService;
import com.itheima.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;


    /**
     * 角色列表查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", name = "角色列表查询")
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        //分页查询
        PageInfo pageInfo = roleService.findAll(page, size, super.companyId);
        //分页显示
        request.setAttribute("page" , pageInfo);
        return "system/role/role-list";
    }




    /**
     * 角色跳转新增
     * @return
     */
    @RequestMapping(value = "/toAdd" , name = "角色跳转新增")
    public String toAdd(){


        return "system/role/role-add";
    }


    /**
     * 增加和修改的方法
     * @return
     */
    @RequestMapping(value = "/edit" , name = "角色新增或修改")
    public String edit(Role role){
        role.setCompanyId(super.companyId);//父类中获得
        role.setCompanyName(super.companyName);//父类中获得
        if(StringUtils.isEmpty(role.getId())){
            roleService.save(role);
        }else{
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }

    /**
     * 跳转角色修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "跳转角色修改页面")
    public String toUpdate(String id){
        Role role = roleService.findById(id);
        request.setAttribute("role" , role);
        return "system/role/role-update";
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete" , name = "删除角色")
    public String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }
    /**
     * 角色和模块权限分配页面
     * @param roleid
     * @return
     */
    @RequestMapping(value = "/roleModule" , name = "角色和模块权限分配页面")
    public String roleModule(String roleid){
        //根据角色的id查询出角色的信息
        Role role = roleService.findById(roleid);
        request.setAttribute("role" , role);
        return "system/role/role-module";
    }

    @Autowired
    private ModuleService moduleService;

    /**
     * 初始化树结构
     * 页面需要的数据
     * [
     *      {id:2, pId:0, name:"随意勾选 2", checked:true, open:true} ,
     *      {id:2, pId:0, name:"随意勾选 2", checked:true},  checked:true 当前角色所具有的权限
     *      {id:2, pId:0, name:"随意勾选 2"}
     * ]
     * [] 数组 集合
     * {} javaBean Map
     * javaBean 对象 构建一个对象 对象中有多少属性  对象中不管有多少属性 一定出现在json中   checked:""
     * Map 可以自动往里封装数据
     * @return
     */
    @RequestMapping(value = "/initTree" , name = "初始化树结构")
    @ResponseBody//异步请求的方法
    public List<Map> initTree(String roleid){ //角色id  没有roleid 获得不了当前角色的权限信息

        //1.定义List<Map>
        List<Map> list = new ArrayList<Map>();
        //3.查询数据库得到数据-> 从模块表中获得
        List<Module> moduleList = moduleService.findAll(); //所有的模块数据 1 2 3 4 5

        //4.根据角色的id 获得当前角色的模块数据  2 3 4
        //dao baseResultMap映射的实体是module
        List<Module> roleModuleList = moduleService.findByRid(roleid);
        System.out.println(roleModuleList);

        //遍历数据 将List<Module>  转换成 List<Map>
        for (Module module : moduleList) {
            Map<String , Object> map = new HashMap<>();//创建对象
            //{id:2, pId:0, name:"随意勾选 2"}
            map.put("id" , module.getId());
            map.put("pId" , module.getParentId());
            map.put("name" , module.getName());

            //判断 角色的模块id数量一定小于等于 所有的模块数据  如果当前角色的模块在所有的模块中 应该加上 checked属性
            /*for (Module roleModule : roleModuleList) { //遍历 用户的模块对象
                if(module.getId().equals(roleModule.getId())){ //id一致 数据就一致
                    map.put("checked" , true);//不能够随便加
                }
            }*/
            //roleModuleList 2 3 4
            //集合 包含 对象 -> 比较的地址址
           if(roleModuleList.contains(module)){ //module 大集合的其中一个对象 1   2  3   4  5
               map.put("checked" , true);//不能够随便加
           }
            list.add(map);//添加到集合中
        }

        //2.返回List<Map>
        return list;
    }


    /**
     * 修改角色的权限信息
     * roleid: 4028a1cd4ee2d9d6014ee2df4c6a0008
     * moduleIds: 2,201,202,203,204,3,301,302,303
     * @return
     */
    @RequestMapping(value = "/updateRoleModule" , name = "修改角色的权限信息")
    public String updateRoleModule(String roleid, String moduleIds){

        //修改角色的权限数据 必须在service中 因为需要事务
        moduleService.updateRoleModule(roleid , moduleIds);
        return "redirect:/system/role/list.do";
    }
}
