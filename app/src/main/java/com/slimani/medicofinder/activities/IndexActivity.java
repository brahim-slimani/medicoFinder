package com.slimani.medicofinder.activities;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.slimani.medicofinder.R;
import com.slimani.medicofinder.database.DB_Manager;
import com.slimani.medicofinder.model.SymptomeService;

import java.util.ArrayList;
import java.util.List;


public class IndexActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DB_Manager dbm = new DB_Manager(this);
    public Cursor cursor;
    public List<SymptomeService> listeSymptomes = new ArrayList<SymptomeService>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        init();

        Button btn = (Button) findViewById(R.id.btn_begin);

        // Basculement vers la l'activité de renseignement
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressdialog = new ProgressDialog(IndexActivity.this, R.style.AppCompatAlertDialogStyle);
                progressdialog.setMessage("Please Wait....");
                progressdialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressdialog.dismiss();
                        Intent intent =new Intent(IndexActivity.this, Form1Activity.class);
                        intent.putExtra("valeur", 1);
                        startActivity(intent);
                    }
                }, 1000);

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

    // Initialisation de la DB SQLite
    public void init(){
        cursor = dbm.getAll();
        if(cursor.getCount()==0){
            fillRows();
        }

    }
    public void fillRows(){
        /*1*/  dbm.addSymptome("Angoisse, stress, fatigue","Psychologue");
        /*2*/  dbm.addSymptome("Mal au ventre, Nausée ou vomissement","Gastrologue");
        /*3*/  dbm.addSymptome("Hypoglycimé diabétique","Diabétologue");
        /*4*/  dbm.addSymptome("Mal à la tête, migraine","Neurologue");
        /*5*/  dbm.addSymptome("Douleur au thorax (côte, poumon, coeur...)","Phtiésiologue");
        /*6*/  dbm.addSymptome("Fractures ou fissures d os","Orthopédique");
        /*7*/  dbm.addSymptome("Tricot de peau ou rougeole ou la gale","Dermatoloque");
        /*8*/  dbm.addSymptome("Anémie ou hypertension ou batements de coeur","Cardiologue");
        /*9*/  dbm.addSymptome("Carrie dentaire","Dentiste");
        /*10*/ dbm.addSymptome("Angine ou Oreillons ou Sinusite ou goitre","ORL");
        /*11*/ dbm.addSymptome("Gripe(fièvre, toux, fatigue)","Généraliste");
        /*12*/ dbm.addSymptome("Déprésssion, Insomnie","Psychatre");
        /*13*/ dbm.addSymptome("Inféction urinaire, prostate","Urologue");
        /*14*/ dbm.addSymptome("Femme enceinte, Obstétrique...","Gynécologue");

    }

    //mise en place d'un item pour le help et pour fermer l'app
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            Intent intent =new Intent(IndexActivity.this, IndexActivity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form1) {
            Intent intent =new Intent(IndexActivity.this, Form1Activity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form2) {
            Intent intent =new Intent(IndexActivity.this, Form2Activity.class);
            intent.putExtra("valeur", 1);
            startActivity(intent);
        } else if (id == R.id.nav_form3) {
            Intent intent =new Intent(IndexActivity.this, Form3Activity.class);
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
