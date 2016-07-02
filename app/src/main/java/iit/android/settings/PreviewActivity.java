package iit.android.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import iit.android.swarachakra.R;

public class PreviewActivity extends AppCompatActivity {

    private EditText previewEditText;
    private Button back;
    private String title;
    private boolean inEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        Intent i = getIntent();
        title = i.getExtras().getString("title");
        inEnglish = i.getExtras().getBoolean("inEnglish");

        previewEditText = (EditText) findViewById(R.id.preview_edit_text);
        back = (Button) findViewById(R.id.backToSetting);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), iit.android.settings.SettingsActivity.class);
                i.putExtra("inEnglish", inEnglish);
                startActivity(i);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
            }
        });

        back.setText(title);
        showPreview();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        showPreview();
    }

    private void showPreview() {
        Log.d("dbgm", "showpreview() in preview");
        back.setText(title);

        previewEditText.requestFocus();
        previewEditText.setText(null);

        InputMethodManager imm = (InputMethodManager) getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(previewEditText, 0);
    }
}
