package com.example.finalprojectappointmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    private ImageView profileImage;
    private ImageView hamburgerMenu;
    private LinearLayout drawerMenu;
    private TextView menuHome;
    private TextView menuProfile;
    private TextView menuSignOut;
    private Button categoryDentist;
    private Button categorygpv;
    private Button categorysurgeon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the drawer menu components
        profileImage = findViewById(R.id.profile_image);
        hamburgerMenu = findViewById(R.id.hamburger_menu);
        drawerMenu = findViewById(R.id.drawer_menu);
        menuHome = findViewById(R.id.menu_home);
        menuProfile = findViewById(R.id.menu_profile);
        menuSignOut = findViewById(R.id.menu_sign_out);

        View.OnClickListener toggleMenuListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerMenu.getVisibility() == View.VISIBLE) {
                    drawerMenu.setVisibility(View.GONE);
                } else {
                    drawerMenu.setVisibility(View.VISIBLE);
                }
            }
        };

        profileImage.setOnClickListener(toggleMenuListener);
        hamburgerMenu.setOnClickListener(toggleMenuListener);

        // Set OnClickListener for "Sign out"
        menuSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace profile image with hamburger menu
                profileImage.setVisibility(View.GONE);
                hamburgerMenu.setVisibility(View.VISIBLE);

                // Update drawer menu items
                menuProfile.setText(R.string.sign_in);
                menuSignOut.setText(R.string.register);

                // Minimize the drawer
                drawerMenu.setVisibility(View.GONE);

                // Set OnClickListener for "Sign In"
                menuProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.sign_in);
                    }
                });

                // Set OnClickListener for "Register"
                menuSignOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.sign_up);
                    }
                });
            }
        });

        // Find the "See All" TextView
        TextView seeAllTextView = findViewById(R.id.see_all2);

        // Set an OnClickListener to the "See All" TextView
        seeAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AllDocsActivity
                Intent intent = new Intent(Home.this, Alldoctor.class);
                startActivity(intent);
            }
        });

        Button btnBookZion = findViewById(R.id.btndoczionhome);

        btnBookZion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Appointmentdetails.class);
                startActivity(intent);
            }
        });


        categoryDentist = findViewById(R.id.category_dentist);
        categorygpv = findViewById(R.id.category_gpv);
        categorysurgeon = findViewById(R.id.category_surgeon);


        categoryDentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home.this, Appointmentdetails.class);
                startActivity(intent);
            }
        });
        categorygpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home.this, Appointmentdetails2.class);
                startActivity(intent);
            }
        });
        categorysurgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home.this, Appointmentdetails3.class);
                startActivity(intent);
            }
        });
    }
}
