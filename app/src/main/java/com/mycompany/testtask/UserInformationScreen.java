package com.mycompany.testtask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserInformationScreen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView textName, textEmail, textPhone;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information_screen);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();

        textName = (TextView)findViewById(R.id.text_name);
        textName.setText( intent.getExtras().getString("name"));

        textEmail = (TextView)findViewById(R.id.text_email);
        textEmail.setText( intent.getExtras().getString("email"));
        textEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1_email = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + textEmail.getText().toString()));
                startActivity(intent1_email);
            }
        });

        textPhone = (TextView)findViewById(R.id.text_fhone);
        textPhone.setText( intent.getExtras().getString("fhone"));
        textPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1_call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+textPhone.getText().toString()));
                startActivity(intent1_call);
            }
        });

        webView =(WebView)findViewById(R.id.web_view);
        webView.loadUrl( "https://" + intent.getExtras().getString("web")+"/");

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        double mLongitude = Double.parseDouble(intent.getExtras().getString("longitude"));
        double mLatitude =Double.parseDouble(intent.getExtras().getString("latitude"));
        String adress = intent.getExtras().getString("adress");

        // Add a marker in Sydney and move the camera
        LatLng myCity = new LatLng(mLatitude, mLongitude);


        mMap.addMarker(new MarkerOptions().position(myCity).title(adress));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(myCity));
        //начальная позиция
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myCity, 9);
        //анимация
        mMap.animateCamera(cameraUpdate);
        //тип карты
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        //zoom
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

}
