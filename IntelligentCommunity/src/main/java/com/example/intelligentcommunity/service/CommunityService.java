package com.example.intelligentcommunity.service;

import com.example.intelligentcommunity.entity.Community;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.intelligentcommunity.form.CommunityListForm;
import com.example.intelligentcommunity.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
public interface CommunityService extends IService<Community> {
    public PageVO communityList(CommunityListForm communityListForm);
}
