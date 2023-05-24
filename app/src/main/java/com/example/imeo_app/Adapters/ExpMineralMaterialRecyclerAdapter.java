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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imeo_app.DataModels.MineralMaterialLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MineralmaterialLocalServiceUtil;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.github.florent37.expansionpanel.ExpansionHeader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExpMineralMaterialRecyclerAdapter extends RecyclerView.Adapter<ExpMineralMaterialRecyclerAdapter.MineralmaterialViewHolder> {
    ArrayList<MineralMaterialLayout> mineralMaterialLayoutsArrayList;
    RecyclerView recyclerView;
    Dialog dialog;
    JSONObject mineInfo;
    private Context context;
    private int lastPosition = -1;


    public ExpMineralMaterialRecyclerAdapter(Context context, ArrayList<MineralMaterialLayout> arrayList) {
        mineralMaterialLayoutsArrayList = new ArrayList<MineralMaterialLayout>();
        mineralMaterialLayoutsArrayList = arrayList;
    }


    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    @NonNull
    @Override
    public MineralmaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exp_mineral_material_layout, parent, false);
        context = parent.getContext();
        return new MineralmaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MineralmaterialViewHolder holder, final int position) {
        final MineralMaterialLayout dataModel = mineralMaterialLayoutsArrayList.get(position);
        mineInfo = new JSONObject();
        SharedPreferences shared = context.getSharedPreferences("repId", MODE_PRIVATE);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < holder.mainConsLayout.getChildCount(); i++) {
                View child = holder.mainConsLayout.getChildAt(i);
                child.setEnabled(false);
            }
        }

        holder.txtSheklekansar.setText(dataModel.getSheklekansar());
        holder.txtKanihayeasli.setText(dataModel.getKanihayeasli());
        holder.txtZone.setText(dataModel.getZonesakhtari());
        holder.txtSangemizban.setText(dataModel.getSangemizban());
        holder.txtVahed.setText(dataModel.getVahedhayesangshenasieasli());
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

    @Override
    public int getItemCount() {
        return mineralMaterialLayoutsArrayList.size();
    }

    public class MineralmaterialViewHolder extends RecyclerView.ViewHolder {
        public AppCompatButton btnReviewList,btnShowOnMap,btnRouting;
        public ExpansionHeader expansionHeader;
        TextView txtSheklekansar,txtKanihayeasli,txtZone,txtSangemizban,txtVahed;
        AppCompatButton btnEdit,btnDelete;
        AppCompatButton btnReport;
        public int id;
        ConstraintLayout mainConsLayout;
        public MineralmaterialViewHolder(View itemView) {
            super(itemView);
            txtSheklekansar = (TextView)itemView.findViewById(R.id.txtSheklekansar);
            txtKanihayeasli = (TextView)itemView.findViewById(R.id.txtKanihayeasli);
            txtZone = (TextView)itemView.findViewById(R.id.txtZone);
            txtSangemizban = (TextView)itemView.findViewById(R.id.txtSangemizban);
            txtVahed = (TextView)itemView.findViewById(R.id.txtVahed);
            mainConsLayout = (ConstraintLayout)itemView.findViewById(R.id.mainConsLayout);
            btnEdit = (AppCompatButton)itemView.findViewById(R.id.btnEdit);
            btnDelete = (AppCompatButton)itemView.findViewById(R.id.btnDelete);
        }
    }
    public void deleteMineralMaterial(int id,long mineralMaterialId,ArrayList<MineralMaterialLayout> mineralMaterialLayoutsArrayList,MineralmaterialViewHolder holder){
        mineralMaterialLayoutsArrayList.remove(holder.getAdapterPosition());  // remove the item from list
        System.out.println("id" + id);
        notifyItemRemoved(Integer.parseInt(String.valueOf(id)));
        Intent intent = new Intent("deleteMineralMaterial");
        intent.putExtra("expMineralMaterialId" , mineralMaterialId);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
