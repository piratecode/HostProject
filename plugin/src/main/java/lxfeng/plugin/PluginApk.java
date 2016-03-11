package lxfeng.plugin;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * <类描述>
 * 作者： Administrator
 * 时间： 2016/3/10
 */
public class PluginApk {

    public String packageName;
    public PackageInfo packageInfo;
    public DexClassLoader dexClassLoader;
    public Resources resources;
    public AssetManager assetManager;

    public PluginApk(DexClassLoader classLoader,Resources r,PackageInfo packageInfo){
        this.dexClassLoader = classLoader;
        this.resources = r;
        this.packageInfo = packageInfo;
        this.assetManager = r.getAssets();
        this.packageName = packageInfo.packageName;
    }
}
