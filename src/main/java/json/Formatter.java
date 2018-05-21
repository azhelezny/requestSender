package json;

import org.json.JSONObject;

import java.text.ParseException;

public class Formatter {
    private JSONObject jsonObject;

    public Formatter(String json) throws ParseException {
        jsonObject = new JSONObject(json);
    }

    public String getFormattedText() {
        return jsonObject.toString(6);
    }
}
