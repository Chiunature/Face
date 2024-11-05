package com.example.intelligentcommunity.controller;


import com.example.intelligentcommunity.form.LogForm;
import com.example.intelligentcommunity.service.LogService;
import com.example.intelligentcommunity.util.Result;
import com.example.intelligentcommunity.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;

/**
 * <p>
 * 操作日志表 前端控制器
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
@RestController
@RequestMapping("/sys/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/list")
    public Result list(LogForm logForm){
        PageVO pageVO = this.logService.logList(logForm);
        return Result.ok().put("data", pageVO);
    }

    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids){
        boolean removeByIds = this.logService.removeByIds(Arrays.asList(ids));
        if(!removeByIds) return Result.error("删除日志失败");
        return Result.ok();
    }
}

