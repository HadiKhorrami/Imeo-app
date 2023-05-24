package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Machinery;

import java.util.List;

public class MachineryLocalServiceUtil {

    private MachineryLocalService machineryLocalService;

    public MachineryLocalServiceUtil(Context context) {
        machineryLocalService = new MachineryLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Machinery insertMachinery(Machinery user) {
        return machineryLocalService.createMachinery(user);
    }

    public Machinery updateMachinery(Machinery machinery) {
        return machineryLocalService.updateMachinery(machinery);
    }

    public Machinery getMachineryById(long machineryId) {
        return machineryLocalService.getMachineryById(machineryId);
    }

    public List<Machinery> getMachinery() {

        return machineryLocalService.getMachinery();
    }

    public List<Machinery> getMachineryByReportId(long reportId) {
        return machineryLocalService.getMachineryByReportId(reportId);
    }

    public boolean deleteMachinery(long userId) {
     return machineryLocalService.deleteMachinery(userId);
   }

}
