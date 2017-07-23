package com.english.rockGod.pc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by jimHu on 17/7/23.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultMap implements Serializable {

    private String msg;
    private Object data;
    private String code;




}
