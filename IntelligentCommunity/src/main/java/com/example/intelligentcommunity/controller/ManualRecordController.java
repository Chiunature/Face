package com.example.intelligentcommunity.controller;


import com.example.intelligentcommunity.annotation.LogAnnotation;
import com.example.intelligentcommunity.entity.Community;
import com.example.intelligentcommunity.entity.ManualRecord;
import com.example.intelligentcommunity.entity.User;
import com.example.intelligentcommunity.form.ManualRecordForm;
import com.example.intelligentcommunity.service.CommunityService;
import com.example.intelligentcommunity.service.ManualRecordService;
import com.example.intelligentcommunity.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 招自然
 * @since 2024-07-23
 */
@RestController
@RequestMapping("/sys/manual")
public class ManualRecordController {

    @Autowired
    private ManualRecordService manualRecordService;
    @Autowired
    private CommunityService communityService;

    @GetMapping("/list")
    public Result list(ManualRecordForm manualRecordForm){
        Map map = this.manualRecordService.manualRecordList(manualRecordForm);
        return Result.ok().put("data", map);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        if(id == 0){
            List<Community> list = this.communityService.list();
            Map map = new HashMap();
            map.put("communityList",list);
            return Result.ok().put("data",map);
        } else {
            List<Community> list = this.communityService.list();
            Map map = new HashMap();
            map.put("communityList",list);
            ManualRecord manualRecord = this.manualRecordService.getById(id);
            map.put("manualInfo", manualRecord);
            return Result.ok().put("data", map);
        }
    }

    @LogAnnotation("添加访问记录")
    @PostMapping("/add")
    public Result add(@RequestBody ManualRecord manualRecord, HttpSession session){
        User user = (User) session.getAttribute("user");
        manualRecord.setUserName(user.getUsername());
        boolean save = this.manualRecordService.save(manualRecord);
        if(!save) return Result.error("访客记录添加失败");
        return Result.ok();
    }

    @LogAnnotation("编辑访问记录")
    @PutMapping("/edit")
    public Result edit(@RequestBody ManualRecord manualRecord){
        boolean updateById = this.manualRecordService.updateById(manualRecord);
        if(!updateById) return Result.error("编辑访客记录失败");
        return Result.ok();
    }

    @LogAnnotation("删除访问记录")
    @DeleteMapping("/del")
    public Result del(@RequestBody Integer[] ids){
        boolean removeByIds = this.manualRecordService.removeByIds(Arrays.asList(ids));
        if(!removeByIds) return Result.error("删除访客记录失败");
        return Result.ok();
    }
}