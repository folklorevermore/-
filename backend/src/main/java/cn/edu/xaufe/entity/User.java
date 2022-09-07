package cn.edu.xaufe.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private Integer id;
    private Integer uid;
    private String userName;
    private Integer role;
    private String password;

    public User() {
    }

    public User(Integer id, Integer uid, String userName, Integer role, String password) {
        this.id = id;
        this.uid = uid;
        this.userName = userName;
        this.role = role;
        this.password = password;
    }


    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //新增&&修改停车管理员的方法
    public void setInfo(Integer uid, String userName, String password) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uid=" + uid +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
