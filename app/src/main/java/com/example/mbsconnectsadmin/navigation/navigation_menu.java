package com.example.mbsconnectsadmin.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mbsconnectsadmin.MainActivity;
import com.example.mbsconnectsadmin.R;
import com.example.mbsconnectsadmin.about_dev.developers;
import com.example.mbsconnectsadmin.assigment.assigment_options;
import com.example.mbsconnectsadmin.fees.course_activity;
import com.example.mbsconnectsadmin.gallery.upload_image;
import com.example.mbsconnectsadmin.live_class.create_class;
import com.example.mbsconnectsadmin.login_signup.forgot_password1;
import com.example.mbsconnectsadmin.login_signup.sign_up;
import com.example.mbsconnectsadmin.paper.paper;
import com.example.mbsconnectsadmin.result.result_activity;
import com.example.mbsconnectsadmin.support.support_activity;
import com.example.mbsconnectsadmin.teacher_data.add_teacher;
import com.example.mbsconnectsadmin.teacher_data.view_teacher;
import com.example.mbsconnectsadmin.timetable.schedule_menu;
import com.example.mbsconnectsadmin.upload_notice.delete_notice;
import com.example.mbsconnectsadmin.upload_notice.notice_activity;
import com.example.mbsconnectsadmin.uploadbook.book_upload;
import com.example.mbsconnectsadmin.youtube_data.youtube;
import com.google.android.material.navigation.NavigationView;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class navigation_menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String item="";
    String [] items={"College Related Options","Student Related Options"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ImageView menu;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    String rateus_url="https://forms.gle/N7ZyPEu2RYxwytMB9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
        //DropDown
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this,R.layout.drop_down,items);
        autoCompleteTxt.setAdapter(adapterItems);
        //DropDown
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                if(item=="College Related Options")
                {
                    Intent intent=new Intent(navigation_menu.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        //Drawer
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.naviagtion_view);
        menu=findViewById(R.id.navigation_icon);

        navigationDrawer();

    }


    //Drawer Functions
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else

                   drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        super.onBackPressed();
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_account)
        {
            Intent intent=new Intent(this, sign_up.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.logout)
        {
           logout_confirm();
        }

        if(item.getItemId()==R.id.pass)
        {
            Intent intent=new Intent(this, forgot_password1.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.nav_about)
        {
            Intent intent=new Intent(this, developers.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.nav_supp)
        {
            Intent intent=new Intent(this, support_activity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    public void logout_confirm()
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure that you want to logout your account?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(navigation_menu.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(navigation_menu.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void online_class(View view) {
        Intent intent=new Intent(this, create_class.class);
        startActivity(intent);
    }



    public void youtube_lauch(View view) {
        Intent intent=new Intent(this, youtube.class);
        startActivity(intent);
    }

    public void contactus(View view) {
        String phone = "05512290845";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }



    public void rateus(View view) {

        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

        customIntent.setToolbarColor(ContextCompat.getColor(navigation_menu.this, R.color.red));

        openCustomTab(navigation_menu.this, customIntent.build(), Uri.parse(rateus_url));


    }



    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {

        String packageName = "com.android.chrome";
        if (packageName != null) {


            customTabsIntent.intent.setPackage(packageName);


            customTabsIntent.launchUrl(activity, uri);
        } else
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void support(View view) {
        Intent intent=new Intent(this, support_activity.class);
        startActivity(intent);
    }

    public void notice(View view) {
        startActivity(new Intent(this, notice_activity.class));
    }

    public void upload_book(View view) {
        startActivity(new Intent(this, book_upload.class));
    }

    public void upload_result(View view) {
        startActivity(new Intent(this, result_activity.class));
    }

    public void fee_launch(View view) {
        startActivity(new Intent(this, course_activity.class));
    }

    public void add_teacher(View view) {
        startActivity(new Intent(this, add_teacher.class));
    }

    public void view_teacher(View view) {startActivity(new Intent(this, view_teacher.class));
    }

    public void gallery(View view) {startActivity(new Intent(this, upload_image.class));
    }

    public void assigment(View view) {startActivity(new Intent(this, assigment_options.class));
    }

    public void upload_timetable(View view) {startActivity(new Intent(this, schedule_menu.class));
    }

    public void uploadpaper(View view) {startActivity(new Intent(this, paper.class));
    }

    public void delete_notice(View view) { startActivity(new Intent(this, delete_notice.class));
    }
}