package com.qutopia.blog.service;

import com.qutopia.blog.entity.UserDO;
import com.qutopia.blog.gateway.web.front.dto.LoginForm;
import com.qutopia.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 用户服务
 *
 * @author choaklin
 * @date 2019.01.26
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    /**
     * 处理登陆的请求
     *
     * @param loginForm
     * @return
     */
    public boolean login(LoginForm loginForm) {

        UserDO userDO = userRepository.findUniqueOneByQuery(Query.query(
                Criteria.where("loginInput").is(loginForm.getLoginInput())
                        .and("password").is(loginForm.getPassword())
        ));
        Assert.notNull(userDO, "账号不存在或密码不正确");
        return true;
    }
}
