package demo.web.exception;


import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyException implements HandlerExceptionResolver {
    /**
     * 异常处理方法
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //模型视图对象
        ModelAndView mv = new ModelAndView();
        /*
        <property name="prefix" value="/WEB-INF/pages/"></property>
		<property name="suffix" value=".jsp"></property>
        * */

        /*if(ex instanceof NullPointerException){
            mv.addObject("errorMsg" , "未知异常,请联系管理员,错误码为:1001"  );
        }else if(ex instanceof RuntimeException){
            mv.addObject("errorMsg" , "未知异常,请联系管理员,错误码为:1002"  );
        }*/

        if(ex instanceof UnauthorizedException){ //判断是否没有权限异常 如果是 跳转unauthorized.jsp 权限不足页面
            mv.setViewName("redirect:/unauthorized.jsp");
        }else{
            //非权限异常还是以前的代码
            mv.setViewName("error");
            mv.addObject("errorMsg" , "未知异常,请联系管理员" + ex.getMessage());
        }


        return mv;
    }
}
