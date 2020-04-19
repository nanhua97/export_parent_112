package com.itheima.dao.consumer;


import com.itheima.domain.consumer.ConsumerInfo;
import com.itheima.domain.consumer.ConsumerInfoExample;

import java.util.List;

public interface ConsumerDao {
    int deleteByPrimaryKey(String id);

    int insert(ConsumerInfo record);

    int insertSelective(ConsumerInfo record);

    List<ConsumerInfo> selectByExample(ConsumerInfoExample example);

    ConsumerInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ConsumerInfo record);

    int updateByPrimaryKey(ConsumerInfo record);
}