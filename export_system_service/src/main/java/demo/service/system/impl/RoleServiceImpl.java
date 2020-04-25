package demo.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.dao.system.RoleDao;
import demo.domain.system.Role;
import demo.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    /**
     * 分页查询角色列表数据
     * @param page
     * @param size
     * @param companyId
     * @return
     */
    @Override
    public PageInfo findAll(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page,size);
        List<Role> list = roleDao.findAll(companyId);
        return new PageInfo(list);
    }

    /**
     * 查询所有的角色数据
     * @param companyId
     * @return
     */
    @Override
    public List<Role> findAll(String companyId) {
        List<Role> list = roleDao.findAll(companyId);
        return list;
    }


    /**
     * 保存角色
     * @param role
     */
    @Override
    public void save(Role role) {
        role.setId(UUID.randomUUID().toString());
        roleDao.save(role);
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    /**
     * 根据id查询角色对象
     * @param id
     * @return
     */
    @Override
    public Role findById(String id) {

        return roleDao.findById(id);
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    /**
     * 根据用户的id查询用户的角色信息
     * @param id
     * @return
     */
    @Override
    public List<Role> findByUid(String id) {
        return roleDao.findByUid(id);
    }

    /**
     * 修改用户的角色信息
     * @param userid
     * @param roleIds
     */
    @Override
    public void updateRoleUser(String userid, String roleIds) {
        //1.删除原来的角色信息
        roleDao.deleteUserRoleByUid(userid);
        //2.添加新的角色信息
        if(!StringUtils.isEmpty(roleIds)){//判断 , 有可能删除所有的用户权限
            String[] splitRoleId = roleIds.split(",");
            //批量添加
            for (String roleid : splitRoleId) {
                //修改用户的角色信息
                roleDao.insertUserRole(userid , roleid);
            }
        }

    }
}
