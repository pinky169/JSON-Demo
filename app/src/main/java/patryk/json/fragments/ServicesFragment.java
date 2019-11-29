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

import java.io.Serializable;
import java.util.List;

import es.dmoral.toasty.Toasty;
import patryk.json.MainActivity;
import patryk.json.R;
import patryk.json.adapters.RecyclerAdapter;
import patryk.json.api.API;
import patryk.json.api.APIClient;
import patryk.json.model.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends Fragment implements RecyclerAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private MainActivity activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Service> services;
    private API api;

    public static ServicesFragment newInstance(String text) {

        ServicesFragment f = new ServicesFragment();
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
            services = (List<Service>) savedInstanceState.getSerializable("services");
        } else {
            getServices();
        }

        adapter = new RecyclerAdapter(getContext(), services, R.layout.item_document);
        adapter.setOnItemClickListener(ServicesFragment.this);
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
        outState.putSerializable("services", (Serializable) services);
    }

    private void getServices() {

        activity.showProgress();

        Call<List<Service>> call = api.getServices();

        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {

                if (!response.isSuccessful()) {
                    Toasty.error(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                services = response.body();

                adapter = new RecyclerAdapter(getContext(), services, R.layout.item_document);
                adapter.setOnItemClickListener(ServicesFragment.this);
                recyclerView.setAdapter(adapter);

                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toasty.normal(getContext(), "Przegląd auta: " + services.get(position).getRegistryNr(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        getServices();
    }
}
