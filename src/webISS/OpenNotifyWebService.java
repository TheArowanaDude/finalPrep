package webISS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenNotifyWebService implements ISSWebService{
  public String fetchJSON(double latitude, double longitude) throws IOException {
    String requestURL = "http://api.open-notify.org/iss-pass.json?lat=" + latitude + "&lon=" + longitude + "&n=1";
    URL request = new URL(requestURL);
    HttpURLConnection connection = (HttpURLConnection) request.openConnection();
    connection.setRequestMethod("GET");
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuffer response = new StringBuffer();
    String inputLine;

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }

    return response.toString();
  }



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

  public long fetchIssFlyOverData(double latitude, double longitude) throws IOException {

    return Long.parseLong(
            parseJSON(
                    fetchJSON(latitude,longitude)));
  }
}
