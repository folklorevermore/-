package cn.edu.xaufe.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "record")
public class Record implements Serializable {

    private Integer id;

    //停车记录编号
    private String rid;

    //车辆id
    private Integer cid;

    //停车类型（永久1/临停0）
    private Integer type;

    //进场时间
    private Integer inTime;

    //出场时间
    private Integer outTime;

    //收费金额
    private Integer charge;

    //Mybatis一对多的关系映射，停车记录record中封装car车辆信息
    private Car car;


    public Record() {
    }

    public Record(Integer id, String rid, Integer cid, Integer type, Integer inTime, Integer outTime, Integer charge, Car car) {
        this.id = id;
        this.rid = rid;
        this.cid = cid;
        this.type = type;
        this.inTime = inTime;
        this.outTime = outTime;
        this.charge = charge;
        this.car = car;
    }


    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInTime() {
        return inTime;
    }

    public void setInTime(Integer inTime) {
        this.inTime = inTime;
    }

    public Integer getOutTime() {
        return outTime;
    }

    public void setOutTime(Integer outTime) {
        this.outTime = outTime;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    //新增&&修改停车记录的方法
    public void setInfo(String rid, Integer cid, Integer type, Integer inTime, Integer outTime, Integer charge) {
        this.rid = rid;
        this.cid = cid;
        this.type = type;
        this.inTime = inTime;
        this.outTime = outTime;
        this.charge = charge;
    }


    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", rid='" + rid + '\'' +
                ", cid=" + cid +
                ", type=" + type +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", charge=" + charge +
                ", car=" + car +
                '}';
    }
}
