package geoLocalization;
import android.location.Location;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;

public class UserData implements Cloneable, XMLSerializable {

    /**
     * geographical coordinates of user's location
     */
    private Location location;

    /**
     * city address of the user.
     */
    private String address;

    public UserData(Location location, String address) {
        this.location = location;
        this.address = address;
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

        Document doc = XMLUtils.getNewDocInstance();

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

        return root;
    }
}
