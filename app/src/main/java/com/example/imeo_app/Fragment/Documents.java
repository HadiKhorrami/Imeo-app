package com.example.imeo_app.Fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeo_app.Adapters.FilesRecyclerAdapter;
import com.example.imeo_app.DataModels.FilesLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.DocumentLocalServiceUtil;
import com.example.imeo_app.db.service.MachineryLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Document;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class Documents extends Fragment {

    AppCompatButton btnUpload,btnSave,btnBack,btnDelete,btnConfirm;
    Dialog dialog;
    private int PICK_PDF_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    TextView txtName;
    private Uri filePath;
    String pdfPath;
    ArrayList<FilesLayout> filesLayoutArrayList;
    FilesRecyclerAdapter filesRecyclerAdapter;
    JSONArray relativeLayoutList;
    JSONArray documentsArray;
    JSONArray insertArray;
    JSONArray jsonArray;
    RecyclerView recyclerView;
    long repId,mineId;
    ConstraintLayout mainLayout;
    public Documents() {
        // Required empty public constructor
    }

    public static Documents newInstance() {
        Documents fragment = new Documents();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        btnUpload = view.findViewById(R.id.btnUpload);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        mainLayout = view.findViewById(R.id.mainLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.choosefile_dialog_layout);
        TextView txtChooseFile = (TextView) dialog.findViewById(R.id.txtChooseFile);
        TextView txtTakePicture = (TextView) dialog.findViewById(R.id.txtTakePicture);

        relativeLayoutList = new JSONArray();
        documentsArray = new JSONArray();
        insertArray = new JSONArray();
        filesLayoutArrayList = new ArrayList<>();

        SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
        repId = shared.getLong("reportId", 0);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < mainLayout.getChildCount(); i++) {
            View child = mainLayout.getChildAt(i);
            child.setEnabled(false);
        }
        }
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        mineId = report.getMineid();

        SharedPreferences shared1 = getActivity().getSharedPreferences("mineType", MODE_PRIVATE);
        String mineType = shared1.getString("mineType","");

        getExistDocument();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                try {
//                    if (wktFlag == 1) {
//                        insertInspectionJson.put("wkt", pointWKTFromEdt);
//                        insertInspectionJson.put("lat", Double.parseDouble(edtLat.getText().toString()));
//                        insertInspectionJson.put("lng", Double.parseDouble(edtLng.getText().toString()));
//                    } else if (wktFlag == 2) {
//                        insertInspectionJson.put("wkt", polygonWKT);
//                        WKTReader wktReader = new WKTReader();
//                        Geometry geometry = wktReader.read(polygonWKT);
//                        geometry.setSRID(4326);
//                        insertInspectionJson.put("lat", geometry.getCentroid().getY());
//                        insertInspectionJson.put("lng", geometry.getCentroid().getX());
//                    } else if (wktFlag == 3) {
//                        insertInspectionJson.put("wkt", pointWKTByMarker);
//                        insertInspectionJson.put("lat", onePointLat);
//                        insertInspectionJson.put("lng", onePointLng);
//                    }
                    for (int i = 0; i < relativeLayoutList.length(); i++) {
                        JSONObject jsonObject = relativeLayoutList.getJSONObject(i);

                        jsonArray.put(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(jsonArray);
                JsonInsertUtil.insertDocumentFromJSON(jsonArray, getActivity());
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = CommentOffer.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                if(mineType.equals("pellekani") || mineType.equals("ekteshafi")){
                    intent.putExtra("button","btnForm15");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    intent.putExtra("button","btnForm14");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("enfejari")){
                    intent.putExtra("button","btnForm16");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(getActivity());
                infoBuilder.setMessage("میخواهید گزارش تایید تهایی شود؟")
                        .setPositiveButton("بله", (dialog, id) ->setMineConfirm()).
                        setNegativeButton("خیر", (dialog, id) -> infoBuilder.create().dismiss());
                infoBuilder.create().show();
            }
        });

        txtChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        txtTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(cameraIntent, "Capture Image"), CAMERA_REQUEST);
            }
        });
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("deleteFile"));
        return view;
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onReceive(Context context, Intent intent) {
            long documentId = intent.getLongExtra("documentId",0);
            DocumentLocalServiceUtil documentLocalServiceUtil = new DocumentLocalServiceUtil(context);
            documentLocalServiceUtil.deleteDocument(documentId);
            for (int i = 0; i < relativeLayoutList.length(); i++) {
                try {
                    JSONObject jsonObject = relativeLayoutList.getJSONObject(i);
                    if (jsonObject.getLong("documentId")==documentId) {
                        relativeLayoutList.remove(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            for(int i = 0;i<filesLayoutArrayList.size();i++){
                if(documentId==filesLayoutArrayList.get(i).getDocumentid()){
                    filesLayoutArrayList.remove(i);
                }
            }
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansWeb.ttf");
            SpannableString efr = new SpannableString("حذف شد");
            efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Toast.makeText(context, efr, Toast.LENGTH_SHORT).show();
            System.out.println("relativeLayoutList :" + relativeLayoutList);
        }
    };
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf/jpg/image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            System.out.println("relativeLayoutList" + relativeLayoutList.length());
            System.out.println("filesLayoutArrayList" + filesLayoutArrayList.size());
            filePath = data.getData();
            File file = new File(filePath.toString());
            String fileName = file.getName();
            System.out.println("onActivityResult: uri" + fileName.toString());
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream in = getActivity().getContentResolver().openInputStream(filePath);

                byte[] bytes = getBytes(in);
                Log.d("data", "onActivityResult: bytes size=" + bytes.length);
                System.out.println("onActivityResult: base64" + Base64.encodeToString(bytes, Base64.DEFAULT));

                LayoutInflater inflater = LayoutInflater.from(getActivity());

                View view = inflater.inflate(R.layout.files_layout, null);
                txtName = (TextView) view.findViewById(R.id.txtChooseFile);
                btnDelete = (AppCompatButton) view.findViewById(R.id.btnDelete);


                JSONObject jsonObj = new JSONObject();

                Random rn = new Random();
                long randomDocumentId = rn.nextLong() * -1;
                jsonObj.put("documentId", randomDocumentId);
                jsonObj.put("reportId", repId);
                jsonObj.put("mineId", mineId);
                jsonObj.put("fileName", fileName.toString());
                if (relativeLayoutList.length() == 0) {
                    jsonObj.put("id", 0);
                } else
                    jsonObj.put("id", String.valueOf(relativeLayoutList.length()));
                jsonObj.put("base64", Base64.encodeToString(bytes, Base64.DEFAULT));

                relativeLayoutList.put(jsonObj);
                System.out.println("relativeLayoutList" + relativeLayoutList);

                filesLayoutArrayList.clear();
                for (int i = 0; i < relativeLayoutList.length(); i++) {
                    JSONObject jsonObject = relativeLayoutList.getJSONObject(i);
                    FilesLayout filesLayout = new FilesLayout();
                    filesLayout.setDocumentid(jsonObject.getLong("documentId"));
                    filesLayout.setId(jsonObject.getString("id"));
                    filesLayout.setReportid(jsonObject.getLong("reportId"));
                    filesLayout.setMineid(jsonObject.getLong("mineId"));
                    filesLayout.setName(jsonObject.getString("fileName"));
                    filesLayout.setBase64(jsonObject.getString("base64"));
//                    System.out.println(fileImages);
                    filesLayoutArrayList.add(filesLayout);
                    System.out.println("mmm" + " " + filesLayoutArrayList);
                    filesRecyclerAdapter = new FilesRecyclerAdapter(getActivity(), filesLayoutArrayList);
                    recyclerView.setAdapter(filesRecyclerAdapter);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Log.d("error", "onActivityResult: " + e.toString());
            }
            if (filePath.toString() != null) {
                Log.d("Path: ", filePath.toString());
                pdfPath = filePath.toString();
                String filename = pdfPath.substring(pdfPath.lastIndexOf("/") + 1);
                filename = filename.replace("%20", " ");
            }
            dialog.dismiss();
        } else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                JSONObject jsonObj = new JSONObject();

                if (relativeLayoutList.length() == 0) {
                    jsonObj.put("id", 0);
                } else
                    jsonObj.put("id", String.valueOf(relativeLayoutList.length()));

                Random randomGenerator = new Random();
                long newimagename = randomGenerator.nextInt((11111 + 1 - 1111) + 1111);
                File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + newimagename);
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String fileName = f.getName();
                String encodedImage = encodeImage(photo);
                jsonObj.put("fileName", newimagename + ".jpg");
                jsonObj.put("base64", encodedImage);
                relativeLayoutList.put(jsonObj);
                System.out.println("relativeLayoutList" + relativeLayoutList);

                filesLayoutArrayList.clear();
                for (int i = 0; i < relativeLayoutList.length(); i++) {
                    JSONObject jsonObject = relativeLayoutList.getJSONObject(i);
                    FilesLayout filesLayout = new FilesLayout();
                    filesLayout.setDocumentid(jsonObject.getLong("documentId"));
                    filesLayout.setReportid(jsonObject.getLong("reportId"));
                    filesLayout.setMineid(jsonObject.getLong("mineId"));
                    filesLayout.setId(jsonObject.getString("id"));
                    filesLayout.setName(jsonObject.getString("fileName"));
                    filesLayout.setBase64(jsonObject.getString("base64"));
//                    System.out.println(fileImages);
                    filesLayoutArrayList.add(filesLayout);
                    System.out.println("mmm" + " " + filesLayoutArrayList);
                    filesRecyclerAdapter = new FilesRecyclerAdapter(getActivity(), filesLayoutArrayList);
                    recyclerView.setAdapter(filesRecyclerAdapter);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
            } catch (Exception e) {

            }
            dialog.dismiss();

            //write the bytes in file

//                try {
//                    fo = new FileOutputStream(f.getAbsoluteFile());
//                } catch (FileNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                try {
//                    fo.write(bytes.toByteArray());
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                uri=f.getAbsolutePath();
//                //this is the url that where you are saved the image
//            }
        }
    }
    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        Toast.makeText(getActivity(),b.toString() + "",Toast.LENGTH_SHORT).show();
        System.out.println("OOPP" + b);
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
//        return encodeToString(b);
        return encImage;
    }
    private void getExistDocument(){
        DocumentLocalServiceUtil documentLocalServiceUtil = new DocumentLocalServiceUtil(getActivity());
        List<Document> document = documentLocalServiceUtil.getDocumentByReportId(repId);
        if(document.size()>0){
            relativeLayoutList = new JSONArray();
            for(int i = 0;i<document.size();i++){
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("documentId", document.get(i).getDocumentid());
                    jsonObj.put("reportId", document.get(i).getReportid());
                    jsonObj.put("mineId", document.get(i).getMineid());
                    jsonObj.put("id", String.valueOf(relativeLayoutList.length()));
                    jsonObj.put("fileName", document.get(i).getFilename());
                    jsonObj.put("base64", document.get(i).getBase64());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                relativeLayoutList.put(jsonObj);
            }
            try {
            for (int i = 0; i < relativeLayoutList.length(); i++) {
                JSONObject jsonObject = relativeLayoutList.getJSONObject(i);
                FilesLayout filesLayout = new FilesLayout();
                filesLayout.setId(jsonObject.getString("id"));
                filesLayout.setDocumentid(jsonObject.getLong("documentId"));
                filesLayout.setReportid(jsonObject.getLong("reportId"));
                filesLayout.setMineid(jsonObject.getLong("mineId"));
                filesLayout.setName(jsonObject.getString("fileName"));
                filesLayout.setBase64(jsonObject.getString("base64"));
                filesLayoutArrayList.add(filesLayout);
                filesRecyclerAdapter = new FilesRecyclerAdapter(getActivity(), filesLayoutArrayList);
                recyclerView.setAdapter(filesRecyclerAdapter);
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
            System.out.println("relativeLayoutList :" + relativeLayoutList);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setMineConfirm(){
        PersianDate pdate = new PersianDate();
        PersianDateFormat pdformater = new PersianDateFormat("Y/m/d");
        jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reportId",repId);
            jsonObject.put("status",1);
            jsonObject.put("statusDate",pdformater.format(pdate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        System.out.println(jsonArray);
        JsonInsertUtil.insertReportsFromJSON(jsonArray,getActivity());
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
        SpannableString efr = new SpannableString("ثبت شد");
        efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(),Report.class);
        startActivity(intent);
    }
}