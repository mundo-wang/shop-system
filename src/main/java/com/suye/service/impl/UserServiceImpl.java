package com.suye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suye.entity.User;
import com.suye.service.UserService;
import com.suye.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




