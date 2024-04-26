package com.example.sensorspresentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MotionFragment extends Fragment implements SensorEventListener {
    private TextView x;
    private SensorManager sensorManager;
    private Sensor mGravity;
    private Boolean isGravitySensorAvailable;
    private AudioManager aManager;
    private float lastX, lastY, lastZ;
    private static final float THRESHOLD = 1.0f;
    private FrameLayout frameLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_motion, container, false);
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//        startActivity(intent);

        frameLayout = rootView.findViewById(R.id.motionFrame);
        aManager = (AudioManager) requireContext().getSystemService(Context.AUDIO_SERVICE);
        x = rootView.findViewById(R.id.x);

        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            mGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            isGravitySensorAvailable = true;
        } else {
            x.setText("Proximity Sensor is not available !");
            isGravitySensorAvailable = false;
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isGravitySensorAvailable){
            sensorManager.unregisterListener(this, mGravity);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isGravitySensorAvailable){
            sensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float deltaX = Math.abs(event.values[0] - lastX);
        float deltaY = Math.abs(event.values[1] - lastY);
        float deltaZ = Math.abs(event.values[2] - lastZ);

        if (deltaX > THRESHOLD || deltaY > THRESHOLD || deltaZ > THRESHOLD) {
            lastX = event.values[0];
            lastY = event.values[1];
            lastZ = event.values[2];

            x.setText("x " + lastX + " m/m2 " + "\n" + "y " + lastY + " m/m2 " + "\n" + "z" + lastZ + "m/m2");
//            if (event.values[2] < -9.7) {
//                frameLayout.setBackgroundColor(Color.BLACK);
//                x.setText("Focus Mode ...");
//                x.setTextColor(Color.WHITE);
//                aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//            } else {
//                frameLayout.setBackgroundColor(Color.parseColor("#A4C639"));
//                x.setText("It's Time to focus !");
//                x.setTextColor(Color.BLACK);
//                aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}