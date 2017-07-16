package com.english.rockGod.pc.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * Created by Administrator on 2017/7/16/016.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryEntity {
    private Integer id;
    private String sname;
    private String fatherId;
}
