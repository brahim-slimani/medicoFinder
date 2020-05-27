package com.slimani.medicofinder.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.slimani.medicofinder.R;
import com.slimani.medicofinder.database.DB_Manager;
import com.slimani.medicofinder.model.Medcin;

import java.util.ArrayList;
import java.util.List;

import static com.slimani.medicofinder.activities.Form1Activity.ageUser;


public class Form3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DB_Manager dbm = new DB_Manager(this);
    String specialiste = "...";
    String serviceApproprie;

    public static List<Medcin> MedcinsAppropries = new ArrayList<Medcin>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form3);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        //récuperation du symptome sélectionné
        Intent intent = getIntent();
        final String symptome = intent.getStringExtra("symptome");

        TextView estimation = (TextView) findViewById(R.id.estimation);

        //récupération du service approprié au symptome sélectionné d'aprés la DB SQLite
        Cursor cur = dbm.getSymptome(symptome);
        if (cur.moveToFirst()) {
            do {
                try {
                    specialiste = cur.getString(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cur.moveToNext());
        }

        if(ageUser<16){
            specialiste = "Pédiatre";
        }

        estimation.setText("Based on the estimate it appears that you should visit a "+specialiste);


        //Initialisation de la liste des spécialistes par les médecins disponibles
        //final Spinner spinnerMedcin = (Spinner) findViewById(R.id.SpecialiteMedcin);
        List<String> itemService = new ArrayList<String>();
        itemService.clear();
        for(int i = 0; i< Form2Activity.listeMedcins.size(); i++){
            if(!exist(itemService, Form2Activity.listeMedcins.get(i).getSpecialite())){
                itemService.add(new String(Form2Activity.listeMedcins.get(i).getSpecialite()));
            }

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemService);
        //spinnerMedcin.setAdapter(adapter);

        //Affichage de spécialité du médecin approprié dans le spiner
        //spinnerMedcin.setSelection(adapter.getPosition(specialiste));

        Button btn = (Button) findViewById(R.id.btn_localise);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedcinsAppropries.clear();
                //serviceApproprie = spinnerMedcin.getSelectedItem().toString();

                //Récupération finale des médecins qui seront marqués sur la carte map
                /*for(int i = 0; i< Form2Activity.listeMedcins.size(); i++){
                    if(Form2Activity.listeMedcins.get(i).getSpecialite().equals(serviceApproprie)){
                        MedcinsAppropries.add(Form2Activity.listeMedcins.get(i));
                    }
                }*/

                MedcinsAppropries.add(new Medcin(1, "Mourad Bentamer", specialiste, "3.951350", "36.68923", "0665 45 23 44"));
                MedcinsAppropries.add(new Medcin(2, "Nadia Amokrane", specialiste, "3.055941", "36.742770", "0555 12 65 90"));
                MedcinsAppropries.add(new Medcin(3, "Karim Zougar", specialiste, "3.069958", "36.712578", "0557 09 43 89"));
                MedcinsAppropries.add(new Medcin(4, "Fadia Hadj Sadouk", specialiste, "3.155810", "36.661294", "0770 10 43 54"));
                MedcinsAppropries.add(new Medcin(5, "Mohamed Kadi", specialiste, "3.053782", "36.708853", "0662 54 60 67"));


                final ProgressDialog progressdialog = new ProgressDialog(Form3Activity.this, R.style.AppCompatAlertDialogStyle);
                progressdialog.setMessage("Please Wait....");
                progressdialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent =new Intent(Form3Activity.this, MapsActivity.class);
                        intent.putExtra("valeur", 1);
                        startActivity(intent);
                    }
                }, 2000);


            }
        });


        //la mise en place du menu de navigation
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

    //check request allowing location permission
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                //request is accepted
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
                    //request is denied
                } else {
                    System.exit(0);
                }
                return;
            }
        }
    }

    //pour éviter la duplication lors de l'affichage des spécialités
    public Boolean exist(List<String> liste, String service){
        Boolean b = false;
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).equals(service)){
                b =  true;
            }
        }
        return b;
    }

    //mise en place d'un item pour fermer l'app
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

    //les actions des items du menu de navigation
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            Intent intent =new Intent(Form3Activity.this, IndexActivity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form1) {
            Intent intent =new Intent(Form3Activity.this, Form1Activity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form2) {
            Intent intent =new Intent(Form3Activity.this, Form2Activity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form3) {
            Intent intent =new Intent(Form3Activity.this, Form3Activity.class);
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
