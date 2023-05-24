package com.example.imeo_app.db.util;

import android.content.Context;


import androidx.work.Data;

import com.example.imeo_app.db.service.DocumentLocalServiceUtil;
import com.example.imeo_app.db.service.MachineryLocalServiceUtil;
import com.example.imeo_app.db.service.MemberShipLocalServiceUtil;
import com.example.imeo_app.db.service.MineLocalServiceUtil;
import com.example.imeo_app.db.service.MinefrontLocalServiceUtil;
import com.example.imeo_app.db.service.MineoperationLocalServiceUtil;
import com.example.imeo_app.db.service.MineralmaterialLocalServiceUtil;
import com.example.imeo_app.db.service.ProducesellLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Document;
import com.example.imeo_app.db.tables.Machinery;
import com.example.imeo_app.db.tables.Membership;
import com.example.imeo_app.db.tables.Mine;
import com.example.imeo_app.db.tables.Minefront;
import com.example.imeo_app.db.tables.Mineoperation;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.example.imeo_app.db.tables.Producesell;
import com.example.imeo_app.db.tables.Report;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;
import timber.log.Timber;

public class JsonInsertUtil {

    public static boolean insertMineFromJSON(JSONArray result, Context context) {

        try {
            MineLocalServiceUtil mineLocalServiceUtil = new MineLocalServiceUtil(context);
            for (int j = 0; j < result.length(); j++) {
                Mine mine = new Mine();
                long mineId = result.getJSONObject(j).getLong("mineId");
                mine.setMineid(mineId);
                mine.setMinecode(result.getJSONObject(j).has("mineCode") ? result.getJSONObject(j).get("mineCode").toString() : "");
                mine.setMinename(result.getJSONObject(j).has("mineName") ? result.getJSONObject(j).get("mineName").toString() : "");
                mine.setMinetype(result.getJSONObject(j).has("mineType") ? result.getJSONObject(j).get("mineType").toString() : "");
                mine.setMinestage(result.getJSONObject(j).has("mineStage") ? result.getJSONObject(j).getInt("mineStage") : 0);
                mine.setCadastreid(result.getJSONObject(j).has("cadastreId") ? result.getJSONObject(j).get("cadastreId").toString() : "");
                mine.setNationalcode(result.getJSONObject(j).has("nationalCode") ? result.getJSONObject(j).get("nationalCode").toString() : "");
                mine.setBeneficiaryname(result.getJSONObject(j).has("beneficiaryName") ? result.getJSONObject(j).get("beneficiaryName").toString() : "");
                mine.setBeneficiaryaddress(result.getJSONObject(j).has("beneficiaryAddress") ? result.getJSONObject(j).get("beneficiaryAddress").toString() : "");
                mine.setBeneficiaryphone(result.getJSONObject(j).has("beneficiaryPhone") ? result.getJSONObject(j).get("beneficiaryPhone").toString() : "");
                mine.setMineaddress(result.getJSONObject(j).has("mineAddress") ? result.getJSONObject(j).get("mineAddress").toString() : "");
                mine.setMinephone(result.getJSONObject(j).has("minePhone") ? result.getJSONObject(j).get("minePhone").toString() : "");
                mine.setSupervisormembershipcode(result.getJSONObject(j).has("supervisorMembershipCode") ? result.getJSONObject(j).get("supervisorMembershipCode").toString() : "");
                mine.setExplorelicensename(result.getJSONObject(j).has("exploreLicenseName") ? result.getJSONObject(j).get("exploreLicenseName").toString() : "");
                mine.setExplorelicensenumber(result.getJSONObject(j).has("exploreLicenseNumber") ? result.getJSONObject(j).get("exploreLicenseNumber").toString() : "");
                mine.setExplorelicensedate(result.getJSONObject(j).has("exploreLicenseDate") ? result.getJSONObject(j).get("exploreLicenseDate").toString() : "");
                mine.setExplorelicenseexpiredate(result.getJSONObject(j).has("exploreLicenseExpireDate") ? result.getJSONObject(j).get("exploreLicenseExpireDate").toString() : "");
                mine.setMineralmaterial(result.getJSONObject(j).has("mineralMaterial") ? result.getJSONObject(j).get("exploreLicenseName").toString() : "");
                mine.setPhysicalprogresspercent(result.getJSONObject(j).has("physicalProgressPercent") ? result.getJSONObject(j).getInt("physicalProgressPercent") : 0);
                mine.setClosestcity(result.getJSONObject(j).has("closestCity") ? result.getJSONObject(j).get("closestCity").toString() : "");
                mine.setClosestvillage(result.getJSONObject(j).has("closestVillage") ? result.getJSONObject(j).get("closestVillage").toString() : "");
                mine.setExtractioncapacity(result.getJSONObject(j).has("extractionCapacity") ? result.getJSONObject(j).getInt("extractionCapacity") : 0);
                mine.setExtractionmethod(result.getJSONObject(j).has("extractionMethod") ? result.getJSONObject(j).get("extractionMethod").toString() : "");
                mine.setLicensenumber(result.getJSONObject(j).has("licenseNumber") ? result.getJSONObject(j).get("licenseNumber").toString() : "");
                mine.setLicensetype(result.getJSONObject(j).has("licenseType") ? result.getJSONObject(j).get("licenseType").toString() : "");
                mine.setLicenseexpiredate(result.getJSONObject(j).has("licenseExpireDate") ? result.getJSONObject(j).get("licenseExpireDate").toString() : "");
                mine.setSazmanmembershipcode(result.getJSONObject(j).has("sazmanMembershipCode") ? result.getJSONObject(j).get("sazmanMembershipCode").toString() : "");
                mine.setSazmanmembershipname(result.getJSONObject(j).has("sazmanMembershipName") ? result.getJSONObject(j).get("sazmanMembershipName").toString() : "");
                mine.setWkt(result.getJSONObject(j).has("wkt") ? result.getJSONObject(j).get("wkt").toString() : "");
                mine.setWktcenter(result.getJSONObject(j).has("wktCenter") ? result.getJSONObject(j).get("wktCenter").toString() : "");
                mine.setWmsgetmapurl(result.getJSONObject(j).has("wmsGetMapUrl") ? result.getJSONObject(j).get("wmsGetMapUrl").toString() : "");

                // check
//                if(result.getJSONObject(j).has("wmsGetMapUrl"))
//                    mine.setWmsgetmapurl(result.getJSONObject(j).get("wmsGetMapUrl").toString());

                Mine existed = mineLocalServiceUtil.getMineById(mineId);
                if (existed != null) {
                    mine.setMineid(existed.getMineid());
                    mineLocalServiceUtil.updateMine(mine);
                } else
                    mine.setMineid(mineId);
                mineLocalServiceUtil.insertMine(mine);
            }
            return true;
        } catch (JSONException e) {
            System.out.println("asdff" + e.getMessage());
            Timber.e(e.getMessage(), e.getMessage());
        }

        return false;
    }

    public static boolean insertDocumentFromJSON(JSONArray result, Context context) {

        try {
            DocumentLocalServiceUtil documentLocalServiceUtil = new DocumentLocalServiceUtil(context);
            for (int j = 0; j < result.length(); j++) {
                Document document = new Document();
                long documentId = result.getJSONObject(j).getLong("documentId");
                document.setDocumentid(documentId);
                document.setMineid(result.getJSONObject(j).has("mineId") ? result.getJSONObject(j).getLong("mineId") :0);
                document.setReportid(result.getJSONObject(j).has("reportId") ? result.getJSONObject(j).getLong("reportId") : 0);
                document.setFilename(result.getJSONObject(j).has("fileName") ? result.getJSONObject(j).get("fileName").toString() : "");
                document.setBase64(result.getJSONObject(j).has("base64") ? result.getJSONObject(j).get("base64").toString() : "");

                Document existed = documentLocalServiceUtil.getDocumentById(documentId);
                if (existed != null) {
                    document.setMineid(existed.getMineid());
                    documentLocalServiceUtil.updateDocument(document);
                } else
                    documentLocalServiceUtil.insertDocument(document);
            }
            return true;
        } catch (JSONException e) {
            System.out.println("asdff" + e.getMessage());
            Timber.e(e.getMessage(), e.getMessage());
        }

        return false;
    }

