package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Mineralmaterial;

import java.util.List;

public class MineralmaterialLocalServiceUtil {

    private MineralmaterialLocalService  mineralMaterialLocalService;

    public MineralmaterialLocalServiceUtil(Context context) {
         mineralMaterialLocalService = new MineralmaterialLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Mineralmaterial insertMineralmaterial(Mineralmaterial mineralmaterial) {
        return  mineralMaterialLocalService.createMineralmaterial(mineralmaterial);
    }

    public Mineralmaterial updateMineralmaterial(Mineralmaterial  mineralMaterial) {
        return  mineralMaterialLocalService.updateMineralmaterial( mineralMaterial);
    }

    public Mineralmaterial getMineralmaterialById(long  mineralMaterialId) {
        return  mineralMaterialLocalService.getMineralmaterialById( mineralMaterialId);
    }

    public List<Mineralmaterial> getMineralmaterial() {

        return  mineralMaterialLocalService.getMineralmaterial();
    }

    public List<Mineralmaterial> getMineralmaterialByMineId(long mineId) {
        return  mineralMaterialLocalService.getMineralmaterialByMineId(mineId);
    }

    public List<Mineralmaterial> getMineralmaterialByMineralId(long mineralMaterialId) {
        return  mineralMaterialLocalService.getMineralmaterialByMineralId(mineralMaterialId);
    }

    public boolean deleteMineralmaterial(long mineralMaterialId) {
     return  mineralMaterialLocalService.deleteMineralmaterial(mineralMaterialId);
   }

}
