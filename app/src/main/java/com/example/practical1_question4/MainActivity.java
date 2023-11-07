package com.example.practical1_question4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView imageView;
    private SensorManager sensorManager;
    private Sensor proximitySensor, accelerometerSensor;
    private boolean colour = false;
    private TextView view, proximityView;
    private long lastUpdateTime;
    private static float SHAKE_THRESHOLD_GRAVITY = 2;
    private static int proximity_Point=0;
    ProgressBar progressBar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.textview_Shake);
        view.setBackgroundColor(Color.GREEN);
        view.setText("Shake me");

        progressBar = findViewById(R.id.progressBar_Shake);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        progressBar.setMax(5);

        sensorManager = (SensorManager)  getSystemService(SENSOR_SERVICE);

        lastUpdateTime = System.currentTimeMillis();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            Toast.makeText(this, "Accelerometer sensor is not available!!", Toast.LENGTH_SHORT).show();
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        } else {
            Toast.makeText(this, "Proximity sensor is not available!!", Toast.LENGTH_SHORT).show();
        }

        imageView = findViewById(R.id.imageView_Proxi);
        proximityView = findViewById(R.id.textView_Proxi);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(sensorEvent);
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            proximityView.setText("Proximity Sensor: " + sensorEvent.values[0]);
            proximity_Point = (int) sensorEvent.values[0];
            updateProximity();
        }
    }

    private void getAccelerometer(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float gX = x/SensorManager.GRAVITY_EARTH;
        float gY = y/SensorManager.GRAVITY_EARTH;
        float gZ = z/SensorManager.GRAVITY_EARTH;

        float gForce = (float)Math.sqrt(gX*gX + gY*gY + gZ*gZ);
        progressBar.setProgress((int) gForce);

        long currentTime = System.currentTimeMillis();
        if (gForce >= SHAKE_THRESHOLD_GRAVITY){

            if (currentTime - lastUpdateTime < 200){
                return;
            }
            lastUpdateTime = currentTime;

            view.setText("Device was shaken");
            if (!colour){
                view.setBackgroundColor(Color.RED);
            }
            colour = !colour;
        }

        if (gForce < SHAKE_THRESHOLD_GRAVITY && currentTime - lastUpdateTime > 3000){
            progressBar.setProgress(0);
            view.setText("Shake me");
            view.setBackgroundColor(Color.GREEN);
        }
    }

    private void updateProximity() {

        switch (proximity_Point){
            case 10:
                imageView.setImageResource(R.drawable.a1);
                break;
            case 9:
                imageView.setImageResource(R.drawable.a2);
                break;
            case 8:
                imageView.setImageResource(R.drawable.a3);
                break;
            case 7:
                imageView.setImageResource(R.drawable.a4);
                break;
            case 6:
                imageView.setImageResource(R.drawable.a5);
                break;
            case 5:
                imageView.setImageResource(R.drawable.a6);
                break;
            case 4:
                imageView.setImageResource(R.drawable.a7);
                break;
            case 3:
                imageView.setImageResource(R.drawable.a8);
                break;
            case 2:
                imageView.setImageResource(R.drawable.a9);
                break;
            case 1:
                imageView.setImageResource(R.drawable.a10);
                break;
            case 0:
                imageView.setImageResource(R.drawable.a11);
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}