package webISS;
import org.json.JSONObject;
import webISS.OpenNotifyWebService.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface ISSWebService {

  long fetchIssFlyOverData(double latitude, double longitude) throws Exception;
}