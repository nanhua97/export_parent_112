package demo.dao.system;


import demo.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleDao {

    //根据id查询
    Role findById(String id);

    //查询全部用户
    List<Role> findAll(String companyId);

	//根据id删除
    int delete(String id);

	//添加
    int save(Role role);

	//更新
    int update(Role role);

    //根据用户的id查询用户的角色信息
    List<Role> findByUid(String id);

    //删除原来的角色信息
    void deleteUserRoleByUid(String userid);

    //修改用户的角色信息
    void insertUserRole(@Param("userid") String userid, @Param("roleid") String roleid);
}