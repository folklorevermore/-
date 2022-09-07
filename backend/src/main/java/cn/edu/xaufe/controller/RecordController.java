package cn.edu.xaufe.controller;

import cn.edu.xaufe.entity.Record;
import cn.edu.xaufe.util.CommonResult;
import cn.edu.xaufe.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class RecordController {

    @Autowired
    private RecordService recordService;

    //查询所有停车记录
    @RequestMapping(value = "/record/findAll")
    private CommonResult findAllRecord() {
        List<Record> records = recordService.findAllRecord();
        if (records != null) {
            return CommonResult.success("全部停车记录查询成功", records);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过车牌号plate查询一条/多条停车记录
    @RequestMapping(value = "/record/findByPlate")
    private CommonResult selectRecordByPlate(String plate) {
        List<Record> records = recordService.selectRecordByPlate(plate);
        if (records != null) {
            return CommonResult.success("停车记录查询成功", records);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过停车记录编号rid查询一条停车记录
    @RequestMapping(value = "/record/findByRid")
    private CommonResult selectRecordByRid(String rid) {
        Record record = recordService.selectRecordByRid(rid);
        if (record != null) {
            return CommonResult.success("停车记录查询成功", record);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过车辆车位号carport来查询一条/多条停车记录（* 注意：如果参数为空则变成了查询所有临停停车记录，前端查询时参数不能为空）
    @RequestMapping(value = "/record/findByCarport")
    private CommonResult selectRecordByCarport(String carport) {
        List<Record> records = recordService.selectRecordByCarport(carport);
        if (records != null) {
            return CommonResult.success("停车记录查询成功", records);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //通过停车类型（永久1/临停0）查询一条/多条停车记录
    @RequestMapping(value = "/record/findByType")
    private CommonResult selectRecordByType(String type) {
        List<Record> records = recordService.selectRecordByType(type);
        if (records != null) {
            return CommonResult.success("停车记录查询成功", records);
        } else {
            return CommonResult.error("查询失败");
        }
    }

    //新增一条停车记录
    @RequestMapping(value = "/record/add")
    private CommonResult addRecord(HttpServletRequest request) {
        String rid = request.getParameter("rid");
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        Integer type = Integer.parseInt(request.getParameter("type"));
        Integer inTime = Integer.parseInt(request.getParameter("inTime"));
        Integer outTime = Integer.parseInt(request.getParameter("outTime"));
        Integer charge = Integer.parseInt(request.getParameter("charge"));

        Record newRecord = new Record();
        newRecord.setInfo(rid, cid, type, inTime, outTime, charge);
        if (recordService.addRecord(newRecord)) {
            return CommonResult.success("停车记录新增成功");
        } else {
            return CommonResult.error("停车记录新增失败");
        }
    }

    //修改一条停车记录
    @RequestMapping(value = "/record/update")
    private CommonResult updateRecord(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));
        String rid = request.getParameter("rid");
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        Integer type = Integer.parseInt(request.getParameter("type"));
        Integer inTime = Integer.parseInt(request.getParameter("inTime"));
        Integer outTime = Integer.parseInt(request.getParameter("outTime"));
        Integer charge = Integer.parseInt(request.getParameter("charge"));

        Record alterRecord = new Record();
        alterRecord.setId(id);
        alterRecord.setInfo(rid, cid, type, inTime, outTime, charge);
        if (recordService.updateRecord(alterRecord)) {
            return CommonResult.success("停车记录修改成功");
        } else {
            return CommonResult.error("停车记录修改失败");
        }
    }

}
