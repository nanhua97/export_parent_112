package demo.service.company;

import com.github.pagehelper.PageInfo;
import demo.common.entity.PageResult;
import demo.domain.company.Company;

import java.util.List;

public interface CompanyService {
    //查询所有企业
    public List<Company> findAll();

    //企业保存
    void save(Company company);

    //根据id删除数据
    void delete(String id);

    //根据id查询对象
    Company findById(String id);

    //修改方法
    void update(Company company);

    //分页查询数据
    PageResult findByPage(Integer page, Integer size);

    //分页插件
    PageInfo findByMybatis(Integer page, Integer size);
}
