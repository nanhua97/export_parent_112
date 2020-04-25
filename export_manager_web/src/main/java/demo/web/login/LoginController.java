package demo.web.login;


import demo.domain.system.Module;
import demo.domain.system.User;
import demo.service.system.ModuleService;
import demo.service.system.UserService;
import demo.web.base.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {//

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;
    /**
     * 登录的sql
     *  select * from user where email=xx
     * 登录 : 使用的是用户名和密码 , 为什么使用邮箱
     * 用户名必须是唯一: 手机号 身份证 邮箱  QQ 微信 微博
     * 邮箱 : 每个公司的邮箱标识是唯一的
     *
     * select * from user where email = xxx
     * select * from user where email = xxx and password = xxx
     * @param email
     * @param password
     * @return
     */
	/*@RequestMapping(value = "/login" ,name = "正在进行登录操作")
	public String login(String email,String password) {

	    //当用户名或者密码其中一个没有输入
	    if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
	        return "redirect:/login.jsp";
        }
        //查询数据库进行登录
        User user = userService.findByEmail(email);
        //String md5Pwd = Encrypt.md5(password, email);
        //System.out.println(md5Pwd);
        if(user == null){//用户名错误
	        request.setAttribute("error" , "用户名错误");
	        return "forward:/login.jsp";//只能使用物理视图
        }else if(user != null && !password.equals(user.getPassword())){//用户名对的 密码错误
            request.setAttribute("error" , "密码错误");
	        return "forward:/login.jsp";//只能使用物理视图
        }else{//登录成功
            //根据当前登录的用户人不同 查询不同的模块信息
            List<Module> moduleList = moduleService.findByUid(user.getId());
            //存入session   menu 菜单
            session.setAttribute("menus" , moduleList);

	        //将数据存入session域
            //如果使用的是user 在新增用户的时候 ${user} 先从request域中获得数据 再从session中获得数据
            session.setAttribute("loginUser" , user);
            //登录的代码
            return "home/main";//跳转页面的首页
        }
	}*/


    /**
     * 借助shiro帮助我们操作权限
     * shiro提供 realm域 有认证(登录)和授权的方法
     * 1.拿到subject对象
     * 2.借助shiro对象开始登录 (登录 后 数据保存在shiro中)
     * 3.从shiro获得我们登录的数据 user
     * 4.放入session域
     * 5.查询左侧的菜单权限数据
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/login" ,name = "正在进行登录操作")
    public String login(String email,String password) {
        //当用户名或者密码其中一个没有输入
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            return "redirect:/login.jsp";
        }
        try {
            //1.拿到subject对象
            Subject subject = SecurityUtils.getSubject();
            //2.借助shiro对象开始登录 (登录 后 数据保存在shiro中)
            /**
             * AuthenticationToken   Authentication 认证 Token 令牌 是唯一的  用户和密码
             */
            //构建一个用户名密码对象 传入给shiro框架
            UsernamePasswordToken upToken = new UsernamePasswordToken(email , password);
            /**
             * login方法 会自动执行 realm域中的方法
             * 认证方法->登陆
             */
            subject.login(upToken ); // 如果登录成功正常运行   如果登录失败 直接抛出异常
            //3.从shiro获得我们登录的数据 user
            //最重要的; 用户数据
            User user = (User)subject.getPrincipal(); //user不可能为null
            //4.放入session域
            session.setAttribute("loginUser" , user);
            //5.查询左侧的菜单权限数据
            //根据当前登录的用户人不同 查询不同的模块信息
            List<Module> moduleList = moduleService.findByUid(user.getId());
            //存入session   menu 菜单
            session.setAttribute("menus" , moduleList);
            return "home/main";
        } catch (AuthenticationException e) {
            //e.printStackTrace();
            //真正的开发不允许出现精准异常
            request.setAttribute("error" , "用户名或密码错误");
            //登录失败
            return "forward:/login.jsp";
        }
    }

    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        //SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}
