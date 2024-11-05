package com.example.intelligentcommunity.mapper;

import com.example.intelligentcommunity.entity.Person;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
public interface PersonMapper extends BaseMapper<Person> {
    @Select({
            "select count(*) from person where community_id = #{communityId} "
    })
    public Integer getCountByCommunityId(Integer communityId);
}
