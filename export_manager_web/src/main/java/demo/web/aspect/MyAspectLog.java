package demo.web.aspect;


import demo.domain.syslog.SysLog;
import demo.domain.system.User;
import demo.service.syslog.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Component  //comment 注释
@Aspect //当前类是一个切面类 增强类
public class MyAspectLog {

    @Autowired
    private SysLogService sysLogService;//注入service

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;
    /**
     * 环绕通知
     * 切面=切点(正在被执行的方法)+通知(增强)
     */
    @Around("execution(* com.itheima.web.controller.*.*.*(..))")
    public Object around(ProceedingJoinPoint pjp){//ProceedingJoinPoint  切点 放行(执行切点)
        //System.out.println("拦截到了controller的代码");
        Object object = null;//放行
        try {
            //new出来的日志 是空的
            SysLog sysLog = new SysLog();
            //构建日志对象
            User loginUser = (User)session.getAttribute("loginUser");
            if(loginUser!=null){
                // private String userName;//操作的人
                sysLog.setUserName(loginUser.getUserName());
                // private String companyId;//公司
                sysLog.setCompanyId(loginUser.getCompanyId());
                // private String companyName;//公司名称
                sysLog.setCompanyName(loginUser.getCompanyName());
            }
            // private Date time; //操作时间
            sysLog.setTime(new Date());


            // private String ip; //远程主机 客户机的ip地址  在request对象中封装了浏览器端的所有信息
            String remoteAddr = request.getRemoteAddr();
            sysLog.setIp(remoteAddr);
            // private String method;//操作方法   method方法对象.getName(); 获得方法名称
            //Signature 签名  每一个方法都有方法签名 找到方法对象
            MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
            //获得方法对象
            Method method = methodSignature.getMethod();
            String methodName = method.getName();
            sysLog.setMethod(methodName);

            // private String action;//操作方法的名称
            //中文名称 从注解中获得
            //注解也是一个类  接口是类  枚举是类 创建类
            //判断当前方法上是否含有 RequestMapping注解
            boolean flag = method.isAnnotationPresent(RequestMapping.class);
            if(flag){
                //获得注解对象
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                //获得对象中的属性值  get/set普通的类  注解获得属性值 属性的名称()即可
                String methodNameCN = requestMapping.name();
                sysLog.setAction(methodNameCN);
            }

            sysLogService.save(sysLog);//保存日志
            object = pjp.proceed();//放行
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;

    }

}
