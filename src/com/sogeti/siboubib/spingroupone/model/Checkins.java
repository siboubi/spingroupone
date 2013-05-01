package com.sogeti.siboubib.spingroupone.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Checkins {
	public final static String LATITUDE = "x_coord";
	public final static String LONGITUDE = "y_coord";
	public final static String TITLE = "title";
	public final static String LOCATION_NAME = "location_name";
	public static final String ID = "id";
	
	private String id;
	private Double mlatitude;
	private Double mlongitude;
	private String title;
	private String locationName;

	public Checkins(Double mlatitude, Double mlongitude, String title,
			String locationName) {
		super();
		this.mlatitude = mlatitude;
		this.mlongitude = mlongitude;
		this.title = title;
		this.locationName = locationName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Checkins(JSONObject response) throws JSONException {
		super();
		this.id = response.getString("_id");
		this.mlatitude = response.getDouble(Checkins.LATITUDE);
		this.mlongitude = response.getDouble(Checkins.LONGITUDE);
		this.title = response.getString(Checkins.TITLE);
		this.locationName = response.getString(Checkins.LATITUDE);
	}
	public Double getMlatitude() {
		return mlatitude;
	}
	public void setMlatitude(Double mlatitude) {
		this.mlatitude = mlatitude;
	}
	public Double getMlongitude() {
		return mlongitude;
	}
	public void setMlongitude(Double mlongitude) {
		this.mlongitude = mlongitude;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
