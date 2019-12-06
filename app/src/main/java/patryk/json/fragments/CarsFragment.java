package patryk.json.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
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
import patryk.json.model.Car;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsFragment extends Fragment implements RecyclerAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private RecyclerAdapter adapter;
    private MainActivity activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Car> cars;
    private List<Car> filteredList;
    private API api;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        APIClient apiClient = new APIClient();
        api = apiClient.getClient();

        if (savedInstanceState != null) {
            cars = savedInstanceState.getParcelableArrayList("cars");
            filteredList = savedInstanceState.getParcelableArrayList("filteredList");
        } else {
            cars = new ArrayList<>();
            getCars();
        }

        adapter = new RecyclerAdapter(getContext(), cars, R.layout.item_car);
        adapter.setHasStableIds(true);
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
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columns));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("cars", (ArrayList) cars);
        outState.putParcelableArrayList("filteredList", (ArrayList) filteredList);
    }

    private void getCars() {

        activity.showProgress();

        Call<List<Car>> call = api.getCars();

        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(@NonNull Call<List<Car>> call, @NonNull Response<List<Car>> response) {

                if (!response.isSuccessful()) {
                    Toasty.error(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                cars = response.body();
                filteredList = new ArrayList<>(cars);
                adapter.updateList(cars);

                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<Car>> call, @NonNull Throwable t) {
                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {

        // This handles situation when you filtered recyclerview and then clicked item
        int id = cars.indexOf(filteredList.get(position));

        // Open fragment with parts for selected car
        PartsFragment partsFragment = PartsFragment.newInstance(id);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, partsFragment, "PartsDetailsFragment").commit();

        Toasty.custom(getContext(),
                "Części auta\n" + cars.get(id).getMarka() + " " + cars.get(id).getModel(),
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint(getString(R.string.action_search_hint));
        searchView.setOnQueryTextListener(this);
    }

    private void filter(String newText) {

        filteredList = new ArrayList<>();

        if (newText == null || newText.length() == 0) {
            filteredList.addAll(cars);
        } else {

            String pattern = newText.toLowerCase().trim();

            for (Car car : cars) {
                if (car.getMarka().toLowerCase().contains(pattern) ||
                        car.getModel().toLowerCase().contains(pattern) ||
                        car.getRokProdukcji().contains(pattern) ||
                        car.getMoc().contains(pattern) ||
                        car.getPojemnosc().contains(pattern)) {
                    filteredList.add(car);
                }
            }
        }

        adapter.updateList(filteredList);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return true;
    }
}
