package com.example.caiogenzerico.com.br.ikigayproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivityFragment extends Fragment {

    private DoodleView doodleView;

    private float acceleration;
    private float lastAcceleretion;
    private float currentAccleration;

    private boolean dialogOnScreen = false;

    private static final int ACCELERATION_THRESHOLD = 1000000;

    private static final int SAVE_IMAGE_PERMISSION_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View raiz =
                inflater.inflate(
                        R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        doodleView = raiz.findViewById(R.id.doodleView);
        currentAccleration = SensorManager.GRAVITY_EARTH;
        lastAcceleretion = SensorManager.GRAVITY_EARTH;
        acceleration = 0.00f;
        return raiz;
    }

    //acelerometro
    private SensorEventListener listener =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    lastAcceleretion = currentAccleration;
                    currentAccleration = x * x + y * y + z * z;

                    acceleration = currentAccleration * (currentAccleration - lastAcceleretion);
                    if (acceleration > ACCELERATION_THRESHOLD) {
                        confirmErase();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
    private void confirmErase() {
        EraseImageDialogFragment eraseImageDialogFragment =
                new EraseImageDialogFragment();
        eraseImageDialogFragment.show(getFragmentManager(), "erase dialog");
    }

    private void enableAccelerometerListening() {
        SensorManager sensorManager = (SensorManager)
                getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(
                listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                sensorManager.SENSOR_DELAY_NORMAL);
    }

    private void disableAccelerometerListener() {
        SensorManager sensorManager = (SensorManager)
                getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.unregisterListener(
                listener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        disableAccelerometerListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableAccelerometerListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.doodle_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.color:
                ColorDialogFragment colorDialogFragment =
                        new ColorDialogFragment();
                colorDialogFragment.show(getFragmentManager(), "color Dialog");
                break;
            case R.id.line_width:
                LineWidthDialogFragment lineWidthDialogFragment =
                        new LineWidthDialogFragment();
                lineWidthDialogFragment.show(getFragmentManager(), "width Fragment");
                break;

            case R.id.print:
                //doodleView.printImage();
                break;
            case R.id.delete_drawing:
                confirmErase();
                break;
            case R.id.save:
                //saveImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveImage() {
        if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.permission_explanation);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                SAVE_IMAGE_PERMISSION_REQUEST_CODE
                        );
                    }
                });
                builder.create().show();
            }
            else {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        SAVE_IMAGE_PERMISSION_REQUEST_CODE
                );
            }
        }
        else{
            //doodleView.saveImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case SAVE_IMAGE_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //doodleView.saveImage;
                }
                else{
                    Toast.makeText(getActivity(),
                            getString(R.string.permission_explanation),
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public DoodleView getDoodleView(){
        return doodleView;
    }
    public void setDialogOnScreen(boolean dialogOnScreen){
        this.dialogOnScreen = dialogOnScreen;
    }
}
