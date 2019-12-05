package patryk.json.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Insurance implements Parcelable {

    public static final Creator<Insurance> CREATOR = new Creator<Insurance>() {
        @Override
        public Insurance createFromParcel(Parcel in) {
            return new Insurance(in);
        }

        @Override
        public Insurance[] newArray(int size) {
            return new Insurance[size];
        }
    };
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
     * @param additionalInfo dodatkowe informacje
     * @param dateTo         data waznosci ubezpieczenia
     * @param insuranceId    id ubezpieczenia
     * @param dateFrom       od kiedy obowiazuje ubezpieczenie
     * @param carId          id auta do ktorego nalezy ubezpieczenie
     * @param policyNr       numer polisy ubezpieczeniowej
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

    protected Insurance(Parcel in) {
        carId = in.readInt();
        insuranceId = in.readInt();
        policyNr = in.readString();
        additionalInfo = in.readString();
        dateFrom = in.readString();
        dateTo = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(carId);
        dest.writeInt(insuranceId);
        dest.writeString(policyNr);
        dest.writeString(additionalInfo);
        dest.writeString(dateFrom);
        dest.writeString(dateTo);
    }
}