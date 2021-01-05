package com.prgers.mp.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prgers.entity.User;
import com.prgers.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class) //运行在什么环境下：加载spring容器
@SpringBootTest
public class MPTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户
     */
    @Test
    public void insertUserTest() {
        User user = new User();
        user.setAge(18);
        user.setEmail("prger@163.com");
        user.setName("prger4");
        userMapper.insert(user);
        System.err.println(user.toString());
    }

    /**
     * 用户数据更新
     *
     * 1、在企业中经常会出现创建时间和修改时间,每次在插入和修改数据的时候都要赋值这个字段，那么mp帮我们自动添加
     * 2、修改的时候容易数据混乱，数据不准确；
     *    锁：
     *      悲观锁：它认为你是不合法的操作、每一次操作都要加锁；  效率太低；
     *      乐观锁： 认为你的操作是合法的、每一次操作都不加锁；   数据安全太差！
     *      在表中添加一个version字段，在修改数据的时候、根据版本号来修改；
     */
    @Test
    public void updateUserTest() {

//        User user = new User();
//        user.setId(4L);
//        user.setAge(22);
//        int result = userMapper.updateById(user);
//        System.out.println(result);

        User user = userMapper.selectById(1346300191006875649L);
        user.setEmail("123456@163.com");
        user.setVersion(user.getVersion() - 1);
        userMapper.updateById(user);
    }

    /**
     * 用户查询
     */
    @Test
    public void selectUserTest() {
//        User user = userMapper.selectById(1346300191006875649L);
//        System.err.println(user.toString());

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        ids.add(5L);

        List<User> users = userMapper.selectBatchIds(ids);
        users.forEach(System.err::println);
    }

    /**
     * 查询所有用户
     */
    @Test
    public void selectAllUserList() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.err::println);
    }

    /**
     * 根据条件查询用户
     */
    @Test
    public void selectUserByMapTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","潘小懒");
        map.put("age", 100);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.err::println);
    }

    /**
     * Wrapper 条件查询
     */
    @Test
    public void selectByWrapperTest() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 20);
        queryWrapper.like("name", "潘");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.err::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void selectByPageTest() {
        Page<User> page = new Page<>(1,3);
        //设置分页的起始值和每页的数量
        userMapper.selectPage(page, null);

        //显示每页的数据
        List<User> userList = page.getRecords();
        userList.forEach(System.err::println);

        System.out.println("当前页码：" + page.getCurrent());
        System.out.println("每页数量：" + page.getSize());
        System.err.println("总页数：" + page.getPages());
        System.err.println("总记录数：" + page.getTotal());
        System.err.println("是否有下一页：" + page.hasNext());
        System.err.println("是否有上一页：" + page.hasPrevious());
    }

    /**
     * 删除用户
     */
    @Test
    public void deleteUserTest() {

        //根据id删除
//        int result = userMapper.deleteById(1346341527013597186L);
//        System.err.println(result);

        //批量删除
//        int result = userMapper.deleteBatchIds(Arrays.asList(1346342377228410881L, 1346341477202071554L));
//        System.err.println(result);

        //条件删除
        Map<String, Object> map = new HashMap<>();
        map.put("name","prger1");
        map.put("age", 18);
        userMapper.deleteByMap(map);
    }

    /**
     * 物理删除：真实的删除，将对应数据从数据库中删除，之后查询不到此条被删除的数据
     * 逻辑删除：假删除，将对应数据中代表被删除字段状态修改为"被删除状态"，之后在数据库中仍旧能看到此条数据记录
     */
    @Test
    public void LogicDeleteTest() {

        int result = userMapper.deleteById(1346355405088956418L);
        System.err.println(result);
    }
}
