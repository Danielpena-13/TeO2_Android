package com.daniel13pe.navdrw_java.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daniel13pe.navdrw_java.DetailPredioActivity;
import com.daniel13pe.navdrw_java.MainActivity;
import com.daniel13pe.navdrw_java.R;
import com.daniel13pe.navdrw_java.adapters.CustomAdapter;
import com.daniel13pe.navdrw_java.databinding.FragmentHomeBinding;
import com.daniel13pe.navdrw_java.entities.Predio;
import com.daniel13pe.navdrw_java.network.APIclient;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    //FragmenBinding instance
    private FragmentHomeBinding binding;

    private GridView gridView;
    private static List<Predio> predioList;
    private ProgressDialog progressDialog;
    private CustomAdapter customAdapter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Bind Enlace GridView
        gridView = binding.gridView;
        predioList = new ArrayList<>();

        //Make Network call
        Call<List<Predio>> call = APIclient.apIinterface().getPredio();
        call.enqueue(new Callback<List<Predio>>() {
            @Override
            public void onResponse(Call<List<Predio>> call, Response<List<Predio>> response) {
                if(response.isSuccessful()){
                    //LoadingDialog
                    progressDialog.dismiss();

                    //Retreving body data from GET request
                    predioList = response.body();
                    customAdapter = new CustomAdapter(predioList, getActivity());
                    gridView.setAdapter(customAdapter);

                    //Action when GridItem is Clicked.
                    onClickItemGrid();
                }else {
                    Toast.makeText(getContext(), "An error occured", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Predio>> call, Throwable t) {
                Toast.makeText(getContext(), "An error occured "+ t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void onClickItemGrid() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailPredioActivity.class);
                intent.putExtra("NombrePredio", predioList.get(position).getTitle());
                intent.putExtra("ImgPredio",predioList.get(position).getThumbnailUrl());
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
            }
        });
    }

    private void loadingDialog(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.loading_alertdialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loadingDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
