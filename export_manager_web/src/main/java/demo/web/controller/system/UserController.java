package demo.web.controller.system;

import com.github.pagehelper.PageInfo;
import demo.common.utils.Encrypt;
import demo.domain.system.Dept;
import demo.domain.system.Role;
import demo.domain.system.User;
import demo.service.system.DeptService;
import demo.service.system.RoleService;
import demo.service.system.UserService;
import demo.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    /**
     * 用户列表查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", name = "用户列表查询")
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        //分页查询
        PageInfo pageInfo = userService.findAll(page, size , companyId);
        //存入页面
        request.setAttribute("page" , pageInfo);
        return "system/user/user-list";
    }



    @Autowired
    private DeptService deptService;

    /**
     * 用户跳转新增
     * @return
     */
    @RequestMapping(value = "/toAdd" , name = "用户跳转新增")
    public String toAdd(){
        //查询上级部门
        List<Dept> deptList = deptService.findAll(super.companyId);
        request.setAttribute("deptList" , deptList);

        return "system/user/user-add";
    }


    /**
     * 增加和修改的方法
     * @return
     */
    @RequestMapping(value = "/edit" , name = "用户新增或修改")
    public String edit(User user){
        //添加用户的时候 必须添加 企业的名称 和企业的id
        //String companyId = "1";
        //String companyName = "传智播客"; //以后从登陆用户中获得
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if(StringUtils.isEmpty(user.getId())){//新增
            String md5Pwd = Encrypt.md5(user.getPassword(), user.getEmail());
            user.setPassword(md5Pwd);
            userService.save(user);
        }else{
            //修改
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }

    /**
     * 跳转用户修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "跳转用户修改页面")
    public String toUpdate(String id){
        //String companyId="1";
        //1.回显用户的信息
        User user = userService.findById(id);
        request.setAttribute("user" , user);
        //2.回显上级部门信息
        List<Dept> deptList = deptService.findAll(super.companyId);
        request.setAttribute("deptList" , deptList);

        return "system/user/user-update";
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete" , name = "删除用户")
    public String delete(String id){
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }


    @Autowired
    private RoleService roleService;
    /**
     * 修改用户的角色页面
     * @return
     */
    @RequestMapping(value = "/roleList" , name = "跳转修改角色的页面")
    public String roleList(String id){//用户的id数据
        System.out.println(id);
        //1.查询用户的信息
        User user = userService.findById(id);
        request.setAttribute("user" , user);

        //2.查询所有的角色信息  1    2   3   4  5   页面遍历的时候 每次只拿一个值
        List<Role> roleList = roleService.findAll(super.companyId);
        request.setAttribute("roleList" , roleList);

        //3.查询当前用户所具有的角色信息  2  3  4   集合  将集合的列表数据 组成一个字符串  2 ,3 ,4
        List<Role> userRoleList = roleService.findByUid(id);
        System.out.println(userRoleList);
        String userRoleStr = "";//拼接 id字符串
        for (Role role : userRoleList) {
            userRoleStr+= role.getId() + ",";
        }
        //将id的拼接字符串传入到页面 2,3,4
        request.setAttribute("userRoleStr" , userRoleStr);
        return "system/user/user-role";
    }

    /**
     * 修改用户的角色数据
     * userid: 002108e2-9a10-4510-9683-8d8fd1d374ef
     * roleIds: 4028a1c34ec2e5c8014ec2ebf8430001
     * roleIds: 4028a1c34ec2e5c8014ec2ec38cc0002
     * roleIds: 4028a1cd4ee2d9d6014ee2df4c6a0000
     * @return
     */
    @RequestMapping(value = "/changeRole" , name = "修改用户的角色数据")
    public String changeRole(String userid ,String roleIds){
        //System.out.println(userid);
        //System.out.println(roleIds);
        //System.out.println(Arrays.toString(roleIds));
        //在service层处理
        roleService.updateRoleUser(userid , roleIds);
        return "redirect:/system/user/list.do";
    }
}
