package com.example.intelligentcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.intelligentcommunity.annotation.LogAnnotation;
import com.example.intelligentcommunity.entity.*;
import com.example.intelligentcommunity.form.CommunityAddOrUpdateForm;
import com.example.intelligentcommunity.form.CommunityListForm;
import com.example.intelligentcommunity.service.*;
import com.example.intelligentcommunity.util.Result;
import com.example.intelligentcommunity.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/sys/community")
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    @Autowired
    private CameraService cameraService;
    @Autowired
    private InOutRecordService inOutRecordService;
    @Autowired
    private ManualRecordService manualRecordService;
    @Autowired
    private PersonService personService;

    @GetMapping("/list")
    public Result list(CommunityListForm communityListForm){
        PageVO pageVO = this.communityService.communityList(communityListForm);
        return Result.ok().put("data", pageVO);
    }

    @LogAnnotation("添加小区")
    @PostMapping("/add")
    public Result add(@RequestBody CommunityAddOrUpdateForm communityAddOrUpdateForm, HttpSession session){
        Community community = new Community();
        BeanUtils.copyProperties(communityAddOrUpdateForm, community);
        User user = (User) session.getAttribute("user");
        community.setCreater(user.getUsername());
        boolean save = this.communityService.save(community);
        if(!save) return Result.error("小区添加失败");
        return Result.ok();
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Integer id){
        Community community = this.communityService.getById(id);
        if(community == null) return Result.error("小区不存在");
        return Result.ok().put("data", community);
    }


    @LogAnnotation("编辑小区")
    @PutMapping("/edit")
    public Result edit(@RequestBody CommunityAddOrUpdateForm communityAddOrUpdateForm){
        Community community = new Community();
        BeanUtils.copyProperties(communityAddOrUpdateForm, community);
        boolean updateById = this.communityService.updateById(community);
        if(!updateById) return Result.error("编辑小区失败");
        return Result.ok();
    }

    @LogAnnotation("删除小区")
    @DeleteMapping("/del")
    @Transactional
    public Result del(@RequestBody Integer[] ids){
        QueryWrapper<Camera> cameraQueryWrapper = new QueryWrapper<>();
        cameraQueryWrapper.in("community_id", ids);
        boolean remove1 = this.cameraService.remove(cameraQueryWrapper);
        if(!remove1) return Result.error("小区摄像头删除失败");
        QueryWrapper<InOutRecord> inOutRecordQueryWrapper = new QueryWrapper<>();
        inOutRecordQueryWrapper.in("community_id", ids);
        boolean remove2 = this.inOutRecordService.remove(inOutRecordQueryWrapper);
        if(!remove2) return Result.error("小区出入记录删除失败");
        QueryWrapper<ManualRecord> manualRecordQueryWrapper = new QueryWrapper<>();
        manualRecordQueryWrapper.in("community_id", ids);
        boolean remove3 = this.manualRecordService.remove(manualRecordQueryWrapper);
        if(!remove3) return Result.error("小区访客记录删除失败");
        QueryWrapper<Person> personQueryWrapper = new QueryWrapper<>();
        personQueryWrapper.in("community_id", ids);
        boolean remove4 = this.personService.remove(personQueryWrapper);
        if(!remove4) return Result.error("小区居民删除失败");
        boolean remove5 = this.communityService.removeByIds(Arrays.asList(ids));
        if(!remove5) return Result.error("小区删除失败");
        return Result.ok();
    }

    @GetMapping("/getCommunityMap")
    public Result getCommunityMap(){
        List<Community> list = this.communityService.list();
        if(list == null) return Result.error("没有小区数据");
        return Result.ok().put("data", list);
    }
}

