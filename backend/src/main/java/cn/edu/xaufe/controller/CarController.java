package cn.edu.xaufe.controller;

import cn.edu.xaufe.entity.Car;
import cn.edu.xaufe.service.CarService;
import cn.edu.xaufe.util.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class CarController {

    @Autowired
    private CarService carService;


    //根据车牌号plate查询车辆信息
    @RequestMapping(value = "/car/findByPlate")
    private CommonResult selectCarByPlate(String plate) {
        Car car = carService.selectCarByPlate(plate);
        if (car != null) {
            return CommonResult.success("车辆信息查询成功", car);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过车位号carport查询车辆信息（！！注意：前端传回的参数不能为空）
    @RequestMapping(value = "/car/findByCarport")
    private CommonResult selectCarByCarport(String carport) {
        Car car = carService.selectCarByCarport(carport);
        if (car != null) {
            return CommonResult.success("车辆信息查询成功", car);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过车主姓名ownerName查询一个/多个车辆信息
    @RequestMapping(value = "/car/findByOwnerName")
    private CommonResult selectCarByOwnerName(String ownerName) {
        List<Car> cars = carService.selectCarByOwnerName(ownerName);
        if (cars != null) {
            return CommonResult.success("车辆信息查询成功", cars);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过车主联系电话ownerPhone查询一个/多个车辆信息
    @RequestMapping(value = "/car/findByOwnerPhone")
    private CommonResult selectCarByOwnerPhone(String ownerPhone) {
        List<Car> cars = carService.selectCarByOwnerPhone(ownerPhone);
        if (cars != null) {
            return CommonResult.success("车辆信息查询成功", cars);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //查询所有车辆信息
    @RequestMapping(value = "/car/findAll")
    private CommonResult findAllCar() {
        List<Car> cars = carService.findAllCar();
        if (cars != null) {
            return CommonResult.success("查询所有车辆信息成功", cars);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过车辆类型type（永久1/临停0）查询一个/多个车辆信息
    @RequestMapping(value = "/car/findByType")
    private CommonResult selectCarByType(String type) {
        List<Car> cars = carService.selectCarByType(type);
        if (cars != null) {
            return CommonResult.success("查询车辆信息成功", cars);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //新增一个车辆信息
    @RequestMapping(value = "/car/add")
    private CommonResult addCar(HttpServletRequest request) {
        String plate = request.getParameter("plate");
        String carport = request.getParameter("carport");
        Integer type = Integer.parseInt(request.getParameter("type"));
        String ownerName = request.getParameter("ownerName");
        String ownerPhone = request.getParameter("ownerPhone");

        Car newCar = new Car();
        newCar.setInfo(plate,carport,type,ownerName,ownerPhone);
        if (carService.addCar(newCar)) {
            return CommonResult.success("车辆新增成功");
        } else {
            return CommonResult.error("车辆新增失败");
        }
    }

    //修改一个车辆信息
    @RequestMapping(value = "/car/update")
    private CommonResult updateCar(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String plate = request.getParameter("plate");
        String carport = request.getParameter("carport");
        Integer type = Integer.parseInt(request.getParameter("type"));
        String ownerName = request.getParameter("ownerName");
        String ownerPhone = request.getParameter("ownerPhone");

        Car alterCar = new Car();
        alterCar.setId(id);
        alterCar.setInfo(plate,carport,type,ownerName,ownerPhone);
        if (carService.updateCar(alterCar)) {
            return CommonResult.success("车辆信息修改成功");
        } else {
            return CommonResult.error("车辆信息修改失败");
        }
    }


}
