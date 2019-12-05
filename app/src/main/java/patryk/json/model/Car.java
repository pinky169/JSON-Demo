package patryk.json.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Car implements Parcelable {

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };
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

    protected Car(Parcel in) {
        id = in.readInt();
        marka = in.readString();
        model = in.readString();
        rokProdukcji = in.readString();
        pojemnosc = in.readString();
        moc = in.readString();
        image = in.readString();
        isMainCar = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(marka);
        dest.writeString(model);
        dest.writeString(rokProdukcji);
        dest.writeString(pojemnosc);
        dest.writeString(moc);
        dest.writeString(image);
        dest.writeInt(isMainCar);
    }
}