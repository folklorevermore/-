package cn.edu.xaufe.service;

import cn.edu.xaufe.entity.Car;

import java.util.List;

public interface CarService {

    //通过车牌号plate查询车辆信息
    Car selectCarByPlate(String plate);

    //通过车位号carport查询车辆信息
    Car selectCarByCarport(String carport);

    //通过车主姓名ownerName查询一个/多个车辆信息
    List<Car> selectCarByOwnerName(String ownerName);

    //通过车主联系电话ownerPhone查询一个/多个车辆信息
    List<Car> selectCarByOwnerPhone(String ownerPhone);

    //查询所有车辆信息
    List<Car> findAllCar();

    //通过车辆类型（永久1/临停0）查询一个/多个车辆信息
    List<Car> selectCarByType(String type);

    //新增一个车辆信息
    Boolean addCar(Car car);

    //修改一个车辆信息
    Boolean updateCar(Car car);
}
