package com.example.intelligentcommunity.vo;

import lombok.Data;

@Data
public class ChildMenuRouterVO {
    private String name;
    private String path;
    private String component;
    private String hidden;
    private MetaVO meta;
}
