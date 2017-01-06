package iit.android.settings;

import iit.android.swarachakra.R;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CongratsFragment extends Fragment {
	private MainActivity mMainActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mMainActivity = MainActivity.getMainApp();

		View v = inflater.inflate(R.layout.congrats_fragment, container, false);
		TextView title = (TextView) v.findViewById(R.id.title);
		TextView instruction = (TextView) v.findViewById(R.id.instruction);
		Button congratsButton = (Button) v.findViewById(R.id.congrats_button);
		FloatingActionButton langButton = (FloatingActionButton) v.findViewById(R.id.toggleLanguage_congrats);
		//langButton.setText(mMainActivity.getStringResourceByName("menu_language"));
		int resId;
		if(mMainActivity.inEnglish) {
			/*String languageName = getResources().getString(
					R.string.language_name);
			resId = getResources().getIdentifier(
					languageName + "_" + "menu_language", "string",
					mMainActivity.getPackageName());*/
			langButton.setImageResource(R.drawable.gujarati);
		}else{
			/*resId = getResources().getIdentifier(
					"menu_language", "string",
					mMainActivity.getPackageName());*/
			langButton.setImageResource(R.drawable.english);

		}
		//language_text= getResources().getString(resId);
		//m:langButton.setText(getResources().getString(resId));

		instruction.setText(mMainActivity
				.getStringResourceByName("congrats_instruction"));
		title.setText(mMainActivity.getStringResourceByName("congrats_title"));
		congratsButton.setText(mMainActivity.getStringResourceByName("congrats_button"));
		return v;
	}

}
