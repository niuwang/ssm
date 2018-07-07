package com.service.impl;

import com.dao.IUserDao;
import com.model.User;
import com.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: apple
 * @Date: 2018/7/2 21:35
 * @Description:
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public User test(Long id) {
        User byId = userDao.getById(id);
        return byId;
    }
}
