package com.daniel13pe.navdrw_java.ui.predios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daniel13pe.navdrw_java.R;
import com.daniel13pe.navdrw_java.adapters.CustomAdapter;
import com.daniel13pe.navdrw_java.databinding.FragmentPrediosBinding;
import com.daniel13pe.navdrw_java.entities.Predio;
import com.daniel13pe.navdrw_java.network.APIclient;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrediosFragment extends Fragment {

    //FragmenBinding instance
    private FragmentPrediosBinding binding;

    //RecyclerView
    private RecyclerView recyclerView;
    private static List<Predio> predioList;
    //Others
    private ProgressDialog progressDialog;
    private CustomAdapter customAdapter;
    private NotificationBadge badge;
    int i = 0;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             Bundle savedInstanceState) {
        //Fragment Permission to participate in menu's Action
        setHasOptionsMenu(true);
        //binding declaration
        binding = FragmentPrediosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Bind Enlace RecyclerView
        recyclerView = binding.recyclerpredios;
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
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    recyclerView.setAdapter(customAdapter);

                }else {
                    Toast.makeText(getContext(), "An error occured", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Predio>> call, Throwable t) {
                //Bug #1 Rotation and NoConnetion
                Toast.makeText(requireActivity(), "An error occured "+ t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.toolbar_menu, menu);
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


    private void loadingDialog(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.loading_alertdialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
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
