package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public interface Cascade {
    Map process(List<Field> var1, Map var2);
}
