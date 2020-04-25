package demo.service.system;

import com.github.pagehelper.PageInfo;
import demo.domain.system.User;

import java.util.List;

public interface UserService {
    //根据企业id 分页查询数据
    PageInfo findAll(Integer page, Integer size, String companyId);

    //查询所有的用户数据
    List<User> findAll(String companyId);

    //保存用户
    void save(User user);

    //修改用户
    void update(User user);

    //根据id查询用户数据
    User findById(String id);

    //根据id删除用户数据
    void delete(String id);

    //根据邮箱获得用户数据
    User findByEmail(String email);
}
