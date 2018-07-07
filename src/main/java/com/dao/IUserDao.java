package com.dao;

import com.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Auther: apple
 * @Date: 2018/7/2 21:35
 * @Description:
 */
public interface IUserDao {

    User getById(@Param("id") Long id);
}
