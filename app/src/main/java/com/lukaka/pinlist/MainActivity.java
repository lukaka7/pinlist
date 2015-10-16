package com.lukaka.pinlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int ACTIVITY_EDIT = 1;

    private Menu menu;
    ArrayList<String> todoItems = new ArrayList<String>();
    ArrayList<Pin> pins = new ArrayList<Pin>();
    PinlistAdapter aToDoAdapter;
    ListView lvItems;
    EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.drawable.ic_sort);

        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Delete your pin");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(position);
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                String title = todoItems.get(position);
                Bundle b = new Bundle();
                b.putInt("position", position);
                b.putString("title", title);
                intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtras(b);
                startActivityForResult(intent, ACTIVITY_EDIT);
            }
        });
    }

    private void removeItem(int position) {
        todoItems.remove(position);
        aToDoAdapter.notifyDataSetChanged();
        writeItems();
    }

    private void populateArrayItems() {
        readItems();
        if (todoItems != null) {
            aToDoAdapter = new PinlistAdapter(this, R.layout.list_row, todoItems);
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "pinlist.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "pinlist.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");
        writeItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            Log.i("lujia!!!!!!!!!!!!", "Cancellllllllll");
            return;
        }

        Bundle b = data.getExtras();
        Log.i("lujia!!!!!!!!!!!!", b.getString("title"));
        switch (requestCode) {
            case ACTIVITY_EDIT:
                int position = b.getInt("position");
                String newTitle = b.getString("title");
                Log.i("lujia", Integer.toString(position));
                Log.i("lujia", newTitle);
                todoItems.set(position, newTitle);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                break;
        }
    }
}
