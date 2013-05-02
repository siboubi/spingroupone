package com.sogeti.siboubib.spingroupone;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sogeti.siboubib.spingroupone.model.Checkins;

public class MapActivity extends FragmentActivity implements GpsStatus.Listener,
		LocationListener {
	private GpsStatus mStatus;
	private LocationManager mLocationManager;
	private String provider;
	private Location mLastLocation;
	protected Object mActionMode;
	public int selectedItem = -1;
	private Context thisActivity;
	final List<Checkins> mList = new ArrayList<Checkins>();
	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		thisActivity = this;
		 setContentView(R.layout.activity_map);
		 setUpMapIfNeeded();
		// Set up GPS
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = mLocationManager.getBestProvider(criteria, false);
		mLocationManager.addGpsStatusListener(this);
		mLastLocation = mLocationManager.getLastKnownLocation(provider);
		//
		// final TextView textView = (TextView) findViewById(R.id.mapTextView);
		// textView.setText(getString(R.string.waiting_data_msg));

//		getListView().setOnItemClickListener(new OnItemClickListener() {
//			
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//				if (mActionMode != null) {
//					return;
//				}
//				selectedItem = position;
//
//				// Start the CAB using the
//				// ActionMode.Callback defined above
////				mActionMode = MapActivity.this
////						.startActionMode(mActionModeCallback);
//				Toast.makeText(thisActivity, "onGpsStatusChanged", Toast.LENGTH_LONG).show();
//				Checkins checkin = mList.get(position);
//				startEditCheckinIntent(checkin.getId());
//				view.setSelected(true);
//				return;
//			}
//		});

	}

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

	private List<Checkins> getModel(JSONArray responses) {
		for (int i = 0; i < responses.length(); i++) {
			try {
				JSONObject response = responses.getJSONObject(i);
				mList.add(get(response));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// Initially select one of the items
//		list.get(1).setSelected(true);
		return mList;
	}

	private Checkins get(JSONObject response) throws JSONException {
		return new Checkins(response);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_checkin:
			startAddCheckinIntent();
			return true;
		case R.id.menu_edit_checkin:
			startEditCheckinIntent();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void addCheckin(View view) {
		startAddCheckinIntent();
	}

	private void startAddCheckinIntent() {
		mLastLocation = mLocationManager.getLastKnownLocation(provider);
		Intent addCheckinIntent = new Intent(this, AddActivity.class);
		addCheckinIntent.putExtra(Checkins.LATITUDE,
				Double.toString(mLastLocation.getLatitude()));
		addCheckinIntent.putExtra(Checkins.LONGITUDE,
				Double.toString(mLastLocation.getLongitude()));
		startActivity(addCheckinIntent);
	}

	public void editCheckin(View view) {
		startEditCheckinIntent();

	}

	private void startEditCheckinIntent(String checkinId) {
		Intent editCheckinIntent = new Intent(this, EditActivity.class);
		editCheckinIntent.putExtra(Checkins.ID, checkinId);
		editCheckinIntent.putExtra(Checkins.LATITUDE,
				Double.toString(mLastLocation.getLatitude()));
		editCheckinIntent.putExtra(Checkins.LONGITUDE,
				Double.toString(mLastLocation.getLongitude()));
		startActivity(editCheckinIntent);
	}

	private void startEditCheckinIntent() {
		Intent editCheckinIntent = new Intent(this, EditActivity.class);
		editCheckinIntent.putExtra(Checkins.ID, "5176a2edc0acc81516000002");
		editCheckinIntent.putExtra(Checkins.LATITUDE,
				Double.toString(mLastLocation.getLatitude()));
		editCheckinIntent.putExtra(Checkins.LONGITUDE,
				Double.toString(mLastLocation.getLongitude()));
		startActivity(editCheckinIntent);
	}

	@Override
	public void onLocationChanged(Location location) {
		mLastLocation = location;

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGpsStatusChanged(int event) {
		Toast.makeText(this, "onGpsStatusChanged", Toast.LENGTH_LONG).show();
		mStatus = mLocationManager.getGpsStatus(mStatus);
		switch (event) {
		case GpsStatus.GPS_EVENT_STARTED:

			break;

		case GpsStatus.GPS_EVENT_STOPPED:

			break;

		case GpsStatus.GPS_EVENT_FIRST_FIX:

			break;

		case GpsStatus.GPS_EVENT_SATELLITE_STATUS:

			break;

		}

	}

	// private ActionMode.Callback mActionModeCallback = new
	// ActionMode.Callback() {
	//
	// // Called when the action mode is created; startActionMode() was called
	// public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	// // Inflate a menu resource providing context menu items
	// MenuInflater inflater = mode.getMenuInflater();
	// // Assumes that you have "contexual.xml" menu resources
	// inflater.inflate(R.menu.rowselection, menu);
	// return true;
	// }
	//
	// // Called each time the action mode is shown. Always called after
	// // onCreateActionMode, but
	// // may be called multiple times if the mode is invalidated.
	// public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	// return false; // Return false if nothing is done
	// }
	//
	// // Called when the user selects a contextual menu item
	// public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.menuitem1_show:
	// show();
	// // Action picked, so close the CAB
	// mode.finish();
	// return true;
	// default:
	// return false;
	// }
	// }
	//
	// // Called when the user exits the action mode
	// public void onDestroyActionMode(ActionMode mode) {
	// mActionMode = null;
	// selectedItem = -1;
	// }
	// };
	//
	// private void show() {
	// Toast.makeText(MapActivity.this, String.valueOf(selectedItem),
	// Toast.LENGTH_LONG).show();
	// }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    	FragmentManager fragmentManager = getFragmentManager();  
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
    	mMap = mapFragment.getMap();
//		mMap.getMyLocation();

		try {
			TrainingRESTClient.get("checkins/json", null,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONArray responses) {
							// Set Up list view
							List<Checkins> list = getModel(responses);
							for (Checkins checkin : list) {
								mMap.addMarker(new MarkerOptions().position(new LatLng(checkin.getMlatitude().doubleValue(), checkin.getMlongitude().doubleValue())).title(checkin.getTitle()));
							}
							MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(
									thisActivity, list);
//							setListAdapter(adapter);

							super.onSuccess(responses);
						}
					});
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

}
