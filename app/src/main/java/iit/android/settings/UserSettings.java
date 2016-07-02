package iit.android.settings;

import iit.android.swarachakra.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

public class UserSettings extends PreferenceFragment {
	private static SharedPreferences prefs;
	SharedPreferences.Editor editor;

	public static SharedPreferences getPrefs() {
		return prefs;
	}

	public static void setPrefs(SharedPreferences prefs) {
		UserSettings.prefs = prefs;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.settings);
        SharedPreferences settings = getPreferenceManager().getSharedPreferences();
        prefs = settings;
        editor = settings.edit();
		//String key = this.getResources().getString(R.string.tablet_layout_setting_key);
		String layout = this.getResources().getString(R.string.layout);
		String screensize = this.getResources().getString(R.string.layout_screensize);

		boolean isFirstRun = settings.getBoolean("is_first_run", true);
		if (isFirstRun) {
			editor.putBoolean("is_first_run", false);
			//editor.putBoolean(key, SettingsActivity.isTablet);

			/*if(SettingsActivity.isTablet)
				editor.putInt(key, 2);
			else
				editor.putInt(key, 1);*/

			editor.putInt(layout, SettingsActivity.LAYOUT_HIVE);
			//editor.putInt(screensize, SettingsActivity.isTablet);
			editor.putInt(screensize, SettingsActivity.isTablet);


			Log.d("settings",""+prefs.getInt(screensize,0));
			editor.commit();
		}
	}
	
	/*public void setPreferenceValue(String boolName, boolean boolValue) {
		editor.putBoolean(boolName, boolValue);
		editor.commit();
	}*/
}
