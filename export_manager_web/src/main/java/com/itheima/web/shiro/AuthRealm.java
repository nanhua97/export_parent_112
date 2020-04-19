package com.itheima.web.shiro;


import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义的realm域
 * 含有 认证和授权方法
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private ModuleService moduleService;
    /**
     * 授权方法 判断有没有权限
     *
     * 是否需要放行-> 通知shiro 哪些路径需要被放行即可
     *
     * 方法名称 : doGetAuthorizationInfo 获得授权对象信息
     * 参数名称 : PrincipalCollection 重要数据的集合 -> User - >当前用户角色 -> 当前用户能够操作的权限模块
     * 返回值 :  授权信息对象
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权方法");
        //1.获得当前登录的用户数据
        User user  =(User) principals.getPrimaryPrincipal();//用户数据
        //2.根据用户数据查询当前用户可操作的权限模块数据
        List<Module> moduleList = moduleService.findByUid(user.getId());

        //将list转换成set集合
        Set<String> set = new HashSet();
        for (Module module : moduleList) {
            set.add(module.getName());
        }

        //3.构建shiro需要的权限数据
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(set);
        return simpleAuthorizationInfo;
    }

    @Autowired
    private UserService userService;
    /**
     *  认证方法 登录
     *  方法名称:doGetAuthenticationInfo 执行完以后 获得认证信息
     *  参数: AuthenticationToken 用户输入的用户名和密码
     *  返回值:  AuthenticationInfo  认证信息
     *  通过用户输入数据 构建 AuthenticationInfo 数据
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("登录方法");
        //获得用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;

        //用户名
        String email = upToken.getUsername();
        String password = new String(upToken.getPassword());
        //查询数据库
        User user = userService.findByEmail(email);

        if(user == null ){ //登录失败
            return null; //此处return  底层处理代码 会报错 直接抛出异常
        }else{
            //构建认证数据 -> 数据从 查询数据库得到
            //数据库数据 user
            //public SimpleAuthenticationInfo(Object principal, Object credentials, String realmName) {
            //principal :  user 最重要的数据 登录数据
            //credentials : 当前用户的数据库密码  方便一会我们更快的获得密码
            //realmName : realm域的名称 唯一即可 名称随意
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user ,user.getPassword() , this.getName()  );
            //如果构建authenticationInfo 认证数据成功 自动进入密码比较器的方法
            return authenticationInfo;
        }

    }
}
