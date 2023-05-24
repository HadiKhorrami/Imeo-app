package com.example.imeo_app.Fragment;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imeo_app.Adapters.MineExpansionRecyclerAdapter;
import com.example.imeo_app.DataModels.MineExpansionLayout;
import com.example.imeo_app.R;

import com.example.imeo_app.db.service.MineLocalServiceUtil;
import com.example.imeo_app.db.tables.Mine;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin;
import com.mapbox.mapboxsdk.plugins.localization.MapLocale;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {
    private MapView mapView;
    private int finishedMinesCount=0;
    private int doingMinesCount=0;
    private int stoppedMinesCount=0;
    private int notStartedMinesCount=0;
    TextView txtShowAllMines,txtFinishedCount,txtDoingCount,txtStoppedCount,txtNotStartedCount,txtAllMines;
    AppCompatButton btnFinished,btnDoing,btnStopped,btnNotStarted;
    ArrayList<MineExpansionLayout> mineExpansionLayoutArrayList;
    MineExpansionRecyclerAdapter mineExpansionRecyclerAdapter;
    RecyclerView recyclerView;
    private static final List<Double> minesLatLng = new ArrayList<>();
    private static final List<List<Double>> minesCenter = new ArrayList<>();
    private static final List<List<Point>> POINTS = new ArrayList<>();
    private static final List<Point> OUTER_POINTS = new ArrayList<>();
    ArrayList<String> points = new ArrayList();
    String[] newPoints;

    public Dashboard() {
    }
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();

        return fragment;
    }
    public static Fragment newInstance() {
        Dashboard dashboard = new Dashboard();
        return dashboard;
    }
    public static Fragment getWKTInstance(ArrayList<String> arrayList) {
        Dashboard dashboard = new Dashboard();
        return dashboard;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(), getString(R.string.mapbox_access_token));
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mineExpansionLayoutArrayList = new ArrayList<>();
        mapView = (MapView)view.findViewById(R.id.mapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        LocalizationPlugin localizationPlugin = new LocalizationPlugin(mapView, mapboxMap , style);
                        localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);

                        MineLocalServiceUtil mineLocalServiceUtil = new MineLocalServiceUtil(getActivity());
                        List<Mine> mine = mineLocalServiceUtil.getMine();

                        for (int i = 0; i < mine.size(); i++) {
                            final String wkt;
                            final long sourceId, layerId;
                            wkt = mine.get(i).getWkt();
                            if(!wkt.equals("")){
                            String sa, sa1, sa2, sa3, sa4;
                            sa = mine.get(i).getWktcenter();
                            sa1 = sa.replaceAll("POINT ", "");
                            sa2 = sa1.replaceAll("[()]", "");
                            sa3 = sa2.replaceAll(", ", "#");
                            sa4 = sa3.replaceAll(" ", ",");
                            String[] splitString = sa4.split("#");
                            for (int j = 0; j < splitString.length; j++) {
                                points.clear();
                                points.add(splitString[j]);
//
                            }
                            for (int k = 0; k < points.size(); k++) {
                                newPoints = points.get(k).split(",");
                            }
                            SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);
                            symbolManager.setIconAllowOverlap(true);
                            symbolManager.setTextAllowOverlap(true);
                            SymbolOptions symbolOptions = new SymbolOptions().withLatLng(new LatLng(Double.parseDouble(newPoints[1]), Double.parseDouble(newPoints[0]))).withIconImage("marker-15").withIconSize(3f);
                            symbolManager.create(symbolOptions);
                            sourceId = mine.get(i).getMineid();
                            layerId = mine.get(i).getMineid();

                            double finalCenterLat = Double.parseDouble(newPoints[1]);
                            double finalCenterLng = Double.parseDouble(newPoints[0]);
                            symbolManager.addClickListener(new OnSymbolClickListener() {
                                @Override
                                public boolean onAnnotationClick(Symbol symbol) {
                                    ArrayList<String> points = new ArrayList();
                                    String sa, sa1, sa2, sa3, sa4;
                                    sa = wkt;
                                    sa1 = sa.replaceAll("MULTIPOLYGON ", "").replaceAll("POINT ", "").replaceAll("POLYGON ", "");
                                    sa2 = sa1.replaceAll("[()]", "");
                                    sa3 = sa2.replaceAll(", ", "#");
                                    sa4 = sa3.replaceAll(" ", ",");
                                    String[] splitString = sa4.split("#");
                                    for (int i = 0; i < splitString.length; i++) {
                                        points.add(splitString[i]);
                                    }
                                    OUTER_POINTS.clear();
                                    POINTS.clear();
                                    for (int i = 0; i < points.size(); i++) {
                                        newPoints = points.get(i).split(",");
                                        OUTER_POINTS.add(Point.fromLngLat(Double.parseDouble(newPoints[0]), Double.parseDouble(newPoints[1])));
                                        POINTS.add(OUTER_POINTS);
                                    }

                                    if (OUTER_POINTS.size() > 1) {
                                        mapboxMap.getStyle(new Style.OnStyleLoaded() {
                                            @Override
                                            public void onStyleLoaded(@NonNull Style style) {

                                                if (style.getSource(String.valueOf(sourceId)) != null) {
                                                    style.removeLayer(String.valueOf(layerId));
                                                    style.removeSource(String.valueOf(sourceId));
                                                } else {
                                                    style.removeLayer(String.valueOf(layerId));
                                                    style.addSource(new GeoJsonSource(String.valueOf(sourceId), Polygon.fromLngLats(POINTS)));
                                                    style.addLayerBelow(new FillLayer(String.valueOf(layerId), String.valueOf(sourceId)).withProperties(
                                                            fillColor(Color.parseColor("#D62F2F"))), "settlement-label"
                                                    );
                                                }
                                            }
                                        });


                                    }
                                    CameraPosition position = new CameraPosition.Builder().target(new LatLng(finalCenterLat, finalCenterLng)).zoom(12).tilt(20).build();
                                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                                    return false;
                                }
                            });
                        }
                        }
                        CameraPosition position = new CameraPosition.Builder().target(new LatLng(32.6539, 51.6660)).zoom(8).tilt(20).build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                    }
                });
            }
        });
        getMine();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,new IntentFilter("ShowOnMap"));
        return view;
    }
    private void getMine(){
        MineLocalServiceUtil mineLocalServiceUtil = new MineLocalServiceUtil(getActivity());
        List<Mine> mine = mineLocalServiceUtil.getMine();
        for(int i = 0;i<mine.size();i++){
            MineExpansionLayout mineExpansionLayout = new MineExpansionLayout();
            mineExpansionLayout.setMineid(mine.get(i).getMineid());
            mineExpansionLayout.setMinecode(mine.get(i).getMinecode());
            mineExpansionLayout.setMinename(mine.get(i).getMinename());
            mineExpansionLayout.setMinestage(mine.get(i).getMinestage());
            mineExpansionLayout.setNationalcode(mine.get(i).getNationalcode());
            mineExpansionLayout.setBeneficiaryname(mine.get(i).getBeneficiaryname());
            mineExpansionLayout.setBeneficiaryaddress(mine.get(i).getBeneficiaryaddress());
            mineExpansionLayout.setBeneficiaryphone(mine.get(i).getBeneficiaryphone());
            mineExpansionLayout.setMineaddress(mine.get(i).getMineaddress());
            mineExpansionLayout.setMinephone(mine.get(i).getMinephone());
            mineExpansionLayout.setSupervisormembershipcode(mine.get(i).getSupervisormembershipcode());
            mineExpansionLayout.setExplorelicensename(mine.get(i).getExplorelicensename());
            mineExpansionLayout.setExplorelicensenumber(mine.get(i).getExplorelicensenumber());
            mineExpansionLayout.setExplorelicensedate(mine.get(i).getExplorelicensedate());
            mineExpansionLayout.setExplorelicenseexpiredate(mine.get(i).getExplorelicenseexpiredate());
            mineExpansionLayout.setLicenseexpiredate(mine.get(i).getLicenseexpiredate());
            mineExpansionLayout.setMineralmaterial(mine.get(i).getMineralmaterial());
            mineExpansionLayout.setPhysicalprogresspercent(mine.get(i).getPhysicalprogresspercent());
            mineExpansionLayout.setClosestcity(mine.get(i).getClosestcity());
            mineExpansionLayout.setClosestvillage(mine.get(i).getClosestvillage());
            mineExpansionLayout.setCadastreid(mine.get(i).getCadastreid());
            mineExpansionLayout.setLicensenumber(mine.get(i).getLicensenumber());
            mineExpansionLayout.setLicensetype(mine.get(i).getLicensetype());
            mineExpansionLayout.setMinetype(mine.get(i).getMinetype());
            mineExpansionLayout.setExtractionmethod(mine.get(i).getExtractionmethod());
            mineExpansionLayout.setExtractioncapacity(mine.get(i).getExtractioncapacity());
            mineExpansionLayout.setSazmanmembershipname(mine.get(i).getSazmanmembershipname());
            mineExpansionLayout.setSazmanmembershipcode(mine.get(i).getSazmanmembershipcode());
            mineExpansionLayout.setWkt(mine.get(i).getWkt());
            mineExpansionLayout.setWktcenter(mine.get(i).getWktcenter());
            mineExpansionLayoutArrayList.add(mineExpansionLayout);
            mineExpansionRecyclerAdapter = new MineExpansionRecyclerAdapter(getActivity(), mineExpansionLayoutArrayList);
            recyclerView.setAdapter(mineExpansionRecyclerAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
        }
    }
//    private void getMineByState(String state){
//        MineLocalServiceUtil mineLocalServiceUtil = new MineLocalServiceUtil(getActivity());
//        List<Mine> mine = mineLocalServiceUtil.getMinesByState("mineimplementationstatus",state);
//        System.out.println(mine.size());
//        if(mine.size()>0){
//            for(int i = 0;i<mine.size();i++){
//                MineExpansionLayout expansionLayout = new MineExpansionLayout();
//                expansionLayout.setLocalmineid(mine.get(i).getLocalmineid());
//                expansionLayout.setTitle(mine.get(i).getTitle());
//                expansionLayout.setCreatedate(mine.get(i).getCreatedate());
//                expansionLayout.setMasterpersonname(mine.get(i).getMasterpersonname());
//                expansionLayout.setMinecode(mine.get(i).getMinecode());
//                expansionLayout.setBahrebardarpersonname(mine.get(i).getBahrebardarpersonname());
//                expansionLayout.setMinetype(mine.get(i).getMinetype());
//                expansionLayout.setTargetname(mine.get(i).getTargetname());
//                expansionLayout.setMinestatus(mine.get(i).getMinestatus());
//                expansionLayout.setCreditAllocation(mine.get(i).getCreditAllocation());
//                expansionLayout.setCreditExchanged(mine.get(i).getCreditExchanged());
//                expansionLayout.setMinecredit(mine.get(i).getMinecredit());
//                expansionLayout.setPeriodtime(mine.get(i).getPeriodtime());
//                expansionLayout.setWkt(mine.get(i).getWkt());
//                expansionLayout.setLat(mine.get(i).getLat());
//                expansionLayout.setLng(mine.get(i).getLng());
//                mineExpansionLayoutArrayList.add(expansionLayout);
//                mineExpansionRecyclerAdapter = new MineExpansionRecyclerAdapter(getActivity(), mineExpansionLayoutArrayList);
//                recyclerView.setAdapter(mineExpansionRecyclerAdapter);
//                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
//            }
//        }else if(mine.size()==0){
//            mineExpansionLayoutArrayList.clear();
//            mineExpansionRecyclerAdapter = new MineExpansionRecyclerAdapter(getActivity(), mineExpansionLayoutArrayList);
//            recyclerView.setAdapter(mineExpansionRecyclerAdapter);
//            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
//        }
//
//    }
    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double centerLat= intent.getDoubleExtra("centerLat",0);
            double centerLng= intent.getDoubleExtra("centerLng",0);
            System.out.println(centerLat + "***" + centerLng);
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            CameraPosition position = new CameraPosition.Builder().target(new LatLng(centerLat, centerLng)).zoom(10).tilt(20).build();
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                        }
                    });

                }
            });
            System.out.println(POINTS);
        }
    };
}
