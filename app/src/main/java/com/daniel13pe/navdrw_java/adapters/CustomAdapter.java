package com.daniel13pe.navdrw_java.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daniel13pe.navdrw_java.DetailPredioActivity;
import com.daniel13pe.navdrw_java.R;
import com.daniel13pe.navdrw_java.entities.Predio;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private List<Predio> predioList;
    private List<Predio> predioListFiltered;
    private Context context;
    private Activity activity = (Activity) context;
    ActivityOptions activityOptions;

    public CustomAdapter(List<Predio> predioList, Context context) {
        this.predioList = predioList;
        this.predioListFiltered = predioList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_predios, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.nombrePredio.setText(predioListFiltered.get(position).getNombrePredio());
        holder.entidad.setText(predioListFiltered.get(position).getEntidad());
        //Glide Load ImageView
        Glide.with(context)
               .load(predioListFiltered.get(position).getImagenes().get(0).getDireccionBucket())
                .into(holder.imageCampesino);

        //Onclick Action
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailPredioActivity.class);
                intent.putExtra("NombrePredio", predioList.get(position).getNombrePredio());
                intent.putExtra("ImgCampesino",predioList.get(position).getImagenes().get(0).getDireccionBucket());
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(holder.imageCampesino, "ImageTransition");
                pairs[1] = new Pair<View, String>(holder.nombrePredio, "NameTransition");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
                }
                context.startActivity(intent, activityOptions.toBundle());
            }
        });

        //OnLongClick Action
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Creating Bottom Sheet Dialog
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(context)
                        .inflate(R.layout.bottom_sheet_dialog_detail,
                                (LinearLayout)view.findViewById(R.id.linear_bottom_sheet));

                //FindViews assign variables
                ImageView imageView = bottomSheetView.findViewById(R.id.img_campesino_sheet_dialog);
                TextView textView = bottomSheetView.findViewById(R.id.tx_nombrePredio_sheet_dialog);

                Glide.with(context)
                        .load(predioList.get(position).getImagenes().get(0).getDireccionBucket())
                        .into(imageView);
                textView.setText(predioList.get(position).getNombrePredio());

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                bottomSheetDialog.show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return predioListFiltered.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombrePredio;
        ImageView imageCampesino;
        TextView entidad;
        CardView cardView;

       public MyViewHolder(@NonNull View itemView) {
           super(itemView);

           nombrePredio = (TextView) itemView.findViewById(R.id.tx_nombrePredio);
           imageCampesino = (ImageView) itemView.findViewById(R.id.img_persona);
           entidad = (TextView) itemView.findViewById(R.id.tx_entidad);
           cardView = (CardView) itemView.findViewById(R.id.cardView);
       }
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
                        if (predio.getNombrePredio().toLowerCase().contains(searchString)) {
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
