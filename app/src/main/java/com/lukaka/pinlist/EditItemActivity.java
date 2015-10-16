package com.lukaka.pinlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {
    private Menu menu;
    private String eventTitle;
    Bundle b = new Bundle();
    EditText etEventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (toolbar != null) {
            getSupportActionBar().setTitle("Pinlist");
            toolbar.setNavigationIcon(R.drawable.ic_navigate_before);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        this.b = getIntent().getExtras();
        this.eventTitle = this.b.getString("title");

        etEventTitle = (EditText) findViewById(R.id.etEventTitle);
        etEventTitle.setText(this.eventTitle);
        etEventTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEventTitle.setCursorVisible(true);
            }
        });
        etEventTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                etEventTitle.setCursorVisible(false);
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // save to event title
                    eventTitle = etEventTitle.getText().toString();
                    return true;
                }
                return false;
            }
        });
        int textLen = etEventTitle.getText().length();
        etEventTitle.setSelection(textLen, textLen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            // save the event and back to last activity
            eventTitle = etEventTitle.getText().toString();
            onSubmit();
            return true;
        }

        if (id == R.id.action_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSubmit() {
        Intent resultIntent = new Intent();
        this.b.putString("title", eventTitle);
        resultIntent.putExtras(b);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
