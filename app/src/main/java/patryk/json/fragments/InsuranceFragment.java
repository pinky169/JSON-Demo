package patryk.json.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import patryk.json.R;
import patryk.json.activity.MainActivity;
import patryk.json.adapters.RecyclerAdapter;
import patryk.json.api.API;
import patryk.json.api.APIClient;
import patryk.json.model.Insurance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsuranceFragment extends Fragment implements RecyclerAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private MainActivity activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Insurance> insurances;
    private API api;

    public static InsuranceFragment newInstance(String text) {

        InsuranceFragment f = new InsuranceFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APIClient apiClient = new APIClient();
        api = apiClient.getClient();

        if (savedInstanceState != null) {
            insurances = savedInstanceState.getParcelableArrayList("insurance");
        } else {
            insurances = new ArrayList<>();
            getInsurance();
        }

        adapter = new RecyclerAdapter(getContext(), insurances, R.layout.item_document);
        adapter.setOnItemClickListener(InsuranceFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("insurance", (ArrayList) insurances);
    }

    private void getInsurance() {

        activity.showProgress();

        Call<List<Insurance>> call = api.getInsurance();

        call.enqueue(new Callback<List<Insurance>>() {
            @Override
            public void onResponse(@NonNull Call<List<Insurance>> call, @NonNull Response<List<Insurance>> response) {

                if (!response.isSuccessful()) {
                    Toasty.error(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                insurances = response.body();
                adapter.updateList(insurances);

                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Insurance>> call, @NonNull Throwable t) {
                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toasty.normal(getContext(), "Polisa: " + insurances.get(position).getPolicyNr(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        getInsurance();
    }
}
