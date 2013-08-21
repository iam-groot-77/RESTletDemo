package com.netsuite.training;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONPrettyPrint;
import org.json.simple.JSONValue;

public class JObject
{
	private boolean submitted = false;
	private JSONObject obj;
	private JSONObject changes = new JSONObject();
	// @formatter:off
	private String staleMessage = "ObjectStaleException: \n" +
			  					  "     Attempt to get or set a field has failed\n" +
			  					  "     Object has been submitted and is stale; " + "" +
			  					  		"construct a new object to do additional proceessing\n";
	// @formatter:on

	private ObjectStaleException ex = new ObjectStaleException(staleMessage);

	@SuppressWarnings({ "unchecked" })
	public JObject(String recordType, String id) throws Exception
	{
		changes.put("recordtype", recordType);
		changes.put("id", id);

		String jsonString = HTTPMethods.getRecord(recordType, id);
		obj = (JSONObject)JSONValue.parse(jsonString);

		prettyPrint(obj);
	}

	public String getFieldValue(String field) throws Exception
	{
		if (submitted)
		{
			throw ex;
		}

		String returnString = "";
		if (changes.containsKey(field))
		{
			returnString = (changes.get(field)).toString();
		}
		else
		{
			returnString = (obj.get(field)).toString();
		}
		if (returnString.indexOf("{") >= 0)
		{
			JSONObject objLocal = (JSONObject)JSONValue.parse(returnString);
			returnString = (objLocal.get("internalid")).toString();
		}
		else
		{
			if (returnString == "true")
			{
				returnString = "T";
			}
			else if (returnString == "false")
			{
				returnString = "F";
			}
		}
		return returnString;
	}

	public String getFieldText(String field) throws Exception
	{
		if (submitted)
		{
			throw ex;
		}

		if (changes.containsKey(field))
		{
			return "";
		}
		String returnString = (obj.get(field)).toString();
		if (returnString.indexOf("{") >= 0)
		{
			JSONObject objLocal = (JSONObject)JSONValue.parse(returnString);
			returnString = (objLocal.get("name")).toString();
		}
		else
		{
			returnString = "";
		}
		return returnString;
	}

	@SuppressWarnings("unchecked")
	public void setFieldValue(String field, String value) throws Exception
	{
		if (submitted)
		{
			throw ex;
		}
		if (changes.containsKey(field))
		{
			changes.remove(field);
		}
		changes.put(field, value);
	}

	public String submit() throws Exception
	{
		submitted = true;
		StringWriter out = new StringWriter();
		changes.writeJSONString(out);
		String json = out.toString();
		print("JSON to PUT:");
		prettyPrint(changes);
		return HTTPMethods.putRecord(json);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void prettyPrint(JSONObject obj) throws Exception
	{
		JSONPrettyPrint prettyPrinter = new JSONPrettyPrint();
		Iterator entries = obj.entrySet().iterator();
		while (entries.hasNext())
		{
			Map.Entry entry = (Map.Entry)entries.next();
			String key = (String)entry.getKey();
			prettyPrinter.put(key, obj.get(key));
		}
		StringWriter out = new StringWriter();
		prettyPrinter.writeJSONString(out);
		String json = out.toString();
		print(json);

	}

	private void print(String in)
	{
		System.out.println(in);
	}
}
