package com.bgraefnitz.wrapper;

import android.content.Context;
import android.os.Handler;

import zj.com.cn.bluetooth.sdk.BluetoothService;
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class MunbynWrapper extends CordovaPlugin {
  private static final String DURATION_LONG = "long";
  @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {
      if (action.equals("show")) {
		callbackContext.success("win");
        return true;
      }
	  if (action.equals("write")) {
		byte[] byteArray = "write string".getBytes();
		Context context = this.cordova.getActivity().getApplicationContext();
		BluetoothService btService = new BluetoothService(context, new Handler());
		btService.write(byteArray);
		callbackContext.success("written");
        return true;
      }
      return false;
  }
}