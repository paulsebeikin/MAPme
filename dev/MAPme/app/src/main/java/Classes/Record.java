package Classes;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Paul on 5/24/2016.
 */


@DatabaseTable(tableName = "records")
public class Record {

    //region Meta-Properties
    @DatabaseField(canBeNull = false)
    private boolean uploaded;

    @DatabaseField(canBeNull = false)
    private boolean deleted;

    //endregion

    //region Properties
    @DatabaseField(id = true)
    private int adu;

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(canBeNull = false)
    private String email;

    @DatabaseField(canBeNull = false)
    private String project;

    @DatabaseField(canBeNull = false)
    private String country;

    @DatabaseField(canBeNull = false)
    private String province;

    @DatabaseField(canBeNull = false)
    private String town;

    // represents the locality field required by the API
    @DatabaseField(canBeNull = false)
    private String desc;

    // represents the minelev and maxelev fields required by the API
    @DatabaseField(canBeNull = false)
    private double altitude;

    @DatabaseField(canBeNull = false)
    private double latitude;

    @DatabaseField(canBeNull = false)
    private double longitude;

    @DatabaseField(canBeNull = false)
    private String source;

    @DatabaseField(canBeNull = false)
    private int year;

    @DatabaseField(canBeNull = false)
    private int month;

    @DatabaseField(canBeNull = false)
    private int day;

    // stores the paths to the image
    @DatabaseField(canBeNull =  false)
    private String url;

    @DatabaseField
    private String datum;

    @DatabaseField
    private double accuracy;

    @DatabaseField
    private String note;

    // represents the userdet field optional in the API
    @DatabaseField
    private String species;

    @DatabaseField
    private String observers;

    // used for the PHOWN projects (weavers)
    @DatabaseField
    private int nestCount;

    // used for the PHOWN projects (weavers)
    @DatabaseField
    private String nestSite;

    @DatabaseField
    private boolean roadKill;

    @DatabaseField
    private int taxonID;

    @DatabaseField
    private String taxonName;

    @DatabaseField
    private String institution_code;

    @DatabaseField
    private String collecttion;

    @DatabaseField
    private int cat_number;

    @DatabaseField
    private String recordBasis;

    // represents the optional recordStatus field in the API
    @DatabaseField
    private String natCul;

    //endregion

    //region GET/SET Methods
    public int getAdu() {
        return adu;
    }

    public void setAdu(int adu) {
        this.adu = adu;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getObservers() {
        return observers;
    }

    public void setObservers(String observers) {
        this.observers = observers;
    }

    public int getNestCount() {
        return nestCount;
    }

    public void setNestCount(int nestCount) {
        this.nestCount = nestCount;
    }

    public String getNestSite() {
        return nestSite;
    }

    public void setNestSite(String nestSite) {
        this.nestSite = nestSite;
    }

    public boolean isRoadKill() {
        return roadKill;
    }

    public void setRoadKill(boolean roadKill) {
        this.roadKill = roadKill;
    }

    public int getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(int taxonID) {
        this.taxonID = taxonID;
    }

    public String getTaxonName() {
        return taxonName;
    }

    public void setTaxonName(String taxonName) {
        this.taxonName = taxonName;
    }

    public String getInstitution_code() {
        return institution_code;
    }

    public void setInstitution_code(String institution_code) {
        this.institution_code = institution_code;
    }

    public String getCollecttion() {
        return collecttion;
    }

    public void setCollecttion(String collecttion) {
        this.collecttion = collecttion;
    }

    public int getCat_number() {
        return cat_number;
    }

    public void setCat_number(int cat_number) {
        this.cat_number = cat_number;
    }

    public String getRecordBasis() {
        return recordBasis;
    }

    public void setRecordBasis(String recordBasis) {
        this.recordBasis = recordBasis;
    }

    public String getNatCul() {
        return natCul;
    }

    public void setNatCul(String natCul) {
        this.natCul = natCul;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    //endregion

    public Record(){

    }

    public Record(Template template){
        this.username = "Paul Sebeikin";
        this.latitude = template.location[0];
        this.longitude = template.location[1];
        this.altitude = template.altitude;
        this.project = template.project;
        this.country = template.country;
        this.province = template.province;
        this.town = template.town;
        this.desc = template.desc;
        this.url = template.images[0] + ";" + template.images[1] + ";" + template.images[2];

        Calendar c = Calendar.getInstance();
        c.setTime(template.dt);
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.month = c.get(Calendar.MONTH);
        this.year = c.get(Calendar.YEAR);
        this.source = template.source;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("adu=").append(adu);
        sb.append(", ").append("username=").append(username);
        sb.append(", ").append("email=").append(email);
        sb.append(", ").append("latitude=").append(latitude);
        sb.append(", ").append("longitude=").append(longitude);
        sb.append(", ").append("altitude=").append(altitude);
        sb.append(", ").append("project=").append(project);
        sb.append(", ").append("country=").append(country);
        sb.append(", ").append("province=").append(province);
        sb.append(", ").append("town=").append(town);
        sb.append(", ").append("desc=").append(desc);
        sb.append(", ").append("url=").append(url);
        sb.append(", ").append("year=").append(year);
        sb.append(", ").append("month=").append(month);
        sb.append(", ").append("day=").append(day);
        sb.append(", ").append("source=").append(source);
        return sb.toString();
    }
}
