package com.example.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.intelligentcommunity.annotation.LogAnnotation;
import com.example.intelligentcommunity.entity.Role;
import com.example.intelligentcommunity.entity.RoleMenu;
import com.example.intelligentcommunity.form.RoleAddOrUpdateForm;
import com.example.intelligentcommunity.form.RoleForm;
import com.example.intelligentcommunity.service.RoleMenuService;
import com.example.intelligentcommunity.service.RoleService;
import com.example.intelligentcommunity.util.Result;
import com.example.intelligentcommunity.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping("/getRoleList")
    public Result getRoleList(){
        List<Role> list = this.roleService.list();
        return Result.ok().put("data", list);
    }

    @GetMapping("/list")
    public Result list(RoleForm roleForm){
        PageVO pageVO = this.roleService.roleList(roleForm);
        return Result.ok().put("data", pageVO);
    }

    @LogAnnotation("添加角色")
    @PostMapping("/add")
    public Result add(@RequestBody RoleAddOrUpdateForm roleAddOrUpdateForm){
        Role role = new Role();
        BeanUtils.copyProperties(roleAddOrUpdateForm, role);
        boolean save = this.roleService.save(role);
        for (Integer integer : roleAddOrUpdateForm.getMenuIdList()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(role.getRoleId());
            roleMenu.setMenuId(integer);
            boolean save1 = this.roleMenuService.save(roleMenu);
            if(!save1) return Result.error("添加角色失败");
        }
        if(save) return Result.ok();
        return Result.error("添加角色失败");
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        Role role = this.roleService.getById(id);
        RoleAddOrUpdateForm addOrUpdateForm = new RoleAddOrUpdateForm();
        BeanUtils.copyProperties(role, addOrUpdateForm);
        List<Integer> list = new ArrayList<>();
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", addOrUpdateForm.getRoleId());
        List<RoleMenu> roleMenuList = this.roleMenuService.list(queryWrapper);
        for (RoleMenu roleMenu : roleMenuList) {
            list.add(roleMenu.getMenuId());
        }
        addOrUpdateForm.setMenuIdList(list);
        return Result.ok().put("data", addOrUpdateForm);
    }

    @LogAnnotation("编辑角色")
    @PutMapping("/edit")
    public Result edit(@RequestBody RoleAddOrUpdateForm roleAddOrUpdateForm){
        Role role = new Role();
        BeanUtils.copyProperties(roleAddOrUpdateForm, role);
        boolean updateById = this.roleService.updateById(role);
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleAddOrUpdateForm.getRoleId());
        boolean remove = this.roleMenuService.remove(queryWrapper);
        for (Integer integer : roleAddOrUpdateForm.getMenuIdList()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleAddOrUpdateForm.getRoleId());
            roleMenu.setMenuId(integer);
            boolean save = this.roleMenuService.save(roleMenu);
            if(!save) return Result.error("编辑角色失败");
        }
        if(updateById) return Result.ok();
        return Result.error("编辑角色失败");
    }

    @LogAnnotation("删除角色")
    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids){
        boolean removeByIds = this.roleService.removeByIds(Arrays.asList(ids));
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id",ids);
        boolean remove = this.roleMenuService.remove(queryWrapper);
        if(remove && removeByIds) return Result.ok();
        return Result.error("删除角色失败");
    }
}