    public static boolean insertMineralMaterialFromJSON(JSONArray result, Context context,String from) {

        try {
            MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(context);
                for (int j = 0; j < result.length(); j++) {
                    if(from.equals("report")){
                        mineralmaterialLocalServiceUtil.deleteMineralmaterial(result.getJSONObject(j).getLong("mineralmaterialId"));
                    }
                    Mineralmaterial mineralmaterial = new Mineralmaterial();
                    long mineralmaterialId = result.getJSONObject(j).getLong("mineralmaterialId");
                    mineralmaterial.setMineralmaterialid(mineralmaterialId);
                    mineralmaterial.setMineid(result.getJSONObject(j).has("mineId") ? result.getJSONObject(j).getLong("mineId") : 0);
                    mineralmaterial.setMineplace(result.getJSONObject(j).has("minePlace") ? result.getJSONObject(j).get("minePlace").toString() : "");
                    mineralmaterial.setColor(result.getJSONObject(j).has("color") ? result.getJSONObject(j).get("color").toString() : "");
                    mineralmaterial.setDensity(result.getJSONObject(j).has("density") ? result.getJSONObject(j).get("density").toString() : "");
                    mineralmaterial.setKanihayeasli(result.getJSONObject(j).has("kanihayeAsli") ? result.getJSONObject(j).get("kanihayeAsli").toString() : "");
                    mineralmaterial.setSangemizban(result.getJSONObject(j).has("sangeMizban") ? result.getJSONObject(j).get("sangeMizban").toString() : "");
                    mineralmaterial.setSheklekansar(result.getJSONObject(j).has("shekleKansar") ? result.getJSONObject(j).get("shekleKansar").toString() : "");
                    mineralmaterial.setType_(result.getJSONObject(j).has("type") ? result.getJSONObject(j).get("type").toString() : "");
                    mineralmaterial.setUsecase(result.getJSONObject(j).has("usecase") ? result.getJSONObject(j).get("usecase").toString() : "");
                    mineralmaterial.setVahedhayesangshenasieasli(result.getJSONObject(j).has("vahedhayeSangshenasieAsli") ? result.getJSONObject(j).get("vahedhayeSangshenasieAsli").toString() : "");
                    mineralmaterial.setZonesakhtari(result.getJSONObject(j).has("zoneSakhtari") ? result.getJSONObject(j).get("zoneSakhtari").toString() : "");
                    mineralmaterial.setGrading(result.getJSONObject(j).has("grading") ? result.getJSONObject(j).get("grading").toString() : "");
                    mineralmaterial.setDegree(result.getJSONObject(j).has("degree") ? result.getJSONObject(j).get("degree").toString() : "");

                    Mineralmaterial existed = mineralmaterialLocalServiceUtil.getMineralmaterialById(mineralmaterialId);
                    if (existed != null) {
                        mineralmaterial.setMineralmaterialid(existed.getMineralmaterialid());
                        mineralmaterialLocalServiceUtil.updateMineralmaterial(mineralmaterial);
                    } else
                        mineralmaterialLocalServiceUtil.insertMineralmaterial(mineralmaterial);
                }
                    return true;
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                Timber.e(e.getMessage(), e.getMessage());
            }
        return false;
    }

    public static boolean insertMemberShipFromJSON(JSONArray result, Context context) {

        MemberShipLocalServiceUtil memberShipLocalServiceUtil = new MemberShipLocalServiceUtil(context);
        for (int j = 0; j < result.length(); j++) {
            try {
                com.example.imeo_app.db.tables.Membership membership = new com.example.imeo_app.db.tables.Membership();
                long membershipId = result.getJSONObject(j).getLong("membershipId");
                membership.setMembershipid(membershipId);

                membership.setGroupid(result.getJSONObject(j).getLong("groupId"));
                membership.setUsername(result.getJSONObject(j).get("userName").toString());
                membership.setUserid(result.getJSONObject(j).getLong("userId"));

                membership.setMembershipcode(result.getJSONObject(j).get("membershipCode").toString());
                membership.setFirstname(result.getJSONObject(j).get("firstName").toString());
                membership.setLastname(result.getJSONObject(j).get("lastName").toString());
                membership.setPhonenumber(result.getJSONObject(j).get("phoneNumber").toString());
                membership.setFieldstudy(result.getJSONObject(j).get("fieldStudy").toString());
                membership.setSpeciality(result.getJSONObject(j).get("speciality").toString());
                membership.setFirstfield(result.getJSONObject(j).get("firstField").toString());
                membership.setFirstbase(result.getJSONObject(j).get("firstBase").toString());
                membership.setSecondfield(result.getJSONObject(j).get("secondField").toString());
                membership.setSecondbase(result.getJSONObject(j).get("secondBase").toString());


                Membership existed = memberShipLocalServiceUtil.getMemberShipById(membershipId);
                if (existed != null) {
                    membership.setMembershipid(existed.getMembershipid());
                    memberShipLocalServiceUtil.updateMemberShip(membership);
                } else
                    memberShipLocalServiceUtil.insertMemberShip(membership);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                Timber.e(e.getMessage(), e.getMessage());
            }
        }

        return true;
    }

    public static boolean insertMachineryFromJSON(JSONArray result, Context context) {

        MachineryLocalServiceUtil machineryLocalServiceUtil = new MachineryLocalServiceUtil(context);
        for (int j = 0; j < result.length(); j++) {
            try {
                com.example.imeo_app.db.tables.Machinery machinery = new com.example.imeo_app.db.tables.Machinery();
                long machineryId = result.getJSONObject(j).getLong("machineryId");
                machinery.setMachineryid(machineryId);
                machinery.setReportid(result.getJSONObject(j).has("reportId") ? result.getJSONObject(j).getLong("reportId") : 0);
                machinery.setMineid(result.getJSONObject(j).has("mineId") ? result.getJSONObject(j).getLong("mineId") : 0);
                machinery.setEquipment(result.getJSONObject(j).has("equipmentCode") ? result.getJSONObject(j).getInt("equipmentCode") : 0);
                machinery.setMasrafesookht(result.getJSONObject(j).has("masrafeSookht") ? result.getJSONObject(j).getInt("masrafeSookht") : 0);
                machinery.setNamemashin(result.getJSONObject(j).has("nameMashinCode") ? result.getJSONObject(j).getInt("nameMashinCode") : 0);
                machinery.setModelemashin(result.getJSONObject(j).has("modeleMashin") ? result.getJSONObject(j).get("modeleMashin").toString() : "");
                machinery.setRuzekari(result.getJSONObject(j).has("ruzeKari") ? result.getJSONObject(j).getInt("ruzeKari") : 0);

                Machinery existed = machineryLocalServiceUtil.getMachineryById(machineryId);
                if (existed != null) {
                    machinery.setMachineryid(existed.getMachineryid());
                    machineryLocalServiceUtil.updateMachinery(machinery);
                } else
                    machineryLocalServiceUtil.insertMachinery(machinery);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                Timber.e(e.getMessage(), e.getMessage());
            }
        }

        return true;
    }

    public static boolean insertMineOperationFromJSON(JSONArray result, Context context) {

        MineoperationLocalServiceUtil mineoperationLocalServiceUtil = new MineoperationLocalServiceUtil(context);
        for (int j = 0; j < result.length(); j++) {
            try {
                com.example.imeo_app.db.tables.Mineoperation mineOperation = new com.example.imeo_app.db.tables.Mineoperation();
                long mineOperationId = result.getJSONObject(j).getLong("mineOperationId");
                mineOperation.setMineoperationid(mineOperationId);
                mineOperation.setReportid(result.getJSONObject(j).has("reportId") ? result.getJSONObject(j).getLong("reportId") : 0);
                mineOperation.setMineid(result.getJSONObject(j).has("mineId") ? result.getJSONObject(j).getLong("mineId") : 0);
                mineOperation.setOperationtype(result.getJSONObject(j).has("operationType") ? result.getJSONObject(j).getInt("operationType") : 0);
                mineOperation.setDimension(result.getJSONObject(j).has("dimension") ? result.getJSONObject(j).getInt("dimension") : 0);
                mineOperation.setVolume(result.getJSONObject(j).has("volume") ? result.getJSONObject(j).getInt("volume") : 0);
                mineOperation.setMeterage(result.getJSONObject(j).has("meterage") ? result.getJSONObject(j).getInt("meterage") : 0);
                mineOperation.setCost(result.getJSONObject(j).has("cost") ? result.getJSONObject(j).getInt("cost") : 0);

                Mineoperation existed = mineoperationLocalServiceUtil.getMineoperationById(mineOperationId);
                if (existed != null) {
                    mineOperation.setMineoperationid(existed.getMineoperationid());
                    mineoperationLocalServiceUtil.updateMineoperation(mineOperation);
                } else
                    mineoperationLocalServiceUtil.insertMineoperation(mineOperation);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                Timber.e(e.getMessage(), e.getMessage());
            }
        }

        return true;
    }

