package demo.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.dao.system.DeptDao;
import demo.domain.system.Dept;
import demo.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    /**
     * 分页查询部门列表数据
     * @param page
     * @param size
     * @param companyId
     * @return
     */
    @Override
    public PageInfo findAll(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page,size);
        List<Dept> list = deptDao.findAll(companyId);
        return new PageInfo(list);
    }

    /**
     * 查询所有的部门数据
     * @param companyId
     * @return
     */
    @Override
    public List<Dept> findAll(String companyId) {
        List<Dept> list = deptDao.findAll(companyId);
        return list;
    }


    /**
     * 保存部门
     * @param dept
     */
    @Override
    public void save(Dept dept) {
        dept.setId(UUID.randomUUID().toString());
        deptDao.save(dept);
    }

    /**
     * 修改部门
     * @param dept
     */
    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    /**
     * 根据id查询部门对象
     * @param id
     * @return
     */
    @Override
    public Dept findById(String id) {

        return deptDao.findById(id);
    }

    @Override
    public void delete(String id) {
        deptDao.delete(id);
    }
}
