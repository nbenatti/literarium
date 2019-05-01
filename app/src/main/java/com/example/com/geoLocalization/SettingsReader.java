package geoLocalization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SettingsReader {

    private static SettingsReader instance = null;

    private HashMap<String, String> options;

    private static String configFilePath;

    private static File configFileHandler;

    private static String separator = ":";

    private static HashMap<String, String> defaultSettings = new HashMap<String, String>() {{

        put("SERVER_IP", "87.4.93.101");
        put("SERVER_PORT", "6000");
    }};

    private SettingsReader() throws IOException {

        options = new HashMap<>();

        // if the file exists, fetch content
        // otherwise create a new file with default settings

        //File f = new File(configFilePath);

        if(configFileHandler.exists()) {
            readSettings();
        }
        else {

            restoreDefaultSettings();
        }
    }

    private void restoreDefaultSettings() throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFileHandler));

        for(String settingKey : defaultSettings.keySet()) {

            bufferedWriter.write(settingKey+"\n");
        }

        bufferedWriter.close();
    }

    /**
     * acquisisce i parametri e le impostazioni.
     */
    private void readSettings() throws IOException {

        BufferedReader rdbuf = new BufferedReader(new FileReader(configFilePath));

        String setting = "";

        while((setting = rdbuf.readLine()) != null) {

            //System.out.println("ciaooooo");

            String[] parsedSetting = parseSetting(setting);

            //System.out.println("**" + Arrays.toString(parsedSetting) + "**");

            options.put(parsedSetting[0], parsedSetting[1]);
        }
    }

    public static void setConfigFilePath(String cfp) {

        configFilePath = cfp;
        configFileHandler = new File(configFilePath);

        instance = null;    // innesco la ricreazione dell'oggetto
    }

    public static void setSeparator(String sep) {

        separator = sep;
    }

    public static SettingsReader getInstance() throws IOException {

        if(instance == null)
            instance = new SettingsReader();

        return instance;
    }

    private String[] parseSetting(String setting) {

        String[] tokens = setting.split("\\s*["+separator+"]\\s*");

        return tokens;
    }

    /**
     * ritorna l'impostazione scelta.
     * @param settingName nome dell'impostazione
     * @return valore dell'impostazione.
     */
    public String getSetting(String settingName) {

        return options.get(settingName);
    }

}
