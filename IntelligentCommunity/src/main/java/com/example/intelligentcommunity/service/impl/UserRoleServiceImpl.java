package com.example.intelligentcommunity.service.impl;

import com.example.intelligentcommunity.entity.UserRole;
import com.example.intelligentcommunity.mapper.UserRoleMapper;
import com.example.intelligentcommunity.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
