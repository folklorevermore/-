package cn.edu.xaufe.service;

import cn.edu.xaufe.entity.Car;
import cn.edu.xaufe.dao.CarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    //通过车牌号plate查询车辆信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Car selectCarByPlate(String plate) {
        return carDao.selectCarByPlate(plate);
    }

    //通过车位号carport查询车辆信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Car selectCarByCarport(String carport) {
        return carDao.selectCarByCarport(carport);
    }

    //通过车主姓名ownerName查询一个/多个车辆信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Car> selectCarByOwnerName(String ownerName) {
        return carDao.selectCarByOwnerName(ownerName);
    }

    //通过车主联系电话ownerPhone查询一个/多个车辆信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Car> selectCarByOwnerPhone(String ownerPhone) {
        return carDao.selectCarByOwnerPhone(ownerPhone);
    }

    //查询所有车辆信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Car> findAllCar() {
        return carDao.findAllCar();
    }

    //通过车辆类型（永久1/临停0）查询一个/多个车辆信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Car> selectCarByType(String type) {
        return carDao.selectCarByType(type);
    }

    //新增一个车辆信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean addCar(Car car) {
        if (carDao.addCar(car)) {
            return true;
        } else {
            return false;
        }
    }

    //修改一个车辆信息
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean updateCar(Car car) {
        if (carDao.updateCar(car)) {
            return true;
        } else {
            return false;
        }
    }

}
