package geoLocalization;

/**
 * definition of some constants to handle:<br>
 *     results of the operations<br>
 *     keys for object-passing between intentService and Activities (or both Activities)<br>
 */
public final class Constants {
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.example.com.geolocator";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";
}