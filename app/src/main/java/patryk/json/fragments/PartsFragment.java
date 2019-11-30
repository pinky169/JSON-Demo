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
import patryk.json.model.Part;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartsFragment extends Fragment implements RecyclerAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_NUMBER = "position";
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private MainActivity activity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Part> parts;
    private API api;

    public static PartsFragment newInstance(int position) {

        PartsFragment f = new PartsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_NUMBER, position);

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
            parts = (List<Part>) savedInstanceState.getSerializable("parts");
        } else if (getArguments() != null) {
            int clickedItemPosition = getArguments().getInt("position") + 1;
            getCarParts(clickedItemPosition);
        } else {
            getParts();
        }

        adapter = new RecyclerAdapter(getContext(), parts, R.layout.item_part);
        adapter.setOnItemClickListener(PartsFragment.this);
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
        outState.putSerializable("parts", (Serializable) parts);
    }

    private void getParts() {

        activity.showProgress();

        Call<List<Part>> call = api.getParts();

        call.enqueue(new Callback<List<Part>>() {
            @Override
            public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {

                if (!response.isSuccessful()) {
                    Toasty.error(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                parts = response.body();

                adapter = new RecyclerAdapter(getContext(), parts, R.layout.item_part);
                adapter.setOnItemClickListener(PartsFragment.this);
                recyclerView.setAdapter(adapter);

                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Part>> call, Throwable t) {
                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCarParts(int id) {

        activity.showProgress();

        Call<List<Part>> call = api.getCarParts(id);

        call.enqueue(new Callback<List<Part>>() {
            @Override
            public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {

                if (!response.isSuccessful()) {
                    Toasty.error(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                parts = response.body();
                adapter = new RecyclerAdapter(getContext(), parts, R.layout.item_part);
                adapter.setOnItemClickListener(PartsFragment.this);
                recyclerView.setAdapter(adapter);

                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Part>> call, Throwable t) {
                activity.hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toasty.info(getContext(), parts.get(position).getPartName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        getParts();
    }
}
