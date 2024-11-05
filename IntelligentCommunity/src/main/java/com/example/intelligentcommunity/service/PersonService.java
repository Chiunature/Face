package com.example.intelligentcommunity.service;

import com.example.intelligentcommunity.entity.Person;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.intelligentcommunity.form.PersonListForm;
import com.example.intelligentcommunity.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
public interface PersonService extends IService<Person> {
    public PageVO personList(PersonListForm personListForm);
}
