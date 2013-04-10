package com.sogeti.siboubib.spingroupone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addCheckin(View view) {
		Intent addCheckinIntent = new Intent(getBaseContext(), AddActivity.class);
		startActivity(addCheckinIntent);
	}
	
	public void editCheckin(View view) {
		Intent editCheckinIntent = new Intent(getBaseContext(), EditActivity.class);
		startActivity(editCheckinIntent);
		
	}

}
