package com.bgraefnitz.wrapper;

import android.content.Context;
import android.os.Handler;

import zj.com.cn.bluetooth.sdk.BluetoothService;
import zj.com.command.sdk.PrinterCommand;
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class MunbynWrapper extends CordovaPlugin {
    private static final String LOG_TAG = "BluetoothPrinter";
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mmDevice;
    @Override
    public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) throws JSONException {
        if (action.equals("write")) {
            String name = args.getString(0);
            String message = args.getString(1);
            if (findBT(callbackContext, name)) {
                try {
                    Context context = this.cordova.getActivity().getApplicationContext();
                    BluetoothService btService = new BluetoothService(context, new Handler());
                    btService.start();
                    try {
                        btService.connect(mmDevice);
                        long t= System.currentTimeMillis();
                        long end = t+5000;
                        while(btService.mConnectedThread == null && System.currentTimeMillis() < end)
                        {

                        }
                        if(btService.mConnectedThread == null)
                        {
                            callbackContext.error("Failed to connect to Bluetooth device.");
                            throw new JSONException("Failed to connect to Bluetooth device.");
                        }
                        message = message + "\n";
                        byte[] sendCommand = PrinterCommand.POS_Print_Text(message, "GBK", 0, 0, 0, 0);
                        btService.write(sendCommand);
                        btService.stop();
                        callbackContext.success("written");
                        return true;
                    } catch (Exception e) {
                        callbackContext.error("Bluetooth connection error");
                    }
                    callbackContext.success("connected");

                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }
            } else {
                callbackContext.error("Bluetooth Device Not Found: " + name);
            }
            return true;
        }
        else if (action.equals("list")) {
            listBT(callbackContext);
            return true;
        }
      return true;
    }

    //list all bluetooth devices (return list of names)
    void listBT(CallbackContext callbackContext) {
        BluetoothAdapter mBluetoothAdapter = null;
        String errMsg = null;
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                errMsg = "No bluetooth adapter available";
                Log.e(LOG_TAG, errMsg);
                callbackContext.error(errMsg);
                return;
            }
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                this.cordova.getActivity().startActivityForResult(enableBluetooth, 0);
            }
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                JSONArray json = new JSONArray();
                for (BluetoothDevice device : pairedDevices) {
                    /*
                    Hashtable map = new Hashtable();
                    map.put("type", device.getType());
                    map.put("address", device.getAddress());
                    map.put("name", device.getName());
                    JSONObject jObj = new JSONObject(map);
                    */
                    json.put(device.getName());
                }
                callbackContext.success(json);
            } else {
                callbackContext.error("No Bluetooth Device Found");
            }
            //Log.d(LOG_TAG, "Bluetooth Device Found: " + mmDevice.getName());
        } catch (Exception e) {
            errMsg = e.getMessage();
            Log.e(LOG_TAG, errMsg);
            e.printStackTrace();
            callbackContext.error(errMsg);
        }
    }

    // This will find a bluetooth printer device
    boolean findBT(CallbackContext callbackContext, String name) {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Log.e(LOG_TAG, "No bluetooth adapter available");
            }
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                this.cordova.getActivity().startActivityForResult(enableBluetooth, 0);
            }
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    String btName = device.getName();
                    if (device.getName().equalsIgnoreCase(name)) {
                        mmDevice = device;
                        return true;
                    }
                }
            }
            Log.d(LOG_TAG, "Bluetooth Device Found: " + mmDevice.getName());
        } catch (Exception e) {
            String errMsg = e.getMessage();
            Log.e(LOG_TAG, errMsg);
            e.printStackTrace();
            callbackContext.error(errMsg);
        }
        return false;
    }
}