package com.user.gpjaunpur;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import static maes.tech.intentanim.CustomIntent.customType;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.user.gpjaunpur.authentication.Login;
import com.user.gpjaunpur.pdf.PdfActivity;

import java.util.Objects;

import maes.tech.intentanim.CustomIntent;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int checkedItem;
    private String selected,users;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private final String CHECKEDITEM="checked_item";
   private TextView usern,usere;



    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser() == null){
            openLogin();
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("Notification");
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance().getReference("Users");



        sharedPreferences=this.getSharedPreferences("themes",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        try {
            switch (getCheckedItem()) {
                case 0:
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
                    break;

                case 1:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;

                case 3:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        navigationView=findViewById(R.id.navigation_view);

       drawerLayout=findViewById(R.id.drawerLayout);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        navController=Navigation.findNavController(this,R.id.frame_layout);
       View headerView=navigationView.getHeaderView(0);
       usern=headerView.findViewById(R.id.username);
       usere=headerView.findViewById(R.id.userEmail);



        // dialoge show
        if(!isConnected(MainActivity.this))
        {
            buildDialog(MainActivity.this).show();
        }


        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

       // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


     /*   Intent formlog=getIntent();
       String ue= formlog.getStringExtra("emailsss");

        View view=navigationView.inflateHeaderView(R.layout.drawer_header);
        TextView useremail=view.findViewById(R.id.userEmail);
        useremail.setText(ue);
*/

        navigationView.setNavigationItemSelectedListener(this);




        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        bottomNavigationView.setOnItemReselectedListener(item -> {

        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.option_logout) {
            auth.signOut();
            openLogin();
        }
        return true;
    }

    private void openLogin() {
        startActivity(new Intent(MainActivity.this, Login.class));
        CustomIntent.customType(MainActivity.this,"left-to-right");
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            switch(item.getItemId()) {
                case R.id.navigation_developer:
                    auth.signOut();
                    openLogin();
                    break;

                case R.id.navigation_website:
                    Intent intent = new Intent(this, Webview.class);
                    startActivity(intent);
                    CustomIntent.customType(MainActivity.this,"left-to-right");
                    break;

                case R.id.navigation_share:

                    try {
                        Intent s = new Intent(Intent.ACTION_SEND);
                        s.setType("text/plain");
                        s.putExtra(Intent.EXTRA_SUBJECT, "GP Jaunpur");
                        s.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                        startActivity(Intent.createChooser(s, "Share with"));
                        CustomIntent.customType(MainActivity.this,"up-to-bottom");
                    } catch (Exception e) {
                        Toast.makeText(this, "Unable to share this application.", Toast.LENGTH_SHORT).show();
                    } break;





                case R.id.navigation_rate:
                    Uri uri=Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                    Intent r=new Intent(Intent.ACTION_VIEW,uri);
                    try {
                        startActivity(r);
                        CustomIntent.customType(MainActivity.this,"bottom-to-up");
                    } catch (Exception e) {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    break;


                case R.id.navigation_pdf:
                    Intent i=new Intent(this, PdfActivity.class);
                    startActivity(i);
                    CustomIntent.customType(MainActivity.this,"left-to-right");
                    break;
                case R.id.navigation_color:
                    showDialog();
                    break;


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu, menu);
        return true;
    }

    private void showDialog() {

        String [] themes=this.getResources().getStringArray(R.array.theme);

        MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(this);

        builder.setTitle("Select Theme");
        builder.setSingleChoiceItems(R.array.theme, getCheckedItem(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selected = themes[i];
                checkedItem = i;


            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(selected==null){
                    try {
                        selected= themes[i];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    checkedItem = i;
                }
                try {
                    switch (selected)
                    {
                        case "System Default":
                            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
                            break;

                        case "Dark" :
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            break;

                        case "Light" :
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            break;

                    }

                setCheckedItem(checkedItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();


    }
    private int getCheckedItem(){
        return sharedPreferences.getInt(CHECKEDITEM,0);

    }

    private void  setCheckedItem(int i)
    {
      try {
          editor.putInt(CHECKEDITEM, i);

          editor.apply();
      }catch (Exception e)
      {
          e.printStackTrace();
      }
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {  //drawer close
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(bottomNavigationView.getSelectedItemId()==R.id.navigation_home){

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //app close
            builder.setMessage("Are you sure! you want to exit this application.  ")
                    .setTitle("Alert!!")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog= builder.create();
           alertDialog.show();

        }else
        {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }



    }
    // internet connection check inapplication
    public boolean isConnected(Context context)
    {
        ConnectivityManager manger= (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info=manger.getActiveNetworkInfo();
        if(info !=null && info.isConnectedOrConnecting())
        {
            android.net.NetworkInfo wifi= manger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=manger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        }else
            return false;
    }
    public AlertDialog.Builder buildDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setTitle("No Internet Connection!");
        builder.setMessage("Please Check Your Internet Connection.");
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        return builder;


    }
}

