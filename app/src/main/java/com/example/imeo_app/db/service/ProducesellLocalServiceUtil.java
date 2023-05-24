package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Producesell;

import java.util.List;

public class ProducesellLocalServiceUtil {

    private ProducesellLocalService produceSellLocalService;

    public ProducesellLocalServiceUtil(Context context) {
        produceSellLocalService = new ProducesellLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Producesell insertProducesell(Producesell user) {
        return produceSellLocalService.createProducesell(user);
    }

    public Producesell updateProducesell(Producesell produceSell) {
        return produceSellLocalService.updateProducesell(produceSell);
    }

    public Producesell getProducesellById(long produceSellId) {
        return produceSellLocalService.getProducesellById(produceSellId);
    }

    public List<Producesell> getProducesell() {

        return produceSellLocalService.getProducesell();
    }

    public List<Producesell> getProducesellByReportId(long reportId) {
        return produceSellLocalService.getProducesellByReportId(reportId);
    }

    public List<Producesell> getProducesellByMineralMaterialId(long mineId) {
        return produceSellLocalService.getProducesellByReportId(mineId);
    }

    public boolean deleteProducesell(long userId) {
     return produceSellLocalService.deleteProducesell(userId);
   }

}
