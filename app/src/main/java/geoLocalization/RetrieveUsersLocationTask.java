package geoLocalization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.literarium.Globals;
import com.example.com.literarium.HttpRequest;
import com.example.com.literarium.IListableActivity;
import com.example.com.literarium.RequestManager;
import com.example.com.literarium.RequestType;
import com.example.com.literarium.XMLUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.InvalidClassException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

public class RetrieveUsersLocationTask extends AsyncTask<Void, Void, List<UserData>> {

    @SuppressLint("StaticFieldLeak")
    private Context ref;

    public RetrieveUsersLocationTask(Context main) {
        this.ref = main;
    }

    /**
     * public IP of the server
     */
    private final String SERVER_IP = /*"95.236.93.207"*/"192.168.1.7";

    private final int SERVER_PORT = 6000;   // TCP port

    private final String REQUEST_NAME = "geolocalizationReport";
    private final int CONN_TIMEOUT = 5000;  // ms

    private Exception lastThrown;

    @Override
    protected List<UserData> doInBackground(Void... voids) {

        Log.d("RetrieveUsrLocationTask", "background service started");

        List<UserData> userDataList = new ArrayList<>();

        // send request
        // TODO: send XML data instead of raw string.
        /*Socket s = new Socket(SERVER_IP, SERVER_PORT);
        s.setSoTimeout(CONN_TIMEOUT);
        PrintWriter out =  new PrintWriter( s.getOutputStream(), true );
        out.println(REQUEST_NAME);

        // get data
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xmlResponse = docBuilder.parse(s.getInputStream());
        s.close();*/

        //Document xmlResponse = null;
        String requestUrl = null;
        try {
            requestUrl = RequestManager.formatRequest(RequestType.GEO_REPORT, Globals.getInstance().getUserLocalData().getUserId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpRequest request = new HttpRequest(requestUrl, HttpRequest.HttpRequestMethod.GET);
        //Log.d("RetrieveUsrLocationTask", requestUrl);
        request.send();
        Document xmlResponse = request.getResult();


        Log.d("RetrieveUsrLocationTask", "xml response: " + xmlResponse.getDocumentElement().getTextContent());
        Log.d("RetrieveUsrLocationTask", "url: " + requestUrl);

        // process data
        if(ref instanceof Activity) {

            Activity callerAct = (Activity) ref;

            // check if the passed context is an activity which can display a list of items
            // that is, if it implements IListableActivity
            if(IListableActivity.class.isAssignableFrom(callerAct.getClass())) {

                IListableActivity concreteAct = (IListableActivity)callerAct;

                // aid lists
                List<Node> addressList = null;
                List<Node> locationList = null;
                List<Node> usernameList = null;

                // extract relevant data from the XML response
                try {
                    addressList = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(xmlResponse, "/response/rilevation/streetAddress"));
                    locationList = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(xmlResponse, "/response/rilevation/location"));
                    usernameList = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(xmlResponse, "response/rilevation/username"));
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < addressList.size(); ++i) {

                    Log.d("RetrieveUsrLocationTask", "address: " + addressList.get(i).getTextContent());
                    Log.d("RetrieveUsrLocationTask", "location: " + locationList.get(i).getTextContent());

                    Element locationNode = (Element)locationList.get(i);
                    String lat = locationNode.getElementsByTagName("latitude").item(0).getTextContent();
                    String lon = locationNode.getElementsByTagName("longitude").item(0).getTextContent();
                    Log.d("RetrieveUsrLocationTask", "raw location: " + lat+", "+lon);

                    Location tmpLoc = new Location("");
                    tmpLoc.setLatitude(Double.parseDouble(lat));
                    tmpLoc.setLongitude(Double.parseDouble(lon));

                    String username = ((Element)usernameList.get(i)).getTextContent().trim();

                    userDataList.add(new UserData(username, tmpLoc, addressList.get(i).getTextContent()));
                }

                Log.d("RetrieveUsrLocationTask", "final data: " + userDataList.toString());
            }
        }
        else {
            Log.d("RetrieveUsrLocationTask", "ERROR: the passed context is not a listableActivity, or is not possible to cast");
            lastThrown = new InvalidClassException("ERROR: the passed context is not a listableActivity, or is not possible to cast");
        }

        return userDataList;
    }

    @Override
    protected void onPostExecute(List<UserData> ris) {

        // check if the task has thrown any exception and, if yes, rethrow
        if(lastThrown != null) {
            if(lastThrown instanceof SocketTimeoutException) {

                ((GeoLocalizationActivity)ref).handleSocketTimeout((SocketTimeoutException)lastThrown);
            }
        }

        // don't need to check the actual type of ref because the doInBackground() method already does the job
        IListableActivity concreteAct = (IListableActivity)ref;

        concreteAct.populateList(ris);
        concreteAct.populateMapWithMarkers();
    }
}
