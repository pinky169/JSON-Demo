package patryk.json.api;

import java.util.List;

import patryk.json.model.Car;
import patryk.json.model.DataSet;
import patryk.json.model.Insurance;
import patryk.json.model.Part;
import patryk.json.model.Service;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface API {

    @GET
    Call<List<DataSet>> getVehicles(@Url String url);

    @GET("cars")
    Call<List<Car>> getCars();

    @GET("parts")
    Call<List<Part>> getParts();

    @GET("insurance")
    Call<List<Insurance>> getInsurance();

    @GET("services")
    Call<List<Service>> getServices();

    @GET("cars")
    Call<List<Car>> getMainCar(@Query("isMainCar") int isMainCar);
}
