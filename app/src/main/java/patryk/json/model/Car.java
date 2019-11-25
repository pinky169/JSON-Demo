package patryk.json.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("marka")
    @Expose
    private String marka;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("rok_produkcji")
    @Expose
    private String rokProdukcji;
    @SerializedName("pojemnosc")
    @Expose
    private String pojemnosc;
    @SerializedName("moc")
    @Expose
    private String moc;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("isMainCar")
    @Expose
    private int isMainCar;

    /**
     * No args constructor for use in serialization
     */
    public Car() {
    }

    /**
     * @param pojemnosc
     * @param moc
     * @param image
     * @param isMainCar
     * @param marka
     * @param model
     * @param id
     * @param rokProdukcji
     */
    public Car(int id, String marka, String model, String rokProdukcji, String pojemnosc, String moc, String image, int isMainCar) {
        super();
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rokProdukcji;
        this.pojemnosc = pojemnosc;
        this.moc = moc;
        this.image = image;
        this.isMainCar = isMainCar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRokProdukcji() {
        return rokProdukcji;
    }

    public void setRokProdukcji(String rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

    public String getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(String pojemnosc) {
        this.pojemnosc = pojemnosc;
    }

    public String getMoc() {
        return moc;
    }

    public void setMoc(String moc) {
        this.moc = moc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsMainCar() {
        return isMainCar;
    }

    public void setIsMainCar(int isMainCar) {
        this.isMainCar = isMainCar;
    }

}