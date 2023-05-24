package com.example.imeo_app.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.imeo_app.DataModels.AMineOperationLayout;
import com.example.imeo_app.DataModels.FilesLayout;
import com.example.imeo_app.R;

import java.util.ArrayList;

public class FilesRecyclerAdapter extends RecyclerView.Adapter<FilesRecyclerAdapter.FilesViewHolder> {
    ArrayList<FilesLayout> filesLayouts;
    RecyclerView recyclerView;
      private Context context;
    private int lastPosition = -1;


    public FilesRecyclerAdapter(Context context, ArrayList<FilesLayout> arrayList) {
        filesLayouts = new ArrayList<FilesLayout>();
        filesLayouts = arrayList;
    }

    @NonNull
    @Override
    public FilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_layout, parent, false);
        context = parent.getContext();
        return new FilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilesViewHolder holder, final int position) {
        final FilesLayout dataModel = filesLayouts.get(position);
        String base64 = dataModel.getBase64();
        holder.txtName.setText(dataModel.getName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(context);
                infoBuilder.setMessage("میخواهید حذف کنید؟")
                        .setPositiveButton("بله", (dialog, id) ->deleteDocument(dataModel.getId(),dataModel.getDocumentid(),filesLayouts,holder)).
                        setNegativeButton("خیر", (dialog, id) -> infoBuilder.create().dismiss());
                infoBuilder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return filesLayouts.size();
    }

    public class FilesViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        AppCompatButton btnDelete;
        public int id;
        public FilesViewHolder(View itemView) {
            super(itemView);
            btnDelete = (AppCompatButton)itemView.findViewById(R.id.btnDelete);
            txtName = (TextView)itemView.findViewById(R.id.txtChooseFile);
        }
    }
    public void deleteDocument(String id, long documentId, ArrayList<FilesLayout> filesLayouts, FilesRecyclerAdapter.FilesViewHolder holder){
        filesLayouts.remove(holder.getAdapterPosition());  // remove the item from list
        System.out.println("id" + id);
        notifyItemRemoved(Integer.parseInt(id));
        Intent intent = new Intent("deleteFile");
        intent.putExtra("documentId" , documentId);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
