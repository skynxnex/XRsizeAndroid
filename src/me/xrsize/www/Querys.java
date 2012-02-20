package me.xrsize.www;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
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
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class Querys extends Activity {
	
	public String login(String name, String pass) {
		pass = encryptPassword(pass);
		String result = query(name, pass);
		return result;
	}
	
	private static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    return formatter.toString();
	}

	
	private String query(String name, String pass){
		
		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		
		// Creating HTTP client
				HttpClient httpClient = new DefaultHttpClient();
				// Creating HTTP Post
				HttpPost httpPost = new HttpPost(
						"http://10.0.2.2/user/remotelogin");

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
					Log.e("JSON", json);
				} catch (Exception e) {
					Log.e("Buffer Error", "Error converting result " + e.toString());
				}
		return json;
	}

}
