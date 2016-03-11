package lxfeng.plugin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Bundle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <类描述>
 * 作者： Administrator
 * 时间： 2016/3/11
 */
public class ProxyController {

    private Activity mProxyActivity;

    private PluginManager mPluginManager;
    private PluginApk mPluginApk;

    private AssetManager mAssetManager;
    private Resources mResources;
    private Theme mTheme;

    private String mPackageName;
    private String mPluginClass;

    protected Plugin mPluginActivity;

    public ProxyController(Activity activity){
        mProxyActivity = activity;
    }

    public void onCreate(Intent intent){

        mPluginClass = intent.getStringExtra(Constants.PLUGIN_CLASS_NAME);
        mPackageName = intent.getStringExtra(Constants.PACKAGE_NAME);

        mPluginManager = PluginManager.getInstance();
        mPluginManager.init(mProxyActivity);
        mPluginApk = mPluginManager.getPluginApk(mPackageName);
        mResources = mPluginApk.resources;
        mAssetManager = mPluginApk.assetManager;

        launchTargetActivity();
    }

    protected void launchTargetActivity(){
        try {
            Class<?> localClass = getClassLoader().loadClass(mPluginClass);
            Constructor<?> localClassConstructor = localClass.getConstructor(new Class[]{});
            Object instance = localClassConstructor.newInstance(new Object[]{});
            mPluginActivity = (Plugin) instance;
            ((Attachable)mProxyActivity).attach(mPluginActivity,mPluginManager);
            mPluginActivity.attach(mProxyActivity,mPluginApk);

            Bundle bundle = new Bundle();
            mPluginActivity.onCreate(bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public ClassLoader getClassLoader(){
        return mPluginApk.dexClassLoader;
    }

    public AssetManager getAssets(){
        return mAssetManager;
    }

    public Resources getResources(){
        return mResources;
    }

    public Theme getTheme(){
        return mTheme;
    }


}
