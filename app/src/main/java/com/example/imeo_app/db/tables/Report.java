package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "report")
public class Report {
    @DatabaseField(id = true)
    private long reportid;

    @DatabaseField
    private Date createdate;

    @DatabaseField
    private String persiancreatedate;

    @DatabaseField
    private Date modifieddate;

    @DatabaseField
    private int status;

    @DatabaseField
    private Date statusdate;

    @DatabaseField
    private int statususerid;

    @DatabaseField
    private String reportdate;

    @DatabaseField
    private String explorelicensename;

    @DatabaseField
    private long mineid;

    @DatabaseField
    private String minename;

    @DatabaseField
    private int mineactive;

    @DatabaseField
    private String geometrysinekarjebhe;

    @DatabaseField
    private String geometrydepomadani;

    @DatabaseField
    private String geometrydepowaste;

    @DatabaseField
    private String geometrydahanetunnel;

    @DatabaseField
    private String geometrysinekar;

    @DatabaseField
    private String geometrytrench;

    @DatabaseField
    private String geometrywell;

    @DatabaseField
    private int workdayinmonth;

    @DatabaseField
    private int shiftinday;

    @DatabaseField
    private int workhourinshift;

    @DatabaseField
    private int workshiftinmonth;

    @DatabaseField
    private int workhourinmonth;

    @DatabaseField
    private int extractionworkshopqty;

    @DatabaseField
    private int advancingtunnelqty;

    @DatabaseField
    private int totallengthtunnels;

    @DatabaseField
    private int totalvolumetunnels;

    @DatabaseField
    private int advancingdoilesqty;

    @DatabaseField
    private int totallengthdoiles;

    @DatabaseField
    private int totaldrillingdoiles;

    @DatabaseField
    private int extractedvolume;

    @DatabaseField
    private int donegozareshehaffari;

    @DatabaseField
    private int metrazhekollehaffari;

    @DatabaseField
    private int qotrechalha;

    @DatabaseField
    private int tedadechalha;

    @DatabaseField
    private int faseleyechalha;

    @DatabaseField
    private int tedadechalhadarradif;

    @DatabaseField
    private int tedaderadif;

    @DatabaseField
    private int faseleyeradifi;

    @DatabaseField
    private int faseletajebheyekareazadechalha;

    @DatabaseField
    private double omghemotavassetechalha;

    @DatabaseField
    private double meghdaresangeghabeleestekhraj;

    @DatabaseField
    private double haffarievizhe;

    @DatabaseField
    private int donegozaresheenfejari;

    @DatabaseField
    private int anfo;

    @DatabaseField
    private int dinamit;

    @DatabaseField
    private int emolayt;

    @DatabaseField
    private int baroot;

    @DatabaseField
    private int chashnieelektriki;

    @DatabaseField
    private int chashniemamuli;

    @DatabaseField
    private int kortex;

    @DatabaseField
    private int natel;

    @DatabaseField
    private int pc;

    @DatabaseField
    private int takhiri;

    @DatabaseField
    private int booster;

    @DatabaseField
    private int fitile;

    @DatabaseField
    private int mohandesiamani;

    @DatabaseField
    private int mohandesipeymani;

    @DatabaseField
    private int mohandesisum;

    @DatabaseField
    private int kargaranamani;

    @DatabaseField
    private int kargaranpeymani;

    @DatabaseField
    private int kargaransum;

    @DatabaseField
    private int edariamani;

    @DatabaseField
    private int edaripeymani;

    @DatabaseField
    private int edarisum;

    @DatabaseField
    private int amanisum;

    @DatabaseField
    private int peymanisum;

    @DatabaseField
    private int totalsum;

    @DatabaseField
    private int uninsuredworker;

    @DatabaseField
    private int doreamoozeshibimeworker;

    @DatabaseField
    private double averageefficiencytonenafar;

    @DatabaseField
    private double averageefficiencykarkonanetolidi;

    @DatabaseField
    private double averageefficiencykarkonansum;

    @DatabaseField
    private int bargh;

    @DatabaseField
    private int gazetabiyi;

    @DatabaseField
    private int abesanati;

    @DatabaseField
    private int benzin;

    @DatabaseField
    private int gazoyil;

    @DatabaseField
    private int abeshorb;

    @DatabaseField
    private String sayer;

    @DatabaseField
    private int usebargh;

