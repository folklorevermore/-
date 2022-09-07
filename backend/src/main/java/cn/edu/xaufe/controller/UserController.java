package cn.edu.xaufe.controller;

import cn.edu.xaufe.entity.User;
import cn.edu.xaufe.service.UserService;
import cn.edu.xaufe.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    //查询所有管理员
    @RequestMapping(value = "/user/findAll")
    private CommonResult findAllUser() {
        List<User> users = userService.findAllUser();
        if (users != null) {
            return CommonResult.success("查询所有管理员成功", users);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过uid查询管理员信息
    @RequestMapping(value = "/user/findByUid")
    private CommonResult selectUserByUid(Integer uid) {
        User user = userService.selectUserByUid(uid);
        if (user != null) {
            return CommonResult.success("管理员查询成功", user);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过userName模糊查询一个/多个管理员信息
    @RequestMapping(value = "/user/findByUserName")
    private CommonResult selectUserByUserName(String userName) {
        List<User> users = userService.selectUserByUserName(userName);
        if (users != null) {
            return CommonResult.success("管理员查询成功", users);
        } else {
            return CommonResult.error("查询失败");
        }
    }


    //新增一个停车管理员
    @RequestMapping("/user/add")
    private CommonResult addUser(HttpServletRequest request) {
        Integer uid = Integer.parseInt(request.getParameter("uid"));
        String name = request.getParameter("userName");
        String password = request.getParameter("password");

        User user = new User();
        user.setInfo(uid, name, password);
        if (userService.addUser(user)) {
            return CommonResult.success("管理员新增成功");
        } else {
            return CommonResult.error("管理员新增失败");
        }
    }

    //更新停车管理员信息
    @RequestMapping("/user/update")
    private CommonResult updateUser(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer uid = Integer.parseInt(request.getParameter("uid"));
        String name = request.getParameter("userName");
        String password = request.getParameter("password");

        User user = new User();
        user.setId(id);
        user.setInfo(uid, name, password);
        if (userService.updateUser(user)) {
            return CommonResult.success("管理员信息修改成功");
        } else {
            return CommonResult.error("管理员信息修改失败");
        }
    }

}
