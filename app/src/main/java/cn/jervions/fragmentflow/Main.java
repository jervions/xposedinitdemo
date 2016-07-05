package cn.jervions.fragmentflow;

import android.graphics.Color;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by jervions on 16-7-5.
 */
public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                tv.setText(text + " :)");
                tv.setTextColor(Color.RED);
            }
        });
    }
}
