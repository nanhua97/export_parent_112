package com.itheima.test;

import com.itheima.dao.company.CompanyDao;
import com.itheima.domain.company.Company;
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
 * @createDate 2019/11/05 17:05
 * @see com.itheima.test
 */
public class TestDemo {

    @Test
    public void test01(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        CompanyDao companyDao = ac.getBean(CompanyDao.class);
        List<Company> companyList = companyDao.findAll();
        for (Company company : companyList) {
            System.out.println(company);
        }
    }
}
