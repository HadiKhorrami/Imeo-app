package com.example.imeo_app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.imeo_app.R;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;
import com.google.maps.android.PolyUtil;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.MultiPolygon;
import com.mapbox.geojson.Polygon;
import com.mapbox.geojson.*;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin;
import com.mapbox.mapboxsdk.plugins.localization.MapLocale;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.turf.TurfJoins;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.io.WKTReader;

import com.mapbox.geojson.Point;

import java.util.ArrayList;
import java.util.List;


public class GeometryJebheKar extends Fragment {
    MapView mapView;
    long currRepId;
    private LocationComponent locationComponent;
    AppCompatButton btnLocation,btnSave,btnBack;
    JSONObject mineInfoJsonObject;
    WKTReader reader;
    Boolean isInside;
    String reportId,mineWkt,wktCenter,centerLat,centerLng;
    long mineId,repId;
    String[] newPoints;
    String[] centerNewPoints;
    String[] jebheNewPoints;
    Polygon poly;
    Double lat,lng;
    private static final List<List<Point>> POINTS = new ArrayList<>();
    private static final List<Point> OUTER_POINTS = new ArrayList<>();
    private static final List<List<Point>> jebhePOINTS = new ArrayList<>();
    private static final List<Point> jebhe_OUTER_POINTS = new ArrayList<>();
    private static final List<com.google.android.gms.maps.model.LatLng> jebhe_LatLng_List = new ArrayList<>();
    ConstraintLayout mainLayout;
    public GeometryJebheKar() {
        // Required empty public constructor
    }

    public static GeometryJebheKar newInstance() {
        GeometryJebheKar fragment = new GeometryJebheKar();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity(), getString(R.string.mapbox_access_token));
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
        reader = new WKTReader();
        SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
        repId = shared.getLong("reportId", 0);

