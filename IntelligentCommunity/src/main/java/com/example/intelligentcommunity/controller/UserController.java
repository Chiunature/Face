package com.example.intelligentcommunity.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.intelligentcommunity.entity.User;
import com.example.intelligentcommunity.entity.UserRole;
import com.example.intelligentcommunity.form.UpdatePasswordForm;
import com.example.intelligentcommunity.form.UserAddOrUpdateForm;
import com.example.intelligentcommunity.form.UserForm;
import com.example.intelligentcommunity.mapper.RoleMapper;
import com.example.intelligentcommunity.service.MenuService;
import com.example.intelligentcommunity.service.UserRoleService;
import com.example.intelligentcommunity.service.UserService;
import com.example.intelligentcommunity.util.Result;
import com.example.intelligentcommunity.vo.MenuRouterVO;
import com.example.intelligentcommunity.vo.PageVO;
import com.example.intelligentcommunity.vo.UserEditVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.intelligentcommunity.annotation.LogAnnotation;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author 招自然
 * @since 2024-07-22
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/getRouters")
    public Result getRouters(HttpSession session) {
        User user = (User) session.getAttribute("user");
        String role = this.roleMapper.getRoleNameByUserId(user.getUserId());
        List<MenuRouterVO> routers = this.menuService.getMenuRouterByUserId(user.getUserId());
        return Result.ok()
                .put("data", user)
                .put("roles", role)
                .put("routers",routers);
    }

    @LogAnnotation("修改密码")
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm,HttpSession session){
        User user = (User) session.getAttribute("user");
        //验证原密码是否正确1.进行输入的原密码加密 2.跟数据库的加密的密码判断是否一样
        String password = SecureUtil.sha256(updatePasswordForm.getPassword());
        if(!user.getPassword().equals(password)) return Result.ok().put("status", "passwordError");
        //更新密码
        String newPassword = SecureUtil.sha256(updatePasswordForm.getNewPassword());
        user.setPassword(newPassword);
        boolean updateById = this.userService.updateById(user);
        if(updateById) return Result.ok().put("status", "success");
        return Result.error("更新密码失败");
    }

    @GetMapping("/list")
    public Result list(UserForm userForm){
        PageVO pageVO = this.userService.userList(userForm);
        return Result.ok().put("data", pageVO);
    }

    @LogAnnotation("添加用户")
    @PostMapping("/add")
    public Result add(@RequestBody UserAddOrUpdateForm userAddOrUpdateForm){
        User user = new User();
        BeanUtils.copyProperties(userAddOrUpdateForm, user);
        boolean save = this.userService.save(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(userAddOrUpdateForm.getRoleId());
        boolean save1 = this.userRoleService.save(userRole);
        if(save && save1) return Result.ok();
        return Result.error("添加用户失败");
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        User user = this.userService.getById(id);
        UserEditVO userEditVO = new UserEditVO();
        BeanUtils.copyProperties(user, userEditVO);
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        UserRole userRole = this.userRoleService.getOne(queryWrapper);
        userEditVO.setRoleId(userRole.getRoleId());
        return Result.ok().put("data", userEditVO);
    }

    @LogAnnotation("编辑用户")
    @PutMapping("/edit")
    public Result edit(@RequestBody UserEditVO userEditVO){
        User user = new User();
        BeanUtils.copyProperties(userEditVO, user);
        boolean updateById = this.userService.updateById(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(userEditVO.getRoleId());
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId());
        this.userRoleService.update(userRole, queryWrapper);
        if(updateById) return Result.ok();
        return Result.error("编辑用户失败");
    }

    @LogAnnotation("删除用户")
    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids){
        boolean removeByIds = this.userService.removeByIds(Arrays.asList(ids));
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ids.length > 0, "user_id", ids);
        boolean remove = this.userRoleService.remove(queryWrapper);
        if(removeByIds && remove) return Result.ok();
        return Result.error("删除用户失败");
    }
}

