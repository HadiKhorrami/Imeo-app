package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Mine;

import java.util.List;

public class MineLocalServiceUtil {

    private MineLocalService mineLocalService;

    public MineLocalServiceUtil(Context context) {
        mineLocalService = new MineLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Mine insertMine(Mine user) {
        return mineLocalService.createMine(user);
    }

    public Mine updateMine(Mine mine) {
        return mineLocalService.updateMine(mine);
    }

    public Mine getMineById(long mineId) {
        return mineLocalService.getMineById(mineId);
    }

    public List<Mine> getMine() {

        return mineLocalService.getMine();
    }

    public List<Mine> getMineByCode(String minecode,long mineCode) {
        return mineLocalService.getMineByCode(minecode,mineCode);
    }

    public boolean deleteMine(long userId) {
     return mineLocalService.deleteMine(userId);
   }

}
