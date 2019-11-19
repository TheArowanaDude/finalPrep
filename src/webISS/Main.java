package webISS;

public class Main {
  public static void main(String[] args) throws Exception {
    double latitude = Double.parseDouble(args[0]);
    double longitude = Double.parseDouble(args[1]);
    GeographicTime geographicTime = new GeographicTime(latitude, longitude);

  }
}
