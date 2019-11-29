package patryk.json.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Part implements Serializable {

    @SerializedName("carId")
    @Expose
    private int carId;
    @SerializedName("partId")
    @Expose
    private int partId;
    @SerializedName("part_name")
    @Expose
    private String partName;
    @SerializedName("additional_info")
    @Expose
    private String additionalInfo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("price")
    @Expose
    private String price;

    /**
     * No args constructor for use in serialization
     */
    public Part() {
    }

    /**
     * @param date           data wymiany
     * @param partId         id czesci
     * @param price          cena czesci
     * @param additionalInfo dodatkowe informacje
     * @param partName       nazwa wymienionej czesci
     * @param carId          id auta w ktorym byla wymieniona dana czesc
     */
    public Part(int carId, int partId, String partName, String additionalInfo, String date, String price) {
        super();
        this.carId = carId;
        this.partId = partId;
        this.partName = partName;
        this.additionalInfo = additionalInfo;
        this.date = date;
        this.price = price;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}