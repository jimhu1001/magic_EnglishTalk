package com.english.rockGod.pc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by jimHu on 16/5/31.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionDto implements Serializable {
    private Integer permissionId;
    private String name;
    private int groupType;
    private String enums;
    private PermissionRangeDto orgRange;

}
