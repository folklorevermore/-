package cn.edu.xaufe.service;

import cn.edu.xaufe.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import cn.edu.xaufe.dao.RecordDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordDao recordDao;

    //查询所有停车记录
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Record> findAllRecord() {
        return recordDao.findAllRecord();
    }

    //通过车牌号plate查询一条/多条停车记录
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Record> selectRecordByPlate(String plate) {
        return recordDao.selectRecordByPlate(plate);
    }

    //通过停车记录编号rid查询一条停车记录
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Record selectRecordByRid(String rid) {
        return recordDao.selectRecordByRid(rid);
    }

    //通过车辆车位号carport来查询一条/多条停车记录
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Record> selectRecordByCarport(String carport) {
        return recordDao.selectRecordByCarport(carport);
    }

    //通过停车类型（永久1/临停0）查询一条/多条停车记录
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Record> selectRecordByType(String type) {
        return recordDao.selectRecordByType(type);
    }

    //新增一条停车记录
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean addRecord(Record record) {
        if (recordDao.addRecord(record)) {
            return true;
        } else {
            return false;
        }
    }

    //修改一条停车记录
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Boolean updateRecord(Record record) {
        if (recordDao.updateRecord(record)) {
            return true;
        } else {
            return false;
        }
    }

}
