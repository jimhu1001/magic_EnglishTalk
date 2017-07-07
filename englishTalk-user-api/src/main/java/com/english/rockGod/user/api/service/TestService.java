package com.english.rockGod.user.api.service;

import com.english.rockGod.user.api.dto.TestEntityDto;

/**
 * Created by Administrator on 2017/7/7/007.
 */
public interface TestService {
    TestEntityDto findById(int id);
}