    public static boolean insertReportsFromJSON(JSONArray result, Context context) {

        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(context);

        for (int j = 0; j < result.length(); j++) {
            try {
                com.example.imeo_app.db.tables.Report report = new com.example.imeo_app.db.tables.Report();
                long reportId = result.getJSONObject(j).getLong("reportId");
                Report existed = reportLocalServiceUtil.getReportById(reportId);
                PersianDate pdate = new PersianDate();
                PersianDateFormat pdformater = new PersianDateFormat("Y/m");
                if(existed==null){
                report.setReportid(reportId);
                
                report.setStatus(result.getJSONObject(j).has("status") ? result.getJSONObject(j).getInt("status") : 0);


                String reportDate = result.getJSONObject(j).get("reportDate").toString();
                if(reportDate.substring(reportDate.indexOf("/") + "/".length()).length()==1){
                    reportDate = reportDate.substring(0,5) + "0" + reportDate.substring(5,reportDate.length());
                }


                report.setReportdate(result.getJSONObject(j).has("reportDate") ? reportDate : "");
                report.setStatusdate(result.getJSONObject(j).has("statusDate") ? new Date(result.getJSONObject(j).getLong("statusDate")) : null);
                report.setCreatedate(result.getJSONObject(j).has("createDate") ? new Date(result.getJSONObject(j).getLong("createDate")) : null);
                report.setPersiancreatedate(result.getJSONObject(j).has("persianCreateDate") ? result.getJSONObject(j).getString("persianCreateDate") : "");
                report.setModifieddate(result.getJSONObject(j).has("modifiedDate") ? new Date(result.getJSONObject(j).getLong("modifiedDate")) : null);
                report.setMineid(result.getJSONObject(j).has("mineId") ? result.getJSONObject(j).getLong("mineId") : 0);
                report.setMinename(result.getJSONObject(j).has("mineName") ? result.getJSONObject(j).get("mineName").toString() : "");
                report.setMineactive(result.getJSONObject(j).has("mineActive") ? result.getJSONObject(j).getInt("mineActive") : 0);
                
                    report.setStatususerid(result.getJSONObject(j).has("statusUserId") ? result.getJSONObject(j).getInt("statusUserId") : 0);
                    report.setExplorelicensename(result.getJSONObject(j).has("exploreLicenseName") ? result.getJSONObject(j).get("exploreLicenseName").toString() : "");
                    report.setGeometrysinekarjebhe(result.getJSONObject(j).has("geometrySinekarJebhe") ? result.getJSONObject(j).get("geometrySinekarJebhe").toString() : "");
                    report.setGeometrydepomadani(result.getJSONObject(j).has("geometryDepoMadani") ? result.getJSONObject(j).get("geometryDepoMadani").toString() : "");
                    report.setGeometrydepowaste(result.getJSONObject(j).has("geometryDepoWaste") ? result.getJSONObject(j).get("geometryDepoWaste").toString() : "");
                    report.setGeometrydahanetunnel(result.getJSONObject(j).has("geometryDahaneTunnel") ? result.getJSONObject(j).get("geometryDahaneTunnel").toString() : "");
                    report.setGeometrysinekar(result.getJSONObject(j).has("geometrySinekar") ? result.getJSONObject(j).get("geometrySinekar").toString() : "");
                    report.setGeometrytrench(result.getJSONObject(j).has("geometryTrench") ? result.getJSONObject(j).get("geometryTrench").toString() : "");
                    report.setGeometrywell(result.getJSONObject(j).has("geometryWell") ? result.getJSONObject(j).get("geometryWell").toString() : "");
                    report.setWorkdayinmonth(result.getJSONObject(j).has("workDayInMonth") ? result.getJSONObject(j).getInt("workDayInMonth") : 0);
                    report.setShiftinday(result.getJSONObject(j).has("shiftInDay") ? result.getJSONObject(j).getInt("shiftInDay") : 0);
                    report.setWorkhourinshift(result.getJSONObject(j).has("workHourInShift") ? result.getJSONObject(j).getInt("workHourInShift") : 0);
                    report.setWorkshiftinmonth(result.getJSONObject(j).has("workShiftInMonth") ? result.getJSONObject(j).getInt("workShiftInMonth") : 0);
                    report.setWorkhourinmonth(result.getJSONObject(j).has("workHourInMonth") ? result.getJSONObject(j).getInt("workHourInMonth") : 0);
                    report.setExtractionworkshopqty(result.getJSONObject(j).has("extractionWorkShopQty") ? result.getJSONObject(j).getInt("extractionWorkShopQty") : 0);
                    report.setAdvancingtunnelqty(result.getJSONObject(j).has("advancingTunnelQty") ? result.getJSONObject(j).getInt("advancingTunnelQty") : 0);
                    report.setTotallengthtunnels(result.getJSONObject(j).has("totalLengthTunnels") ? result.getJSONObject(j).getInt("totalLengthTunnels") : 0);
                    report.setTotalvolumetunnels(result.getJSONObject(j).has("totalVolumeTunnels") ? result.getJSONObject(j).getInt("totalVolumeTunnels") : 0);
                    report.setAdvancingdoilesqty(result.getJSONObject(j).has("advancingDoilesQty") ? result.getJSONObject(j).getInt("advancingDoilesQty") : 0);
                    report.setTotallengthdoiles(result.getJSONObject(j).has("totalLengthDoiles") ? result.getJSONObject(j).getInt("totalLengthDoiles") : 0);
                    report.setTotaldrillingdoiles(result.getJSONObject(j).has("totalDrillingDoiles") ? result.getJSONObject(j).getInt("totalDrillingDoiles") : 0);
                    report.setExtractedvolume(result.getJSONObject(j).has("extractedVolume") ? result.getJSONObject(j).getInt("extractedVolume") : 0);
                    report.setDonegozareshehaffari(result.getJSONObject(j).has("DoneGozaresheHaffari") ? result.getJSONObject(j).getInt("DoneGozaresheHaffari") : 0);
                    report.setMetrazhekollehaffari(result.getJSONObject(j).has("metrazheKolleHaffari") ? result.getJSONObject(j).getInt("metrazheKolleHaffari") : 0);
                    report.setQotrechalha(result.getJSONObject(j).has("qotreChalha") ? result.getJSONObject(j).getInt("qotreChalha") : 0);
                    report.setTedadechalha(result.getJSONObject(j).has("tedadeChalha") ? result.getJSONObject(j).getInt("tedadeChalha") : 0);
                    report.setFaseleyechalha(result.getJSONObject(j).has("faseleyeChalha") ? result.getJSONObject(j).getInt("faseleyeChalha") : 0);
                    report.setTedadechalhadarradif(result.getJSONObject(j).has("tedadeChalhaDarRadif") ? result.getJSONObject(j).getInt("tedadeChalhaDarRadif") : 0);
                    report.setTedaderadif(result.getJSONObject(j).has("tedadeRadif") ? result.getJSONObject(j).getInt("tedadeRadif") : 0);
                    report.setFaseleyeradifi(result.getJSONObject(j).has("faseleyeRadifi") ? result.getJSONObject(j).getInt("faseleyeRadifi") : 0);
                    report.setFaseletajebheyekareazadechalha(result.getJSONObject(j).has("faseleTaJebheyeKareAzadeChalha") ? result.getJSONObject(j).getInt("faseleTaJebheyeKareAzadeChalha") : 0);
                    report.setOmghemotavassetechalha(result.getJSONObject(j).has("omgheMotavasseteChalha") ? result.getJSONObject(j).getDouble("omgheMotavasseteChalha") : 0);
                    report.setMeghdaresangeghabeleestekhraj(result.getJSONObject(j).has("meghdareSangeGhabeleEstekhraj") ? result.getJSONObject(j).getDouble("meghdareSangeGhabeleEstekhraj") : 0);
                    report.setHaffarievizhe(result.getJSONObject(j).has("haffarieVizhe") ? result.getJSONObject(j).getDouble("haffarieVizhe") : 0);
                    report.setDonegozaresheenfejari(result.getJSONObject(j).has("DoneGozaresheEnfejari") ? result.getJSONObject(j).getInt("DoneGozaresheEnfejari") : 0);
                    report.setAnfo(result.getJSONObject(j).has("anfo") ? result.getJSONObject(j).getInt("anfo") : 0);
                    report.setDinamit(result.getJSONObject(j).has("dinamit") ? result.getJSONObject(j).getInt("dinamit") : 0);
                    report.setEmolayt(result.getJSONObject(j).has("emolayt") ? result.getJSONObject(j).getInt("emolayt") : 0);
                    report.setBaroot(result.getJSONObject(j).has("baroot") ? result.getJSONObject(j).getInt("baroot") : 0);
                    report.setChashnieelektriki(result.getJSONObject(j).has("chashnieElektriki") ? result.getJSONObject(j).getInt("chashnieElektriki") : 0);
                    report.setChashniemamuli(result.getJSONObject(j).has("chashnieMamuli") ? result.getJSONObject(j).getInt("chashnieMamuli") : 0);
                    report.setKortex(result.getJSONObject(j).has("kortex") ? result.getJSONObject(j).getInt("kortex") : 0);
                    report.setNatel(result.getJSONObject(j).has("natel") ? result.getJSONObject(j).getInt("natel") : 0);
                    report.setPc(result.getJSONObject(j).has("pc") ? result.getJSONObject(j).getInt("pc") : 0);
                    report.setTakhiri(result.getJSONObject(j).has("takhiri") ? result.getJSONObject(j).getInt("takhiri") : 0);
                    report.setBooster(result.getJSONObject(j).has("booster") ? result.getJSONObject(j).getInt("booster") : 0);
                    report.setFitile(result.getJSONObject(j).has("fitile") ? result.getJSONObject(j).getInt("fitile") : 0);
                    report.setMohandesiamani(result.getJSONObject(j).has("mohandesiAmani") ? result.getJSONObject(j).getInt("mohandesiAmani") : 0);
                    report.setMohandesipeymani(result.getJSONObject(j).has("mohandesiPeymani") ? result.getJSONObject(j).getInt("mohandesiPeymani") : 0);
                    report.setMohandesisum(result.getJSONObject(j).has("mohandesisum") ? result.getJSONObject(j).getInt("mohandesisum") : 0);
                    report.setKargaranamani(result.getJSONObject(j).has("kargaranAmani") ? result.getJSONObject(j).getInt("kargaranAmani") : 0);
                    report.setKargaranpeymani(result.getJSONObject(j).has("kargaranPeymani") ? result.getJSONObject(j).getInt("kargaranPeymani") : 0);
                    report.setKargaransum(result.getJSONObject(j).has("kargaranSum") ? result.getJSONObject(j).getInt("kargaranSum") : 0);
                    report.setEdariamani(result.getJSONObject(j).has("edariAmani") ? result.getJSONObject(j).getInt("edariAmani") : 0);
                    report.setEdaripeymani(result.getJSONObject(j).has("edariPeymani") ? result.getJSONObject(j).getInt("edariPeymani") : 0);
                    report.setEdarisum(result.getJSONObject(j).has("edariSum") ? result.getJSONObject(j).getInt("edariSum") : 0);
                    report.setAmanisum(result.getJSONObject(j).has("amaniSum") ? result.getJSONObject(j).getInt("amaniSum") : 0);
                    report.setPeymanisum(result.getJSONObject(j).has("peymaniSum") ? result.getJSONObject(j).getInt("peymaniSum") : 0);
                    report.setTotalsum(result.getJSONObject(j).has("totalSum") ? result.getJSONObject(j).getInt("totalSum") : 0);
                    report.setUninsuredworker(result.getJSONObject(j).has("unInsuredWorker") ? result.getJSONObject(j).getInt("unInsuredWorker") : 0);
                    report.setDoreamoozeshibimeworker(result.getJSONObject(j).has("doreAmoozeshiBimeWorker") ? result.getJSONObject(j).getInt("doreAmoozeshiBimeWorker") : 0);
                    report.setAverageefficiencytonenafar(result.getJSONObject(j).has("averageEfficiencyToneNafar") ? result.getJSONObject(j).getInt("averageEfficiencyToneNafar") : 0);
                    report.setAverageefficiencykarkonanetolidi(result.getJSONObject(j).has("averageEfficiencyKarkonaneTolidi") ? result.getJSONObject(j).getInt("averageEfficiencyKarkonaneTolidi") : 0);
                    report.setAverageefficiencykarkonansum(result.getJSONObject(j).has("averageEfficiencyKarkonanSum") ? result.getJSONObject(j).getInt("averageEfficiencyKarkonanSum") : 0);
                    report.setBargh(result.getJSONObject(j).has("bargh") ? result.getJSONObject(j).getInt("bargh") : 0);
                    report.setGazetabiyi(result.getJSONObject(j).has("gazeTabiyi") ? result.getJSONObject(j).getInt("gazeTabiyi") : 0);
                    report.setAbesanati(result.getJSONObject(j).has("abeSanati") ? result.getJSONObject(j).getInt("abeSanati") : 0);
                    report.setBenzin(result.getJSONObject(j).has("benzin") ? result.getJSONObject(j).getInt("benzin") : 0);
                    report.setGazoyil(result.getJSONObject(j).has("gazoyil") ? result.getJSONObject(j).getInt("gazoyil") : 0);
                    report.setAbeshorb(result.getJSONObject(j).has("abeShorb") ? result.getJSONObject(j).getInt("abeShorb") : 0);
                    report.setSayer(result.getJSONObject(j).has("sayer") ? result.getJSONObject(j).get("sayer").toString() : "");
                    report.setUsebargh(result.getJSONObject(j).has("useBargh") ? result.getJSONObject(j).getInt("useBargh") : 0);
                    report.setTedadegenerator(result.getJSONObject(j).has("tedadeGenerator") ? result.getJSONObject(j).getInt("tedadeGenerator") : 0);
                    report.setTavanegenerator(result.getJSONObject(j).has("tavaneGenerator") ? result.getJSONObject(j).getInt("tavaneGenerator") : 0);
                    report.setMasrafegenerator(result.getJSONObject(j).has("masrafeGenerator") ? result.getJSONObject(j).getInt("masrafeGenerator") : 0);
                    report.setVaziaterefahiepersonel(result.getJSONObject(j).has("vaziateRefahiePersonel") ? result.getJSONObject(j).getInt("vaziateRefahiePersonel") : 0);
                    report.setIstajhizateimeniefardi(result.getJSONObject(j).has("isTajhizateImenieFardi") ? result.getJSONObject(j).getInt("isTajhizateImenieFardi") : 0);
                    report.setUsetajhizateimeni(result.getJSONObject(j).has("useTajhizateImeni") ? result.getJSONObject(j).getInt("useTajhizateImeni") : 0);
                    report.setDrivergovahimotabar(result.getJSONObject(j).has("driverGovahiMotabar") ? result.getJSONObject(j).getInt("driverGovahiMotabar") : 0);
                    report.setMachineryimeni(result.getJSONObject(j).has("machineryimeni") ? result.getJSONObject(j).getInt("machineryimeni") : 0);
                    report.setReayateshibemojaz(result.getJSONObject(j).has("reayateShibeMojaz") ? result.getJSONObject(j).getInt("reayateShibeMojaz") : 0);
                    report.setShibejaddeyeasli(result.getJSONObject(j).has("shibeJaddeyeAsli") ? result.getJSONObject(j).getInt("shibeJaddeyeAsli") : 0);
                    report.setShiberamphayedastrasi(result.getJSONObject(j).has("shibeRamphayeDastrasi") ? result.getJSONObject(j).getInt("shibeRamphayeDastrasi") : 0);
                    report.setReayateayinnamehayeimeni(result.getJSONObject(j).has("reayateAyinnamehayeImeni") ? result.getJSONObject(j).getInt("reayateAyinnamehayeImeni") : 0);
                    report.setIsaccidenthappen(result.getJSONObject(j).has("isAccidentHappen") ? result.getJSONObject(j).getInt("isAccidentHappen") : 0);
                    report.setLaghgiri(result.getJSONObject(j).has("laghGiri") ? result.getJSONObject(j).getInt("laghGiri") : 0);
                    report.setNeedgheyrefaalimeni(result.getJSONObject(j).has("needGheyreFaalImeni") ? result.getJSONObject(j).getInt("needGheyreFaalImeni") : 0);
                    report.setOtherdesc(result.getJSONObject(j).has("otherDesc") ? result.getJSONObject(j).get("otherDesc").toString() : "");
                    report.setMoshkelatvamavane(result.getJSONObject(j).has("moshkelatVaMavane") ? result.getJSONObject(j).get("moshkelatVaMavane").toString() : "");
                    report.setPishnahadatvaezharenazar(result.getJSONObject(j).has("pishnahadatVaEzhareNazar") ? result.getJSONObject(j).get("pishnahadatVaEzhareNazar").toString() : "");
                    report.setOpinion(result.getJSONObject(j).has("opinion") ? result.getJSONObject(j).get("opinion").toString() : "");
                    report.setSetenergy(result.getJSONObject(j).has("setEnergy") ? result.getJSONObject(j).getBoolean("setEnergy") : false);
                    report.setSetimeni(result.getJSONObject(j).has("setImeni") ? result.getJSONObject(j).getBoolean("setImeni") : false);
                    report.setSetmachinery(result.getJSONObject(j).has("setMachinery") ? result.getJSONObject(j).getBoolean("setMachinery") : false);
                    report.setSetoperation1(result.getJSONObject(j).has("setOperation1") ? result.getJSONObject(j).getBoolean("setOperation1") : false);
                    report.setSetoperation2(result.getJSONObject(j).has("setOperation2") ? result.getJSONObject(j).getBoolean("setOperation2") : false);
                    report.setSetpeople(result.getJSONObject(j).has("setPeople") ? result.getJSONObject(j).getBoolean("setPeople") : false);
                    report.setSetproblems(result.getJSONObject(j).has("setProblems") ? result.getJSONObject(j).getBoolean("setProblems") : false);
                    report.setSetproducesell(result.getJSONObject(j).has("setProduceSell") ? result.getJSONObject(j).getBoolean("setProduceSell") : false);
                    report.setSetgeom1(result.getJSONObject(j).has("setGeom1") ? result.getJSONObject(j).getBoolean("setGeom1") : false);
                    report.setSetgeom2(result.getJSONObject(j).has("setGeom2") ? result.getJSONObject(j).getBoolean("setGeom2") : false);
                    report.setSetgeom3(result.getJSONObject(j).has("setGeom3") ? result.getJSONObject(j).getBoolean("setGeom3") : false);
                    report.setSetsuggest(result.getJSONObject(j).has("setSuggest") ? result.getJSONObject(j).getBoolean("setSuggest") : false);

                    reportLocalServiceUtil.insertReport(report);
                }else if (existed != null) {
                    report.setReportid(existed.getReportid());
                    report.setStatus(result.getJSONObject(j).has("status") ? result.getJSONObject(j).getInt("status") : existed.getStatus());
                    

                    if(result.getJSONObject(j).has("reportDate")) {
                        String reportDate = result.getJSONObject(j).get("reportDate").toString();
                        if (reportDate.substring(reportDate.indexOf("/") + "/".length()).length() == 1) {
                            reportDate = reportDate.substring(0, 5) + "0" + reportDate.substring(5, reportDate.length());
                        }
                        report.setReportdate(reportDate);
                    }else {
                        report.setReportdate(existed.getReportdate());
                    }

                    report.setStatusdate(result.getJSONObject(j).has("statusDate") ? new Date(result.getJSONObject(j).getLong("statusDate")) : existed.getStatusdate());
                    report.setMineid(result.getJSONObject(j).has("mineId") ? result.getJSONObject(j).getInt("mineId") : existed.getMineid());
                    report.setMinename(result.getJSONObject(j).has("mineName") ? result.getJSONObject(j).get("mineName").toString() : existed.getMinename());
                    report.setMineactive(result.getJSONObject(j).has("mineActive") ? result.getJSONObject(j).getInt("mineActive") : existed.getMineactive());
                    report.setCreatedate(result.getJSONObject(j).has("createDate") ? new Date(result.getJSONObject(j).getLong("createDate")) : existed.getCreatedate());
                    report.setPersiancreatedate(result.getJSONObject(j).has("persianCreateDate") ? result.getJSONObject(j).getString("persianCreateDate") : existed.getPersiancreatedate());
                    report.setModifieddate(result.getJSONObject(j).has("modifiedDate") ? new Date(result.getJSONObject(j).getLong("modifiedDate")) : existed.getModifieddate());
                    report.setStatususerid(result.getJSONObject(j).has("statusUserId") ? result.getJSONObject(j).getInt("statusUserId") : existed.getStatususerid());
                    report.setExplorelicensename(result.getJSONObject(j).has("exploreLicenseName") ? result.getJSONObject(j).get("exploreLicenseName").toString() : existed.getExplorelicensename());
                    report.setGeometrysinekarjebhe(result.getJSONObject(j).has("geometrySinekarJebhe") ? result.getJSONObject(j).get("geometrySinekarJebhe").toString() : existed.getGeometrysinekarjebhe());
                    report.setGeometrydepomadani(result.getJSONObject(j).has("geometryDepoMadani") ? result.getJSONObject(j).get("geometryDepoMadani").toString() : existed.getGeometrydepomadani());
                    report.setGeometrydepowaste(result.getJSONObject(j).has("geometryDepoWaste") ? result.getJSONObject(j).get("geometryDepoWaste").toString() : existed.getGeometrydepowaste());
                    report.setGeometrydahanetunnel(result.getJSONObject(j).has("geometryDahaneTunnel") ? result.getJSONObject(j).get("geometryDahaneTunnel").toString() : existed.getGeometrydahanetunnel());
                    report.setGeometrysinekar(result.getJSONObject(j).has("geometrySinekar") ? result.getJSONObject(j).get("geometrySinekar").toString() : existed.getGeometrysinekar());
                    report.setGeometrytrench(result.getJSONObject(j).has("geometryTrench") ? result.getJSONObject(j).get("geometryTrench").toString() : existed.getGeometrytrench());
                    report.setGeometrywell(result.getJSONObject(j).has("geometryWell") ? result.getJSONObject(j).get("geometryWell").toString() : existed.getGeometrywell());
                    report.setWorkdayinmonth(result.getJSONObject(j).has("workDayInMonth") ? result.getJSONObject(j).getInt("workDayInMonth") : existed.getWorkdayinmonth());
                    report.setShiftinday(result.getJSONObject(j).has("shiftInDay") ? result.getJSONObject(j).getInt("shiftInDay") : existed.getShiftinday());
                    report.setWorkhourinshift(result.getJSONObject(j).has("workHourInShift") ? result.getJSONObject(j).getInt("workHourInShift") : existed.getWorkhourinshift());
                    report.setWorkshiftinmonth(result.getJSONObject(j).has("workShiftInMonth") ? result.getJSONObject(j).getInt("workShiftInMonth") : existed.getWorkshiftinmonth());
                    report.setWorkhourinmonth(result.getJSONObject(j).has("workHourInMonth") ? result.getJSONObject(j).getInt("workHourInMonth") : existed.getWorkhourinmonth());
                    report.setExtractionworkshopqty(result.getJSONObject(j).has("extractionWorkShopQty") ? result.getJSONObject(j).getInt("extractionWorkShopQty") : existed.getExtractionworkshopqty());
                    report.setAdvancingtunnelqty(result.getJSONObject(j).has("advancingTunnelQty") ? result.getJSONObject(j).getInt("advancingTunnelQty") : existed.getAdvancingtunnelqty());
                    report.setTotallengthtunnels(result.getJSONObject(j).has("totalLengthTunnels") ? result.getJSONObject(j).getInt("totalLengthTunnels") : existed.getTotallengthtunnels());
                    report.setTotalvolumetunnels(result.getJSONObject(j).has("totalVolumeTunnels") ? result.getJSONObject(j).getInt("totalVolumeTunnels") : existed.getTotalvolumetunnels());
                    report.setAdvancingdoilesqty(result.getJSONObject(j).has("advancingDoilesQty") ? result.getJSONObject(j).getInt("advancingDoilesQty") : existed.getAdvancingdoilesqty());
                    report.setTotallengthdoiles(result.getJSONObject(j).has("totalLengthDoiles") ? result.getJSONObject(j).getInt("totalLengthDoiles") : existed.getTotallengthdoiles());
                    report.setTotaldrillingdoiles(result.getJSONObject(j).has("totalDrillingDoiles") ? result.getJSONObject(j).getInt("totalDrillingDoiles") : existed.getTotaldrillingdoiles());
                    report.setExtractedvolume(result.getJSONObject(j).has("extractedVolume") ? result.getJSONObject(j).getInt("extractedVolume") : existed.getExtractedvolume());
                    report.setDonegozareshehaffari(result.getJSONObject(j).has("DoneGozaresheHaffari") ? result.getJSONObject(j).getInt("DoneGozaresheHaffari") : existed.getDonegozareshehaffari());
                    report.setMetrazhekollehaffari(result.getJSONObject(j).has("metrazheKolleHaffari") ? result.getJSONObject(j).getInt("metrazheKolleHaffari") : existed.getMetrazhekollehaffari());
                    report.setQotrechalha(result.getJSONObject(j).has("qotreChalha") ? result.getJSONObject(j).getInt("qotreChalha") : existed.getQotrechalha());
                    report.setTedadechalha(result.getJSONObject(j).has("tedadeChalha") ? result.getJSONObject(j).getInt("tedadeChalha") : existed.getTedadechalha());
                    report.setFaseleyechalha(result.getJSONObject(j).has("faseleyeChalha") ? result.getJSONObject(j).getInt("faseleyeChalha") : existed.getFaseleyechalha());
                    report.setTedadechalhadarradif(result.getJSONObject(j).has("tedadeChalhaDarRadif") ? result.getJSONObject(j).getInt("tedadeChalhaDarRadif") : existed.getTedadechalhadarradif());
                    report.setTedaderadif(result.getJSONObject(j).has("tedadeRadif") ? result.getJSONObject(j).getInt("tedadeRadif") : existed.getTedaderadif());
                    report.setFaseleyeradifi(result.getJSONObject(j).has("faseleyeRadifi") ? result.getJSONObject(j).getInt("faseleyeRadifi") : existed.getFaseleyeradifi());
                    report.setFaseletajebheyekareazadechalha(result.getJSONObject(j).has("faseleTaJebheyeKareAzadeChalha") ? result.getJSONObject(j).getInt("faseleTaJebheyeKareAzadeChalha") : existed.getFaseletajebheyekareazadechalha());
                    report.setOmghemotavassetechalha(result.getJSONObject(j).has("omgheMotavasseteChalha") ? result.getJSONObject(j).getInt("omgheMotavasseteChalha") : existed.getOmghemotavassetechalha());
                    report.setMeghdaresangeghabeleestekhraj(result.getJSONObject(j).has("meghdareSangeGhabeleEstekhraj") ? result.getJSONObject(j).getInt("meghdareSangeGhabeleEstekhraj") : existed.getMeghdaresangeghabeleestekhraj());
                    report.setHaffarievizhe(result.getJSONObject(j).has("haffarieVizhe") ? result.getJSONObject(j).getInt("haffarieVizhe") : existed.getHaffarievizhe());
                    report.setDonegozaresheenfejari(result.getJSONObject(j).has("DoneGozaresheEnfejari") ? result.getJSONObject(j).getInt("DoneGozaresheEnfejari") : existed.getDonegozaresheenfejari());
                    report.setAnfo(result.getJSONObject(j).has("anfo") ? result.getJSONObject(j).getInt("anfo") : existed.getAnfo());
                    report.setDinamit(result.getJSONObject(j).has("dinamit") ? result.getJSONObject(j).getInt("dinamit") : existed.getDinamit());
                    report.setEmolayt(result.getJSONObject(j).has("emolayt") ? result.getJSONObject(j).getInt("emolayt") : existed.getEmolayt());
                    report.setBaroot(result.getJSONObject(j).has("baroot") ? result.getJSONObject(j).getInt("baroot") : existed.getBaroot());
                    report.setChashnieelektriki(result.getJSONObject(j).has("chashnieElektriki") ? result.getJSONObject(j).getInt("chashnieElektriki") : existed.getChashnieelektriki());
                    report.setChashniemamuli(result.getJSONObject(j).has("chashnieMamuli") ? result.getJSONObject(j).getInt("chashnieMamuli") : existed.getChashniemamuli());
                    report.setKortex(result.getJSONObject(j).has("kortex") ? result.getJSONObject(j).getInt("kortex") : existed.getKortex());
                    report.setNatel(result.getJSONObject(j).has("natel") ? result.getJSONObject(j).getInt("natel") : existed.getNatel());
                    report.setPc(result.getJSONObject(j).has("pc") ? result.getJSONObject(j).getInt("pc") : existed.getPc());
                    report.setTakhiri(result.getJSONObject(j).has("takhiri") ? result.getJSONObject(j).getInt("takhiri") : existed.getTakhiri());
                    report.setBooster(result.getJSONObject(j).has("booster") ? result.getJSONObject(j).getInt("booster") : existed.getBooster());
                    report.setFitile(result.getJSONObject(j).has("fitile") ? result.getJSONObject(j).getInt("fitile") : existed.getFitile());
                    report.setMohandesiamani(result.getJSONObject(j).has("mohandesiAmani") ? result.getJSONObject(j).getInt("mohandesiAmani") : existed.getMohandesiamani());
                    report.setMohandesipeymani(result.getJSONObject(j).has("mohandesiPeymani") ? result.getJSONObject(j).getInt("mohandesiPeymani") : existed.getMohandesipeymani());
                    report.setMohandesisum(result.getJSONObject(j).has("mohandesisum") ? result.getJSONObject(j).getInt("mohandesisum") : existed.getMohandesisum());
                    report.setKargaranamani(result.getJSONObject(j).has("kargaranAmani") ? result.getJSONObject(j).getInt("kargaranAmani") : existed.getKargaranamani());
                    report.setKargaranpeymani(result.getJSONObject(j).has("kargaranPeymani") ? result.getJSONObject(j).getInt("kargaranPeymani") : existed.getKargaranpeymani());
                    report.setKargaransum(result.getJSONObject(j).has("kargaranSum") ? result.getJSONObject(j).getInt("kargaranSum") : existed.getKargaransum());
                    report.setEdariamani(result.getJSONObject(j).has("edariAmani") ? result.getJSONObject(j).getInt("edariAmani") : existed.getEdariamani());
                    report.setEdaripeymani(result.getJSONObject(j).has("edariPeymani") ? result.getJSONObject(j).getInt("edariPeymani") : existed.getEdaripeymani());
                    report.setEdarisum(result.getJSONObject(j).has("edariSum") ? result.getJSONObject(j).getInt("edariSum") : existed.getEdarisum());
                    report.setAmanisum(result.getJSONObject(j).has("amaniSum") ? result.getJSONObject(j).getInt("amaniSum") : existed.getAmanisum());
                    report.setPeymanisum(result.getJSONObject(j).has("peymaniSum") ? result.getJSONObject(j).getInt("peymaniSum") : existed.getPeymanisum());
                    report.setTotalsum(result.getJSONObject(j).has("totalSum") ? result.getJSONObject(j).getInt("totalSum") : existed.getTotalsum());
                    report.setUninsuredworker(result.getJSONObject(j).has("unInsuredWorker") ? result.getJSONObject(j).getInt("unInsuredWorker") : existed.getUninsuredworker());
                    report.setDoreamoozeshibimeworker(result.getJSONObject(j).has("doreAmoozeshiBimeWorker") ? result.getJSONObject(j).getInt("doreAmoozeshiBimeWorker") : existed.getDoreamoozeshibimeworker());
                    report.setAverageefficiencytonenafar(result.getJSONObject(j).has("averageEfficiencyToneNafar") ? result.getJSONObject(j).getInt("averageEfficiencyToneNafar") : existed.getAverageefficiencytonenafar());
                    report.setAverageefficiencykarkonanetolidi(result.getJSONObject(j).has("averageEfficiencyKarkonaneTolidi") ? result.getJSONObject(j).getInt("averageEfficiencyKarkonaneTolidi") : existed.getAverageefficiencykarkonanetolidi());
                    report.setAverageefficiencykarkonansum(result.getJSONObject(j).has("averageEfficiencyKarkonanSum") ? result.getJSONObject(j).getInt("averageEfficiencyKarkonanSum") : existed.getAverageefficiencykarkonansum());
                    report.setBargh(result.getJSONObject(j).has("bargh") ? result.getJSONObject(j).getInt("bargh") : existed.getBargh());
                    report.setGazetabiyi(result.getJSONObject(j).has("gazeTabiyi") ? result.getJSONObject(j).getInt("gazeTabiyi") : existed.getGazetabiyi());
                    report.setAbesanati(result.getJSONObject(j).has("abeSanati") ? result.getJSONObject(j).getInt("abeSanati") : existed.getAbesanati());
                    report.setBenzin(result.getJSONObject(j).has("benzin") ? result.getJSONObject(j).getInt("benzin") : existed.getBenzin());
                    report.setGazoyil(result.getJSONObject(j).has("gazoyil") ? result.getJSONObject(j).getInt("gazoyil") : existed.getGazoyil());
                    report.setAbeshorb(result.getJSONObject(j).has("abeShorb") ? result.getJSONObject(j).getInt("abeShorb") : existed.getAbeshorb());
                    report.setSayer(result.getJSONObject(j).has("sayer") ? result.getJSONObject(j).get("sayer").toString() : existed.getSayer());
                    report.setUsebargh(result.getJSONObject(j).has("useBargh") ? result.getJSONObject(j).getInt("useBargh") : existed.getUsebargh());
                    report.setTedadegenerator(result.getJSONObject(j).has("tedadeGenerator") ? result.getJSONObject(j).getInt("tedadeGenerator") : existed.getTedadegenerator());
                    report.setTavanegenerator(result.getJSONObject(j).has("tavaneGenerator") ? result.getJSONObject(j).getInt("tavaneGenerator") : existed.getTavanegenerator());
                    report.setMasrafegenerator(result.getJSONObject(j).has("masrafeGenerator") ? result.getJSONObject(j).getInt("masrafeGenerator") : existed.getMasrafegenerator());
                    report.setVaziaterefahiepersonel(result.getJSONObject(j).has("vaziateRefahiePersonel") ? result.getJSONObject(j).getInt("vaziateRefahiePersonel") : existed.getVaziaterefahiepersonel());
                    report.setIstajhizateimeniefardi(result.getJSONObject(j).has("isTajhizateImenieFardi") ? result.getJSONObject(j).getInt("isTajhizateImenieFardi") : existed.getIstajhizateimeniefardi());
                    report.setUsetajhizateimeni(result.getJSONObject(j).has("useTajhizateImeni") ? result.getJSONObject(j).getInt("useTajhizateImeni") : existed.getUsetajhizateimeni());
                    report.setDrivergovahimotabar(result.getJSONObject(j).has("driverGovahiMotabar") ? result.getJSONObject(j).getInt("driverGovahiMotabar") : existed.getDrivergovahimotabar());
                    report.setMachineryimeni(result.getJSONObject(j).has("machineryimeni") ? result.getJSONObject(j).getInt("machineryimeni") : existed.getMachineryimeni());
                    report.setReayateshibemojaz(result.getJSONObject(j).has("reayateShibeMojaz") ? result.getJSONObject(j).getInt("reayateShibeMojaz") : existed.getReayateshibemojaz());
                    report.setShibejaddeyeasli(result.getJSONObject(j).has("shibeJaddeyeAsli") ? result.getJSONObject(j).getInt("shibeJaddeyeAsli") : existed.getShibejaddeyeasli());
                    report.setShiberamphayedastrasi(result.getJSONObject(j).has("shibeRamphayeDastrasi") ? result.getJSONObject(j).getInt("shibeRamphayeDastrasi") : existed.getShiberamphayedastrasi());
                    report.setReayateayinnamehayeimeni(result.getJSONObject(j).has("reayateAyinnamehayeImeni") ? result.getJSONObject(j).getInt("reayateAyinnamehayeImeni") : existed.getReayateayinnamehayeimeni());
                    report.setIsaccidenthappen(result.getJSONObject(j).has("isAccidentHappen") ? result.getJSONObject(j).getInt("isAccidentHappen") : existed.getIsaccidenthappen());
                    report.setLaghgiri(result.getJSONObject(j).has("laghGiri") ? result.getJSONObject(j).getInt("laghGiri") : existed.getLaghgiri());
                    report.setNeedgheyrefaalimeni(result.getJSONObject(j).has("needGheyreFaalImeni") ? result.getJSONObject(j).getInt("needGheyreFaalImeni") : existed.getNeedgheyrefaalimeni());
                    report.setOtherdesc(result.getJSONObject(j).has("otherDesc") ? result.getJSONObject(j).get("otherDesc").toString() : existed.getOtherdesc());
                    report.setMoshkelatvamavane(result.getJSONObject(j).has("moshkelatVaMavane") ? result.getJSONObject(j).get("moshkelatVaMavane").toString() : existed.getMoshkelatvamavane());
                    report.setPishnahadatvaezharenazar(result.getJSONObject(j).has("pishnahadatVaEzhareNazar") ? result.getJSONObject(j).get("pishnahadatVaEzhareNazar").toString() : existed.getPishnahadatvaezharenazar());
                    report.setOpinion(result.getJSONObject(j).has("opinion") ? result.getJSONObject(j).get("opinion").toString() : existed.getOpinion());
                    report.setSetenergy(result.getJSONObject(j).has("setEnergy") ? result.getJSONObject(j).getBoolean("setEnergy") : existed.getSetenergy());
                    report.setSetimeni(result.getJSONObject(j).has("setImeni") ? result.getJSONObject(j).getBoolean("setImeni") : existed.getSetimeni());
                    report.setSetmachinery(result.getJSONObject(j).has("setMachinery") ? result.getJSONObject(j).getBoolean("setMachinery") : existed.getSetmachinery());
                    report.setSetoperation1(result.getJSONObject(j).has("setOperation1") ? result.getJSONObject(j).getBoolean("setOperation1") : existed.getSetoperation1());
                    report.setSetoperation2(result.getJSONObject(j).has("setOperation2") ? result.getJSONObject(j).getBoolean("setOperation2") : existed.getSetoperation2());
                    report.setSetpeople(result.getJSONObject(j).has("setPeople") ? result.getJSONObject(j).getBoolean("setPeople") : existed.getSetpeople());
                    report.setSetproblems(result.getJSONObject(j).has("setProblems") ? result.getJSONObject(j).getBoolean("setProblems") : existed.getSetproblems());
                    report.setSetproducesell(result.getJSONObject(j).has("setProduceSell") ? result.getJSONObject(j).getBoolean("setProduceSell") : existed.getSetproducesell());
                    report.setSetgeom1(result.getJSONObject(j).has("setGeom1") ? result.getJSONObject(j).getBoolean("setGeom1") : existed.getSetgeom1());
                    report.setSetgeom2(result.getJSONObject(j).has("setGeom2") ? result.getJSONObject(j).getBoolean("setGeom2") : existed.getSetgeom2());
                    report.setSetgeom3(result.getJSONObject(j).has("setGeom3") ? result.getJSONObject(j).getBoolean("setGeom3") : existed.getSetgeom3());
                    report.setSetsuggest(result.getJSONObject(j).has("setSuggest") ? result.getJSONObject(j).getBoolean("setSuggest") : existed.getSetsuggest());
                    
                    reportLocalServiceUtil.updateReport(report);
                }

            } catch (JSONException e) {
                System.out.println(e.getMessage());
                Timber.e(e.getMessage(), e.getMessage());
            }
        }

        return true;
    }

