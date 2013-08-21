package com.netsuite.training;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HTTPMethods
{
	public static String restletURL;
	public static String account;
	public static String email;
	public static String password;

	public static String getRecord(String recordType, String id)
			throws Exception
	{
		String getResponse = "";

		// @formatter:off
		String auth = "NLAuth nlauth_account=" + account + "," +
					   "nlauth_email=" + email + "," + 
					   "nlauth_signature=" + password + "," + 
					   "nlauth_role=3";
		// @formatter:on

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(restletURL + "&recordtype=" + recordType
				+ "&id=" + id);

		httpGet.addHeader("Authorization", auth);
		httpGet.addHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if(httpEntity != null)
		{
			getResponse = EntityUtils.toString(httpEntity);
		}
		return (getResponse);
	}

	public static String putRecord(String json) throws Exception
	{
		String putResponse = "";

		// @formatter:off
		String auth = "NLAuth nlauth_account=" + account + "," +
					   "nlauth_email=" + email + "," + 
					   "nlauth_signature=" + password + "," + 
					   "nlauth_role=3";
		// @formatter:on

		HttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = new HttpPut(restletURL);

		HttpEntity reqEntity = new StringEntity(json);
		httpPut.setEntity(reqEntity);
		httpPut.addHeader("Authorization", auth);
		httpPut.addHeader("Content-Type", "application/json");

		HttpResponse response = httpClient.execute(httpPut);
		HttpEntity resEntity = response.getEntity();
		if(resEntity != null)
		{
			putResponse = EntityUtils.toString(resEntity);
		}
		return (putResponse);
	}
}
