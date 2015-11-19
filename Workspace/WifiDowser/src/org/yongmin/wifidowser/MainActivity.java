package org.yongmin.wifidowser;

import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	WifiManager wifiManager;
	int linkSpeed = 0;
	TextView sigText;
	boolean on = true;
	int[] sigColor = {15023616,13323029,11688234,13142887,11967871,10793111,9618095,8443335, 12443903 ,10211839, 10211839};
	//int[] rainbow = this.getResources().getIntArray(R.array.rainbow);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sigText = (TextView)findViewById(R.id.signalText);
		//WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		//int linkSpeed = wifiManager.getConnectionInfo().getRssi();
		//sigText.setText(linkSpeed);
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/thinfont.ttf"); 
		sigText.setTypeface(type);
		wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        linkSpeed = wifiManager.getConnectionInfo().getRssi() + 100;
        //Intent intent = new Intent(MainActivity.this, Map.class);
        //startActivity(intent);
        startTimerThread();
        
	}
	


	private void startTimerThread() {
	    final Handler handler = new Handler();
	    Runnable runnable = new Runnable() {
	        private long startTime = System.currentTimeMillis();
	        public void run() {
	        	while(on) {  
	                try {
	                    Thread.sleep(1000);
	                    linkSpeed = wifiManager.getConnectionInfo().getRssi() + 100;
	                }    
	                catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                handler.post(new Runnable(){
	                    public void run() {
	                    	sigText.setTextColor(Color.BLACK);
	                    	sigText.setText("" + linkSpeed);
	                }
	            });
	            }
	        }
	    };
	    new Thread(runnable).start();
	}
	
	public static String getCurrentSsid(Context context) {
		  String ssid = null;
		  ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		  NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		  if (networkInfo.isConnected()) {
		    final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		    final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
		    if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
		      ssid = connectionInfo.getSSID();
		    }
		  }
		  return ssid;
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