        String mineInfo = getArguments().getString("mineInfo");
        try {
            mineInfoJsonObject = new JSONObject(mineInfo);
            mineWkt = mineInfoJsonObject.getString("wkt");
            mineId = mineInfoJsonObject.getLong("mineId");
            reportId = mineInfoJsonObject.getString("reportId");
            wktCenter = mineInfoJsonObject.getString("wktCenter");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int mineStage = getArguments().getInt("mineStage");
        String extractionMethod = getArguments().getString("extractionMethod");

        View view = inflater.inflate(R.layout.fragment_geometry_workplace, container, false);
        btnLocation = view.findViewById(R.id.btnLocation);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        mainLayout = view.findViewById(R.id.mainLayout);
        mapView = view.findViewById(R.id.workPlaceMapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        LocalizationPlugin localizationPlugin = new LocalizationPlugin(mapView, mapboxMap , style);
                        localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);

                        if(wktCenter != null) {
                            ArrayList<String> centerPoints = new ArrayList();
                            String s, s1, s2, s3, s4;
                            s = wktCenter;
                            s1 = s.replaceAll("POLYGON ", "").replaceAll("POINT ", "");
                            s2 = s1.replaceAll("[()]", "");
                            s3 = s2.replaceAll(", ", "#");
                            s4 = s3.replaceAll(" ", ",");
                            String[] centerSplitString = s4.split("#");
                            for (int i = 0; i < centerSplitString.length; i++) {
                                centerPoints.add(centerSplitString[i]);
                            }
                            for (int i = 0; i < centerPoints.size(); i++) {
                                centerNewPoints = centerPoints.get(i).split(",");
                            }
                        }
                        /////////////////////////////////////////////////////////////////////
                        ArrayList<String> points = new ArrayList();
                        String sa,sa1,sa2,sa3,sa4;
                        sa = mineWkt;
                        sa1 = sa.replaceAll("POLYGON ","").replaceAll("POINT ","").replaceAll("MULTI","");
                        sa2 = sa1.replaceAll("[()]","");
                        sa3 = sa2.replaceAll(", ","#");
                        sa4 = sa3.replaceAll(" ",",");
                        String[] splitString = sa4.split( "#" );
                        for (int i = 0;i<splitString.length;i++)
                        {
                            points.add(splitString[i]);
                        }
                        OUTER_POINTS.clear();
                        POINTS.clear();
                        for(int i = 0;i<points.size();i++){
                            newPoints = points.get(i).split( "," );
                            OUTER_POINTS.add(Point.fromLngLat(Double.parseDouble(newPoints[0]), Double.parseDouble(newPoints[1])));
                            POINTS.add(OUTER_POINTS);
                        }
                        poly = Polygon.fromLngLats(POINTS);

                        final long sourceId, layerId;
                        sourceId = mineId;
                        layerId = mineId;
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
                        CameraPosition position = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(centerNewPoints[1]), Double.parseDouble(centerNewPoints[0]))).zoom(12).tilt(20).build();
                        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                    }
                });
            }
        });
        getGeoJebhe();
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomToMyLocation();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                if(isInside == true) {
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("reportId", repId);
                        jsonObject.put("geometrySinekarJebhe", "POINT (" + lng + " " + lat + ")");
                        jsonObject.put("setGeom1", true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                    System.out.println("jsonArray = " + jsonArray);
                    JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                    Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                    SpannableString efr = new SpannableString("ثبت شد");
                    efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();

                    Fragment selectedFragment = GeometryMineralDeposit.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putLong("mineId", mineId);
                    bundle.putInt("mineStage", mineStage);
                    bundle.putString("mineInfo", mineInfo);
                    bundle.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button", "btnForm6");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else {
                    Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                    SpannableString efr = new SpannableString("موقعیت مکانی در محدوده ی معدن نیست");
                    efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Toast.makeText(getActivity(), efr, Toast.LENGTH_LONG).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = MineCondition.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putLong("mineId", mineId);
                bundle.putInt("mineStage", mineStage);
                bundle.putString("mineInfo", mineInfo);
                bundle.putString("extractionMethod", extractionMethod);
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm4");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });

        return view;
    }
    public void zoomToMyLocation() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @SuppressWarnings({"MissingPermission"})
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        LocalizationPlugin localizationPlugin = new LocalizationPlugin(mapView, mapboxMap , style);
                        localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);
                        System.out.println(mineWkt);
                        if(!mineWkt.equals("")) {
                            ArrayList<String> points = new ArrayList();
                            String sa, sa1, sa2, sa3, sa4;
                            sa = mineWkt;
                            sa1 = sa.replaceAll("POLYGON ", "").replaceAll("POINT ", "").replaceAll("MULTI", "");
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
                                jebhe_LatLng_List.add(new com.google.android.gms.maps.model.LatLng(Double.parseDouble(newPoints[1]), Double.parseDouble(newPoints[0])));
                            }

                            final long sourceId, layerId;
                            sourceId = mineId;
                            layerId = mineId;
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
                        if (mapboxMap != null && mapboxMap.getStyle() != null) {
                            enableLocationComponent(mapboxMap.getStyle(), mapboxMap);

                            final Handler handler = new Handler();
                            handler.postDelayed(() -> {

                                if (locationComponent != null && locationComponent.getLastKnownLocation() != null) {
                                    mapboxMap.setCameraPosition(new CameraPosition.Builder().target(new LatLng(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude())).zoom(Math.max(15, mapboxMap.getCameraPosition().zoom)).build());
                                    lat = locationComponent.getLastKnownLocation().getLatitude();
                                    lng = locationComponent.getLastKnownLocation().getLongitude();
                                    isInside = PolyUtil.containsLocation(new com.google.android.gms.maps.model.LatLng(lat,lng), jebhe_LatLng_List, false);

                                }
                            }, 2000);
                        }
                    }
                });
            }
        });

    }
    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle, @NonNull MapboxMap mapboxMap) {
        // Create and customize the LocationComponent's options
        LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(getActivity())
                .elevation(5)
                .accuracyAlpha(.6f)
                .accuracyColor(Color.RED)
                .foregroundTintColor(Color.BLUE)
                .build();

        // Get an instance of the component
        locationComponent = mapboxMap.getLocationComponent();

        LocationComponentActivationOptions locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(getActivity(), loadedMapStyle)
                        .locationComponentOptions(customLocationComponentOptions)
                        .build();

        // Activate with options
        locationComponent.activateLocationComponent(locationComponentActivationOptions);

        // Enable to make component visible
        locationComponent.setLocationComponentEnabled(true);

        // Set the component's camera mode
        locationComponent.setCameraMode(CameraMode.TRACKING_GPS);

        // Set the component's render mode
        locationComponent.setRenderMode(RenderMode.GPS);
    }
    private void getGeoJebhe(){
        SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
        long repId = shared.getLong("reportId", 0);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < mainLayout.getChildCount(); i++) {
            View child = mainLayout.getChildAt(i);
            child.setEnabled(false);
        }
        }
        currRepId = repId;
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            LocalizationPlugin localizationPlugin = new LocalizationPlugin(mapView, mapboxMap , style);
                            localizationPlugin.setMapLanguage(MapLocale.LOCAL_NAME);

                            if(wktCenter != null) {
                                ArrayList<String> centerPoints = new ArrayList();
                                String s, s1, s2, s3, s4;
                                s = wktCenter;
                                s1 = s.replaceAll("POLYGON ", "").replaceAll("POINT ", "");
                                s2 = s1.replaceAll("[()]", "");
                                s3 = s2.replaceAll(", ", "#");
                                s4 = s3.replaceAll(" ", ",");
                                String[] centerSplitString = s4.split("#");
                                for (int i = 0; i < centerSplitString.length; i++) {
                                    centerPoints.add(centerSplitString[i]);
                                }
                                for (int i = 0; i < centerPoints.size(); i++) {
                                    centerNewPoints = centerPoints.get(i).split(",");
                                }
                            }
                            /////////////////////////////////////////////////////////////////////
                            if(!mineWkt.equals("")) {
                                ArrayList<String> points = new ArrayList();
                                String sa, sa1, sa2, sa3, sa4;
                                sa = mineWkt;
                                sa1 = sa.replaceAll("POLYGON ", "").replaceAll("POINT ", "").replaceAll("MULTI", "");
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

                                final long sourceId, layerId;
                                sourceId = mineId;
                                layerId = mineId;
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
                                CameraPosition position = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(centerNewPoints[1]), Double.parseDouble(centerNewPoints[0]))).zoom(12).tilt(20).build();
                                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
                            }
                            /////////////////////////////////////////////////////////
                            if(!report.getGeometrysinekarjebhe().equals("")) {
                                ArrayList<String> jebhPoints = new ArrayList();
                                String s, s1, s2, s3, s4;
                                s = report.getGeometrysinekarjebhe();
                                s1 = s.replaceAll("POLYGON ", "").replaceAll("POINT ", "").replaceAll("MULTI", "").replaceAll("SRID=4326;", "");
                                s2 = s1.replaceAll("[()]", "");
                                s3 = s2.replaceAll(", ", "#");
                                s4 = s3.replaceAll(" ", ",");
                                String[] jebheSplitString = s4.split("#");
                                for (int i = 0; i < jebheSplitString.length; i++) {
                                    jebhPoints.add(jebheSplitString[i]);
                                }
                                jebhe_OUTER_POINTS.clear();
                                jebhePOINTS.clear();
                                for (int i = 0; i < jebhPoints.size(); i++) {
                                    jebheNewPoints = jebhPoints.get(i).split(",");
                                    jebhe_OUTER_POINTS.add(Point.fromLngLat(Double.parseDouble(jebheNewPoints[0]), Double.parseDouble(jebheNewPoints[1])));
                                    jebhePOINTS.add(jebhe_OUTER_POINTS);
                                }

                                final long jebheSourceId, jebheLayerId;
                                jebheSourceId = repId;
                                jebheLayerId = repId;
                                if (style.getSource(String.valueOf(jebheSourceId)) != null) {
                                    style.removeLayer(String.valueOf(jebheLayerId));
                                    style.removeSource(String.valueOf(jebheSourceId));
                                } else {
                                    style.removeLayer(String.valueOf(jebheLayerId));
                                    style.addSource(new GeoJsonSource(String.valueOf(jebheSourceId), Polygon.fromLngLats(jebhePOINTS)));
                                    style.addLayerBelow(new FillLayer(String.valueOf(jebheLayerId), String.valueOf(jebheSourceId)).withProperties(
                                            fillColor(Color.parseColor("#F3E012"))), "settlement-label"
                                    );
                                }
                            }
                        }
                    });
                }
            });


    }


}