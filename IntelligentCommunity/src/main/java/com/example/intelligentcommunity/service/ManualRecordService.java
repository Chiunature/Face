package com.example.intelligentcommunity.service;

import com.example.intelligentcommunity.entity.ManualRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.intelligentcommunity.form.ManualRecordForm;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
public interface ManualRecordService extends IService<ManualRecord> {
    public Map manualRecordList(ManualRecordForm manualRecordForm);
}
