package com.itheima.web.controller.company;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import com.itheima.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {


    @Autowired
    private CompanyService companyService;
    /**
     * 查询企业数据
     * 服务器的日期支持的是/ 而不是- 但是 , 希望支持-
     * 自定义类型转换器
     * @return
     */
    @RequestMapping(value = "/list" ,name ="查询企业数据" )
    public String list(@RequestParam(defaultValue = "1") Integer page ,@RequestParam(defaultValue = "3") Integer size){ //原生对象

        //查询数据
        //List<Company> companyList = companyService.findAll();
        /*存入页面显示数据*/
        //request.setAttribute("list" , companyList);

        //查询分页数据
        //PageResult pageResult = companyService.findByPage(page , size);
        PageInfo pageInfo = companyService.findByMybatis(page , size);
        System.out.println(pageInfo);
        //存入页面
        request.setAttribute("page" , pageInfo);//key必须叫page
        return "company/company-list";
    }


    /**
     * 跳转企业添加的页面
     * name就是个注释
     * @return
     */
    @RequestMapping(value = "/toAdd" , name = "跳转企业添加的页面")
    public String toAdd(){
        return "company/company-add";
    }


    /**
     * 企业保存方法
     * 保存和修改的逻辑一致 但是
     * 保存 company对象 没有id
     * 修改 company对象 有id属性
     * @return
     */
    @RequestMapping(value = "/edit" , name = "企业的保存方法")
    public String edit(Company company){
        if(StringUtils.isEmpty(company.getId())){//判断id是否存在
            //调用service开始保存数据
            companyService.save(company);
        }else{
            companyService.update(company);
        }
        return "redirect:/company/list.do";
    }




    /**
     * 根据id删除企业数据
     * @return
     */
    @RequestMapping(value = "/delete" , name = "根据id删除企业数据")
    public String delete(String id){
        //System.out.println(id);
        companyService.delete(id);
        return "redirect:/company/list.do";
    }


    /**
     * 跳转企业修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "跳转企业修改页面")
    public String toUpdate(String id, HttpServletRequest request){
        //根据id查询数据
        Company company = companyService.findById(id);
        //System.out.println(company);
        request.setAttribute("company" , company);//${company.key}
        return "company/company-update";
    }


    @RequestMapping("/success")
    public String success(){

        return "success";
    }
}
