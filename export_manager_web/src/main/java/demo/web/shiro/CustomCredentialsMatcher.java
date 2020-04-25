package demo.web.shiro;

import demo.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * <Description>
 *
 * 密码比较器 : 比较密码
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     * 方法名称:  doCredentialsMatch  密码匹配方法  用户输入的密码  数据库中当前用户的密码
     * 参数: 用户输入的用户名密码
     * 返回值: 数据库查询的用户名密码
     * 密码加密:
     *  为什么要使用密码加密? 防止数据库数据安全问题 数据库的密码不采用明文数据
     *  数据库密码采用密文数据. 好处: 无法直观的看到密码
     *  程序员 常用密码 123456
     *      md5 加密: 不可逆性,不对称性 , 固长性 , 加密后的长度是一样的 , 恒等性
     *      123456 ==>  xxxxyyyy  -> oooppp ->qwerty
     *      1 ==>       qqqqwwww
     *
     *      => 多次加密
     *      => 加盐加密
     *      针对于每个用户 我们可以在原有密码的基础上进行改造 加盐 , 解决常用密码的问题
     *      123456+xxxx ->  qwerty
     *      12345 +xxxx ->  ertyu
     *      盐必须是动态的 针对于每个用户是不一样的盐  但是针对一个用户的多次操作 盐一致
     *      123456+123 ->qweasdf
     *      12345+qwe -> xxxqasd
     *      我们现在使用的盐是 email
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("密码比较器");
        //1.获得用户输入的密码
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        //获得密码
        String password = new String(upToken.getPassword());
        System.out.println("用户输入的密码:"+password );

        String email = upToken.getUsername();
        String md5Pwd = Encrypt.md5(password, email);
        System.out.println("用户输入密码加密后的结果:" + md5Pwd);
        //2.获得数据库的密码
        String dbPassword = (String)info.getCredentials();
        System.out.println("数据库的密码:" + dbPassword);

        return dbPassword.equals(md5Pwd); //比较密码 如果一致返回true 表示登录成功 如果不一致返回false 表示登录失败
    }
}
