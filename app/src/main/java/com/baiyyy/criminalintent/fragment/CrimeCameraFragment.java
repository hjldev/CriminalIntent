package com.baiyyy.criminalintent.fragment;

import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baiyyy.criminalintent.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by huangjinlong on 2016/1/7.
 */
public class CrimeCameraFragment extends Fragment {

    private Camera camera;

    private Button camera_btn;
    private SurfaceView camera_sv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_camera, container, false);
        camera_btn = (Button) view.findViewById(R.id.camera_btn);
        camera_sv = (SurfaceView) view.findViewById(R.id.camera_sv);
        SurfaceHolder holder = camera_sv.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (camera != null) {
                        camera.setPreviewDisplay(holder);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
                parameters.setPreviewSize(size.width, size.height);
                camera.setParameters(parameters);
                try{
                    camera.startPreview();
                } catch (Exception e){
                    camera.release();
                    camera = null;
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (camera != null){
                    camera.stopPreview();
                }
            }
        });

        return view;
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int width, int height){
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (Camera.Size s : sizes){
            int area = s.width * s.height;
            if (area > largestArea){
                bestSize = s;
                largestArea = area;
            }
        }
        return bestSize;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            camera = Camera.open(0);
        } else {
            camera = Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
