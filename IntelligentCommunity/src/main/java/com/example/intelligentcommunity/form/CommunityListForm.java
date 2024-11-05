package com.example.intelligentcommunity.form;

import lombok.Data;

@Data
public class CommunityListForm {
    private Long page;
    private Long limit;
    private Integer communityId;
    private String communityName;
}
