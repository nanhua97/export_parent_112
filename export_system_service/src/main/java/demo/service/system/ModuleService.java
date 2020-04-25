package demo.service.system;

import com.github.pagehelper.PageInfo;
import demo.domain.system.Module;

import java.util.List;

public interface ModuleService {
    //根据企业id 分页查询数据
    PageInfo findAll(Integer page, Integer size);

    //查询所有的模块数据
    List<Module> findAll();

    //保存模块
    void save(Module module);

    //修改模块
    void update(Module module);

    //根据id查询模块数据
    Module findById(String id);

    //根据id删除模块数据
    void delete(String id);

    //根据角色信息 查询当前角色的模块信息
    List<Module> findByRid(String roleid);

    //修改角色的权限数据 必须在service中 因为需要事务
    void updateRoleModule(String roleid, String moduleIds);

    //根据登录的人 查询模块信息
    List<Module> findByUid(String id);

    //根据ctype修改下拉框数据
    List<Module> findByCtype(Integer ctype);

}
