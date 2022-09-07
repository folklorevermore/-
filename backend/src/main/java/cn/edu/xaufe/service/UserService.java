package cn.edu.xaufe.service;

import cn.edu.xaufe.entity.User;

import java.util.List;

public interface UserService {

    //查询所有管理员
    List<User> findAllUser();

    //通过uid查询管理员信息
    User selectUserByUid(Integer uid);

    //通过userName模糊查询一个/多个管理员信息
    List<User> selectUserByUserName(String userName);

    //新增一个停车管理员
    Boolean addUser(User user);

    //更新停车管理员信息
    Boolean updateUser(User user);

}
