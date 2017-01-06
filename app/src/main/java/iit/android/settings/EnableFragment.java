package iit.android.settings;

import iit.android.swarachakra.R;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EnableFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivity mainApp = MainActivity.getMainApp();
		
		View v = inflater.inflate(R.layout.enable_fragment, container,  false);
		TextView instructionsHeadTextView = (TextView)v.findViewById(R.id.enable_instructions_head);
		TextView step1TextView = (TextView)v.findViewById(R.id.enable_step_1);
		TextView step2TextView = (TextView)v.findViewById(R.id.enable_step_2);
		TextView step3TextView = (TextView)v.findViewById(R.id.enable_step_3);
		Button enableButton = (Button)v.findViewById(R.id.enable_button);
		
		String instructionsHeadText = mainApp.getStringResourceByName("enable_instructions_head");
		String step1Text = mainApp.getStringResourceByName("enable_step_1");
		String step2Text = mainApp.getStringResourceByName("enable_step_2");
		String step3Text = mainApp.getStringResourceByName("enable_step_3");
		String enableButtonText = mainApp.getStringResourceByName("enable_button");
        FloatingActionButton langButton = (FloatingActionButton) v.findViewById(R.id.toggleLanguage_enable);
        int resId = 0;

        if(mainApp.inEnglish) {
            /*String languageName = getResources().getString(
                    R.string.language_name);
            resId = getResources().getIdentifier(
                    languageName + "_" + "menu_language", "string",
                    mainApp.getPackageName());*/
            langButton.setImageResource(R.drawable.gujarati);
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

		instructionsHeadTextView.setText(instructionsHeadText);
		step1TextView.setText(step1Text);
		step2TextView.setText(step2Text);
		step3TextView.setText(step3Text);
		enableButton.setText(enableButtonText);

        //TODO:
       /* Log.d("dbgm", "Enable oncreateview - s:-" + langButton.getText() + "-");
        if(langButton.getText() =="ENG")
            langButton.setText("MAR");
        else
            langButton.setText("ENG");*/

       /* Log.d("dbgm", "Enable oncreateview - e:-" + langButton.getText() + "-");
		Log.d("dbgm", "Enable onCreateView");*/
		return v;
	}

    /*private void setCorrectText(){

        MainActivity mainApp = MainActivity.getMainApp();

        TextView instructionsHeadTextView = (TextView)findViewById(R.id.enable_instructions_head);
        TextView step1TextView = (TextView)findViewById(R.id.enable_step_1);
        TextView step2TextView = (TextView)findViewById(R.id.enable_step_2);
        TextView step3TextView = (TextView)findViewById(R.id.enable_step_3);
        Button enableButton = (Button)findViewById(R.id.enable_button);

        String instructionsHeadText = mainApp.getStringResourceByName("enable_instructions_head");
        String step1Text = mainApp.getStringResourceByName("enable_step_1");
        String step2Text = mainApp.getStringResourceByName("enable_step_2");
        String step3Text = mainApp.getStringResourceByName("enable_step_3");
        String enableButtonText = mainApp.getStringResourceByName("enable_button");
        Button langButton = (Button) findViewById(R.id.toggleLanguage_enable);
        langButton.setOnClickListener(this);

        instructionsHeadTextView.setText(instructionsHeadText);
        step1TextView.setText(step1Text);
        step2TextView.setText(step2Text);
        step3TextView.setText(step3Text);
        enableButton.setText(enableButtonText);

    }*/
	/*@Override
	public void onClick(View v) {
		Log.d("dbgm", "Enable click:-"+((Button) v).getText()+"-");
		MainActivity mainApp = MainActivity.getMainApp();
		//String enableButtonText = mainApp.getStringResourceByName(mainApp.getLabel());

        mainApp.refreshActivity();
        if(((Button) v).getText() =="ENG")
			((Button) v).setText("MAR");
		else
			((Button) v).setText("ENG");

        Log.d("dbgm", "Enable click-e:-" +((Button) v).getText()+"-");
    }*/
}
