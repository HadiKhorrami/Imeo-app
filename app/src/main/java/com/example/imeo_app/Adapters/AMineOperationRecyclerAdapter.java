package com.example.imeo_app.Adapters;

import static android.content.Context.MODE_PRIVATE;

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
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.imeo_app.DataModels.AMineOperationLayout;
import com.example.imeo_app.DataModels.MineralMaterialLayout;
import com.example.imeo_app.R;

import java.util.ArrayList;

public class AMineOperationRecyclerAdapter extends RecyclerView.Adapter<AMineOperationRecyclerAdapter.AMineOperationViewHolder> {
    ArrayList<AMineOperationLayout> aMineOperationLayouts;
    RecyclerView recyclerView;
    private Context context;
    private int lastPosition = -1;


    public AMineOperationRecyclerAdapter(Context context, ArrayList<AMineOperationLayout> arrayList) {
        aMineOperationLayouts = new ArrayList<AMineOperationLayout>();
        aMineOperationLayouts = arrayList;
    }

    @NonNull
    @Override
    public AMineOperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_mine_operation_layout, parent, false);
        context = parent.getContext();
        return new AMineOperationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AMineOperationViewHolder holder, final int position) {
        final AMineOperationLayout dataModel = aMineOperationLayouts.get(position);

        SharedPreferences shared = context.getSharedPreferences("repId", MODE_PRIVATE);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < holder.mainConsLayout.getChildCount(); i++) {
                View child = holder.mainConsLayout.getChildAt(i);
                child.setEnabled(false);
            }
        }

        if(dataModel.getStonetype()==1)
           holder.txtStoneType.setText("کانسنگ");
        else if(dataModel.getStonetype()==2){
            holder.txtStoneType.setText("باطله");
        }
        System.out.println("dataModel :" + dataModel.getWorkfrontwide());
        holder.txtWorkFrontHeight.setText(String.valueOf(dataModel.getWorkfrontheight()));
        holder.txtWorkFrontSlope.setText(String.valueOf(dataModel.getWorkfrontslope()));
        holder.txtWorkFrontWide.setText(String.valueOf(dataModel.getWorkfrontwide()));
        holder.txtWorkFrontLength.setText(String.valueOf(dataModel.getWorkfrontlength()));
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("editMineFront");
                intent.putExtra("mineFrontId" , dataModel.getMinefrontid());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(context);
                infoBuilder.setMessage("میخواهید حذف کنید؟؟")
                        .setPositiveButton("بله", (dialog, id) ->deleteMineFront(dataModel.getId(),dataModel.getMinefrontid(),aMineOperationLayouts,holder)).
                        setNegativeButton("خیر", (dialog, id) -> infoBuilder.create().dismiss());
                infoBuilder.create().show();
            }
        });
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return aMineOperationLayouts.size();
    }


    public class AMineOperationViewHolder extends RecyclerView.ViewHolder {
        public TextView txtStoneType,txtWorkFrontHeight,txtWorkFrontSlope,txtWorkFrontWide,txtWorkFrontLength;
        public AppCompatButton btnEdit,btnDelete;
        public ConstraintLayout mainConsLayout;

        public int id;
        public AMineOperationViewHolder(View itemView) {
            super(itemView);
            txtStoneType = (TextView)itemView.findViewById(R.id.txtStoneType);
            txtWorkFrontHeight = (TextView)itemView.findViewById(R.id.txtWorkFrontHeight);
            txtWorkFrontSlope = (TextView)itemView.findViewById(R.id.txtWorkFrontSlope);
            txtWorkFrontWide = (TextView)itemView.findViewById(R.id.txtWorkFrontWide);
            txtWorkFrontLength = (TextView)itemView.findViewById(R.id.txtWorkFrontLength);
            btnEdit = (AppCompatButton)itemView.findViewById(R.id.btnEdit);
            btnDelete = (AppCompatButton)itemView.findViewById(R.id.btnDelete);
            mainConsLayout = (ConstraintLayout)itemView.findViewById(R.id.mainConsLayout);

        }
    }
    public void deleteMineFront(int id, long mineFrontId, ArrayList<AMineOperationLayout> aMineOperationLayouts, AMineOperationRecyclerAdapter.AMineOperationViewHolder holder){
        aMineOperationLayouts.remove(holder.getAdapterPosition());  // remove the item from list
        System.out.println("id" + id);
        notifyItemRemoved(Integer.parseInt(String.valueOf(id)));
        Intent intent = new Intent("deleteMineFront");
        intent.putExtra("mineFrontId" , mineFrontId);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
