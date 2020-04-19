package com.itheima.service.syslog;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.syslog.SysLog;

public interface SysLogService {
    PageInfo findAll(String companyId, Integer page, Integer size);


    public void save(SysLog sysLog);
}
