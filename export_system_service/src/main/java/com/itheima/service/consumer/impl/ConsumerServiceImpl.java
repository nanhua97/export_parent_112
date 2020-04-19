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

import java.util.List;
import java.util.UUID;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerDao consumerDao;

    @Override
    public PageInfo<ConsumerInfo> selectByStatus(Integer status, Integer page, Integer size) {

        PageHelper.startPage(page,size);

        ConsumerInfoExample consumerInfoExample = new ConsumerInfoExample();

        ConsumerInfoExample.Criteria criteria = consumerInfoExample.createCriteria();

        criteria.andStatusEqualTo(status);

        List<ConsumerInfo> consumerInfos = consumerDao.selectByExample(consumerInfoExample);

        PageInfo<ConsumerInfo> consumerInfoPageInfo = new PageInfo<>(consumerInfos);

        return consumerInfoPageInfo;
    }

    @Override
    public Integer save(ConsumerInfo consumerInfo) {
        ConsumerInfoExample consumerInfoExample = new ConsumerInfoExample();
        ConsumerInfoExample.Criteria criteria = consumerInfoExample.createCriteria();
        criteria.andPhoneEqualTo(consumerInfo.getPhone());
        List<ConsumerInfo> consumerInfos = consumerDao.selectByExample(consumerInfoExample);

        if (StringUtils.isEmpty(consumerInfo.getPhone()) || StringUtils.isEmpty(consumerInfo.getName())){
            return -1;
        }

        if (consumerInfos.size() >0 ){
            return 0;
        }else {
            consumerInfo.setId(UUID.randomUUID().toString());
            consumerInfo.setStatus(1);
            int insert = consumerDao.insert(consumerInfo);
            return insert;
        }

    }

    @Override
    public Integer update(ConsumerInfo consumerInfo) {
        Integer status = 0;
        try {
            status = consumerDao.insert(consumerInfo);
        } catch (Exception e) {
            e.printStackTrace();
            status = -1;
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
            status = -1;
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
}
