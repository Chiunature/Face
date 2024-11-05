package com.example.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.intelligentcommunity.annotation.LogAnnotation;
import com.example.intelligentcommunity.entity.Menu;
import com.example.intelligentcommunity.entity.RoleMenu;
import com.example.intelligentcommunity.service.MenuService;
import com.example.intelligentcommunity.service.RoleMenuService;
import com.example.intelligentcommunity.util.Result;
import com.example.intelligentcommunity.vo.MenuRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping("/list")
    public Result list(){
        List<MenuRoleVO> menuRoleList = this.menuService.getMenuRoleList();
        return Result.ok().put("data", menuRoleList);
    }

    @LogAnnotation("添加菜单")
    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        boolean save = this.menuService.save(menu);
        if(!save) return Result.error("添加菜单失败");
        return Result.ok();
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        Menu menu = this.menuService.getById(id);
        return Result.ok().put("data", menu);
    }

    @LogAnnotation("编辑菜单")
    @PutMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        boolean updateById = this.menuService.updateById(menu);
        if(!updateById) return Result.error("编辑菜单失败");
        return Result.ok();
    }

    @LogAnnotation("删除菜单")
    @DeleteMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id){
        boolean remove = this.menuService.removeById(id);
        QueryWrapper<Menu> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("parent_id", id);
        this.menuService.remove(queryWrapper1);
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id", id);
        this.roleMenuService.remove(queryWrapper);
        if(remove) return Result.ok();
        return Result.error("删除菜单失败");
    }
}

