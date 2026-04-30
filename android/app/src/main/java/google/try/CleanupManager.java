package google.try;

import android.content.Context;
import java.io.File;

public class CleanupManager {

    public static void clearUploadCache(Context context) {
        try {
            File dir = context.getFilesDir();
            if (dir != null && dir.isDirectory()) {
                for (File child : dir.listFiles()) {
                    if (child.getName().startsWith("intruder_")) {
                        child.delete();
                    }
                }
            }
        } catch (Exception e) {
            // Log error
        }
    }
}
