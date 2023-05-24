package com.example.imeo_app.db.helper;


import com.example.imeo_app.db.tables.Document;
import com.example.imeo_app.db.tables.Machinery;
import com.example.imeo_app.db.tables.Membership;
import com.example.imeo_app.db.tables.Mine;
import com.example.imeo_app.db.tables.Minefront;
import com.example.imeo_app.db.tables.Mineoperation;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.example.imeo_app.db.tables.Producesell;
import com.example.imeo_app.db.tables.Report;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

public class DBHelperConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class<?>[]{Document.class,Membership.class, Mine.class, Machinery.class, Minefront.class, Mineoperation.class, Mineralmaterial.class, Producesell.class, Report.class};

    public static void main(String[] args) throws SQLException, IOException {

        writeConfigFile("ormlite_config.txt", classes);
    }
}
