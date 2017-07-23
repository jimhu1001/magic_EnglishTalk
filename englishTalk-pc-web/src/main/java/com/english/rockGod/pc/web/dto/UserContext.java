package com.english.rockGod.pc.web.dto;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by jimHu on 16/4/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {
    private int userId;
    private int customerId;
    private String name;
    private List<UserPermissionDto> permissionDtos;
    private String imageUrl;
    private String languageCode="0001";


    public List<Integer> permissionIds() {

        if (CollectionUtils.isEmpty(permissionDtos)) return Lists.newArrayList();

        List<Integer> permissionIds=Lists.newArrayList();

        for(UserPermissionDto permissionDto :permissionDtos){
            permissionIds.add(permissionDto.getPermissionId());
        }

        return permissionIds;
    }

}
