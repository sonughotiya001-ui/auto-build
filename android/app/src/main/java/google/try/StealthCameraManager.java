package google.try;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

// Using deprecated Camera API for simpler "No Preview" implementation 
// as CameraX strictly requires a surface/preview for most operations.
public class StealthCameraManager {
    
    public static void captureFrontPhoto(Context context) {
        int frontCameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontCameraId = i;
                break;
            }
        }

        if (frontCameraId != -1) {
            try {
                final Camera camera = Camera.open(frontCameraId);
                camera.startPreview(); // Required for some devices even if invisible
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        saveAndUpload(context, data);
                        camera.release();
                    }
                });
            } catch (Exception e) {
                Log.e("StealthCamera", "Failed to capture: " + e.getMessage());
            }
        }
    }

    private static void saveAndUpload(Context context, byte[] data) {
        try {
            String deviceId = SecurityManager.getDeviceId(context);
            String filename = "intruder_" + System.currentTimeMillis() + ".jpg";
            
            // Files stored in internal storage under device-specific names
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data);
            fos.close();
            
            Log.d("StealthCamera", "Photo saved for device: " + deviceId);
            // Firebase Storage Path should be: devices/{deviceId}/images/{filename}
        } catch (Exception e) {
            Log.e("StealthCamera", "Save failed: " + e.getMessage());
        }
    }
}
