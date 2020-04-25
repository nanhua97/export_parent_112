package com.itheima.test;

import demo.domain.company.Company;
import demo.service.company.CompanyService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * <Description>
 *
 * @author hzb@itcast.cn
 * @version 1.0
 * @taskId: <br>
 * @createDate 2019/11/05 17:14
 * @see com.itheima.test
 */
public class TestDemo {
    @Test
    public void test(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        CompanyService companyService = ac.getBean(CompanyService.class);
        List<Company> companyList = companyService.findAll();
        for (Company company : companyList) {
            System.out.println(company);
        }
    }
}
