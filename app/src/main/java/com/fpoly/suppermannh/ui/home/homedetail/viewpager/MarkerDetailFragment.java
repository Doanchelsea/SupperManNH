package com.fpoly.suppermannh.ui.home.homedetail.viewpager;

import android.os.Bundle;
import android.widget.TextView;

import com.fpoly.suppermannh.R;
import com.fpoly.suppermannh.base.BaseFragment;
import com.fpoly.suppermannh.model.Houst;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;

public class MarkerDetailFragment extends BaseFragment implements OnMapReadyCallback {


    @Override
    public void onResume() {
        super.onResume();
    }

    @BindView(R.id.tv_address_marker_fragemnt)
    TextView tv_address_marker_fragemnt;
    private GoogleMap driverMap;
    private Houst houst;

    public static final String HOUST = "HOUST";

    public static MarkerDetailFragment newInstance(Houst houst) {
        Bundle args = new Bundle();
        MarkerDetailFragment fragment = new MarkerDetailFragment();
        args.putParcelable(HOUST,houst);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void addEvents() {
        houst = getArguments().getParcelable(HOUST);
        initMap();
        loadFullName(houst.getAddress(),tv_address_marker_fragemnt);
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.marker_detail_fragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        driverMap = googleMap;
        LatLng currentLatLng = new LatLng(houst.getLat(), houst.getLng());
        driverMap.addMarker(new MarkerOptions().position(currentLatLng)
                .title(houst.getNames()));
//                .icon(bitmapDescriptorFromVector(activity,R.drawable.logoapp)));
        driverMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.driver_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }
}
