package geoLocalization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
    private final String SERVER_IP = "95.236.97.101";
    private final int SERVER_PORT = 6000;
    private final String REQUEST_NAME = "GEOLOCALIZATION";

    private Exception lastThrown;

    @Override
    protected List<UserData> doInBackground(Void... voids) {

        Log.d("RetrieveUsrLocationTask", "background service started");

        List<UserData> userDataList = new ArrayList<>();

        try {

            // send request
            Socket s = new Socket(SERVER_IP, SERVER_PORT);
            PrintWriter out =  new PrintWriter( s.getOutputStream(), true );
            out.println(REQUEST_NAME);

            // get data
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlResponse = docBuilder.parse(s.getInputStream());
            s.close();
            Log.d("RetrieveUsrLocationTask", "xml response: " + xmlResponse.getDocumentElement().getTextContent());

            // process data
            if(ref instanceof Activity) {

                Activity callerAct = (Activity) ref;

                // check if the passed context is an activity which can display a list of items
                if(IListableActivity.class.isAssignableFrom(callerAct.getClass())) {

                    IListableActivity concreteAct = (IListableActivity)callerAct;

                    // aid lists
                    List<Node> addressList = null;
                    List<Node> locationList = null;

                    // extract relevant data from the XML response
                    try {
                        addressList = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(xmlResponse, "/GeoLocalizationResult/Rilevation/streetAddress"));
                        locationList = XMLUtils.NodeListToListNode(XMLUtils.executeXpath(xmlResponse, "/GeoLocalizationResult/Rilevation/location"));
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }

                    for(int i = 0; i < addressList.size(); ++i) {

                        Log.d("RetrieveUsrLocationTask", "address: " + addressList.get(i).getTextContent());
                        Log.d("RetrieveUsrLocationTask", "location: " + locationList.get(i).getTextContent());

                        Element locationNode = (Element)locationList.get(i);

                        String lat = locationNode.getElementsByTagName("latitude").item(0).getTextContent();
                        String lon = locationNode.getElementsByTagName("longitude").item(0).getTextContent();

                        Location tmpLoc = new Location("");
                        tmpLoc.setLatitude(Double.parseDouble(lat));
                        tmpLoc.setLongitude(Double.parseDouble(lon));

                        userDataList.add(new UserData(tmpLoc, addressList.get(i).getTextContent()));
                    }

                    Log.d("RetrieveUsrLocationTask", "final data: " + userDataList.toString());
                }
            }
            else {
                Log.d("RetrieveUsrLocationTask", "ERROR: the passed context is not a listableActivity, or is not possible to cast");
                lastThrown = new InvalidClassException("ERROR: the passed context is not a listableActivity, or is not possible to cast");
            }

        }
        catch (UnknownHostException e) {

            // display message on UI
        }
        catch (IOException | SAXException | ParserConfigurationException e) {
            lastThrown = e;
        }

        return userDataList;
    }

    @Override
    protected void onPostExecute(List<UserData> ris) {

        // check if the task has thrown any exception and, if yes, rethrow
        if(lastThrown != null) {}

        // don't need to check the actual type of ref because the doInBackground() method already does the job
        IListableActivity concreteAct = (IListableActivity)ref;

        concreteAct.populateList(ris);
        concreteAct.populateMapWithMarkers();
    }
}
