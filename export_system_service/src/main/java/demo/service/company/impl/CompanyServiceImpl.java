package demo.service.company.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.common.entity.PageResult;
import demo.dao.company.CompanyDao;
import demo.domain.company.Company;
import demo.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Override
    public List<Company> findAll() {

        return companyDao.findAll();
    }

    /**
     * 企业保存
     * @param company
     */
    @Override
    public void save(Company company) {
        company.setId(UUID.randomUUID().toString());//产生一个36位的随机字符串
        companyDao.save(company);
    }

    /**
     * 根据id删除数据
     * @param id
     */
    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    /**
     * 修改
     * @param company
     */
    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    /**
     * 原始分页查询数据
     * mybatis 提供了分页的插件 帮助我们简化分页后台的代码
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult findByPage(Integer page, Integer size) {
        //查询总记录数
        int total = companyDao.findTotalRecord();
        //查询分页显示的数据
        List<Company> rows = companyDao.findByPage( (page-1)*size , size);

        //构建返回值
        PageResult pageResult = new PageResult(total, rows, page, size);
        return pageResult;
    }

    /**
     * 使用mybatis的分页插件
     * 在你需要进行分页的 MyBatis 查询方法前调用 PageHelper.startPage 静态方法即可，紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页。
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findByMybatis(Integer page, Integer size) {

        //开始使用分页
        PageHelper.startPage(page , size);
        //查询所有 紧接着分页后的第一个sql查询语句自动分页
        //支持三种查询  findAll() 方法支持查询所有  支持查询总记录数据 支持分页查询数据
        List<Company> companyList = companyDao.findAll();
        System.out.println(companyList.size());
        return new PageInfo(companyList);
    }
}
