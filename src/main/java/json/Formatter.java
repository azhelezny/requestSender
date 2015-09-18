package json;

import java.text.ParseException;

import org.json.JSONObject;

public class Formatter
{
	private JSONObject jsonObject;
	
	public Formatter(String json) throws ParseException
	{
			jsonObject = new JSONObject(json);
	}
	
	public String getFormattedText()
	{
		return jsonObject.toString(6);
	}
}