    @DatabaseField
    private int tedadegenerator;

    @DatabaseField
    private int tavanegenerator;

    @DatabaseField
    private int masrafegenerator;

    @DatabaseField
    private int vaziaterefahiepersonel;

    @DatabaseField
    private int istajhizateimeniefardi;

    @DatabaseField
    private int usetajhizateimeni;

    @DatabaseField
    private int drivergovahimotabar;

    @DatabaseField
    private int machineryimeni;

    @DatabaseField
    private int reayateshibemojaz;

    @DatabaseField
    private int shibejaddeyeasli;

    @DatabaseField
    private int shiberamphayedastrasi;

    @DatabaseField
    private int reayateayinnamehayeimeni;

    @DatabaseField
    private int isaccidenthappen;

    @DatabaseField
    private int laghgiri;

    @DatabaseField
    private int needgheyrefaalimeni;

    @DatabaseField
    private String otherdesc;

    @DatabaseField
    private String moshkelatvamavane;
    
    @DatabaseField
    private String pishnahadatvaezharenazar;
    
    @DatabaseField
    private String opinion;
    
    @DatabaseField
    private boolean setenergy;
    
    @DatabaseField
    private boolean setimeni;
    
    @DatabaseField
    private boolean setmachinery;
    
    @DatabaseField
    private boolean setoperation1;
    
    @DatabaseField
    private boolean setoperation2;
    
    @DatabaseField
    private boolean setpeople;
    
    @DatabaseField
    private boolean setproblems;
    
    @DatabaseField
    private boolean setproducesell;
    
    @DatabaseField
    private boolean setgeom1;
    
    @DatabaseField
    private boolean setgeom2;
    
    @DatabaseField
    private boolean setgeom3;
    
    @DatabaseField
    private boolean setsuggest;


    public long getReportid() {
        return reportid;
    }

    public void setReportid(long reportid) {
        this.reportid = reportid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getPersiancreatedate() {
        return persiancreatedate;
    }

    public void setPersiancreatedate(String persiancreatedate) {
        this.persiancreatedate = persiancreatedate;
    }

    public Date getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }

    public long getMineid() {
        return mineid;
    }

    public void setMineid(long mineid) {
        this.mineid = mineid;
    }

    public String getMinename() {
        return minename;
    }

