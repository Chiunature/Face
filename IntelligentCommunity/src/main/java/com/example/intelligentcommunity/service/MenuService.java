package com.example.intelligentcommunity.service;

import com.example.intelligentcommunity.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.intelligentcommunity.vo.MenuRoleVO;
import com.example.intelligentcommunity.vo.MenuRouterVO;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
public interface MenuService extends IService<Menu> {
    public List<MenuRouterVO> getMenuRouterByUserId(Integer userId);
    public List<MenuRoleVO> getMenuRoleList();
}
