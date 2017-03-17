package iit.android.settings;

import iit.android.swarachakra.R;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DefaultFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
MainActivity mainApp = MainActivity.getMainApp();
		
		View v = inflater.inflate(R.layout.default_fragment, container, false);
		TextView instructionsHeadTextView = (TextView)v.findViewById(R.id.default_instructions_head);
		TextView step1TextView = (TextView)v.findViewById(R.id.default_step_1);
		Button defaultButton = (Button)v.findViewById(R.id.default_button);
		FloatingActionButton langButton = (FloatingActionButton) v.findViewById(R.id.toggleLanguage_default);
		int resId;
		if(mainApp.inEnglish) {
			/*String languageName = getResources().getString(
					R.string.language_name);
			resId = getResources().getIdentifier(
					languageName + "_" + "menu_language", "string",
					mainApp.getPackageName());*/
            langButton.setImageResource(R.drawable.language);
		}else{
			/*resId = getResources().getIdentifier(
					"menu_language", "string",
					mainApp.getPackageName());*/
            langButton.setImageResource(R.drawable.english);

		}
		//language_text= getResources().getString(resId);
		//m:langButton.setText(getResources().getString(resId));
		//langButton.setText(mainApp.getStringResourceByName("menu_language"));
		//langButton.setOnClickListener(this);

		String instructionsHeadText = mainApp.getStringResourceByName("default_instructions_head");
		String step1Text = mainApp.getStringResourceByName("default_step_1");
		String defaultButtonText = mainApp.getStringResourceByName("default_button");
		
		instructionsHeadTextView.setText(instructionsHeadText);
		step1TextView.setText(step1Text);
		defaultButton.setText(defaultButtonText);
		return v;
	}

	/*@Override
	public void onClick(View v) {
		Log.d("dbgm", "Default click");
	}*/
}
