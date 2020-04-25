package demo.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.dao.system.UserDao;
import demo.domain.system.User;
import demo.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 分页查询用户列表数据
     * @param page
     * @param size
     * @param companyId
     * @return
     */
    @Override
    public PageInfo findAll(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page,size);
        List<User> list = userDao.findAll(companyId);
        return new PageInfo(list);
    }

    /**
     * 查询所有的用户数据
     * @param companyId
     * @return
     */
    @Override
    public List<User> findAll(String companyId) {
        List<User> list = userDao.findAll(companyId);
        return list;
    }


    /**
     * 保存用户
     * @param user
     */
    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        userDao.save(user);
    }

    /**
     * 修改用户
     * @param user
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * 根据id查询用户对象
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {

        return userDao.findById(id);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    /**
     * //根据邮箱获得用户数据
     * @param email
     * @return
     */
    @Override
    public User findByEmail(String email) {

        return userDao.findByEmail(email);
    }
}
