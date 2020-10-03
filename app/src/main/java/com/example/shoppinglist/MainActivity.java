package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private static final String EXTRA_MESSAGE = "com.example.shoppinglist.EXTRA.MESSAGE";
    private static final int REQUEST_CODE_SELECT_ITEM = 1;

    private ArrayList<Integer> textViewIdArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * I'm sure there's a good way to list all Views within the layout and get their id's.
         * Seems its not so trivial though, so I will do it like this:
         *
         * 1) Manually make a list of all TextViews.
         * 2) Iterate through it and only add item if there are empty slots...
         * */

        // Keep track of id for all relevant TextViews.

        if (textViewIdArrayList == null) {
            textViewIdArrayList = new ArrayList<Integer>(10);
            textViewIdArrayList.add(R.id.text_view_item_1);
            textViewIdArrayList.add(R.id.text_view_item_2);
            textViewIdArrayList.add(R.id.text_view_item_3);
            textViewIdArrayList.add(R.id.text_view_item_4);
            textViewIdArrayList.add(R.id.text_view_item_5);
            textViewIdArrayList.add(R.id.text_view_item_6);
            textViewIdArrayList.add(R.id.text_view_item_7);
            textViewIdArrayList.add(R.id.text_view_item_8);
            textViewIdArrayList.add(R.id.text_view_item_9);
            textViewIdArrayList.add(R.id.text_view_item_10);
        }

        // Fill each TextView with the corresponding saved String.
        TextView textView;
        if (savedInstanceState != null) {
            for (Integer id : textViewIdArrayList) {
                textView = (TextView) findViewById(id);
                if (textView.getText().equals("")) {
                    textView.setText(savedInstanceState.getString(String.valueOf(id)));
                }
            }
        }
    }

    public void onButtonAddItem(View view) {
        Intent intent = new Intent(this, SelectItem.class);
        //intent.putExtra(EXTRA_MESSAGE, "no message..."); // No need to send anything.
        startActivityForResult(intent, REQUEST_CODE_SELECT_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_ITEM) {
            if (resultCode == RESULT_OK) {
                // Iterate through TextViews, if empty: add newly selected String.
                // Eventually, every TextView will be taken.
                TextView textView = null;
                for (Integer id : textViewIdArrayList){
                    textView = (TextView) findViewById(id);
                    if (textView.getText().equals("")){
                        textView.setText(data.getStringExtra(SelectItem.EXTRA_REPLY));
                        // Break out of the for loop, ony one new item is added at a time.
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView textView;
        for (Integer id : textViewIdArrayList) {
            textView = (TextView) findViewById(id);
            if (!textView.getText().equals("")) {
                outState.putString(String.valueOf(id), textView.getText().toString());
            }
        }
    }
}