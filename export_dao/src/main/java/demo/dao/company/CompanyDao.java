package demo.dao.company;

import demo.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {
    public List<Company> findAll();

    /*企业保存*/
    void save(Company company);

    //根据id删除企业数据
    void delete(String id);

    //根据id查询对象
    Company findById(String id);

    //修改企业列表数据
    void update(Company company);

    //查询分页的总记录数
    int findTotalRecord();

    //查询分页数据
    List<Company> findByPage(@Param("startIndex") Integer startIndex,@Param("size") Integer size);
}
