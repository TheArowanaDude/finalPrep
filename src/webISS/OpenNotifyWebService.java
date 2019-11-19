package webISS;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenNotifyWebService {
  static String parseJSON(String response) {

    JSONObject json = new JSONObject(response);
    if (json.getString("message").equals("success")) {
      JSONArray responseArray = json.getJSONArray("response");
      return String.valueOf(responseArray.getJSONObject(0)
        .getInt("risetime"));
    }
    else {
      return json.getString("reason");
    }
  }
}
