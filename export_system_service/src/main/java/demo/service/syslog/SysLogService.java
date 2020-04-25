package demo.service.syslog;

import com.github.pagehelper.PageInfo;
import demo.domain.syslog.SysLog;

public interface SysLogService {
    PageInfo findAll(String companyId, Integer page, Integer size);


    public void save(SysLog sysLog);
}
