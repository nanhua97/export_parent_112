package com.itheima.dao.system;

import com.itheima.domain.system.Dept;

import java.util.List;

public interface DeptDao {
    //根据企业id查询部门列表数据
    List<Dept> findAll(String companyId);

    //根据id查询对象
    Dept findById(String id);

    //保存部门
    void save(Dept dept);

    //修改部门
    void update(Dept dept);

    //根据id删除部门
    void delete(String id);
}
