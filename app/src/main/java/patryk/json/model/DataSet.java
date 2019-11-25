package patryk.json.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSet {

    @SerializedName("cars")
    @Expose
    private List<Car> cars = null;
    @SerializedName("parts")
    @Expose
    private List<Part> parts = null;
    @SerializedName("insurance")
    @Expose
    private List<Insurance> insurance = null;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;

    /**
     * No args constructor for use in serialization
     */
    public DataSet() {
    }

    /**
     * @param insurance
     * @param cars
     * @param parts
     * @param services
     */
    public DataSet(List<Car> cars, List<Part> parts, List<Insurance> insurance, List<Service> services) {
        super();
        this.cars = cars;
        this.parts = parts;
        this.insurance = insurance;
        this.services = services;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public List<Insurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(List<Insurance> insurance) {
        this.insurance = insurance;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}