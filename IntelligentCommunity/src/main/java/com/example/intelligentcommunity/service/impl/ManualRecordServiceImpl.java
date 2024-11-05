package com.example.intelligentcommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.intelligentcommunity.entity.Community;
import com.example.intelligentcommunity.entity.ManualRecord;
import com.example.intelligentcommunity.form.ManualRecordForm;
import com.example.intelligentcommunity.mapper.CommunityMapper;
import com.example.intelligentcommunity.mapper.ManualRecordMapper;
import com.example.intelligentcommunity.service.ManualRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.intelligentcommunity.vo.ManualRecordVO;
import com.example.intelligentcommunity.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
@Service
public class ManualRecordServiceImpl extends ServiceImpl<ManualRecordMapper, ManualRecord> implements ManualRecordService {

    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private ManualRecordMapper manualRecordMapper;

    @Override
    public Map manualRecordList(ManualRecordForm manualRecordForm) {
        Map map = new HashMap();
        map.put("communityList", this.communityMapper.selectList(null));
        Page<ManualRecord> page = new Page<>(manualRecordForm.getPage(), manualRecordForm.getLimit());
        QueryWrapper<ManualRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(manualRecordForm.getCommunityId() != null, "community_id", manualRecordForm.getCommunityId())
                .eq(StringUtils.isNotBlank(manualRecordForm.getMobile()), "mobile", manualRecordForm.getMobile())
                .like(StringUtils.isNotBlank(manualRecordForm.getVisitor()), "visitor", manualRecordForm.getVisitor());
        Page<ManualRecord> resultPage = this.manualRecordMapper.selectPage(page, queryWrapper);
        PageVO pageVO = new PageVO();
        pageVO.setTotalCount(resultPage.getTotal());
        pageVO.setPageSize(resultPage.getSize());
        pageVO.setCurrPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<ManualRecordVO> list = new ArrayList<>();
        for (ManualRecord record : resultPage.getRecords()) {
            ManualRecordVO manualRecordVO = new ManualRecordVO();
            BeanUtils.copyProperties(record, manualRecordVO);
            Community community = this.communityMapper.selectById(record.getCommunityId());
            manualRecordVO.setCommunityName(community.getCommunityName());
            list.add(manualRecordVO);
        }
        pageVO.setList(list);
        map.put("pageList",pageVO);
        return map;
    }
}
