package com.example.sensorspresentation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class AboutFragment extends Fragment {

    private TextView aboutText;
    private SensorManager sensorManager;
    private List<Sensor> deviceSensors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        aboutText = rootView.findViewById(R.id.aboutText);
        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        } else {
            System.out.println("Nici un senzor available");
        }

        aboutText.setText(deviceSensors.toString());

//        printSensors();
        return rootView;
    }

    private void printSensors() {
        aboutText.setText("");
        for(Sensor sensor : deviceSensors){
            aboutText.setText(aboutText.getText() + "\n" + sensor.getName());
        }
    }
}