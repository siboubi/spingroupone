package com.sogeti.siboubib.spingroupone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sogeti.siboubib.spingroupone.model.Checkins;

public class EditActivity extends BaseActivity {

	private Context thisActivity;
	private String url = "checkins/_id/json";
	private String mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Change submit button label to "Edit"
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setText(R.string.edit_button);
        submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				editCheckin(view);
			}
		});
        thisActivity = this;
        
		// Retrieve location manager for GPS update
		Intent intent = this.getIntent();
		Bundle extras = intent.getExtras();
		mId = extras.getString(Checkins.ID);
		url = url.replace("_id", mId);
		
		final TextView whatTextView = (TextView)findViewById(R.id.editTextWhat);
		final TextView whereTextView = (TextView)findViewById(R.id.editTextWhere);
		
		try {
			TrainingRESTClient.get(url, null, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject response) {
					try {
						whereTextView.setText(response.getString(Checkins.LOCATION_NAME));
						whatTextView.setText(response.getString(Checkins.TITLE));
						Toast.makeText(thisActivity, "Current Lat:"+Double.toString(response.getDouble(Checkins.LATITUDE))+" Long:"+Double.toString(response.getDouble(Checkins.LONGITUDE)), Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(response.toString());
					super.onSuccess(response);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}
		
	public void editCheckin(View view){
    	ConnectivityManager connectivitynManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = connectivitynManager.getActiveNetworkInfo();
    	if ((networkInfo != null) && (networkInfo.isConnected())) {
			EditText title = (EditText) findViewById(R.id.editTextWhere);
			EditText locationName = (EditText) findViewById(R.id.editTextWhat);
			RequestParams params = new RequestParams();
			params.put(Checkins.ID, mId);
			params.put(Checkins.TITLE, title.getText().toString());
			params.put(Checkins.LOCATION_NAME, locationName.getText().toString());
			params.put(Checkins.LATITUDE, mlatitude);
			params.put(Checkins.LONGITUDE, mlongitude);
			url = "checkins/json";
			try {
				TrainingRESTClient.put(thisActivity, url, params, new JsonHttpResponseHandler() {
					@Override
					public void onStart() {
						Toast.makeText(thisActivity, getString(R.string.editcheckin_start), Toast.LENGTH_LONG).show();
						super.onStart();
					}
					@Override
					public void onSuccess(JSONObject response) {
						Intent intent = new Intent(thisActivity, MapActivity.class);
						Toast.makeText(thisActivity, response.toString(), Toast.LENGTH_LONG).show();
						startActivity(intent);
						super.onSuccess(response);
					}
					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						Intent intent = new Intent(thisActivity, MapActivity.class);
						Toast.makeText(thisActivity, "JSONObject: "+Integer.toString(statusCode)+response.toString(), Toast.LENGTH_LONG).show();
						startActivity(intent);
						super.onSuccess(response);
					}
					@Override
					public void onSuccess(String response) {
						Intent intent = new Intent(thisActivity, MapActivity.class);
						Toast.makeText(thisActivity, "String: "+response, Toast.LENGTH_LONG).show();
						startActivity(intent);
						super.onSuccess(response);
					}
					@Override
					public void onSuccess(int statusCode, String response) {
						Intent intent = new Intent(thisActivity, MapActivity.class);
						Toast.makeText(thisActivity, "String: "+Integer.toString(statusCode)+response, Toast.LENGTH_LONG).show();
						startActivity(intent);
						super.onSuccess(statusCode, response);
					}
					@Override
					public void onSuccess(JSONArray response) {
						Intent intent = new Intent(thisActivity, MapActivity.class);
						Toast.makeText(thisActivity, "JSONArray: "+response.toString(), Toast.LENGTH_LONG).show();
						startActivity(intent);
						super.onSuccess(response);
					}
					@Override
					public void onSuccess(int statusCode, JSONArray response) {
						Intent intent = new Intent(thisActivity, MapActivity.class);
						Toast.makeText(thisActivity, "JSONArray: "+Integer.toString(statusCode)+response.toString(), Toast.LENGTH_LONG).show();
						startActivity(intent);
						super.onSuccess(response);
					}
					@Override
					public void onFailure(Throwable e,
							JSONObject response) {
						Toast.makeText(thisActivity, getString(R.string.editcheckin_error)+": "+e.getMessage(), Toast.LENGTH_LONG).show();
						super.onFailure(e, response);
					}
					@Override
					public void onFailure(Throwable e, JSONArray response) {
						Toast.makeText(thisActivity, getString(R.string.editcheckin_error)+": "+e.getMessage(), Toast.LENGTH_LONG).show();
						super.onFailure(e, response);
					}
					@Override
					public void onFailure(Throwable e, String response) {
						Toast.makeText(thisActivity, getString(R.string.editcheckin_error)+": "+e.getMessage(), Toast.LENGTH_LONG).show();
						super.onFailure(e, response);
					}
					@Override
					public void onFinish() {
						Toast.makeText(thisActivity, getString(R.string.editcheckin_finish), Toast.LENGTH_LONG).show();
						super.onFinish();
					}
				});
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
    	else {
			Toast.makeText(this, getString(R.string.cant_connect_error), Toast.LENGTH_LONG).show();
		}
	}

}
