package lxfeng.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * <类描述>
 * 作者： Administrator
 * 时间： 2016/3/10
 */
public class PluginManager {

    private Context mContext;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        return PluginHolder.sManager;
    }

    static class PluginHolder {
        static PluginManager sManager = new PluginManager();
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    Map<String, PluginApk> sMap = new HashMap<String, PluginApk>();

    public final void loadApk(String apkPath) {
        PackageInfo packageInfo = queryPackageInfo(apkPath);
        if (packageInfo == null || TextUtils.isEmpty(packageInfo.packageName)) return;
        PluginApk pluginApk = sMap.get(packageInfo.packageName);
        if (pluginApk == null) {
            pluginApk = createApk(apkPath ,packageInfo);
            if (pluginApk != null) {
                sMap.put(packageInfo.packageName, pluginApk);
            } else {
                throw new NullPointerException("PluginApk is null");
            }
        }
    }

    private PackageInfo queryPackageInfo(String apkPath) {
        return mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
    }

    private PluginApk createApk(String apkPath,PackageInfo packageInfo) {
        PluginApk pluginApk = null;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            Resources pluginRes =
                    new Resources(assetManager, mContext.getResources().getDisplayMetrics(),
                            mContext.getResources().getConfiguration());
            DexClassLoader classLoader = createDexClassLoader(apkPath);
            pluginApk = new PluginApk(classLoader,pluginRes,packageInfo);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return pluginApk;
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        File dexOutputDir = mContext.getDir("dex", Context.MODE_PRIVATE);
        DexClassLoader loader = new DexClassLoader(apkPath, dexOutputDir.getAbsolutePath(), null,
                mContext.getClassLoader());
        return loader;
    }

    public void startActivity(Intent intent){
        Intent myIntent = new Intent(mContext,ProxyActivity.class);
        Bundle extra = intent.getExtras();
        if (extra == null || !extra.containsKey(Constants.PLUGIN_CLASS_NAME) &&
                !extra.containsKey(Constants.PACKAGE_NAME)){
                throw new IllegalArgumentException("没有设置插件的类路径和包名");
        }
        myIntent.putExtras(intent);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }

    public PluginApk getPluginApk(String packageName){
        return sMap.get(packageName);
    }

}
