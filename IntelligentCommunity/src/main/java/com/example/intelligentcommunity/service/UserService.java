package com.example.intelligentcommunity.service;

import com.example.intelligentcommunity.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.intelligentcommunity.form.UserForm;
import com.example.intelligentcommunity.vo.PageVO;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-22
 */
public interface UserService extends IService<User> {
    public PageVO userList(UserForm userForm);
}
