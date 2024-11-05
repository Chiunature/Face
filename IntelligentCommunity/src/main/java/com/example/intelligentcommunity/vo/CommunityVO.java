package com.example.intelligentcommunity.vo;

import lombok.Data;

import java.util.Date;
@Data
public class CommunityVO {
    private Integer communityId;
    private String communityName;
    private Integer termCount;
    private Integer seq;
    private String creater;
    private Date createTime;
    private Float lng;
    private Float lat;
    private Integer personCnt;
}
