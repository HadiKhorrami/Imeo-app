package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Mineoperation;

import java.util.List;

public class MineoperationLocalServiceUtil {

    private MineoperationLocalService mineOperationLocalService;

    public MineoperationLocalServiceUtil(Context context) {
        mineOperationLocalService = new MineoperationLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Mineoperation insertMineoperation(Mineoperation user) {
        return mineOperationLocalService.createMineoperation(user);
    }

    public Mineoperation updateMineoperation(Mineoperation mineOperation) {
        return mineOperationLocalService.updateMineoperation(mineOperation);
    }

    public Mineoperation getMineoperationById(long mineOperationId) {
        return mineOperationLocalService.getMineoperationById(mineOperationId);
    }

    public List<Mineoperation> getMineoperation() {

        return mineOperationLocalService.getMineoperation();
    }

    public List<Mineoperation> getMineoperationByReportId(long reportId) {
        return mineOperationLocalService.getMineoperationByReportId(reportId);
    }

    public boolean deleteMineoperation(long userId) {
     return mineOperationLocalService.deleteMineoperation(userId);
   }

}
