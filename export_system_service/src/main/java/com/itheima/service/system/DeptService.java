package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;

import java.util.List;

public interface DeptService {
    //根据企业id 分页查询数据
    PageInfo findAll(Integer page, Integer size, String companyId);

    //查询所有的部门数据
    List<Dept> findAll(String companyId);

    //保存部门
    void save(Dept dept);

    //修改部门
    void update(Dept dept);

    //根据id查询部门数据
    Dept findById(String id);

    //根据id删除部门数据
    void delete(String id);
}
