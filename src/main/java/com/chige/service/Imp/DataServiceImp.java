package com.chige.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chige.domain.DataBean;
import com.chige.mapper.DataMapper;
import com.chige.service.DataService;
import org.springframework.stereotype.Service;


@Service
public class DataServiceImp
        extends ServiceImpl<DataMapper,DataBean>
        implements DataService {

//    @Override
//    public List<DataBean> list() {
//        List<DataBean> dataBeans = null;
//        try {
//            dataBeans = DataHandler.myHandleData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return dataBeans;
//    }
}
