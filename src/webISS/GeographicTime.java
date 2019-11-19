package webISS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import net.iakovlev.timeshape.TimeZoneEngine;
import webISS.OpenNotifyWebService.*;

public class GeographicTime {
  private ISSWebService issWebService;

  public GeographicTime(double latitude, double longitude) throws Exception {
    System.out.println(computeTimeOfFlyOver(latitude, longitude));
  }

  public GeographicTime(ISSWebService mockWebService) {
    mockWebService = issWebService;

  }

  static String convertTimestampToUTCTime(long timestamp) {
    SimpleDateFormat dataFormat = new SimpleDateFormat("y-MM-dd h:mm a");
    TimeZone UTCTimeZone = TimeZone.getTimeZone("UTC");
    dataFormat.setTimeZone(UTCTimeZone);
    return dataFormat.format(new Date(timestamp * 1000L));
  }

  static String convertTimeStampToTimeAtLatLon(
    long timestamp, double latitude, double longitude) {

    SimpleDateFormat dataFormat = new SimpleDateFormat("y-MM-dd h:mm a");
    TimeZoneEngine timeZoneLocator = TimeZoneEngine.initialize();
    Optional<ZoneId> zoneID = timeZoneLocator.query(latitude, longitude);
    TimeZone timeZoneToConvertTo = TimeZone.getTimeZone(zoneID.get());
    dataFormat.setTimeZone(timeZoneToConvertTo);

    return dataFormat.format(new Date(timestamp * 1000L));
  }


  public String computeTimeOfFlyOver(double latitude, double longitude) throws Exception {
    try {
      return convertTimeStampToTimeAtLatLon(
        ISSWebService.fetchIssFlyOverData(latitude, longitude),
        latitude, longitude);

    } catch (RuntimeException | IOException ex) {
      return ex.getMessage();
    }
  }

}