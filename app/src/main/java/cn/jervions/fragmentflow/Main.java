package cn.jervions.fragmentflow;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by jervions on 16-7-5.
 */
public class Main implements IXposedHookLoadPackage {

    //SharedPreferences sp =

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();

                XSharedPreferences pre = new XSharedPreferences("cn.jervions.fragmentflow", "test");
                tv.setText(pre.getString("test_blank_space", "  ") + text + pre.getString("test_blank_space_r", "  "));

                if (Integer.parseInt(pre.getString("test_put", "1"))>1) {

                    //tv.setTextColor(Color.RED);
                    tv.setTextColor(Color.WHITE);
                    XposedBridge.log(pre.getString("test_put", "0"));
                }
                else {
                    tv.setTextColor(Color.YELLOW);
                    XposedBridge.log(pre.getString("test_put", "0"));
                }



            }
        });
    }
}
