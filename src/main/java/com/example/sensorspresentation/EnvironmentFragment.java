package com.example.sensorspresentation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EnvironmentFragment extends Fragment implements SensorEventListener {
    private TextView textEnv;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Boolean isTempAvailable;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_environment, container, false);

        textEnv = rootView.findViewById(R.id.textEnv);
        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTempAvailable = true;
        } else {
            textEnv.setText("Temperature Sensor is not available !");
            isTempAvailable = false;
        }

        return rootView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textEnv.setText(event.values[0] + " °C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if(isTempAvailable){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isTempAvailable){
            sensorManager.registerListener(this,tempSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}