package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //private static final String EXTRA_MESSAGE = "com.example.shoppinglist.EXTRA.MESSAGE";
    private static final int REQUEST_CODE_SELECT_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            // iterate: text_view_item_#
            int i = 1;
            TextView textView = findViewById(R.id.text_view_item_1);
            textView.setText(savedInstanceState.getString(String.valueOf(i)));
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
                TextView textView = findViewById(R.id.text_view_item_1);
                textView.setText(data.getStringExtra(SelectItem.EXTRA_REPLY));
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TextView textView = findViewById(R.id.text_view_item_1);
        // iterate: text_view_item_#

        if (!textView.getText().equals("")) {
            // save
            int i = 1;
            outState.putString(String.valueOf(i), textView.getText().toString());
        }
    }
}