    public static boolean insertProduceSellFromJSON(JSONArray result, Context context) {

        ProducesellLocalServiceUtil producesellLocalServiceUtil = new ProducesellLocalServiceUtil(context);
        for (int j = 0; j < result.length(); j++) {
            try {
                com.example.imeo_app.db.tables.Producesell produceSell = new com.example.imeo_app.db.tables.Producesell();
                long produceSellId = result.getJSONObject(j).getLong("produceSellId");
                produceSell.setProducesellid(produceSellId);
                produceSell.setSalesvalue(result.getJSONObject(j).has("salesValue") ? result.getJSONObject(j).getInt("salesValue") : 0);
                produceSell.setMineraltransport(result.getJSONObject(j).has("mineralTransport") ? result.getJSONObject(j).getInt("mineralTransport") : 0);
                produceSell.setReportid(result.getJSONObject(j).has("reportId") ? result.getJSONObject(j).getInt("reportId") : 0);
                produceSell.setMineid(result.getJSONObject(j).has("mineId") ? result.getJSONObject(j).getLong("mineId") : 0);
                produceSell.setOtherdetails(result.getJSONObject(j).has("otherDetails") ? result.getJSONObject(j).get("otherDetails").toString() : "");
                produceSell.setFixedprice(result.getJSONObject(j).has("fixedPrice") ? result.getJSONObject(j).getInt("fixedPrice") : 0);
                produceSell.setMineralmaterialid(result.getJSONObject(j).has("mineralMaterialId") ? result.getJSONObject(j).getLong("mineralMaterialId") : 0);
                produceSell.setActualextraction(result.getJSONObject(j).has("actualExtraction") ? result.getJSONObject(j).getInt("actualExtraction") : 0);
                produceSell.setWasteamount(result.getJSONObject(j).has("wasteAmount") ? result.getJSONObject(j).getInt("wasteAmount") : 0);
                produceSell.setWaybillserial(result.getJSONObject(j).has("waybillSerial") ? result.getJSONObject(j).get("waybillSerial").toString() : "");

                Producesell existed = producesellLocalServiceUtil.getProducesellById(produceSellId);
                if (existed != null) {
                    produceSell.setReportid(existed.getReportid());
                    producesellLocalServiceUtil.updateProducesell(produceSell);
                } else
                    producesellLocalServiceUtil.insertProducesell(produceSell);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                Timber.e(e.getMessage(), e.getMessage());
            }
        }

        return true;
    }

