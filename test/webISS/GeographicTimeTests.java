package webISS;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static webISS.GeographicTime.*;

public class GeographicTimeTests {

  private static GeographicTime geographicTime;
  private static ISSWebService mockWebService;

  @BeforeAll
  private static void init() throws Exception {
    mockWebService = Mockito.mock(ISSWebService.class);
    geographicTime = new GeographicTime(mockWebService);
  }


  @Test
  void canaryTest() {
    assertTrue(true);
  }

  @Test
  void getUTCTimeTest() {
    assertAll(
      () -> assertEquals(new String("1970-01-01 12:00 AM"),
        convertTimestampToUTCTime(1)),
      () -> assertEquals(new String("1970-01-01 12:00 AM"),
        convertTimestampToUTCTime(2)),
      () -> assertEquals(new String("1970-01-01 12:01 AM"),
        convertTimestampToUTCTime(60)),
      () -> assertEquals(new String("2019-09-22 5:39 AM"),
        convertTimestampToUTCTime(1569130749))
    );
  }

  @Test
  void timeStampForHoustonLatLonTest() {
    assertAll(
      () -> assertEquals("1969-12-31 6:00 PM",
        convertTimeStampToTimeAtLatLon(1, 29.7604, -95.3698)),
      () -> assertEquals("1969-12-31 6:00 PM",
        convertTimeStampToTimeAtLatLon(2, 29.7604, -95.3698)),
      () -> assertEquals("1969-12-31 6:01 PM",
        convertTimeStampToTimeAtLatLon(60, 29.7604, -95.3698)),
      () -> assertEquals("2019-09-22 12:39 AM",
        convertTimeStampToTimeAtLatLon(1569130749, 29.7604, -95.3698))
    );
  }

  @Test
  void timeStampForNewYorkCityLatLonTest() {
    assertAll(
      () -> assertEquals("1969-12-31 7:00 PM",
        convertTimeStampToTimeAtLatLon(1, 40.7128, -74.0060)),
      () -> assertEquals("1969-12-31 7:00 PM",
        convertTimeStampToTimeAtLatLon(2, 40.7128, -74.0060)),
      () -> assertEquals("1969-12-31 7:01 PM",
        convertTimeStampToTimeAtLatLon(60, 40.7128, -74.0060)),
      () -> assertEquals("2019-09-22 1:39 AM",
        convertTimeStampToTimeAtLatLon(1569130749, 40.7128, -74.0060))
    );
  }

  @Test
  void timeStampForSingaporeLatLonTest() {
    assertAll(
      () -> assertEquals("1970-01-01 7:30 AM",
        convertTimeStampToTimeAtLatLon(1, 1.3521, 103.8198)),
      () -> assertEquals("1970-01-01 7:30 AM",
        convertTimeStampToTimeAtLatLon(2, 1.3521, 103.8198)),
      () -> assertEquals("1970-01-01 7:31 AM",
        convertTimeStampToTimeAtLatLon(60, 1.3521, 103.81988)),
      () -> assertEquals("2019-09-22 1:39 PM",
        convertTimeStampToTimeAtLatLon(1569130749, 1.3521, 103.8198))
    );
  }

  @Test
  public void passLatAndLonToFetchIssFlyOverDataFuncTest() throws Exception {
    when(mockWebService.fetchIssFlyOverData(29.7604,-95.3698)).thenReturn(2L);

    geographicTime.computeTimeOfFlyOver(29.7604, -95.3698);

    verify(mockWebService).fetchIssFlyOverData(29.7604, -95.3698);
  }

  @Test
  public void returnsTimeBasedOffTimestampReturnedByFetchISSFlyOverDataTest() throws Exception  {
    when(mockWebService.fetchIssFlyOverData(29.7604,-95.3698)).thenReturn(2L);

    String result = geographicTime.computeTimeOfFlyOver(29.7604, -95.3698);

    assertEquals("1969-12-31 6:00 PM", result);
  }

  @Test
  public void reportErrorDueToErrorFromWebServiceTest() throws Exception {
    when(mockWebService.fetchIssFlyOverData(100, -95.3698))
      .thenThrow(new RuntimeException("Lat out of range"));

    String result = geographicTime.computeTimeOfFlyOver(100, -95.3698);

    assertEquals("Lat out of range", result);
  }

  @Test
  public void reportNetworkFailureTest() throws Exception {
    when(mockWebService.fetchIssFlyOverData(0, 0))
      .thenThrow(new RuntimeException("Network Error"));

    String result = geographicTime.computeTimeOfFlyOver(0, 0);

    assertEquals("Network Error", result);
  }

}
