package demo.service.consumer;

import com.github.pagehelper.PageInfo;
import demo.domain.consumer.ConsumerInfo;

import java.util.List;

public interface ConsumerService {

    PageInfo<ConsumerInfo> selectByStatus(List<Integer> statuses,Integer page,Integer size);

    Integer save(ConsumerInfo consumerInfo);

    Integer update(ConsumerInfo consumerInfo);

    Integer delete(String id);

    ConsumerInfo selectById(String id);

    List<ConsumerInfo> selectByPhone(String phone);

    PageInfo<ConsumerInfo> selectByKw(Integer status, Integer page, Integer size,String kw);

    void updateStatus(String id, Integer status,String updateBy);
}
