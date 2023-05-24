package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.MemberShipHelperConfig;
import com.example.imeo_app.db.tables.Membership;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

class MemberShipLocalService {

    private final Logger logger = LoggerFactory.getLogger(MemberShipLocalService.class);
    private Dao<Membership, Long> membershipDao;

    MemberShipLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        MemberShipHelperConfig dbHelperConfig = (MemberShipHelperConfig) OpenHelperManager.getHelper(context, clz);
        membershipDao = dbHelperConfig.getMemberShipDao();
    }

    Membership createMemberShip(Membership membership) {
        try {
            membershipDao.create(membership);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return membership;
    }

    Membership updateMemberShip(Membership membership) {
        try {
            membershipDao.update(membership);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return membership;
    }



    List<Membership> getMemberShipByCode(String membershipcode,long membershipCode) {
        try {
            return membershipDao.queryForEq(membershipcode,membershipCode);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }



    Membership getMemberShipById(long membershipId) {
        try {
            return membershipDao.queryForId(membershipId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteMemberShip(long userId) {
        try {
            membershipDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
