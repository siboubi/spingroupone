package com.sogeti.siboubib.spingroupone;

import com.sogeti.siboubib.spingroupone.model.Checkins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends Activity {
	protected String mlatitude;
	protected String mlongitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		// Retrieve location manager for GPS update
		Intent intent = this.getIntent();
		Bundle extras = intent.getExtras();
		mlatitude = extras.getString(Checkins.LATITUDE);
		mlongitude = extras.getString(Checkins.LONGITUDE);
		Toast.makeText(this, "New Lat: "+mlatitude+", Long:"+mlongitude, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_main:
			startMainCheckinIntent();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void startMainCheckinIntent() {
		Intent mainIntent = new Intent(getBaseContext(), MapActivity.class);
		startActivity(mainIntent);
	}

}