    public static boolean insertMineFrontFromJSON(JSONArray result, Context context) {

        MinefrontLocalServiceUtil minefrontLocalServiceUtil = new MinefrontLocalServiceUtil(context);
        for (int j = 0; j < result.length(); j++) {
            try {
                com.example.imeo_app.db.tables.Minefront mineFront = new com.example.imeo_app.db.tables.Minefront();
                long mineFrontId = result.getJSONObject(j).getLong("mineFrontId");
                mineFront.setMinefrontid(mineFrontId);
                mineFront.setMineid(result.getJSONObject(j).has("mineId") ? result.getJSONObject(j).getInt("mineId") : 0);
                mineFront.setReportid(result.getJSONObject(j).has("reportId") ? result.getJSONObject(j).getInt("reportId") : 0);
                mineFront.setStonetype(result.getJSONObject(j).has("stoneTypeCode") ? result.getJSONObject(j).getInt("stoneTypeCode") : 0);
                mineFront.setWorkfrontwide(result.getJSONObject(j).has("workFrontWide") ? result.getJSONObject(j).getInt("workFrontWide") : 0);
                mineFront.setWorkfrontheight(result.getJSONObject(j).has("workFrontHeight") ? result.getJSONObject(j).getInt("workFrontHeight") : 0);
                mineFront.setWorkfrontlength(result.getJSONObject(j).has("workFrontLength") ? result.getJSONObject(j).getInt("workFrontLength") : 0);
                mineFront.setWorkfrontslope(result.getJSONObject(j).has("workFrontSlope") ? result.getJSONObject(j).getInt("workFrontSlope") : 0);

                Minefront existed = minefrontLocalServiceUtil.getMinefrontById(mineFrontId);
                if (existed != null) {
                    mineFront.setReportid(existed.getReportid());
                    minefrontLocalServiceUtil.updateMinefront(mineFront);
                } else
                    minefrontLocalServiceUtil.insertMinefront(mineFront);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
                Timber.e(e.getMessage(), e.getMessage());
            }
        }
        return true;
    }

