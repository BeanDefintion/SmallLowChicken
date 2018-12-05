package com.xpj.service.impl;

import com.xpj.common.SpringContextHolder;
import com.xpj.mapper.UserMapper;
import com.xpj.model.User;
import com.xpj.model.UserExample;
import com.xpj.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Override
    public void findBySokeNo() {
        UserMapper userMapper = (UserMapper) SpringContextHolder.getBean(UserMapper.class);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andSokenoEqualTo(1);
        User user = userMapper.selectByExample(userExample).get(0);
        System.err.println(user.getLabel());
    }

}
