package com.prgers.mp.test;

import com.prgers.entity.User;
import com.prgers.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class) //运行在什么环境下：加载spring容器
@SpringBootTest
public class MPTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有用户
     */
    @Test
    public void selectList() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.err::println);
    }

    /**
     * 添加用户
     */
    @Test
    public void insertUserTest() {
        User user = new User();
        user.setAge(100);
        user.setEmail("xx@163.com");
        user.setName("潘小懒");
        userMapper.insert(user);
        System.err.println(user.toString());
    }
}
