package me.xrsize.www;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RemoteDB extends Activity {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	TextView display;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.remotedbtest);

		// Creating HTTP client
		HttpClient httpClient = new DefaultHttpClient();
		// Creating HTTP Post
		HttpPost httpPost = new HttpPost(
				"http://10.0.2.2/android");

		// Building post parameters
		// key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("email", "user@gmail.com"));
		nameValuePair.add(new BasicNameValuePair("message",
				"Hi, trying Android HTTP post!"));

		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}

		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			is = httpEntity.getContent();
			// writing response to log
			Log.d("Http Response:", response.toString());
		} catch (ClientProtocolException e) {
			// writing exception to log
			e.printStackTrace();
		} catch (IOException e) {
			// writing exception to log
			e.printStackTrace();

		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
			// Log.e("JSON", json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
						
			String thisId = jObj.getString("id");
			display = (TextView) findViewById(R.id.tvId);
			display.setText("Id: " + thisId);
			
			String thisName = jObj.getString("name");
			display = (TextView) findViewById(R.id.tvName);
			display.setText("Namn: " + thisName);
			
			String thisEname = jObj.getString("ename");
			display = (TextView) findViewById(R.id.tvEname);
			display.setText("Ename: " + thisEname);
			
			String thisTime = jObj.getString("time");
			display = (TextView) findViewById(R.id.tvTime);
			display.setText("Tid: " + thisTime);
			
			String thisDate = jObj.getString("date");
			display = (TextView) findViewById(R.id.tvDate);
			display.setText("Datum: " + thisDate);
			
			
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
	}
}