    public void setMinename(String minename) {
        this.minename = minename;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public int getStatususerid() {
        return statususerid;
    }

    public void setStatususerid(int statususerid) {
        this.statususerid = statususerid;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getExplorelicensename() {
        return explorelicensename;
    }

    public void setExplorelicensename(String explorelicensename) {
        this.explorelicensename = explorelicensename;
    }

    public int getMineactive() {
        return mineactive;
    }

    public void setMineactive(int mineactive) {
        this.mineactive = mineactive;
    }

    public String getGeometrysinekarjebhe() {
        return geometrysinekarjebhe;
    }

    public void setGeometrysinekarjebhe(String geometrysinekarjebhe) {
        this.geometrysinekarjebhe = geometrysinekarjebhe;
    }

    public String getGeometrydepomadani() {
        return geometrydepomadani;
    }

    public void setGeometrydepomadani(String geometrydepomadani) {
        this.geometrydepomadani = geometrydepomadani;
    }

    public String getGeometrydepowaste() {
        return geometrydepowaste;
    }

    public void setGeometrydepowaste(String geometrydepowaste) {
        this.geometrydepowaste = geometrydepowaste;
    }

    public String getGeometrydahanetunnel() {
        return geometrydahanetunnel;
    }

    public void setGeometrydahanetunnel(String geometrydahanetunnel) {
        this.geometrydahanetunnel = geometrydahanetunnel;
    }

    public String getGeometrysinekar() {
        return geometrysinekar;
    }

    public void setGeometrysinekar(String geometrysinekar) {
        this.geometrysinekar = geometrysinekar;
    }

    public String getGeometrytrench() {
        return geometrytrench;
    }

    public void setGeometrytrench(String geometrytrench) {
        this.geometrytrench = geometrytrench;
    }

    public String getGeometrywell() {
        return geometrywell;
    }

    public void setGeometrywell(String geometrywell) {
        this.geometrywell = geometrywell;
    }

    public int getWorkdayinmonth() {
        return workdayinmonth;
    }

    public void setWorkdayinmonth(int workdayinmonth) {
        this.workdayinmonth = workdayinmonth;
    }

    public int getShiftinday() {
        return shiftinday;
    }

    public void setShiftinday(int shiftinday) {
        this.shiftinday = shiftinday;
    }

    public int getWorkhourinshift() {
        return workhourinshift;
    }

    public void setWorkhourinshift(int workhourinshift) {
        this.workhourinshift = workhourinshift;
    }

    public int getWorkshiftinmonth() {
        return workshiftinmonth;
    }

    public void setWorkshiftinmonth(int workshiftinmonth) {
        this.workshiftinmonth = workshiftinmonth;
    }

    public int getWorkhourinmonth() {
        return workhourinmonth;
    }

    public void setWorkhourinmonth(int workhourinmonth) {
        this.workhourinmonth = workhourinmonth;
    }

    public int getExtractionworkshopqty() {
        return extractionworkshopqty;
    }

    public void setExtractionworkshopqty(int extractionworkshopqty) {
        this.extractionworkshopqty = extractionworkshopqty;
    }

    public int getAdvancingtunnelqty() {
        return advancingtunnelqty;
    }

    public void setAdvancingtunnelqty(int advancingtunnelqty) {
        this.advancingtunnelqty = advancingtunnelqty;
    }

    public int getTotallengthtunnels() {
        return totallengthtunnels;
    }

    public void setTotallengthtunnels(int totallengthtunnels) {
        this.totallengthtunnels = totallengthtunnels;
    }

    public int getTotalvolumetunnels() {
        return totalvolumetunnels;
    }

    public void setTotalvolumetunnels(int totalvolumetunnels) {
        this.totalvolumetunnels = totalvolumetunnels;
    }

    public int getAdvancingdoilesqty() {
        return advancingdoilesqty;
    }

    public void setAdvancingdoilesqty(int advancingdoilesqty) {
        this.advancingdoilesqty = advancingdoilesqty;
    }

    public int getTotallengthdoiles() {
        return totallengthdoiles;
    }

    public void setTotallengthdoiles(int totallengthdoiles) {
        this.totallengthdoiles = totallengthdoiles;
    }

    public int getTotaldrillingdoiles() {
        return totaldrillingdoiles;
    }

    public void setTotaldrillingdoiles(int totaldrillingdoiles) {
        this.totaldrillingdoiles = totaldrillingdoiles;
    }

    public int getExtractedvolume() {
        return extractedvolume;
    }

    public void setExtractedvolume(int extractedvolume) {
        this.extractedvolume = extractedvolume;
    }

    public int getDonegozareshehaffari() {
        return donegozareshehaffari;
    }

    public void setDonegozareshehaffari(int donegozareshehaffari) {
        this.donegozareshehaffari = donegozareshehaffari;
    }

    public int getMetrazhekollehaffari() {
        return metrazhekollehaffari;
    }

    public void setMetrazhekollehaffari(int metrazhekollehaffari) {
        this.metrazhekollehaffari = metrazhekollehaffari;
    }

    public int getQotrechalha() {
        return qotrechalha;
    }

    public void setQotrechalha(int qotrechalha) {
        this.qotrechalha = qotrechalha;
    }

    public int getTedadechalha() {
        return tedadechalha;
    }

    public void setTedadechalha(int tedadechalha) {
        this.tedadechalha = tedadechalha;
    }

    public int getFaseleyechalha() {
        return faseleyechalha;
    }

    public void setFaseleyechalha(int faseleyechalha) {
        this.faseleyechalha = faseleyechalha;
    }

    public int getTedadechalhadarradif() {
        return tedadechalhadarradif;
    }

    public void setTedadechalhadarradif(int tedadechalhadarradif) {
        this.tedadechalhadarradif = tedadechalhadarradif;
    }

    public int getTedaderadif() {
        return tedaderadif;
    }

    public void setTedaderadif(int tedaderadif) {
        this.tedaderadif = tedaderadif;
    }

    public int getFaseleyeradifi() {
        return faseleyeradifi;
    }

    public void setFaseleyeradifi(int faseleyeradifi) {
        this.faseleyeradifi = faseleyeradifi;
    }

    public int getFaseletajebheyekareazadechalha() {
        return faseletajebheyekareazadechalha;
    }

    public void setFaseletajebheyekareazadechalha(int faseletajebheyekareazadechalha) {
        this.faseletajebheyekareazadechalha = faseletajebheyekareazadechalha;
    }

    public double getOmghemotavassetechalha() {
        return omghemotavassetechalha;
    }

    public void setOmghemotavassetechalha(double omghemotavassetechalha) {
        this.omghemotavassetechalha = omghemotavassetechalha;
    }

    public double getMeghdaresangeghabeleestekhraj() {
        return meghdaresangeghabeleestekhraj;
    }

    public void setMeghdaresangeghabeleestekhraj(double meghdaresangeghabeleestekhraj) {
        this.meghdaresangeghabeleestekhraj = meghdaresangeghabeleestekhraj;
    }

    public double getHaffarievizhe() {
        return haffarievizhe;
    }

    public void setHaffarievizhe(double haffarievizhe) {
        this.haffarievizhe = haffarievizhe;
    }

    public int getDonegozaresheenfejari() {
        return donegozaresheenfejari;
    }

    public void setDonegozaresheenfejari(int donegozaresheenfejari) {
        this.donegozaresheenfejari = donegozaresheenfejari;
    }

    public int getAnfo() {
        return anfo;
    }

    public void setAnfo(int anfo) {
        this.anfo = anfo;
    }

    public int getDinamit() {
        return dinamit;
    }

    public void setDinamit(int dinamit) {
        this.dinamit = dinamit;
    }

    public int getEmolayt() {
        return emolayt;
    }

    public void setEmolayt(int emolayt) {
        this.emolayt = emolayt;
    }

    public int getBaroot() {
        return baroot;
    }

    public void setBaroot(int baroot) {
        this.baroot = baroot;
    }

    public int getChashnieelektriki() {
        return chashnieelektriki;
    }

    public void setChashnieelektriki(int chashnieelektriki) {
        this.chashnieelektriki = chashnieelektriki;
    }

    public int getChashniemamuli() {
        return chashniemamuli;
    }

    public void setChashniemamuli(int chashniemamuli) {
        this.chashniemamuli = chashniemamuli;
    }

    public int getKortex() {
        return kortex;
    }

    public void setKortex(int kortex) {
        this.kortex = kortex;
    }

    public int getNatel() {
        return natel;
    }

    public void setNatel(int natel) {
        this.natel = natel;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getTakhiri() {
        return takhiri;
    }

    public void setTakhiri(int takhiri) {
        this.takhiri = takhiri;
    }

    public int getBooster() {
        return booster;
    }

    public void setBooster(int booster) {
        this.booster = booster;
    }

    public int getFitile() {
        return fitile;
    }

    public void setFitile(int fitile) {
        this.fitile = fitile;
    }

    public int getMohandesiamani() {
        return mohandesiamani;
    }

    public void setMohandesiamani(int mohandesiamani) {
        this.mohandesiamani = mohandesiamani;
    }

    public int getMohandesipeymani() {
        return mohandesipeymani;
    }

    public void setMohandesipeymani(int mohandesipeymani) {
        this.mohandesipeymani = mohandesipeymani;
    }

    public int getMohandesisum() {
        return mohandesisum;
    }

    public void setMohandesisum(int mohandesisum) {
        this.mohandesisum = mohandesisum;
    }

    public int getKargaranamani() {
        return kargaranamani;
    }

    public void setKargaranamani(int kargaranamani) {
        this.kargaranamani = kargaranamani;
    }

    public int getKargaranpeymani() {
        return kargaranpeymani;
    }

    public void setKargaranpeymani(int kargaranpeymani) {
        this.kargaranpeymani = kargaranpeymani;
    }

    public int getKargaransum() {
        return kargaransum;
    }

    public void setKargaransum(int kargaransum) {
        this.kargaransum = kargaransum;
    }

    public int getEdariamani() {
        return edariamani;
    }

    public void setEdariamani(int edariamani) {
        this.edariamani = edariamani;
    }

    public int getEdaripeymani() {
        return edaripeymani;
    }

    public void setEdaripeymani(int edaripeymani) {
        this.edaripeymani = edaripeymani;
    }

    public int getEdarisum() {
        return edarisum;
    }

    public void setEdarisum(int edarisum) {
        this.edarisum = edarisum;
    }

    public int getAmanisum() {
        return amanisum;
    }

    public void setAmanisum(int amanisum) {
        this.amanisum = amanisum;
    }

    public int getPeymanisum() {
        return peymanisum;
    }

    public void setPeymanisum(int peymanisum) {
        this.peymanisum = peymanisum;
    }

    public int getTotalsum() {
        return totalsum;
    }

    public void setTotalsum(int totalsum) {
        this.totalsum = totalsum;
    }

    public int getUninsuredworker() {
        return uninsuredworker;
    }

    public void setUninsuredworker(int uninsuredworker) {
        this.uninsuredworker = uninsuredworker;
    }

    public int getDoreamoozeshibimeworker() {
        return doreamoozeshibimeworker;
    }

    public void setDoreamoozeshibimeworker(int doreamoozeshibimeworker) {
        this.doreamoozeshibimeworker = doreamoozeshibimeworker;
    }

    public double getAverageefficiencytonenafar() {
        return averageefficiencytonenafar;
    }

    public void setAverageefficiencytonenafar(double averageefficiencytonenafar) {
        this.averageefficiencytonenafar = averageefficiencytonenafar;
    }

    public double getAverageefficiencykarkonanetolidi() {
        return averageefficiencykarkonanetolidi;
    }

    public void setAverageefficiencykarkonanetolidi(double averageefficiencykarkonanetolidi) {
        this.averageefficiencykarkonanetolidi = averageefficiencykarkonanetolidi;
    }

    public double getAverageefficiencykarkonansum() {
        return averageefficiencykarkonansum;
    }

    public void setAverageefficiencykarkonansum(double averageefficiencykarkonansum) {
        this.averageefficiencykarkonansum = averageefficiencykarkonansum;
    }

    public int getBargh() {
        return bargh;
    }

    public void setBargh(int bargh) {
        this.bargh = bargh;
    }

    public int getGazetabiyi() {
        return gazetabiyi;
    }

    public void setGazetabiyi(int gazetabiyi) {
        this.gazetabiyi = gazetabiyi;
    }

    public int getAbesanati() {
        return abesanati;
    }

    public void setAbesanati(int abesanati) {
        this.abesanati = abesanati;
    }

    public int getBenzin() {
        return benzin;
    }

    public void setBenzin(int benzin) {
        this.benzin = benzin;
    }

    public int getGazoyil() {
        return gazoyil;
    }

    public void setGazoyil(int gazoyil) {
        this.gazoyil = gazoyil;
    }

    public int getAbeshorb() {
        return abeshorb;
    }

    public void setAbeshorb(int abeshorb) {
        this.abeshorb = abeshorb;
    }

    public String getSayer() {
        return sayer;
    }

    public void setSayer(String sayer) {
        this.sayer = sayer;
    }

    public int getUsebargh() {
        return usebargh;
    }

    public void setUsebargh(int usebargh) {
        this.usebargh = usebargh;
    }

    public int getTedadegenerator() {
        return tedadegenerator;
    }

    public void setTedadegenerator(int tedadegenerator) {
        this.tedadegenerator = tedadegenerator;
    }

    public int getTavanegenerator() {
        return tavanegenerator;
    }

    public void setTavanegenerator(int tavanegenerator) {
        this.tavanegenerator = tavanegenerator;
    }

    public int getMasrafegenerator() {
        return masrafegenerator;
    }

    public void setMasrafegenerator(int masrafegenerator) {
        this.masrafegenerator = masrafegenerator;
    }

    public int getVaziaterefahiepersonel() {
        return vaziaterefahiepersonel;
    }

    public void setVaziaterefahiepersonel(int vaziaterefahiepersonel) {
        this.vaziaterefahiepersonel = vaziaterefahiepersonel;
    }

    public int getIstajhizateimeniefardi() {
        return istajhizateimeniefardi;
    }

    public void setIstajhizateimeniefardi(int istajhizateimeniefardi) {
        this.istajhizateimeniefardi = istajhizateimeniefardi;
    }

    public int getUsetajhizateimeni() {
        return usetajhizateimeni;
    }

    public void setUsetajhizateimeni(int usetajhizateimeni) {
        this.usetajhizateimeni = usetajhizateimeni;
    }

    public int getDrivergovahimotabar() {
        return drivergovahimotabar;
    }

    public void setDrivergovahimotabar(int drivergovahimotabar) {
        this.drivergovahimotabar = drivergovahimotabar;
    }

    public int getMachineryimeni() {
        return machineryimeni;
    }

    public void setMachineryimeni(int machineryimeni) {
        this.machineryimeni = machineryimeni;
    }

    public int getReayateshibemojaz() {
        return reayateshibemojaz;
    }

    public void setReayateshibemojaz(int reayateshibemojaz) {
        this.reayateshibemojaz = reayateshibemojaz;
    }

    public int getShibejaddeyeasli() {
        return shibejaddeyeasli;
    }

    public void setShibejaddeyeasli(int shibejaddeyeasli) {
        this.shibejaddeyeasli = shibejaddeyeasli;
    }

    public int getShiberamphayedastrasi() {
        return shiberamphayedastrasi;
    }

    public void setShiberamphayedastrasi(int shiberamphayedastrasi) {
        this.shiberamphayedastrasi = shiberamphayedastrasi;
    }

    public int getReayateayinnamehayeimeni() {
        return reayateayinnamehayeimeni;
    }

    public void setReayateayinnamehayeimeni(int reayateayinnamehayeimeni) {
        this.reayateayinnamehayeimeni = reayateayinnamehayeimeni;
    }

    public int getIsaccidenthappen() {
        return isaccidenthappen;
    }

    public void setIsaccidenthappen(int isaccidenthappen) {
        this.isaccidenthappen = isaccidenthappen;
    }

    public int getLaghgiri() {
        return laghgiri;
    }

    public void setLaghgiri(int laghgiri) {
        this.laghgiri = laghgiri;
    }

    public int getNeedgheyrefaalimeni() {
        return needgheyrefaalimeni;
    }

    public void setNeedgheyrefaalimeni(int needgheyrefaalimeni) {
        this.needgheyrefaalimeni = needgheyrefaalimeni;
    }

    public String getOtherdesc() {
        return otherdesc;
    }

    public void setOtherdesc(String otherdesc) {
        this.otherdesc = otherdesc;
    }

    public String getMoshkelatvamavane() {
        return moshkelatvamavane;
    }

    public void setMoshkelatvamavane(String moshkelatvamavane) {
        this.moshkelatvamavane = moshkelatvamavane;
    }

    public String getPishnahadatvaezharenazar() {
        return pishnahadatvaezharenazar;
    }

    public void setPishnahadatvaezharenazar(String pishnahadatvaezharenazar) {
        this.pishnahadatvaezharenazar = pishnahadatvaezharenazar;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public boolean  getSetenergy() {
        return setenergy;
    }

    public void setSetenergy(boolean setenergy) {
        this.setenergy = setenergy;
    }

    public boolean  getSetimeni() {
        return setimeni;
    }

    public void setSetimeni(boolean setimeni) {
        this.setimeni = setimeni;
    }

    public boolean  getSetmachinery() {
        return setmachinery;
    }

    public void setSetmachinery(boolean setmachinery) {
        this.setmachinery = setmachinery;
    }

    public boolean  getSetoperation1() {
        return setoperation1;
    }

    public void setSetoperation1(boolean setoperation1) {
        this.setoperation1 = setoperation1;
    }

    public boolean  getSetoperation2() {
        return setoperation2;
    }

    public void setSetoperation2(boolean setoperation2) {
        this.setoperation2 = setoperation2;
    }

    public boolean  getSetpeople() {
        return setpeople;
    }

    public void setSetpeople(boolean setpeople) {
        this.setpeople = setpeople;
    }

    public boolean  getSetproblems() {
        return setproblems;
    }

    public void setSetproblems(boolean setproblems) {
        this.setproblems = setproblems;
    }

    public boolean  getSetproducesell() {
        return setproducesell;
    }

    public void setSetproducesell(boolean setproducesell) {
        this.setproducesell = setproducesell;
    }

    public boolean  getSetgeom1() {
        return setgeom1;
    }

    public void setSetgeom1(boolean setgeom1) {
        this.setgeom1 = setgeom1;
    }

    public boolean  getSetgeom2() {
        return setgeom2;
    }

    public void setSetgeom2(boolean setgeom2) {
        this.setgeom2 = setgeom2;
    }

    public boolean  getSetgeom3() {
        return setgeom3;
    }

    public void setSetgeom3(boolean setgeom3) {
        this.setgeom3 = setgeom3;
    }

    public boolean  getSetsuggest() {
        return setsuggest;
    }

    public void setSetsuggest(boolean setsuggest) {
        this.setsuggest = setsuggest;
    }
}
