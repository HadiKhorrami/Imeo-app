package com.example.imeo_app.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.imeo_app.db.tables.Document;
import com.example.imeo_app.db.tables.Machinery;
import com.example.imeo_app.db.tables.Membership;
import com.example.imeo_app.db.tables.Mine;
import com.example.imeo_app.db.tables.Minefront;
import com.example.imeo_app.db.tables.Mineoperation;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.example.imeo_app.db.tables.Producesell;
import com.example.imeo_app.db.tables.Report;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class ImeoDBHelperConfig extends OrmLiteSqliteOpenHelper implements MemberShipHelperConfig,MineHelperConfig,MineralmaterialHelperConfig,MachineryHelperConfig,MinefrontHelperConfig,MineoperationHelperConfig,ReportHelperConfig,ProducesellHelperConfig,DocumentHelperConfig {

    private static final String DATABASE_NAME = "Imeo.db";
    private static final int DATABASE_VERSION = 2;

    private final Logger logger = LoggerFactory.getLogger(com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);

    private Dao<Document, Long> documentDao;
    private Dao<Membership, Long> membershipDao;
    private Dao<Mine, Long> mineDao;
    private Dao<Machinery, Long> machineryDao;
    private Dao<Minefront, Long> minefrontDao;
    private Dao<Mineoperation, Long> mineoperationDao;
    private Dao<Mineralmaterial, Long> mineralmaterialDao;
    private Dao<Producesell, Long> producesellDao;
    private Dao<Report, Long> reportDao;

    private RuntimeExceptionDao<Document, Long> documentRuntimeExceptionDao;
    private RuntimeExceptionDao<Membership, Long> membershipRuntimeDao;
    private RuntimeExceptionDao<Mine, Long> mineRuntimeExceptionDao;
    private RuntimeExceptionDao<Machinery, Long> machineryRuntimeExceptionDao;
    private RuntimeExceptionDao<Minefront, Long> minefrontRuntimeExceptionDao;
    private RuntimeExceptionDao<Mineoperation, Long> mineoperationRuntimeExceptionDao;
    private RuntimeExceptionDao<Mineralmaterial, Long> mineralmaterialRuntimeExceptionDao;
    private RuntimeExceptionDao<Producesell, Long> producesellRuntimeExceptionDaop;
    private RuntimeExceptionDao<Report, Long> reportRuntimeExceptionDao;

    public ImeoDBHelperConfig(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class.getClassLoader().getResourceAsStream("META-INF/ormlite_config.txt"));
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Document.class);
            TableUtils.createTableIfNotExists(connectionSource, Membership.class);
            TableUtils.createTableIfNotExists(connectionSource, Mine.class);
            TableUtils.createTableIfNotExists(connectionSource, Machinery.class);
            TableUtils.createTableIfNotExists(connectionSource, Minefront.class);
            TableUtils.createTableIfNotExists(connectionSource, Mineoperation.class);
            TableUtils.createTableIfNotExists(connectionSource, Mineralmaterial.class);
            TableUtils.createTableIfNotExists(connectionSource, Producesell.class);
            TableUtils.createTableIfNotExists(connectionSource, Report.class);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldversion, int newversion) {

    }

    public Dao<Membership, Long> getMemberShipDao() {
        if (membershipDao == null) {
            try {
                membershipDao = getDao(Membership.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return membershipDao;
    }

    public RuntimeExceptionDao<Membership, Long> getMemberShipRuntimeExceptionDao() {
        if (membershipRuntimeDao == null) {
            membershipRuntimeDao = getRuntimeExceptionDao(Membership.class);
        }
        return membershipRuntimeDao;
    }

    public Dao<Mine, Long> getMineDao() {
        if (mineDao == null) {
            try {
                mineDao = getDao(Mine.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return mineDao;
    }

    public RuntimeExceptionDao<Mine, Long> getMineRuntimeExceptionDao() {
        if (mineRuntimeExceptionDao == null) {
            mineRuntimeExceptionDao = getRuntimeExceptionDao(Mine.class);
        }
        return mineRuntimeExceptionDao;
    }

    public Dao<Machinery, Long> getMachineryDao() {
        if (machineryDao == null) {
            try {
                machineryDao = getDao(Machinery.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return machineryDao;
    }

    public RuntimeExceptionDao<Machinery, Long> getMachineryRuntimeExceptionDao() {
        if (machineryRuntimeExceptionDao == null) {
            machineryRuntimeExceptionDao = getRuntimeExceptionDao(Machinery.class);
        }
        return machineryRuntimeExceptionDao;
    }

    public Dao<Minefront, Long> getMinefrontDao() {
        if (minefrontDao == null) {
            try {
                minefrontDao = getDao(Minefront.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return minefrontDao;
    }

    public RuntimeExceptionDao<Minefront, Long> getMinefrontRuntimeExceptionDao() {
        if (minefrontRuntimeExceptionDao == null) {
            minefrontRuntimeExceptionDao = getRuntimeExceptionDao(Minefront.class);
        }
        return minefrontRuntimeExceptionDao;
    }

    public Dao<Mineoperation, Long> getMineoperationDao() {
        if (mineoperationDao == null) {
            try {
                mineoperationDao = getDao(Mineoperation.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return mineoperationDao;
    }

    public RuntimeExceptionDao<Mineoperation, Long> getMineoperationRuntimeExceptionDao() {
        if (mineoperationRuntimeExceptionDao == null) {
            mineoperationRuntimeExceptionDao = getRuntimeExceptionDao(Mineoperation.class);
        }
        return mineoperationRuntimeExceptionDao;
    }

    public Dao<Mineralmaterial, Long> getMineralmaterialDao() {
        if (mineralmaterialDao == null) {
            try {
                mineralmaterialDao = getDao(Mineralmaterial.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return mineralmaterialDao;
    }

    public RuntimeExceptionDao<Mineralmaterial, Long> getMineralmaterialRuntimeExceptionDao() {
        if (mineralmaterialRuntimeExceptionDao == null) {
            mineralmaterialRuntimeExceptionDao = getRuntimeExceptionDao(Mineralmaterial.class);
        }
        return mineralmaterialRuntimeExceptionDao;
    }

    public Dao<Producesell, Long> getProducesellDao() {
        if (producesellDao == null) {
            try {
                producesellDao = getDao(Producesell.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return producesellDao;
    }

    public RuntimeExceptionDao<Producesell, Long> getProducesellRuntimeExceptionDao() {
        if (producesellRuntimeExceptionDaop == null) {
            producesellRuntimeExceptionDaop = getRuntimeExceptionDao(Producesell.class);
        }
        return producesellRuntimeExceptionDaop;
    }

    public Dao<Report, Long> getReportDao() {
        if (reportDao == null) {
            try {
                reportDao = getDao(Report.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return reportDao;
    }

    public RuntimeExceptionDao<Report, Long> getReportRuntimeExceptionDao() {
        if (reportRuntimeExceptionDao == null) {
            reportRuntimeExceptionDao = getRuntimeExceptionDao(Report.class);
        }
        return reportRuntimeExceptionDao;
    }

    public Dao<Document, Long> getDocumentDao() {
        if (documentDao == null) {
            try {
                documentDao = getDao(Document.class);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return documentDao;
    }

    public RuntimeExceptionDao<Document, Long> getDocumentRuntimeExceptionDao() {
        if (documentRuntimeExceptionDao == null) {
            documentRuntimeExceptionDao = getRuntimeExceptionDao(Document.class);
        }
        return documentRuntimeExceptionDao;
    }

}
