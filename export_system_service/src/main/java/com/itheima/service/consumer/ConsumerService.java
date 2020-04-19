package com.itheima.service.consumer;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.consumer.ConsumerInfo;

import java.util.List;

public interface ConsumerService {
    PageInfo<ConsumerInfo> selectByStatus(Integer status, Integer page, Integer size);

    Integer save(ConsumerInfo consumerInfo);

    Integer update(ConsumerInfo consumerInfo);

    Integer delete(String id);

    ConsumerInfo selectById(String id);
}
