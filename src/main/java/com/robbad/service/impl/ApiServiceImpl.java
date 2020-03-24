package com.robbad.service.impl;

import com.robbad.dao.ApiMapper;
import com.robbad.dao.UserDao;
import com.robbad.model.Basicmanager;
import com.robbad.model.QdTj;
import com.robbad.model.QdXsxl;
import com.robbad.service.ApiService;
import com.robbad.util.WebTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private ApiMapper apiMapper;
    @Autowired
    private UserDao userDao;
    @Override
    public Object apiInsertBasicmanager(Basicmanager basicmanager, String submitIP) {
        String SubmitSkipUrl = userDao.findSubmitSkipUrl();
        try {
            if (userDao.findCaiLiangIP(submitIP) > 0) {
//            return WebTools.returnData("此IP已提交过信息，请不要用此设备再次提交",1);
                return WebTools.returnData("此IP已提交过信息，请不要用此设备再次提交", 1);
            }
            String SourceName = "云借条主渠道";
            Integer ztcgmpay = userDao.inquirePricesssztc();
            if (basicmanager.getQdSource() != null) {
                SourceName = userDao.findQdSourceName(basicmanager.getQdSource());
                QdTj QdTjTable = userDao.findQdTj(basicmanager.getQdSource());
                int klssqls = (int) (Math.ceil((double) (QdTjTable.getQdSql() + 1) * (double) QdTjTable.getQdKlbfb() / (double) 100));

                System.out.println(klssqls);
                userDao.updateQDTJSql(basicmanager.getQdSource(), klssqls);
            }


//        userDao.updateBalancejiage(ztcgmpay);
            basicmanager.setQdSourceName(SourceName);

            if (userDao.insertBasicmanagerImpl(basicmanager, submitIP) > 0) {

                Basicmanager basicmanagersss = userDao.findQdBasicmanagerOneData(basicmanager, submitIP);
//                QdXsxl Xsxlzs=userDao.findSubmitXsxlzs();//Xsxlzs 总数
                List<QdXsxl> QdXsxlTimeIds = userDao.findQdXsxlLatestTime();
                int status = 0;
                for (QdXsxl QdXsxlTimeId : QdXsxlTimeIds) {
                    if (userDao.inquireBalance(QdXsxlTimeId.getLgPhone()) > ztcgmpay) {
                        if (status < (3 - basicmanagersss.getQdQdztcStatus())) {
                            if (userDao.updateBalance(QdXsxlTimeId.getLgPhone(), ztcgmpay) > 0) {
                                if (userDao.ztcpowerAdd(basicmanagersss.getLgShopUid(), QdXsxlTimeId.getLgPhone(), ztcgmpay, basicmanager.getQdSource()) > 0) {
                                    if (userDao.ztcUpdateQdXsxls(QdXsxlTimeId.getXsxlId()) > 0) {
                                        if (userDao.ztcUpdateBasicmanager(basicmanagersss.getLgShopUid()) > 0) {
                                            status = status + 1;
                                        }
                                        ;
                                    }
                                    ;
                                }
                                ;
                            }
                        }
                    }
                }

            }
            Map<String,Object> maps=new HashMap<>();
            maps.put("skipurl",SubmitSkipUrl);
            return WebTools.returnData(maps, 0);
        } catch (Exception e) {
            return WebTools.returnData("添加失败", 1);
        }
    }
}
