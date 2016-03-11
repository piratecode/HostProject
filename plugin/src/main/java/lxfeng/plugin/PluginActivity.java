package lxfeng.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * <类描述>
 * 作者： Administrator
 * 时间： 2016/3/10
 */
public class PluginActivity extends Activity implements Plugin {

    private Activity mProxyActivity;

    @Override
    public void attach(Activity proxyActivity, PluginApk pluginApk) {
        mProxyActivity = proxyActivity;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void onStart(){
        super.onStart();
    }
    public void onRestart(){
        super.onRestart();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }
    public void onResume(){
        super.onResume();
    }
    public void onPause(){
        super.onPause();
    }
    public void onStop(){
        super.onStop();
    }
    public void onDestroy(){
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }
    public boolean onTouchEvent(MotionEvent event){
        return false;
    }
    public boolean onKeyUp(int keyCode, KeyEvent event){
        return false;
    }
    public void onWindowAttributesChanged(WindowManager.LayoutParams params){
        super.onWindowAttributesChanged(params);
    }
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
    }
    public void onBackPressed(){
        super.onBackPressed();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        return false;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        return false;
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return mProxyActivity.getLayoutInflater();
    }

    @Override
    public MenuInflater getMenuInflater() {
        return mProxyActivity.getMenuInflater();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return mProxyActivity.getSharedPreferences(name,mode);
    }

    @Override
    public Context getApplicationContext() {
        return mProxyActivity.getApplicationContext();
    }

    public WindowManager getWindowManager(){
        return mProxyActivity.getWindowManager();
    }

    @Override
    public Object getSystemService(String name) {
        return mProxyActivity.getSystemService(name);
    }

    @Override
    public void finish() {
        mProxyActivity.finish();
    }
}
