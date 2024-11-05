package com.example.intelligentcommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.intelligentcommunity.entity.Community;
import com.example.intelligentcommunity.form.CommunityListForm;
import com.example.intelligentcommunity.mapper.CommunityMapper;
import com.example.intelligentcommunity.mapper.PersonMapper;
import com.example.intelligentcommunity.service.CommunityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.intelligentcommunity.vo.CommunityVO;
import com.example.intelligentcommunity.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private PersonMapper personMapper;

    public PageVO communityList(CommunityListForm communityListForm) {
        Page<Community> page = new Page<>(communityListForm.getPage(), communityListForm.getLimit());
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(communityListForm.getCommunityName()),"community_name", communityListForm.getCommunityName());
        Page<Community> resultPage = this.communityMapper.selectPage(page, queryWrapper);
        PageVO pageVO = new PageVO();
        List<CommunityVO> list = new ArrayList<>();
        for (Community record : resultPage.getRecords()) {
            CommunityVO communityVO = new CommunityVO();
            BeanUtils.copyProperties(record, communityVO);
            communityVO.setPersonCnt(this.personMapper.getCountByCommunityId(record.getCommunityId()));
            list.add(communityVO);
        }
        pageVO.setList(list);
        pageVO.setTotalCount(resultPage.getTotal());
        pageVO.setPageSize(resultPage.getSize());
        pageVO.setCurrPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        return pageVO;
    }
}
