package com.home.sewright22.diabeticglucosecontrol;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DayLogActivity extends AppCompatActivity {

    private JournalEntryList journalEntries = new JournalEntryList();
    ListView listView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        JournalEntry first = new JournalEntry();
        first.setFood("Tofu");
        first.setCarbCount(24);
        first.setStartingBG(123);
        first.setInitialBolus(2);
        journalEntries.addJournalEntry(first);
        UpdateDisplayedJournal();

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent parcelIntent = new Intent(DayLogActivity.this, JournalEntryDetailsActivity.class);

                int itemPosition = position;

                // ListView Clicked item value
                JournalEntry itemValue = (JournalEntry) listView.getItemAtPosition(position);

                parcelIntent.putExtra("item", itemValue);

                startActivityForResult(parcelIntent, 1);
            }

        });

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JournalEntry entry = new JournalEntry();
                PromptUserForFood(entry);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day_log, menu);
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
    }

    public void PromptUserForFood(final JournalEntry entry) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DayLogActivity.this);
        final EditText input = new EditText(DayLogActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setTitle("Food Entry");
        alertDialogBuilder.setMessage("What did you eat?");
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                entry.setFood(input.getText().toString());
                PromptUserForCarbCount(entry);
            }
        });

        alertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        alertDialogBuilder.setIcon(android.R.drawable.btn_plus);
        alertDialogBuilder.show();
    }

    private void PromptUserForBG(final JournalEntry entry) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DayLogActivity.this);
        final EditText input = new EditText(DayLogActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setTitle("Current BG");
        alertDialogBuilder.setMessage("What is your current blood glucose level?");
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                entry.setStartingBG(Integer.parseInt(input.getText().toString()));
                PromptUserForBolus(entry);
            }
        });

        alertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        alertDialogBuilder.setIcon(android.R.drawable.btn_plus);
        alertDialogBuilder.show();
    }

    private void PromptUserForCarbCount(final JournalEntry entry) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DayLogActivity.this);
        final EditText input = new EditText(DayLogActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setTitle("Carb Count");
        alertDialogBuilder.setMessage("How many carbs do you think is in your meal?");
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                entry.setCarbCount(Integer.parseInt(input.getText().toString()));
                PromptUserForBG(entry);
            }
        });
        alertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        alertDialogBuilder.setIcon(android.R.drawable.btn_plus);
        alertDialogBuilder.show();
    }


    private void PromptUserForBolus(final JournalEntry entry) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DayLogActivity.this);
        final EditText input = new EditText(DayLogActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setTitle("Bolus");
        alertDialogBuilder.setMessage("How much did you bolus your meal?");
        alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                entry.setInitialBolus(Integer.parseInt(input.getText().toString()));
                journalEntries.addJournalEntry(entry);
                UpdateDisplayedJournal();
            }
        });

        alertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        alertDialogBuilder.setIcon(android.R.drawable.btn_plus);
        alertDialogBuilder.show();
    }

    private void UpdateDisplayedJournal() {
        listView = (ListView) findViewById(R.id.list);

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<JournalEntry> adapter = new ArrayAdapter<JournalEntry>(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, journalEntries.getJournalEntries());

        listView.setAdapter(adapter);
        //final TextView textViewToChange = (TextView) findViewById(R.id.log);
        //textViewToChange.setText(text.toString());
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DayLog Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.home.sewright22.diabeticglucosecontrol/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DayLog Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.home.sewright22.diabeticglucosecontrol/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
