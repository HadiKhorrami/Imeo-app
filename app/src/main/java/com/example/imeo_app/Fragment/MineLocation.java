package com.example.imeo_app.Fragment;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeo_app.R;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MineLocation extends Fragment {
    private MapView mapView;
    private TextView txtMineType;
    JSONObject mineInfoJsonObject;
    String mineId,mineWkt,mineWktCenter;
    private static final List<List<Point>> POINTS = new ArrayList<>();
    private static final List<Point> OUTER_POINTS = new ArrayList<>();
    ArrayList<String> points = new ArrayList();
    String[] newPoints;
    AppCompatButton btnSave;
    public MineLocation() {
        // Required empty public constructor
    }

    public static MineLocation newInstance() {
        MineLocation fragment = new MineLocation();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(), getString(R.string.mapbox_access_token));
        View view = inflater.inflate(R.layout.fragment_mine_location, container, false);
        txtMineType = view.findViewById(R.id.txtMineType);
        btnSave = view.findViewById(R.id.btnSave);

        String mineInfo = getArguments().getString("mineInfo");
        int mineStage = getArguments().getInt("mineStage");

        System.out.println(mineInfo);
        try {
            mineInfoJsonObject = new JSONObject(mineInfo);
            mineWkt = mineInfoJsonObject.getString("wkt");
            mineWktCenter = mineInfoJsonObject.getString("wktCenter");
            mineId = mineInfoJsonObject.getString("mineId");
            if(mineInfoJsonObject.getInt("mineStage")==1){
                txtMineType.setText("اکتشافی");
            }else if(mineInfoJsonObject.getInt("mineStage")==2){
                txtMineType.setText("استخراجی");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineStage==1){
                    Fragment selectedFragment = ExplorationMineInfo.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle);
                }else if(mineStage==2){
                    Fragment selectedFragment = ExtractiveMineInfo.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle);
                }
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm2");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        mapView = view.findViewById(R.id.workPlaceMapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        LocalizationPlugin localizationPlugin = new LocalizationPlugin(mapView, mapboxMap , style);
                        localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);

                        if(!mineWkt.equals("")) {
                            final String wkt;
                            final long sourceId, layerId;
                            wkt = mineWkt;
                            String sa, sa1, sa2, sa3, sa4;
                            sa = mineWktCenter;
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
                            sourceId = Long.parseLong(mineId);
                            layerId = Long.parseLong(mineId);

                            double finalCenterLat = Double.parseDouble(newPoints[1]);
                            double finalCenterLng = Double.parseDouble(newPoints[0]);
                            symbolManager.addClickListener(new OnSymbolClickListener() {
                                @Override
                                public boolean onAnnotationClick(Symbol symbol) {
                                    ArrayList<String> points = new ArrayList();
                                    String sa, sa1, sa2, sa3, sa4;
                                    sa = wkt;
                                    sa1 = sa.replaceAll("MULTI", "").replaceAll("POINT ", "").replaceAll("POLYGON ", "");
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
                            CameraPosition position = new CameraPosition.Builder().target(new LatLng(finalCenterLat, finalCenterLng)).zoom(8).tilt(20).build();
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                        }
                    }
                });

            }
        });

        return view;
    }
}