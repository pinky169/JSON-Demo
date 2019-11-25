package patryk.json.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import patryk.json.R;
import patryk.json.adapters.RecyclerAdapter;
import patryk.json.api.API;
import patryk.json.api.APIClient;
import patryk.json.model.Part;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartsFragment extends Fragment implements RecyclerAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ProgressBar progressBar;
    private List<Part> parts;
    private API api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APIClient apiClient = new APIClient();
        api = apiClient.getClient();

        adapter = new RecyclerAdapter(getContext(), parts, R.layout.item_part);

        getParts();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void getParts() {

        Call<List<Part>> call = api.getParts();

        call.enqueue(new Callback<List<Part>>() {
            @Override
            public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                parts = response.body();
                adapter = new RecyclerAdapter(getContext(), parts, R.layout.item_part);
                adapter.setOnItemClickListener(PartsFragment.this);
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Part>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Code: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Kliknieto pozycje: " + position, Toast.LENGTH_LONG).show();
    }

    // Dodawanie nowej części dla wybranego auta
    public void addDialog() {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_add_part, null);

        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        alertDialogBuilderUserInput.setView(view);

        final EditText partNew = view.findViewById(R.id.dialog_new_part_input);
        final EditText partAdditionalInfo = view.findViewById(R.id.dialog_additional_info_input);
        final EditText partReplacementDate = view.findViewById(R.id.dialog_replacement_date_input);
        final EditText partPrice = view.findViewById(R.id.dialog_part_price_input);

        // Ustawiam proponowaną datę jako dzisiejszą w EditText
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateString = getResources().getString(R.string.date_hint, dateFormat.format(currentDate));
        partReplacementDate.setText(dateString);

        // Adapter spinnera wypełniany jest markami aut z listy allCars
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, allCars);
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner z markami aut
//        chooseCarSpinner = view.findViewById(R.id.choose_car_spinner_id);
//        chooseCarSpinner.setAdapter(spinnerAdapter);
//        chooseCarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                // Pozycja wybranego elementu na spinnerze
//                spinnerSelectedItemPosition = chooseCarSpinner.getSelectedItemPosition();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//                Toast.makeText(getContext(), "Musisz wybrać auto!", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("DODAJ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        // Wyświetl wiadomość jeżeli któreś pole jest puste
                        if (TextUtils.isEmpty(partNew.getText().toString()) ||
                                TextUtils.isEmpty(partReplacementDate.getText().toString()) ||
                                TextUtils.isEmpty(partPrice.getText().toString())) {
//                                chooseCarSpinner.getSelectedItem() == null) {
                            Toast.makeText(getContext(), "Musisz wypełnić każde pole oraz wybrać auto!", Toast.LENGTH_SHORT).show();
                        } else {
                        }
                    }
                })
                .setNegativeButton("ANULUJ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
    }

    // Edytowanie danych auta o podanym id
    public void editDialog(final int id) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_add_part, null);

        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        alertDialogBuilderUserInput.setView(view);

        final EditText partName = view.findViewById(R.id.dialog_new_part_input);
        final EditText partAdditionalInfo = view.findViewById(R.id.dialog_additional_info_input);
        final EditText partReplacementDate = view.findViewById(R.id.dialog_replacement_date_input);
        final EditText partPrice = view.findViewById(R.id.dialog_part_price_input);

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("ZAPISZ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int dialogID) {

                        // Wyświetl wiadomość jeżeli któreś pole jest puste
                        if (TextUtils.isEmpty(partName.getText().toString()) ||
                                TextUtils.isEmpty(partAdditionalInfo.getText().toString()) ||
                                TextUtils.isEmpty(partReplacementDate.getText().toString()) ||
                                TextUtils.isEmpty(partPrice.getText().toString())) {
                            Toast.makeText(getContext(), "Musisz wypełnić każde pole!", Toast.LENGTH_SHORT).show();
                        } else {
                        }
                    }
                })
                .setNegativeButton("ANULUJ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        })
                .setNeutralButton("USUŃ",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
    }
}