    public static boolean deleteDocumentTable(Context context) {
        DocumentLocalServiceUtil documentLocalServiceUtil = new DocumentLocalServiceUtil(context);
        List<Document> documents = documentLocalServiceUtil.getDocument();
        if(documents.size()>0){
            for (int i = 0; i < documents.size(); i++) {
                documentLocalServiceUtil.deleteDocument(documents.get(i).getDocumentid());
            }
        }
        return true;
    }

    public static boolean deleteMineTable(Context context) {
        MineLocalServiceUtil mineLocalServiceUtil = new MineLocalServiceUtil(context);
        List<Mine> mine = mineLocalServiceUtil.getMine();
        if(mine.size()>0){
            for (int i = 0; i < mine.size(); i++) {
                mineLocalServiceUtil.deleteMine(mine.get(i).getMineid());
            }
        }
        return true;
    }

    public static boolean deleteMineralMaterialTable(Context context) {
        MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(context);
        List<Mineralmaterial> mineralmaterials = mineralmaterialLocalServiceUtil.getMineralmaterial();
        if(mineralmaterials.size()>0){
            for (int i = 0; i < mineralmaterials.size(); i++) {
                mineralmaterialLocalServiceUtil.deleteMineralmaterial(mineralmaterials.get(i).getMineralmaterialid());
            }
        }
        return true;
    }

