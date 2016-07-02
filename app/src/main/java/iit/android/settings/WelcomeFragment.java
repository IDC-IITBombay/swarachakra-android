package iit.android.settings;

import iit.android.swarachakra.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeFragment extends Fragment {
	private MainActivity mMainActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mMainActivity = MainActivity.getMainApp();

		View v = inflater.inflate(R.layout.welcome_fragment, container, false);
		TextView title = (TextView) v.findViewById(R.id.title);
		TextView instruction = (TextView) v.findViewById(R.id.instruction);
		Button welcomeButton = (Button) v.findViewById(R.id.welcome_button);

		instruction.setText(mMainActivity
				.getStringResourceByName("welcome_instruction"));
		title.setText(mMainActivity.getStringResourceByName("welcome_title"));
		welcomeButton.setText(mMainActivity.getStringResourceByName("welcome_button"));
		return v;
	}
}
