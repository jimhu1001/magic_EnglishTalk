package com.english.rockGod.pc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jimHu on 16/6/1.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRangeDto implements Serializable {

    private Boolean isAll;
    private List<Integer> range;//数据权限
    private List<Integer> rangeIds;//去重的rangeId
//    private List<Integer> unActiveOrgIds;
//    private List<Integer> allOrgId    s;

}