    public static boolean deleteMachinerylTable(Context context) {
        MachineryLocalServiceUtil machineryLocalServiceUtil = new MachineryLocalServiceUtil(context);
        List<Machinery> machinery = machineryLocalServiceUtil.getMachinery();
        if(machinery.size()>0){
            for (int i = 0; i < machinery.size(); i++) {
                machineryLocalServiceUtil.deleteMachinery(machinery.get(i).getMineid());
            }
        }
        return true;
    }

    public static boolean deleteMineFrontlTable(Context context) {
        MinefrontLocalServiceUtil minefrontLocalServiceUtil = new MinefrontLocalServiceUtil(context);
        List<Minefront> mineFront = minefrontLocalServiceUtil.getMinefront();
        if(mineFront.size()>0){
            for (int i = 0; i < mineFront.size(); i++) {
                minefrontLocalServiceUtil.deleteMinefront(mineFront.get(i).getMinefrontid());
            }
        }
        return true;
    }

    public static boolean deleteMineOperationTable(Context context) {
        MineoperationLocalServiceUtil mineoperationLocalServiceUtil = new MineoperationLocalServiceUtil(context);
        List<Mineoperation> mineOperation = mineoperationLocalServiceUtil.getMineoperation();
        if(mineOperation.size()>0){
            for (int i = 0; i < mineOperation.size(); i++) {
                mineoperationLocalServiceUtil.deleteMineoperation(mineOperation.get(i).getMineoperationid());
            }
        }
        return true;
    }

    public static boolean deleteReportsTable(Context context) {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(context);
        List<Report> report = reportLocalServiceUtil.getReport();
        if(report.size()>0){
            for (int i = 0; i < report.size(); i++) {
                reportLocalServiceUtil.deleteReport(report.get(i).getReportid());
            }
        }
        return true;
    }

    public static boolean deleteProduceSellTable(Context context) {
        ProducesellLocalServiceUtil producesellLocalServiceUtil = new ProducesellLocalServiceUtil(context);
        List<Producesell> produceSell = producesellLocalServiceUtil.getProducesell();
        if(produceSell.size()>0){
            for (int i = 0; i < produceSell.size(); i++) {
                producesellLocalServiceUtil.deleteProducesell(produceSell.get(i).getProducesellid());
            }
        }
        return true;
    }
}
