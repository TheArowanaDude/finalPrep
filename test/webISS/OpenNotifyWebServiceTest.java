package webISS;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static webISS.OpenNotifyWebService.*;

public class OpenNotifyWebServiceTest {

  @Test
  void JSONStringParseGivenLatLon(){
    String response = "{\n" +
      "  \"message\": \"success\", \n" +
      "  \"request\": {\n" +
      "    \"altitude\": 100, \n" +
      "    \"datetime\": 1570072156, \n" +
      "    \"latitude\": 29.72167, \n" +
      "    \"longitude\": -95.343631, \n" +
      "    \"passes\": 1\n" +
      "  }, \n" +
      "  \"response\": [\n" +
      "    {\n" +
      "      \"duration\": 599, \n" +
      "      \"risetime\": 1570072243\n" +
      "    }\n" +
      "  ]\n" +
      "}";

    assertEquals("1570072243", OpenNotifyWebService.parseJSON(response));
  }

  @Test
  void JSONStringParseGivenADifferentLatLon(){
    String response = "{\n" +
      "  \"message\": \"success\", \n" +
      "  \"request\": {\n" +
      "    \"altitude\": 100, \n" +
      "    \"datetime\": 1570075183, \n" +
      "    \"latitude\": 59.72167, \n" +
      "    \"longitude\": 59.343631, \n" +
      "    \"passes\": 1\n" +
      "  }, \n" +
      "  \"response\": [\n" +
      "    {\n" +
      "      \"duration\": 469, \n" +
      "      \"risetime\": 1570099199\n" +
      "    }\n" +
      "  ]\n" +
      "}\n";

    assertEquals("1570099199", OpenNotifyWebService.parseJSON(response));
  }

  @Test
  void giveErrorForTooLargeLat(){
    String response = "{\n" +
      "  \"message\": \"failure\", \n" +
      "  \"reason\": \"Latitude must be number between -90.0 and 90.0\"\n" +
      "}\n";

    assertEquals("Latitude must be number between -90.0 and 90.0",
      OpenNotifyWebService.parseJSON(response));
  }

  @Test
  void giveErrorForTooLargeLon(){
    String response = "{\n" +
      "  \"message\": \"failure\", \n" +
      "  \"reason\": \"Longitue must be number between -180.0 and 180.0\"\n" +
      "}\n";

    assertEquals("Longitue must be number between -180.0 and 180.0",
      OpenNotifyWebService.parseJSON(response));
  }
}