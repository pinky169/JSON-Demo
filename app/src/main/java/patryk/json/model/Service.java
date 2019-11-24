package patryk.json.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

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
     *
     */
    public Service() {
    }

    /**
     *
     * @param registryNr
     * @param dateTo
     * @param serviceId
     * @param dateFrom
     * @param carId
     * @param mileage
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

}