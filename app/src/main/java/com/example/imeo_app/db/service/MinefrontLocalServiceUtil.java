package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Minefront;

import java.util.List;

public class MinefrontLocalServiceUtil {

    private MinefrontLocalService mineFrontLocalService;

    public MinefrontLocalServiceUtil(Context context) {
        mineFrontLocalService = new MinefrontLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Minefront insertMinefront(Minefront user) {
        return mineFrontLocalService.createMinefront(user);
    }

    public Minefront updateMinefront(Minefront mineFront) {
        return mineFrontLocalService.updateMinefront(mineFront);
    }

    public Minefront getMinefrontById(long mineFrontId) {
        return mineFrontLocalService.getMinefrontById(mineFrontId);
    }

    public List<Minefront> getMinefront() {
        return mineFrontLocalService.getMinefront();
    }

    public List<Minefront> getMinefrontByReportId(long reportId) {
        return mineFrontLocalService.getMinefrontByReportId(reportId);
    }

    public boolean deleteMinefront(long userId) {
     return mineFrontLocalService.deleteMinefront(userId);
   }

}
