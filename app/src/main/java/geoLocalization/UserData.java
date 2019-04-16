package geoLocalization;
import android.location.Location;

import com.example.com.literarium.XMLUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

/**
 * this class represents the data sent to the server
 * to build the location report.
 */
public class UserData implements Cloneable, XMLSerializable {

    /**
     * geographical coordinates of user's location
     */
    private Location location;

    /**
     * city address of the user.
     */
    private String address;

    /**
     * username
     */
    private String name;

    public UserData(String name, Location location, String address) {
        this.location = location;
        this.address = address;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "location=" + location +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData that = (UserData) o;
        return Objects.equals(location, that.location) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, address);
    }

    @Override
    protected Object clone() {

        try {
            UserData cloned = (UserData)super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public void decode(Document doc) {

    }

    @Override
    public Element encode() {

        /*Document doc = XMLUtils.getNewDocInstance();

        Element root = doc.createElement("phoneData");

        Element userName = doc.createElement("userName");
        userName.setTextContent("JustAPhone");

        Element geoTag = doc.createElement("geoTag");
        geoTag.setAttribute("latitude", String.valueOf(location.getLatitude()));
        geoTag.setAttribute("longitude", String.valueOf(location.getLongitude()));

        Element streetAddress = doc.createElement("streetAddress");
        streetAddress.setTextContent(address);

        root.appendChild(userName);
        root.appendChild(geoTag);
        root.appendChild(streetAddress);

        return root;*/
        Document doc = XMLUtils.getNewDocInstance();

        Element root = doc.createElement("Rilevation");

        Element addr = doc.createElement("streetAddress");
        addr.setTextContent(this.address);

        Element gl = doc.createElement("location");

        Element glLat = doc.createElement("latitude");
        glLat.setTextContent(String.valueOf(this.location.getLatitude()));

        Element glLon = doc.createElement("longitude");
        glLon.setTextContent(String.valueOf(this.location.getLongitude()));

        gl.appendChild(glLat);
        gl.appendChild(glLon);

        root.appendChild(addr);
        root.appendChild(gl);

        return root;
    }
}
