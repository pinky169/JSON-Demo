package patryk.json.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service implements Parcelable {

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
    @SerializedName("carId")
    @Expose
    private int carId;
    @SerializedName("serviceId")
    @Expose
    private int serviceId;
    @SerializedName("registry_nr")
    @Expose
    private String registryNr;
    @SerializedName("mileage")
    @Expose
    private String mileage;
    @SerializedName("date_from")
    @Expose
    private String dateFrom;
    @SerializedName("date_to")
    @Expose
    private String dateTo;

    /**
     * No args constructor for use in serialization
     */
    public Service() {
    }

    /**
     * @param registryNr numer rejestracyjny auta
     * @param dateTo     data waznosci przegladu
     * @param serviceId  id przegladu
     * @param dateFrom   data wykonania przegladu technicznego
     * @param carId      id auta dla danego przegladu
     * @param mileage    przebieg podczas badania technicznego
     */
    public Service(int carId, int serviceId, String registryNr, String mileage, String dateFrom, String dateTo) {
        super();
        this.carId = carId;
        this.serviceId = serviceId;
        this.registryNr = registryNr;
        this.mileage = mileage;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    protected Service(Parcel in) {
        carId = in.readInt();
        serviceId = in.readInt();
        registryNr = in.readString();
        mileage = in.readString();
        dateFrom = in.readString();
        dateTo = in.readString();
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getRegistryNr() {
        return registryNr;
    }

    public void setRegistryNr(String registryNr) {
        this.registryNr = registryNr;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
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
        dest.writeInt(serviceId);
        dest.writeString(registryNr);
        dest.writeString(mileage);
        dest.writeString(dateFrom);
        dest.writeString(dateTo);
    }
}