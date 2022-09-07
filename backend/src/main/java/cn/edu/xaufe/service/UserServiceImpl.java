package cn.edu.xaufe.service;

import cn.edu.xaufe.entity.User;
import cn.edu.xaufe.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    //查询所有管理员
    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    //通过uid查询管理员信息
    @Override
    public User selectUserByUid(Integer uid) {
        return userDao.selectUserByUid(uid);
    }

    //通过userName模糊查询一个/多个管理员信息
    @Override
    public List<User> selectUserByUserName(String userName) {
        return userDao.selectUserByUserName(userName);
    }

    //新增一个停车管理员
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean addUser(User user) {
        userDao.addUser(user);
        return true;
    }

    //更新停车管理员信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean updateUser(User user) {
        userDao.updateUser(user);
        return true;
    }
}
