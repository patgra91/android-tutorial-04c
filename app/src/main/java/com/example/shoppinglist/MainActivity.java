package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

        if (textViewIdArrayList == null) {
            // Iterate through views inside LinearLayout (contains the TextViews for items).
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.text_scroller);
            final int childCount = linearLayout.getChildCount();
            textViewIdArrayList = new ArrayList<Integer>(10);
            TextView textView = null;
            for (int i = 0; i < childCount; i++) {
                textView = (TextView) linearLayout.getChildAt(i);
                textViewIdArrayList.add(textView.getId());
            }
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
                for (Integer id : textViewIdArrayList) {
                    textView = (TextView) findViewById(id);
                    if (textView.getText().equals("")) {
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

    public void onButtonFindStore(View view) {
        EditText editText = findViewById(R.id.edittext_find_store);
        String loc = editText.getText().toString();
        Uri webpage = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ShoppingList", "Failed to resolve intent: onButtonFindStore()");
        }
    }
}