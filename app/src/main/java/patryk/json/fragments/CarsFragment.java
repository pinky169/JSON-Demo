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
import androidx.recyclerview.widget.GridLayoutManager;
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
import patryk.json.model.Car;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsFragment extends Fragment implements RecyclerAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private MainActivity activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Car> cars;
    private API api;

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
            cars = (List<Car>) savedInstanceState.getSerializable("cars");
        } else {
            getCars();
        }

        adapter = new RecyclerAdapter(getContext(), cars, R.layout.item_car);
        adapter.setOnItemClickListener(CarsFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // Number of columns depends on the screen orientation -> dimens.xml
        final int columns = getResources().getInteger(R.integer.recyclerview_columns);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columns));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cars", (Serializable) cars);
    }

    private void getCars() {

        activity.showProgress();

        Call<List<Car>> call = api.getCars();

        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {

                if (!response.isSuccessful()) {
                    Toasty.error(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                cars = response.body();
                
                adapter = new RecyclerAdapter(getContext(), cars, R.layout.item_car);
                adapter.setOnItemClickListener(CarsFragment.this);
                recyclerView.setAdapter(adapter);

                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        PartsFragment partsFragment = PartsFragment.newInstance(position);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, partsFragment).addToBackStack(null).commit();
        Toasty.custom(getContext(),
                cars.get(position).getMarka() + " " + cars.get(position).getModel(),
                R.drawable.ic_car,
                R.color.colorAccent,
                Toast.LENGTH_LONG,
                true,
                true)
                .show();
    }

    @Override
    public void onRefresh() {
        getCars();
    }
}
