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
//        userDao.deleteMessage(basicmanager);
//        QdTj QdTjReturn = userDao.findqdtjMessage(basicmanager.getQdSource());
//        String SubmitSkipUrl = userDao.findSubmitSkipUrl();
//        if (QdTjReturn.getQdStatus() == 1) {
            return WebTools.returnData("温馨提示：渠道被冻结", 1);
//        }
//        try {
//            if (userDao.findCaiLiangIP(submitIP) > 0) {
//                return WebTools.returnData(SubmitSkipUrl, 2);
//            }
//            Integer ztcgmpay = userDao.inquirePricesssztc();
//            basicmanager.setQdSourceName(QdTjReturn.getQdQdname());
//            if (userDao.insertBasicmanagerImpl(basicmanager, submitIP) > 0) {
//                if (QdTjReturn.getQdJzt() == 0) {
//                    Basicmanager basicmanagersss = userDao.findQdBasicmanagerOneData(basicmanager, submitIP);
//                    List<QdXsxl> QdXsxlTimeIds = userDao.findQdXsxlLatestTime();
//                    int status = 0;
//                    for (QdXsxl QdXsxlTimeId : QdXsxlTimeIds) {
//                        Integer UserZtcStatus = userDao.findUserZtcStatus(QdXsxlTimeId.getLgPhone());
//                        if (UserZtcStatus != null && UserZtcStatus == 1) {
//                            if (userDao.inquireBalance(QdXsxlTimeId.getLgPhone()) >= ztcgmpay) {
//                                if (status < (3 - basicmanagersss.getQdQdztcStatus())) {
//                                    if (userDao.updateBalance(QdXsxlTimeId.getLgPhone(), ztcgmpay) > 0) {
//                                        if (userDao.ztcpowerAdd(basicmanagersss.getLgShopUid(), QdXsxlTimeId.getLgPhone(), ztcgmpay, basicmanager.getQdSource()) > 0) {
//                                            if (userDao.ztcUpdateQdXsxls(QdXsxlTimeId.getXsxlId()) > 0) {
//                                                if (userDao.ztcUpdateBasicmanager(basicmanagersss.getLgShopUid()) > 0) {
//                                                    status = status + 1;
//                                                }
//                                                ;
//                                            }
//                                            ;
//                                        }
//                                        ;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    if (status == 0) {
//                        userDao.ztcpowerAdd(basicmanagersss.getLgShopUid(), "", 0, basicmanager.getQdSource());
//                    }
//                }
//            }
//            return WebTools.returnData(SubmitSkipUrl, 0);
//        } catch (Exception e) {
//            return WebTools.returnData(SubmitSkipUrl, 2);
//        }
    }
}
