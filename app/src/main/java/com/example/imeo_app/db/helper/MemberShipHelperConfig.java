package com.example.imeo_app.db.helper;

import com.example.imeo_app.db.tables.Membership;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public interface MemberShipHelperConfig {

    Dao<Membership, Long> getMemberShipDao();

    RuntimeExceptionDao<Membership, Long> getMemberShipRuntimeExceptionDao();
}
