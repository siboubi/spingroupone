package com.sogeti.siboubib.spingroupone;

import org.apache.http.Header;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TrainingRESTClient {
	private static final String BASE_URL = "http://sogeti-mobile-training.rs.af.cm/";
	
	private static AsyncHttpClient client = new AsyncHttpClient();

	
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(context, getAbsoluteUrl(url), new Header[0], params, "application/x-www-form-urlencoded", responseHandler);
	}
	
	public static void put(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.put(context, getAbsoluteUrl(url), params, responseHandler);
	}
	
	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}

}
