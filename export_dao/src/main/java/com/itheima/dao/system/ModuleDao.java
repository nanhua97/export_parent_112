package com.itheima.dao.system;


import com.itheima.domain.system.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface ModuleDao {

    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    int delete(String moduleId);

    //添加用户
    int save(Module module);

    //更新用户
    int update(Module module);

    //查询全部
    List<Module> findAll();

    //根据角色信息 查询当前角色的模块信息
    List<Module> findByRid(String roleid);

    //删除角色的所有权限信息
    void deleteRoleModule(String roleid);

    //添加角色的权限信息
    void insertRoleModule(@Param("roleid") String roleid,@Param("moduleId") String moduleId);

    //saas管理员或者企业管理员的模块数据查询
    List<Module> findByBelong(String belong);

    //普通用户查询 根据RBAC权限模型 获得模块数据
    List<Module> findByRBAC(String id);

    //根据ctype查询上级菜单数据
    List<Module> findByCtype(int ctype);

}