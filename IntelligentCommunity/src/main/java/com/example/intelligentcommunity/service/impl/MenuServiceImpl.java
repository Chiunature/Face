package com.example.intelligentcommunity.service.impl;

import com.example.intelligentcommunity.entity.Menu;
import com.example.intelligentcommunity.mapper.MenuMapper;
import com.example.intelligentcommunity.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.intelligentcommunity.vo.ChildMenuRouterVO;
import com.example.intelligentcommunity.vo.MenuRoleVO;
import com.example.intelligentcommunity.vo.MenuRouterVO;
import com.example.intelligentcommunity.vo.MetaVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuRouterVO> getMenuRouterByUserId(Integer userId) {
        List<Menu> menuList = this.menuMapper.getMenusByUserId(userId);
        List<MenuRouterVO> list = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId() == 0) {
                MenuRouterVO menuRouterVO = new MenuRouterVO();
                BeanUtils.copyProperties(menu, menuRouterVO);
                MetaVO metaVO = new MetaVO();
                metaVO.setTitle(menu.getName());
                metaVO.setIcon(menu.getIcon());
                menuRouterVO.setMeta(metaVO);
                //生成children
                Integer menuId = menu.getMenuId();
                List<ChildMenuRouterVO> children = new ArrayList<>();
                for (Menu child : menuList) {
                    if(child.getParentId() == menuId){
                        ChildMenuRouterVO childVO = new ChildMenuRouterVO();
                        BeanUtils.copyProperties(child, childVO);
                        MetaVO childMetaVO = new MetaVO();
                        childMetaVO.setTitle(child.getName());
                        childMetaVO.setIcon(child.getIcon());
                        childVO.setMeta(childMetaVO);
                        children.add(childVO);
                    }
                }
                menuRouterVO.setChildren(children);
                list.add(menuRouterVO);
            }
        }
        return list;
    }

    @Override
    public List<MenuRoleVO> getMenuRoleList() {
        List<Menu> menus = this.menuMapper.selectList(null);
        List<MenuRoleVO> list = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getParentId() == 0) {
                MenuRoleVO menuRoleVO = new MenuRoleVO();
                BeanUtils.copyProperties(menu, menuRoleVO);
                menuRoleVO.setId(menu.getMenuId());
                List<MenuRoleVO> children = new ArrayList<>();
                //获取子菜单
                for (Menu item : menus) {
                    if(item.getParentId().equals(menuRoleVO.getId())){
                        MenuRoleVO child = new MenuRoleVO();
                        BeanUtils.copyProperties(item, child);
                        child.setId(item.getMenuId());
                        children.add(child);
                    }
                }
                menuRoleVO.setChildren(children);
                list.add(menuRoleVO);
            }
        }
        return list;
    }
}
