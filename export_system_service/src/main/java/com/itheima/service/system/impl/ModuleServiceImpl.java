package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.ModuleDao;
import com.itheima.dao.system.UserDao;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 分页查询模块列表数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findAll(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Module> list = moduleDao.findAll();
        return new PageInfo(list);
    }

    /**
     * 查询所有的模块数据
     * @return
     */
    @Override
    public List<Module> findAll() {
        List<Module> list = moduleDao.findAll();
        return list;
    }


    /**
     * 保存模块
     * @param module
     */
    @Override
    public void save(Module module) {
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    /**
     * 修改模块
     * @param module
     */
    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    /**
     * 根据id查询模块对象
     * @param id
     * @return
     */
    @Override
    public Module findById(String id) {

        return moduleDao.findById(id);
    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    /**
     * 根据角色信息 查询当前角色的模块信息
     * @param roleid
     * @return
     */
    @Override
    public List<Module> findByRid(String roleid) {
        return  moduleDao.findByRid(roleid);
    }

    /**
     * 修改角色的权限数据 必须在service中 因为需要事务
     * @param roleid
     * @param moduleIds
     */
    @Override
    public void updateRoleModule(String roleid, String moduleIds) {
        //删除角色的原本权限数据
        moduleDao.deleteRoleModule(roleid);

        //切割字符串
        if(!StringUtils.isEmpty(moduleIds)){//防止删除了所有的权限数据
            String[] splitModuleIds = moduleIds.split(",");
            for (String moduleId : splitModuleIds) {
                //新增角色的权限数据
                moduleDao.insertRoleModule(roleid ,moduleId );
            }

        }

    }

    @Autowired
    private UserDao userDao;
    /**
     * 根据登录的人 查询模块信息
     * User对象中的属性
     *   * 0作为内部控制，租户企业不能使用
     *    0-saas管理员
     *    1-企业管理员
     *    2-管理所有下属部门和人员
     *    3-管理本部门
     *    4-普通员工
     *   private Integer degree;
     *
     *   模块表的数据
     *  从属关系
     *      *  0：sass系统内部菜单
     *      *  1：租用企业菜单
     *      private String belong;
     * @param id
     * @return
     */
    @Override
    public List<Module> findByUid(String id) {
        List<Module> moduleList = null;
        //获得用户信息 其中有 degree字段 用于区分用户的身份
        User user = userDao.findById(id);
        if(user.getDegree() == 0){//saas管理员 : 查询saas的菜单
            moduleList = moduleDao.findByBelong("0");
        }else if(user.getDegree() == 1){//企业管理员 : 查询企业管理员的菜单
            moduleList = moduleDao.findByBelong("1");
        }else{//普通员工 : 根据RBAC查询权限信息
            moduleList = moduleDao.findByRBAC(id);
        }

        return moduleList;
    }

    /**
     *   //根据ctype修改下拉框数据
     *     List<Module> findByCtype(Integer ctype);
     * @param ctype
     * @return
     */
    @Override
    public List<Module> findByCtype(Integer ctype) {
        List<Module> moduleList = null;
        if(ctype==2){
            moduleList = moduleDao.findByCtype(1);
        }else if(ctype==1){
            moduleList = moduleDao.findByCtype(0);
        }
        return moduleList;
    }
}
