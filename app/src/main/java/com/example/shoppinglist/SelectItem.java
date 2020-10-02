package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SelectItem extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.shoppinglist.EXTRA.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);
    }

    public void onSelectedItem(View view) {
        Button button = (Button) view;
        String mMessageReturn = "";

        switch (button.getId()){
            case R.id.button_cheese:
                mMessageReturn = "Cheese";
                break;
            case R.id.button_rice:
                mMessageReturn = "Rice";
                break;
            case R.id.button_apples:
                mMessageReturn = "Apples";
            break;
        }
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, mMessageReturn);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}