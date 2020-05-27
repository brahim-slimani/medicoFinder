package com.slimani.medicofinder.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.slimani.medicofinder.R;
import com.slimani.medicofinder.database.DB_Manager;
import com.slimani.medicofinder.model.Medcin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class Form2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    String signe;
    public static List<Medcin> listeMedcins = new ArrayList<Medcin>();
    DB_Manager dbm = new DB_Manager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form2);

        final Spinner spinnerSymptome = (Spinner) findViewById(R.id.SpinnerSymptome);

        ArrayList<String> itemSymptomes = new ArrayList<String>();

        //Initialisation du spinner par les symptomes à partir de DB SQLite
        Cursor cursor = dbm.getAll();
        if (cursor.moveToFirst()) {
            do {
                try {
                    itemSymptomes.add(cursor.getString(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemSymptomes);
        spinnerSymptome.setAdapter(adapter);

        RestfulServices();

        Button btn = (Button) findViewById(R.id.btn_form3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enregistrement de symptome sélectionné et le passer comme paramètre à l'activité suivante
                    signe = spinnerSymptome.getSelectedItem().toString();

                    Intent intent =new Intent(Form2Activity.this, Form3Activity.class);
                    intent.putExtra("valeur", 1);
                    intent.putExtra("symptome", signe);
                    startActivity(intent);

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    // Connexion au service RESTFUL pour récupérer les médcins disponibles
    public void RestfulServices(){
        //l'URL contient l'adresse IP dont l'application web est hébergée
        String URL="http://192.168.43.120:8000/api/h";

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rest Response",response.toString());
                System.out.println(response.toString());
                try {

                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i< jsonArray.length(); i++){
                        JSONObject medcin = jsonArray.getJSONObject(i);

                        listeMedcins.add(new Medcin(medcin.getInt("id"), medcin.getString("designation"),medcin.getString("spec"),
                                medcin.getString("longitude"), medcin.getString("latitude"), medcin.getString("tel")));

                        System.out.println(listeMedcins.size());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response",error.toString());
                    }
                }


        );
        requestQueue.add(objectRequest);*/
    }


    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_exit:
                logoutConfirmation("Confirmation","Are you sure you want to logout from the app ?");
        }
        switch (item.getItemId()) {

            case R.id.action_help:
                final Dialog dialog2 = new Dialog(this);
                dialog2.setContentView(R.layout.help_dialogue);
                dialog2.setTitle("A Propos de");
                Button btnHelp = (Button) dialog2.findViewById(R.id.btn_help);

                dialog2.show();
                btnHelp.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });


            default: return super.onOptionsItemSelected(item);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            Intent intent =new Intent(Form2Activity.this, IndexActivity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form1) {
            Intent intent =new Intent(Form2Activity.this, Form1Activity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form2) {
            Intent intent =new Intent(Form2Activity.this, Form2Activity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form3) {
            Intent intent =new Intent(Form2Activity.this, Form3Activity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //logout confirmation
    public void logoutConfirmation(String title, String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.ic_help_outline_blue_24dp);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();

                System.exit(0);
            }
        });

        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }




}
