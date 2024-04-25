package com.example.sensorspresentation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sensorspresentation.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
//    ActivityMainBinding binding;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frame_layout);

        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home){
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.about) {
                replaceFragment(new AboutFragment());
            } else if (item.getItemId() == R.id.motion) {
                replaceFragment(new MotionFragment());
            } else if (item.getItemId() == R.id.position) {
                replaceFragment(new PositionFragment());
            } else if (item.getItemId() == R.id.environment) {
                replaceFragment(new EnvironmentFragment());
            }
            return true;
        });

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        replaceFragment(new HomeFragment());
//
//        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//            if(item.getItemId() == R.id.home){
//                replaceFragment(new HomeFragment());
//            } else if (item.getItemId() == R.id.about) {
//                replaceFragment(new AboutFragment());
//            } else if (item.getItemId() == R.id.motion) {
//                replaceFragment(new MotionFragment());
//            } else if (item.getItemId() == R.id.position) {
//                replaceFragment(new PositionFragment());
//            } else if (item.getItemId() == R.id.environment) {
//                replaceFragment(new EnvironmentFragment());
//            }
//
//            return true;
//        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}