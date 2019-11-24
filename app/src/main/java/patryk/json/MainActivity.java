package patryk.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import patryk.json.api.API;
import patryk.json.model.Car;
import patryk.json.model.Insurance;
import patryk.json.model.Part;
import patryk.json.model.Service;
import patryk.json.model.Vehicle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public TextView textView;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.test);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);

        getCars();
        //getParts();
        //getInsurance();
        //getServices();
    }

    public void getCars() {

        Call<List<Car>> call = api.getCars();

        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }

                List<Car> cars = response.body();

                for (Car car : cars) {
                    String content = "";
                    content += "ID: " + car.getId() + "\n";
                    content += "Marka: " + car.getMarka() + "\n";
                    content += "Model: " + car.getModel() + "\n";
                    content += "Rok produkcji: " + car.getRokProdukcji() + "\n";
                    content += "Pojemność: " + car.getPojemnosc() + "\n";
                    content += "Moc: " + car.getMoc() + "\n";
                    content += "URL Obrazka: " + car.getImage() + "\n";
                    content += "Czy główne: " + car.getIsMainCar() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void getParts() {

        Call<List<Part>> call = api.getParts();

        call.enqueue(new Callback<List<Part>>() {
            @Override
            public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }

                List<Part> parts = response.body();

                for (Part part : parts) {
                    String content = "";
                    content += "ID auta: " + part.getCarId() + "\n";
                    content += "ID części: " + part.getPartId() + "\n";
                    content += "Nazwa części: " + part.getPartName() + "\n";
                    content += "Dodatkowe: " + part.getAdditionalInfo() + "\n";
                    content += "Data wymiany: " + part.getDate() + "\n";
                    content += "Cena części: " + part.getPrice() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Part>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void getInsurance() {

        Call<List<Insurance>> call = api.getInsurance();

        call.enqueue(new Callback<List<Insurance>>() {
            @Override
            public void onResponse(Call<List<Insurance>> call, Response<List<Insurance>> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }

                List<Insurance> insurances = response.body();

                for (Insurance insurance : insurances) {
                    String content = "";
                    content += "ID auta: " + insurance.getCarId() + "\n";
                    content += "ID ubezpieczenia: " + insurance.getInsuranceId() + "\n";
                    content += "Numer polisy: " + insurance.getPolicyNr() + "\n";
                    content += "Dodatkowe: " + insurance.getAdditionalInfo() + "\n";
                    content += "Ważne od: " + insurance.getDateFrom() + "\n";
                    content += "Ważne do: " + insurance.getDateTo() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Insurance>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void getServices() {

        Call<List<Service>> call = api.getServices();

        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {

                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }

                List<Service> services = response.body();

                for (Service service : services) {
                    String content = "";
                    content += "ID auta: " + service.getCarId() + "\n";
                    content += "ID przeglądu: " + service.getServiceId() + "\n";
                    content += "Numer rejestracyjny: " + service.getRegistryNr() + "\n";
                    content += "Przebieg podczas badania: " + service.getMileage() + "\n";
                    content += "Ważny od: " + service.getDateFrom() + "\n";
                    content += "Ważny do: " + service.getDateTo() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
