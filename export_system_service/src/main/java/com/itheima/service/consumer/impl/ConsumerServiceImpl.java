package com.itheima.service.consumer.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.entity.PageResult;
import com.itheima.dao.consumer.ConsumerDao;
import com.itheima.domain.consumer.ConsumerInfo;
import com.itheima.domain.consumer.ConsumerInfoExample;
import com.itheima.service.consumer.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerDao consumerDao;


    @Override
    public PageInfo<ConsumerInfo> selectByStatus(List<Integer> statuses, Integer page, Integer size) {
        PageHelper.startPage(page,size);

        ConsumerInfoExample consumerInfoExample = new ConsumerInfoExample();

        ConsumerInfoExample.Criteria criteria = consumerInfoExample.createCriteria();

        criteria.andStatusIn(statuses);

        List<ConsumerInfo> consumerInfos = consumerDao.selectByExample(consumerInfoExample);

        PageInfo<ConsumerInfo> consumerInfoPageInfo = new PageInfo<>(consumerInfos);

        return consumerInfoPageInfo;
    }


    @Override
    public Integer save(ConsumerInfo consumerInfo) {

        try {
            consumerInfo.setStatus(1);
            consumerInfo.setCreateTime(new Date());
            consumerInfo.setUpdateTime(new Date());
            consumerInfo.setId(UUID.randomUUID().toString());
            int insert = consumerDao.insert(consumerInfo);
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public Integer update(ConsumerInfo consumerInfo) {
        Integer status = 0;
        try {
            consumerInfo.setUpdateTime(new Date());
            consumerInfo.setStatus(1);
            status = consumerDao.updateByPrimaryKey(consumerInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Integer delete(String id) {
        Integer status = 0;
        try {
            status = consumerDao.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public ConsumerInfo selectById(String id) {
        ConsumerInfo consumerInfo = new ConsumerInfo();
        try {
            consumerInfo = consumerDao.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return consumerInfo;
    }

    @Override
    public List<ConsumerInfo> selectByPhone(String phone) {
        try {
            ConsumerInfoExample consumerInfoExample = new ConsumerInfoExample();
            ConsumerInfoExample.Criteria criteria = consumerInfoExample.createCriteria();
            criteria.andPhoneEqualTo(phone);
            return consumerDao.selectByExample(consumerInfoExample);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public PageInfo<ConsumerInfo> selectByKw(Integer status, Integer page, Integer size,String kw) {

        PageHelper.startPage(page,size);

        ConsumerInfoExample consumerInfoExample = new ConsumerInfoExample();

        ConsumerInfoExample.Criteria criteria = consumerInfoExample.createCriteria();

        criteria.andStatusEqualTo(status);

        criteria.andNameLike(kw);

        criteria.andPhoneLike(kw);

        List<ConsumerInfo> consumerInfos = consumerDao.selectByExample(consumerInfoExample);

        PageInfo<ConsumerInfo> consumerInfoPageInfo = new PageInfo<>(consumerInfos);

        return consumerInfoPageInfo;

    }

}
