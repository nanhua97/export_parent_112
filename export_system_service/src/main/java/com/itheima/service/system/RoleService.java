package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Role;

import java.util.List;

public interface RoleService {
    //根据企业id 分页查询数据
    PageInfo findAll(Integer page, Integer size, String companyId);

    //查询所有的角色数据
    List<Role> findAll(String companyId);

    //保存角色
    void save(Role role);

    //修改角色
    void update(Role role);

    //根据id查询角色数据
    Role findById(String id);

    //根据id删除角色数据
    void delete(String id);

    //根据用户的id查询用户的角色信息
    List<Role> findByUid(String id);

    //修改用户的角色信息
    void updateRoleUser(String userid, String roleIds);
}
