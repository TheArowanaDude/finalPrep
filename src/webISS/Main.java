package webISS;

public class Main {
  public static void main(String[] args) throws Exception {
    double latitude = 229.72167;
    double longitude = -95.343631;

    ISSWebService service = new ISSInformationService();
    GeographicTime geographicTime = new GeographicTime(service);
    System.out.println(geographicTime.computeTimeOfFlyOver(latitude, longitude));
  }
}
