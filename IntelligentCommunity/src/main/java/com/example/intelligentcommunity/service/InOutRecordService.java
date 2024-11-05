package com.example.intelligentcommunity.service;

import com.example.intelligentcommunity.entity.InOutRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.intelligentcommunity.form.InOutQueryForm;
import com.example.intelligentcommunity.mapper.InOutRecordMapper;
import com.example.intelligentcommunity.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
public interface InOutRecordService extends IService<InOutRecord> {
    public Map chart();
    public PageVO inOutRecordList(InOutQueryForm inOutQueryForm);

}
