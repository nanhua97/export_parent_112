package demo.service.syslog.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.dao.syslog.SysLogDao;
import demo.domain.syslog.SysLog;
import demo.service.syslog.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;
    @Override
    public PageInfo findAll(String companyId, Integer page, Integer size) {
        PageHelper.startPage(page , size);
        List<SysLog> list = sysLogDao.findAll(companyId);
        return new PageInfo(list);
    }

    @Override
    public void save(SysLog sysLog) {
        sysLog.setId(UUID.randomUUID().toString());
        sysLogDao.save(sysLog);
    }
}
