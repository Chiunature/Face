package com.example.intelligentcommunity.form;

import lombok.Data;

@Data
public class InOutQueryForm {
    private Integer page;
    private Integer limit;
    private String userName;
    private String startDate;
    private String endDate;
}
