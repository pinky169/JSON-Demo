package patryk.json.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Car implements Serializable {

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
     * @param pojemnosc    pojemnosc auta
     * @param moc          moc auta
     * @param image        zdjecie auta
     * @param isMainCar    czy jest glownym autem
     * @param marka        marka auta
     * @param model        model auta
     * @param id           id auta
     * @param rokProdukcji rok produkcji auta
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