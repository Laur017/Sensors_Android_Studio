package com.example.sensorspresentation;

import android.content.Context;
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
import android.widget.TextView;


public class MotionFragment extends Fragment implements SensorEventListener {
    private TextView x;
    private SensorManager sensorManager;
    private Sensor mGravity;
    private Boolean isGravitySensorAvailable;
    private AudioManager aManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_motion, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
//        x.setText("x " + event.values[0] + " m/m2 " + "\n" + "y " + event.values[1] + " m/m2 " + "\n" + "z" + event.values[2] + "m/m2");

        if(event.values[2]<-9.7){
            getActivity().getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            x.setText("Focus Mode ...");
            aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }else{
            getActivity().getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            x.setText("It's Time to focus !");
            aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}