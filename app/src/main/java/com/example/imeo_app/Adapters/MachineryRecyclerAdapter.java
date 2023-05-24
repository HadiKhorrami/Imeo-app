package com.example.imeo_app.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imeo_app.DataModels.AMineOperationLayout;
import com.example.imeo_app.DataModels.MachineryLayout;
import com.example.imeo_app.R;
import com.github.florent37.expansionpanel.ExpansionHeader;

import java.util.ArrayList;

public class MachineryRecyclerAdapter extends RecyclerView.Adapter<MachineryRecyclerAdapter.MachineryExpansionViewHolder> {
    ArrayList<MachineryLayout> machineryLayoutsArrayList;
    RecyclerView recyclerView;
    Dialog dialog;
    private Context context;
    private int lastPosition = -1;


    public MachineryRecyclerAdapter(Context context, ArrayList<MachineryLayout> arrayList) {
        machineryLayoutsArrayList = new ArrayList<MachineryLayout>();
        machineryLayoutsArrayList = arrayList;
    }


    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    @NonNull
    @Override
    public MachineryExpansionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.machinery_layout, parent, false);
        context = parent.getContext();
        return new MachineryExpansionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MachineryExpansionViewHolder holder, final int position) {
        final MachineryLayout dataModel = machineryLayoutsArrayList.get(position);
        SharedPreferences shared = context.getSharedPreferences("repId", MODE_PRIVATE);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < holder.mainConsLayout.getChildCount(); i++) {
                View child = holder.mainConsLayout.getChildAt(i);
                child.setEnabled(false);
            }
        }
        holder.txtNameMashin.setText(dataModel.getNamemashin());
        holder.txtModeleMashin.setText(dataModel.getModelemashin());
        holder.txtRuzekari.setText(String.valueOf(dataModel.getRuzekari()));
        holder.txtEquipment.setText(dataModel.getEquipment());
        holder.txtMasrafeSookht.setText(String.valueOf(dataModel.getMasrafesookht()));
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("editMachinery");
                intent.putExtra("machineryId" , dataModel.getMachineryid());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(context);
                infoBuilder.setMessage("میخواهید حذف کنید؟؟")
                        .setPositiveButton("بله", (dialog, id) ->deleteMineFront(dataModel.getId(),dataModel.getMachineryid(),machineryLayoutsArrayList,holder)).
                        setNegativeButton("خیر", (dialog, id) -> infoBuilder.create().dismiss());
                infoBuilder.create().show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return machineryLayoutsArrayList.size();
    }


    public class MachineryExpansionViewHolder extends RecyclerView.ViewHolder {
        public AppCompatButton btnReviewList,btnShowOnMap,btnRouting;
        public ExpansionHeader expansionHeader;
        TextView txtNameMashin,txtModeleMashin,txtEquipment,txtRuzekari,txtMasrafeSookht;
        AppCompatButton btnEdit,btnDelete;
        public int id;
        ConstraintLayout mainConsLayout;
        public MachineryExpansionViewHolder(View itemView) {
            super(itemView);
            txtNameMashin = (TextView)itemView.findViewById(R.id.txtNameMashin);
            txtModeleMashin = (TextView)itemView.findViewById(R.id.txtModeleMashin);
            txtEquipment = (TextView)itemView.findViewById(R.id.txtEquipment);
            txtRuzekari = (TextView)itemView.findViewById(R.id.txtRuzekari);
            txtMasrafeSookht = (TextView)itemView.findViewById(R.id.txtMasrafeSookht);
            btnEdit = (AppCompatButton)itemView.findViewById(R.id.btnEdit);
            btnDelete = (AppCompatButton)itemView.findViewById(R.id.btnDelete);
            mainConsLayout = (ConstraintLayout)itemView.findViewById(R.id.mainConsLayout);
        }
    }
    public void deleteMineFront(int id, long machineryId, ArrayList<MachineryLayout> machineryLayouts, MachineryRecyclerAdapter.MachineryExpansionViewHolder holder){
        machineryLayouts.remove(holder.getAdapterPosition());  // remove the item from list
        notifyItemRemoved(Integer.parseInt(String.valueOf(id)));
        Intent intent = new Intent("deleteMachinery");
        intent.putExtra("machineryId" , machineryId);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
