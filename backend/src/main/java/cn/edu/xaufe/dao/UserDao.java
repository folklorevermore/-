package cn.edu.xaufe.dao;

import cn.edu.xaufe.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {

    //查询所有管理员
    @Select("select * from pk.user;")
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
