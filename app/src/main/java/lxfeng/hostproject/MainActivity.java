package lxfeng.hostproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;

import lxfeng.plugin.Constants;
import lxfeng.plugin.PluginManager;

public class MainActivity extends Activity {

    String apkPath ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PluginManager pluginManager = PluginManager.getInstance();
        pluginManager.init(getApplicationContext());
        String pluginApkPath =
                Environment.getExternalStorageDirectory() + File.separator + "plugin" +
                        File.separator + "plugin.apk";
        String pluginClazz = "lxfeng.pluginproject.MainActivity";
        String pluginPackage = "lxfeng.pluginproject";
        PluginManager.getInstance().loadApk(pluginApkPath);
        Intent intent = new Intent();
        intent.putExtra(Constants.PLUGIN_CLASS_NAME,pluginClazz);
        intent.putExtra(Constants.PACKAGE_NAME, pluginPackage);
        pluginManager.startActivity(intent);
    }
}
