package com.example.rasyona_egitim.rasyonatool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.rasyona_egitim.PRTG.Devices;
import com.example.rasyona_egitim.PRTG.Manipulation;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList device = new ArrayList();

    private String LOG_TAG = "XML";
    private static String TAG = "ExcelLog";
    private int UpdateFlag = 0;
    TextView nMessages,nAlarms,nClock,nBackGroundTasks,nUpdateAvailability,nVersion,nIsAdminUser;


    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListView expListView;
    private ExpandListAdapter listAdapter;
    private ArrayList<String> prtgList;
    private ArrayList<String> fileShareList;
    private ArrayList<String> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nMessages = (TextView)findViewById(R.id.yeniMesajlar);
        nAlarms = (TextView)findViewById(R.id.yeniAlarmlar);
        nClock = (TextView)findViewById(R.id.tarihSaat);
        nVersion = (TextView)findViewById(R.id.surum);
        nBackGroundTasks = (TextView)findViewById(R.id.arkaplanGorevleri);
        nUpdateAvailability = (TextView)findViewById(R.id.guncellenebilirlik);
        nIsAdminUser = (TextView)findViewById(R.id.yoneticiKullaniciMi);


        new SystemStatus().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        enableExpandableList();
    }

    private void enableExpandableList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        expListView = (ExpandableListView) findViewById(R.id.left_drawer);

        prepareListData(listDataHeader, listDataChild);
        listAdapter = new ExpandListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {




                return false;
            }
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                final String selected = (String) listAdapter.getChild(groupPosition, childPosition);

                switch (selected){

                    case "Devices":

                        new FetchData().execute();

                        break;

                    case "Alarms":
                        break;

                    case "Sensors":
                        break;

                    case "Manipulation":

                        Intent intent = new Intent(MainActivity.this, Manipulation.class);
                        startActivity(intent);

                        break;

                    case "Resim":

                        Intent intent2 = new Intent(MainActivity.this, ResimYukle.class);
                        startActivity(intent2);

                        break;

                    case "Pdf":

                        Intent intent3 = new Intent(MainActivity.this, PdfYukle.class);
                        startActivity(intent3);

                        break;

                    case "Excel":

                        Intent intent4 = new Intent(MainActivity.this,ExcelOlustur.class);
                        startActivity(intent4);

                        break;

                }

                return true;
            }
        });
    }
    private void prepareListData(List<String> listDataHeader, Map<String,
                List<String>> listDataChild) {


        // Adding child data
        listDataHeader.add("PRTG");
        listDataHeader.add("Dosya Paylaşımı");
        listDataHeader.add("Raporlama");

        // Adding child data
        prtgList = new ArrayList<String>();
        prtgList.add("Devices");
        prtgList.add("Sensors");
        prtgList.add("Alarms");
        prtgList.add("Manipulation");

        fileShareList = new ArrayList<String>();
        fileShareList.add("Resim");
        fileShareList.add("Pdf");

        reportList = new ArrayList<String>();
        reportList.add("Excel");



        listDataChild.put(listDataHeader.get(0), prtgList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), fileShareList);
        listDataChild.put(listDataHeader.get(2), reportList);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.refresh:

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

                return true;

            case R.id.sentMail:

                Intent intent2 = new Intent(MainActivity.this, MailSender.class);
                startActivity(intent2);

                return true;

                default:
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        /*if (id == R.id.nav_devices) {



        } else if (id == R.id.nav_sensors) {

        } else if (id == R.id.nav_alarms) {

        } else if (id == R.id.nav_task_management){



        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class FetchData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            String jsonStr = null;

            try {

                URL url = new URL("http://192.168.8.203:8080/api/table.xml?content=devices&output=xml&columns=device&noraw=1&usecaption=true&username=prtgadmin&password=prtgadmin");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                jsonStr = buffer.toString();

                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                ParseXML();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }


        }

        public void ParseXML() throws IOException, XPathExpressionException {

            /*String filename = "file2.xml";

            FileOutputStream fos;

            fos = openFileOutput(filename, Context.MODE_APPEND);
            fos.close();*/


            String[] a = getResources().getStringArray(R.array.devices);
            for (int i = 0;i<a.length;i++) {

                device.add(a[i]);

            }

            /*XPath xPath = XPathFactory.newInstance().newXPath();

            FileReader reader = new FileReader("file2.xml");
            InputSource xml = new InputSource(reader);
            NodeList titleNodes = (NodeList) xPath.evaluate("//item/device", xml, XPathConstants.NODESET);


            for(int x=0; x<titleNodes.getLength(); x++) {

                device.add(titleNodes.item(x).getTextContent());

            }*/


                Intent intent1 = new Intent(MainActivity.this, Devices.class);
                intent1.putStringArrayListExtra("devices",device);
                startActivity(intent1);

        }

    }

    private class SystemStatus extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            String jsonStr = null;

            try {

                URL url = new URL("http://192.168.8.203:8080/api/getstatus.xml?id=0&username=prtgadmin&password=prtgadmin");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                jsonStr = buffer.toString();

                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ParseXML(s);

        }

        public void ParseXML(String xmlString) {

            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new StringReader(xmlString));
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        String name = parser.getName();
                        if (name.equals("NewMessages")) {

                            if (parser.next() == XmlPullParser.TEXT) {
                                String newMessages = parser.getText();
                                Log.d(LOG_TAG, "Yeni Mesajlar:" + newMessages);

                                nMessages.setText("Yeni Mesajlar: " + newMessages);
                            }
                        }else if(name.equals("NewAlarms")) {

                            if(parser.next() == XmlPullParser.TEXT) {
                                String newAlarms = parser.getText();
                                Log.d(LOG_TAG,"Yeni Alarmlar:" + newAlarms);

                                nAlarms.setText("Yeni Alarmlar: " + newAlarms);
                            }
                        }else if(name.equals("Clock")) {

                                if(parser.next() == XmlPullParser.TEXT) {
                                    String clock = parser.getText();
                                    Log.d(LOG_TAG,"Tarih/Saat:" + clock);

                                    nClock.setText("Tarih/Saat: " + clock);
                                }
                        }else if(name.equals("Version")) {

                            if (parser.next() == XmlPullParser.TEXT) {
                                String version = parser.getText();
                                Log.d(LOG_TAG, "Sürüm:" + version);

                                nVersion.setText("Sürüm: " + version);
                            }
                        } else if(name.equals("BackgroundTasks")) {

                            if (parser.next() == XmlPullParser.TEXT) {
                                String backgroundTasks = parser.getText();
                                Log.d(LOG_TAG, "Arkaplan Görevleri:" + backgroundTasks);

                                nBackGroundTasks.setText("Arkaplan Görevleri: " + backgroundTasks);
                            }
                        }else if(name.equals("PRTGUpdateAvailable")) {

                            if (parser.next() == XmlPullParser.TEXT) {
                                String update = parser.getText();
                                Log.d(LOG_TAG, "Güncellenebilirlik:" + update);

                                nUpdateAvailability.setText("Güncellenebilirlik: " + update);
                            }
                        }else if(name.equals("IsAdminUser")) {

                            if (parser.next() == XmlPullParser.TEXT) {
                                String adminUser = parser.getText();
                                Log.d(LOG_TAG, "Yetkili Kullanıcı:" + adminUser);

                                nIsAdminUser.setText("Yetkili Kullanıcı: " + adminUser);
                            }
                        }

                    } else if (eventType == XmlPullParser.END_TAG) {


                    }
                    eventType = parser.next();

                }


            } catch (Exception e) {
                Log.d(LOG_TAG, "Error in ParseXML()", e);
            }

        }
    }
}
