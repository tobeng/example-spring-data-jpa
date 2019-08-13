package com.tobeng.test.dao.user;

import com.tobeng.test.dao.BaseDao;
import com.tobeng.test.entity.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<User> {
}
