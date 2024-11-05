package com.example.intelligentcommunity.service;

import com.example.intelligentcommunity.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.intelligentcommunity.form.LogForm;
import com.example.intelligentcommunity.vo.PageVO;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
public interface LogService extends IService<Log> {
    public PageVO logList(LogForm logForm);
}
