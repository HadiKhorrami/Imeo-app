package com.example.imeo_app.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imeo_app.DataModels.ExtMineralMaterialLayout;
import com.example.imeo_app.DataModels.MineralMaterialLayout;
import com.example.imeo_app.R;

import java.util.ArrayList;

public class ExtMineralMaterialRecyclerAdapter extends RecyclerView.Adapter<ExtMineralMaterialRecyclerAdapter.MineralmaterialViewHolder> {
    ArrayList<MineralMaterialLayout> mineralMaterialLayoutsArrayList;
    RecyclerView recyclerView;
    private Context context;
    private int lastPosition = -1;


    public ExtMineralMaterialRecyclerAdapter(Context context, ArrayList<MineralMaterialLayout> arrayList) {
        mineralMaterialLayoutsArrayList = new ArrayList<MineralMaterialLayout>();
        mineralMaterialLayoutsArrayList = arrayList;
    }

    @NonNull
    @Override
    public MineralmaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ext_mineral_material_layout, parent, false);

        context = parent.getContext();
        return new MineralmaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MineralmaterialViewHolder holder, final int position) {
        final MineralMaterialLayout dataModel =  mineralMaterialLayoutsArrayList.get(position);
        SharedPreferences shared = context.getSharedPreferences("repId", MODE_PRIVATE);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < holder.mainConsLayout.getChildCount(); i++) {
                View child = holder.mainConsLayout.getChildAt(i);
                child.setEnabled(false);
            }
        }
        holder.txtType.setText(dataModel.getType_());
        holder.txtMinePlace.setText(dataModel.getMineplace());
        holder.txtDensity.setText(dataModel.getDensity());
        holder.txtColor.setText(dataModel.getColor());
        holder.txtUseCase.setText(dataModel.getUsecase());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("editMineralMaterial");
                intent.putExtra("mineralMaterialId" , dataModel.getMineralmaterialid());
                intent.putExtra("mineStage" , "1");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(context);
                infoBuilder.setMessage("میخواهید حذف کنید؟؟")
                        .setPositiveButton("بله", (dialog, id) ->deleteMineralMaterial(dataModel.getId(),dataModel.getMineralmaterialid(),mineralMaterialLayoutsArrayList,holder)).
                        setNegativeButton("خیر", (dialog, id) -> infoBuilder.create().dismiss());
                infoBuilder.create().show();
            }
        });
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return  mineralMaterialLayoutsArrayList.size();
    }

    public class MineralmaterialViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton btnShowDetail;
        TextView txtType, txtMinePlace, txtDensity,txtColor,txtUseCase;
        AppCompatButton btnEdit,btnDelete;
        public int id;
        ConstraintLayout mainConsLayout;
        public MineralmaterialViewHolder(View itemView) {
            super(itemView);
            btnShowDetail = (AppCompatButton) itemView.findViewById(R.id.btnEdit);
            txtType = (TextView) itemView.findViewById(R.id.txtSheklekansar);
            txtMinePlace = (TextView) itemView.findViewById(R.id.txtZone);
            txtDensity = (TextView) itemView.findViewById(R.id.txtVahed);
            txtColor = (TextView) itemView.findViewById(R.id.txtKanihayeasli);
            txtUseCase = (TextView) itemView.findViewById(R.id.txtSangemizban);
            btnEdit = (AppCompatButton) itemView.findViewById(R.id.btnEdit);
            btnDelete = (AppCompatButton) itemView.findViewById(R.id.btnDelete);
            mainConsLayout = (ConstraintLayout) itemView.findViewById(R.id.mainConsLayout);
        }
    }
    public void deleteMineralMaterial(int id,long mineralMaterialId, ArrayList<MineralMaterialLayout> mineralMaterialLayoutsArrayList, MineralmaterialViewHolder holder){
        mineralMaterialLayoutsArrayList.remove(holder.getAdapterPosition());  // remove the item from list
        notifyItemRemoved(Integer.parseInt(String.valueOf(id)));
        Intent intent = new Intent("deleteMineralMaterial");
        intent.putExtra("mineralMaterialId" , mineralMaterialId);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
