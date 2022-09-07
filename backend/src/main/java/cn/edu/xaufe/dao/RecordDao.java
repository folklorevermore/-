package cn.edu.xaufe.dao;

import cn.edu.xaufe.entity.Record;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecordDao {

    //查询所有停车记录
    List<Record> findAllRecord();

    //通过车牌号plate查询一条/多条停车记录
    List<Record> selectRecordByPlate(String plate);

    //通过停车记录编号rid查询一条停车记录
    Record selectRecordByRid(String rid);

    //通过车辆车位号carport来查询一条/多条停车记录
    List<Record> selectRecordByCarport(String carport);

    //通过停车类型（永久1/临停0）查询一条/多条停车记录
    List<Record> selectRecordByType(String type);

    //新增一条停车记录
    Boolean addRecord(Record record);

    //修改一条停车记录
    Boolean updateRecord(Record record);
}
