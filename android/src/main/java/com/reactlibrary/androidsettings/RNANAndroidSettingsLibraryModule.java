package com.reactlibrary.androidsettings;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNANAndroidSettingsLibraryModule extends ReactContextBaseJavaModule {
	private static final String TAG = "RNANAndroidSettingsLibrary";

	public RNANAndroidSettingsLibraryModule(ReactApplicationContext reactContext) {
		super(reactContext);
	}

	@Override
	public String getName() {
		return TAG;
	}

	@ReactMethod
	public void open(String settingsAction, final Promise promise) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromParts("package", getReactApplicationContext().getPackageName(), null);

		switch (settingsAction) {
			case "ACTION_SETTINGS":
				intent.setAction(Settings.ACTION_SETTINGS);
				break;
			case "ACTION_WIRELESS_SETTINGS":
				intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
				break;
			case "ACTION_AIRPLANE_MODE_SETTINGS":
				intent.setAction(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
				break;
			case "ACTION_WIFI_SETTINGS":
				intent.setAction(Settings.ACTION_WIFI_SETTINGS);
				break;
			case "ACTION_APN_SETTINGS":
				intent.setAction(Settings.ACTION_APN_SETTINGS);
				break;
			case "ACTION_BLUETOOTH_SETTINGS":
				intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
				break;
			case "ACTION_DATE_SETTINGS":
				intent.setAction(Settings.ACTION_DATE_SETTINGS);
				break;
			case "ACTION_LOCALE_SETTINGS":
				intent.setAction(Settings.ACTION_LOCALE_SETTINGS);
				break;
			case "ACTION_INPUT_METHOD_SETTINGS":
				intent.setAction(Settings.ACTION_INPUT_METHOD_SETTINGS);
				break;
			case "ACTION_DISPLAY_SETTINGS":
				intent.setAction(Settings.ACTION_DISPLAY_SETTINGS);
				break;
			case "ACTION_SECURITY_SETTINGS":
				intent.setAction(Settings.ACTION_SECURITY_SETTINGS);
				break;
			case "ACTION_LOCATION_SOURCE_SETTINGS":
				intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				break;
			case "ACTION_INTERNAL_STORAGE_SETTINGS":
				intent.setAction(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
				break;
			case "ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS":
				intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
				intent.setData(uri);
				break;
			case "ACTION_MEMORY_CARD_SETTINGS":
				intent.setAction(Settings.ACTION_MEMORY_CARD_SETTINGS);
				break;
			case "ACTION_SOUND_SETTINGS":
				intent.setAction(Settings.ACTION_SOUND_SETTINGS);
				break;
			case "ACTION_APPLICATION_DETAILS_SETTINGS":
				intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.setData(uri);
				break;
			case "ACTION_MANAGE_OVERLAY_PERMISSION":
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
					intent.setData(uri);
				} else {
					promise.reject("EUNSPECIFIED", "Cannot open ACTION_MANAGE_OVERLAY_PERMISSION on API version < 23");
					return;
				}
				break;
			default:
				intent.setAction(Settings.ACTION_SETTINGS);
				break;
		}
		
		try {
			getReactApplicationContext().startActivity(intent);
		} catch (Exception e) {
			Log.e(TAG, Log.getStackTraceString(e));
			promise.reject(e);
		}
		promise.resolve(null);
	}

	@ReactMethod
	public void main(final Promise promise) {
		open("main", promise);
	}

	@ReactMethod
	public void canDrawOverlays(final Promise promise) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			try {
				boolean canDraw = Settings.canDrawOverlays(getReactApplicationContext());
				promise.resolve(canDraw);
			} catch (Exception e) {
				promise.reject(e);
			}
		} else {
			promise.resolve(true);
		}
	}
}
