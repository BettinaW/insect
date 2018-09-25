import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;


public class JsonServerMonitor {

    public static void main(String[] args) {
        JsonServerMonitor monitor = new JsonServerMonitor();
        monitor.requestServerStatus();
    }


    public void requestServerStatus() {

        try {

            JSONObject jobj = new JSONObject();
            ArrayList<String> sessionArray = new ArrayList<>();
            HashMap<String, String> hmap = new HashMap<String, String>();

            hmap.put("sessionid", "c4d930e9-279b-4ce4-afad-2e0a8f3b9eb2");
            hmap.put("requestid", "c4d930e9-279b-4ce4-afad-2e0a8f3b9eb2");
            hmap.put("type", "getVersion");
            hmap.put("entity", null);
            hmap.put("collphirSessionKey", null);
            jobj.put("action", hmap);

            hmap.clear();

            hmap.put("username", "admin");
            hmap.put("password", "qwe456//");
            hmap.put("portal", "AG");
            hmap.put("agid", "1002");
            jobj.put("data", hmap);


            URL url = new URL("http://localhost:8008/deka/servlet/JsonServer");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(jobj.toString());
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer jsonString = new StringBuffer();
            String line;


            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");


            FileWriter fw = new FileWriter("CollPhirClient " + format.format(date) + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
                bw.write(line);
                bw.newLine();
            }
            br.close();
            connection.disconnect();

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
