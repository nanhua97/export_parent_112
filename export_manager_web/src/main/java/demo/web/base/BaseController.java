package demo.web.base;


import demo.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 定义全局的信息
 */
public class BaseController {
    @Autowired // 先将对象进行线程绑定 将绑定的对象注入进来
    protected HttpServletRequest request;//Current HttpServletRequest
    @Autowired//Current HttpServletResponse 已经经过了包装
    protected HttpServletResponse response; //编译异常 idea的问题  可用对象
    @Autowired
    protected HttpSession session;

    protected User loginUser;
    protected String companyId  ;//从登录的用户获得
    protected String companyName ;//从登录的用户获得

    @ModelAttribute//预处理注解 .令当前方法在执行所有方法前先执行
    public void before(){
        User user =  (User)session.getAttribute("loginUser");
        if(user != null){//防止没有登录的时候 报错
            companyId =  user.getCompanyId(); //user.getCompanyId();
            companyName =user.getCompanyName(); //user.getCompanyName();
            loginUser = user;
        }

    }

}
