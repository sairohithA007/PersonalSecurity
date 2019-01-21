package com.example.security;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener{
	Button addressButton, stop;
    TextView addressTV;
    TextView latLongTV;
    TextView textView6, myAddress;
    EditText editText,dest,phone;
    LocationManager locationManager ;
    String provider,dlati, dlongi,slati,slongi,phoneno;
    double lati,longi,solati,solongi,delati,delongi;
    ImageButton imgButton;
    double a,b,c,d;
    String add,msg;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgButton = (ImageButton) findViewById(R.id.imageButton1);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	 editText = (EditText) findViewById(R.id.editText1);
                 String address = editText.getText().toString();
                 dest = (EditText)findViewById(R.id.editText2);
                 String address1= dest.getText().toString();
                 String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%s&daddr=%s",address,address1);
                 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                 startActivity(intent);
                 
            	}
            });
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if(provider!=null && !provider.equals("")){
            Location location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 20000, 1, this);
            if(location!=null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }
         latLongTV = (TextView) findViewById(R.id.textView4);
         textView6 = (TextView) findViewById(R.id.textView6);
         addressButton = (Button) findViewById(R.id.button2);
         myAddress = (TextView)findViewById(R.id.textView9);
         addressButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View arg0) {
            	 getMyLocationAddress();
                 editText = (EditText) findViewById(R.id.editText1);
                 String address = editText.getText().toString();
                 dest = (EditText)findViewById(R.id.editText2);
                 String address1= dest.getText().toString();
                 phone = (EditText) findViewById(R.id.editText3);
                 phoneno =phone.getText().toString();
                 msg ="Your number is confirmed" ;//phone.getText().toString();
                 sendSMSMessage();
                 GeocodingLocation locationAddress = new GeocodingLocation();
                 locationAddress.getAddressFromLocation(address,
                         getApplicationContext(), new GeocoderHandler());
                 GeocodingLocation1  locationAddress1 = new GeocodingLocation1();
                 locationAddress1.getAddressFromLocation(address1,
                         getApplicationContext(), new GeocoderHandler1());
                if(solati>delati){
                	a= delati;
                	b= solati;
                }
                else{
                   a= solati;
                   b= delati;
                }
                if(solongi>delongi){
                	c= delongi;
                	d= solongi;
                }
                else{
                   c= solongi;
                   d= delongi;
                }
                if((a<=lati)&&(b>=lati)&&(c<=longi)&&(d>=longi)){
                	Log.i("Happy", "");
                }
                else{
                	msg = add;
                	sendSMSMessage();
                }
             }
         });
    }
    protected void sendSMSMessage() {
        Log.i("Send SMS", "");

        String msg = phone.getText().toString();
       
        String message = msg;//txtMessage.getText().toString();

        try {
           SmsManager smsManager = SmsManager.getDefault();
           smsManager.sendTextMessage(phoneno, null, message, null, null);
           Toast.makeText(getApplicationContext(), "SMS sent.",
           Toast.LENGTH_LONG).show();
        } catch (Exception e) {
           Toast.makeText(getApplicationContext(),
           "SMS faild, please try again.",
           Toast.LENGTH_LONG).show();
           e.printStackTrace();
        }
        
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();        
        
     }
     public void onBackPressed() {
  	      moveTaskToBack(true); 
  	      MainActivity.this.finish();
  	   }
    public void getMyLocationAddress() {
        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
        try {
              List<Address> addresses = geocoder.getFromLocation(lati,longi, 1);
              if(addresses != null) {
                  Address fetchedAddress = addresses.get(0);
                  StringBuilder strAddress = new StringBuilder();
                  for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                        add= strAddress.toString();
                  }
                  myAddress.setText("I am at: " +strAddress.toString());
               msg= add;
              }
              else
                  myAddress.setText("No location found..!");
        } 
        catch (IOException e) {
                 e.printStackTrace();
                 Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress,locationAddress1;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            String str = locationAddress;
            String[] temp;
           String delimiter = " ";
          temp = str.split(delimiter);   
          slati = temp[0];   
           slongi = temp[1];
            latLongTV.setText(locationAddress);
            solati = Double.valueOf(slati).doubleValue();
            solongi = Double.valueOf(slongi).doubleValue();
        }
    }    
    public void onLocationChanged(Location location) {
        TextView tvLongitude = (TextView)findViewById(R.id.textView7);
        TextView tvLatitude = (TextView)findViewById(R.id.textView8);
        tvLongitude.setText(""+location.getLongitude());
        longi = location.getLongitude();
        tvLatitude.setText(""+location.getLatitude() );
        lati = location.getLatitude();
    }
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
    private class GeocoderHandler1 extends Handler {
        @Override
        public void handleMessage(Message message) {
            String  locationAddress,locationAddress1;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress1 = bundle.getString("address");
                    break;
                default:
                    locationAddress1 = null;
            }
            String str = locationAddress1;

            String[] temp;

           String delimiter = " ";
          temp = str.split(delimiter);   
          dlati = temp[0];   
           dlongi = temp[1];
            textView6.setText(locationAddress1);
            delati = Double.valueOf(dlati).doubleValue();
            delongi = Double.valueOf(dlongi).doubleValue();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
         
    }
}