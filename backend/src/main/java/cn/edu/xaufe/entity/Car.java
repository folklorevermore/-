package cn.edu.xaufe.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "car")
public class Car implements Serializable {

    private Integer id;

    private String plate;

    private String carport;

    private Integer type;

    private String ownerName;

    private String ownerPhone;


    public Car() {
    }

    public Car(Integer id, String plate, String carport, Integer type, String ownerName, String ownerPhone) {
        this.id = id;
        this.plate = plate;
        this.carport = carport;
        this.type = type;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
    }


    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCarport() {
        return carport;
    }

    public void setCarport(String carport) {
        this.carport = carport;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    //新增&&修改车辆信息的方法
    public void setInfo(String plate, String carport, Integer type, String ownerName, String ownerPhone) {
        this.plate = plate;
        this.carport = carport;
        this.type = type;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", plate='" + plate + '\'' +
                ", carport='" + carport + '\'' +
                ", type=" + type +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                '}';
    }
}
