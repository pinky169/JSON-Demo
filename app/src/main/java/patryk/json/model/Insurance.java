package patryk.json.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Insurance {

    @SerializedName("carId")
    @Expose
    private int carId;
    @SerializedName("insuranceId")
    @Expose
    private int insuranceId;
    @SerializedName("policy_nr")
    @Expose
    private String policyNr;
    @SerializedName("additional_info")
    @Expose
    private String additionalInfo;
    @SerializedName("date_from")
    @Expose
    private String dateFrom;
    @SerializedName("date_to")
    @Expose
    private String dateTo;

    /**
     * No args constructor for use in serialization
     */
    public Insurance() {
    }

    /**
     * @param additionalInfo
     * @param dateTo
     * @param insuranceId
     * @param dateFrom
     * @param carId
     * @param policyNr
     */
    public Insurance(int carId, int insuranceId, String policyNr, String additionalInfo, String dateFrom, String dateTo) {
        super();
        this.carId = carId;
        this.insuranceId = insuranceId;
        this.policyNr = policyNr;
        this.additionalInfo = additionalInfo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getPolicyNr() {
        return policyNr;
    }

    public void setPolicyNr(String policyNr) {
        this.policyNr = policyNr;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

}