package com.example.sensorspresentation;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PositionFragment extends Fragment implements SensorEventListener {
    private TextView textPosition;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Boolean isProximitySensorAvailable;
    private Vibrator vibrator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_position, container, false);

        textPosition = rootView.findViewById(R.id.textPosition);
        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isProximitySensorAvailable = true;
        } else {
            textPosition.setText("Proximity Sensor is not available !");
            isProximitySensorAvailable = false;
        }

        return rootView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textPosition.setText(event.values[0] + " cm");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(isProximitySensorAvailable){
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isProximitySensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}