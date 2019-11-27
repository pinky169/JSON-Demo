package patryk.json.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import es.dmoral.toasty.Toasty;
import patryk.json.R;
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
    private ProgressBar progressBar;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APIClient apiClient = new APIClient();
        api = apiClient.getClient();

        adapter = new RecyclerAdapter(getContext(), insurances, R.layout.item_document);

        getInsurance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void getInsurance() {

        Call<List<Insurance>> call = api.getInsurance();

        call.enqueue(new Callback<List<Insurance>>() {
            @Override
            public void onResponse(Call<List<Insurance>> call, Response<List<Insurance>> response) {

                if (!response.isSuccessful()) {
                    Toasty.error(getContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                insurances = response.body();

                adapter = new RecyclerAdapter(getContext(), insurances, R.layout.item_document);
                adapter.setOnItemClickListener(InsuranceFragment.this);
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Insurance>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                Toasty.error(getContext(), "Code: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toasty.info(getContext(), "Polisa: " + insurances.get(position).getPolicyNr(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        getInsurance();
    }
}
