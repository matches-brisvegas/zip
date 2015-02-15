package net.mjc.zip;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.*;

import java.io.IOException;

public class CameraPreviewSurface extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Camera camera;
    private boolean isPreviewRunning;

    public CameraPreviewSurface(Context context, Camera camera) {
        super(context);
        this.camera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        holder = getHolder();
        holder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(this.getClass().getName(), "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.release();
        // empty. Take care of releasing the Camera preview in your activity.     //TODO
    }

    /**
     * public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
     * {
     * if (isPreviewRunning)
     * {
     * camera.stopPreview();
     * }
     * <p/>
     * Camera.Parameters parameters = camera.getParameters();
     * Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
     * <p/>
     * if(display.getRotation() == Surface.ROTATION_0)
     * {
     * parameters.setPreviewSize(height, width);
     * camera.setDisplayOrientation(90);
     * }
     * <p/>
     * if(display.getRotation() == Surface.ROTATION_90)
     * {
     * parameters.setPreviewSize(width, height);
     * }
     * <p/>
     * if(display.getRotation() == Surface.ROTATION_180)
     * {
     * parameters.setPreviewSize(height, width);
     * }
     * <p/>
     * if(display.getRotation() == Surface.ROTATION_270)
     * {
     * parameters.setPreviewSize(width, height);
     * camera.setDisplayOrientation(180);
     * }
     * <p/>
     * //        camera.setParameters(parameters);
     * try {
     * camera.setDisplayOrientation(90);
     * camera.setPreviewDisplay(holder);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * <p/>
     * previewCamera();
     * }
     * <p/>
     * public void previewCamera()
     * {
     * try
     * {
     * camera.setPreviewDisplay(holder);
     * camera.startPreview();
     * isPreviewRunning = true;
     * }
     * catch(Exception e)
     * {
     * Log.d(this.getClass().getName(), "Cannot start preview", e);
     * }
     * }
     */

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (this.holder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(this.holder);
            camera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(this.getClass().getName(), "Error starting camera preview: " + e.getMessage());
        }
    }
}