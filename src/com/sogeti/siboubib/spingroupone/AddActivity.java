package com.sogeti.siboubib.spingroupone;

import org.json.JSONArray;
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
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sogeti.siboubib.spingroupone.model.Checkins;

public class AddActivity extends BaseActivity {

    private Context thisActivity;
    private String url = "checkins/json";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setText(R.string.add_button);
        submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				addCheckin(view);
				
			}
		});
        thisActivity = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }
    
    public void addCheckin(View view){
    	ConnectivityManager connectivitynManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = connectivitynManager.getActiveNetworkInfo();
    	if ((networkInfo != null) && (networkInfo.isConnected())) {
			EditText title = (EditText) findViewById(R.id.editTextWhat);
			EditText locationName = (EditText) findViewById(R.id.editTextWhere);
			RequestParams params = new RequestParams();
			params.put(Checkins.TITLE, title.getText().toString());
			params.put(Checkins.LOCATION_NAME, locationName.getText().toString());
			params.put(Checkins.LATITUDE, mlatitude);
			params.put(Checkins.LONGITUDE, mlongitude);
			try {
				TrainingRESTClient.post(this, url , params, new JsonHttpResponseHandler() {
					@Override
					public void onStart() {
						Toast.makeText(thisActivity, getString(R.string.addcheckin_start), Toast.LENGTH_LONG).show();
						super.onStart();
					}
					@Override
					public void onSuccess(JSONObject response) {
						Toast.makeText(thisActivity, response.toString(), Toast.LENGTH_LONG).show();
						Intent intent = new Intent(thisActivity, MapActivity.class);
						startActivity(intent);
						super.onSuccess(response);
					}
					@Override
					public void onFailure(Throwable e,
							JSONArray response) {
						Toast.makeText(thisActivity, getString(R.string.addcheckin_error)+": "+e.getMessage(), Toast.LENGTH_LONG).show();
						super.onFailure(e, response);
					}
					@Override
					public void onFinish() {
						Toast.makeText(thisActivity, getString(R.string.addcheckin_finish), Toast.LENGTH_LONG).show();
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
