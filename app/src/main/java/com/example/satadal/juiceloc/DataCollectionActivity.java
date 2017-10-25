package com.example.satadal.juiceloc;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DataCollectionActivity extends Activity implements SensorEventListener {

    private static SensorManager sensorManager;

    private static List<String> availableMotionSensors = new ArrayList<>();
    private static List<String> availablePositionSensors = new ArrayList<>();
    private static List<String> availableEnvironmentSensors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare the items on screen that need changing.
        final Button toggleButton = (Button)findViewById(R.id.toggleButton);
        final TextView toggleTextView = (TextView)findViewById(R.id.toggleTextView);
        final ListView sensorsListView = (ListView)findViewById(R.id.sensorsListView);

        // Initialize sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Set the available sensors available on this device
        setAvailableSensors();

        // Make the list of available sensors visible on app.
        ArrayAdapter<String> motionSensorsAdapter = new ArrayAdapter<String>(
                getBaseContext(), R.layout.activity_main, availableMotionSensors);
        sensorsListView.setAdapter(motionSensorsAdapter);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toggleButton.setEnabled(false);
                toggleTextView.setText(getResources().getString(R.string.stop_text));
                toggleButton.setText(getResources().getString(R.string.stop_button_text));
            }
        });
    }

    private static void setAvailableSensors() {

        List<Integer> supportedMotionSensors = new ArrayList<>();
        List<Integer> supportedPositionSensors = new ArrayList<>();
        List<Integer> supportedEnvironmentSensors = new ArrayList<>();

        // Supported motion sensors on Android.
        // Source: https://developer.android.com/guide/topics/sensors/sensors_motion.html
        // Count is 9.
        supportedMotionSensors.add(Sensor.TYPE_ACCELEROMETER);
        supportedMotionSensors.add(Sensor.TYPE_GRAVITY);
        supportedMotionSensors.add(Sensor.TYPE_GYROSCOPE);
        supportedMotionSensors.add(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        supportedMotionSensors.add(Sensor.TYPE_LINEAR_ACCELERATION);
        supportedMotionSensors.add(Sensor.TYPE_ROTATION_VECTOR);
        supportedMotionSensors.add(Sensor.TYPE_SIGNIFICANT_MOTION);
        supportedMotionSensors.add(Sensor.TYPE_STEP_COUNTER);
        supportedMotionSensors.add(Sensor.TYPE_STEP_DETECTOR);
        
        // Supported position sensors on Android.
        // Source: https://developer.android.com/guide/topics/sensors/sensors_position.html
        // Count is 5.
        supportedPositionSensors.add(Sensor.TYPE_GAME_ROTATION_VECTOR);
        supportedPositionSensors.add(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        supportedPositionSensors.add(Sensor.TYPE_MAGNETIC_FIELD);
        supportedPositionSensors.add(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        supportedPositionSensors.add(Sensor.TYPE_PROXIMITY);

        // Supported environment sensors on Android.
        // Source: https://developer.android.com/guide/topics/sensors/sensors_environment.html
        // Count is 4.
        supportedEnvironmentSensors.add(Sensor.TYPE_AMBIENT_TEMPERATURE);
        supportedEnvironmentSensors.add(Sensor.TYPE_LIGHT);
        supportedEnvironmentSensors.add(Sensor.TYPE_PRESSURE);
        supportedEnvironmentSensors.add(Sensor.TYPE_RELATIVE_HUMIDITY);

        // Check availability of motion sensors on current phone.
        for (Integer currSensorType : supportedMotionSensors) {
            Sensor currSensor = sensorManager.getDefaultSensor(currSensorType);
            if (currSensor != null) {
                availableMotionSensors.add(currSensor.getStringType());
            }
        }

        // Check availability of position sensors on current phone.
        for (Integer currSensorType : supportedMotionSensors) {
            Sensor currSensor = sensorManager.getDefaultSensor(currSensorType);
            if (currSensor != null) {
                availablePositionSensors.add(currSensor.getStringType());
            }
        }

        // Check availability of environment sensors on current phone.
        for (Integer currSensorType : supportedMotionSensors) {
            Sensor currSensor = sensorManager.getDefaultSensor(currSensorType);
            if (currSensor != null) {
                availableEnvironmentSensors.add(currSensor.getStringType());
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

}
