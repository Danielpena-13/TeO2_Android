package com.daniel13pe.navdrw_java.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daniel13pe.navdrw_java.R;
import com.daniel13pe.navdrw_java.entities.Imagenes;
import com.daniel13pe.navdrw_java.entities.Predio;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter implements Filterable {
    private List<Predio> predioList;
    private List<Predio> predioListFiltered;
    private Context context;

    public CustomAdapter(List<Predio> predioList, Context context) {
        this.predioList = predioList;
        this.predioListFiltered = predioList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return predioListFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_predios, parent, false);
        //FindViews
        TextView nombreCampesino = view.findViewById(R.id.tx_nombreCampesino);
        ImageView imageCampesino = view.findViewById(R.id.img_persona);
        TextView idImage = view.findViewById(R.id.tx_id);
        //Set Data
        nombreCampesino.setText(predioListFiltered.get(position).getTitle());
        idImage.setText(predioListFiltered.get(position).getImagenes().get(0).getNombreImage());
        //Glide ImageView
        Glide.with(context)
                .load(predioListFiltered.get(position).getThumbnailUrl())
                .into(imageCampesino);
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.count = predioList.size();
                    filterResults.values = predioList;
                } else {
                    String searchString = charSequence.toString().toLowerCase();
                    List<Predio> resultData = new ArrayList<>();
                    for (Predio predio : predioList) {
                        if (predio.getTitle().toLowerCase().contains(searchString)) {
                            resultData.add(predio);
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                predioListFiltered = (List<Predio>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
