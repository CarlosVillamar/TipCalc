package com.example.carlosvillamar.tipcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener {

    //declare your variables for the widgets
    private EditText editTextBillAmount;
    private TextView textViewBillAmount;
    private TextView TipLabel;
    private TextView TotalLabel;
    private SeekBar amountOfTip;
    private TextView PercentOf;
    // private Button Clear = findViewById(R.id.Clear);

    //declare the variables for the calculations
    private double billAmount = 0.0;
    private double percent = 0;
    //changed to 10 percent
    private double tipValue = 0.0;


    //set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add Listeners to Widgets
        editTextBillAmount = (EditText) findViewById(R.id.editText_BillAmount);//uncomment this line

        editTextBillAmount.addTextChangedListener((TextWatcher) this);//uncomment this line

        TipLabel = findViewById(R.id.Tip_view);

        TotalLabel = findViewById(R.id.total_view);

        /*

        <Button
        android:id="@+id/Clear"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        android:layout_column="0"
        android:layout_columnSpan="2"
        android:layout_gravity="center"
        android:layout_row="4"
        android:background="@color/backgroundBillAmount"
        android:text="@string/clear"
        android:textColor="@color/colorAccent"
        android:textSize="10pt"
        />




        Clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity","insideOnClick");

                clear();
            }
        });*/

        PercentOf = findViewById(R.id.PercentageOf_ID);

        amountOfTip = findViewById(R.id.seekBar);
        amountOfTip.setOnSeekBarChangeListener(this);
        amountOfTip.setMax(100);


        textViewBillAmount = (TextView) findViewById(R.id.textView_BillAmount);
    }

    @Override
    public void onProgressChanged(SeekBar amountOfTip, int progress, boolean b) {
        percent = progress;
        percent = percent/100;

    }

    @Override
    public void onStartTrackingTouch(SeekBar amountOfTip) {
        //amountOfTip.getProgress();
        billAmount = billAmount - tipValue;

    }

    @Override
    public void onStopTrackingTouch(SeekBar amountOfTip) {
        //amountOfTip.getProgress();
        //PercentOf.setText(amountOfTip.getProgress());
        calculate();

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    /*
    Note:   int i, int i1, and int i2
            represent start, before, count respectively
            The charSequence is converted to a String and parsed to a double for you
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= " + charSequence);
        //surround risky calculations with try catch (what if billAmount is 0 ?
        try {
            billAmount = Double.parseDouble(charSequence.toString()) / 100;
        }catch(Exception e){
            billAmount = 0;
        }

        Log.d("MainActivity", "Bill Amount = " + billAmount);
        //setText on the textView
        textViewBillAmount.setText(currencyFormat.format(billAmount));
        //perform tip and total calculation and update UI by calling calculate
        calculate();
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }


    // calculate and display tip and total amounts
    private void calculate() {
        Log.d("MainActivity", "inside calculate method");
        //uncomment below
        // format percent and display in percentTextView
        //amountOfTip.getProgress();

        PercentOf.setText(percentFormat.format(percent));

        // calculate the tip and total
        tipValue = billAmount * percent;

        //use the tip example to do the same for the Total

        billAmount = billAmount + tipValue;

        // display tip and total formatted as currency
        //user currencyFormat instead of percentFormat to set the textViewTip

        TipLabel.setText(currencyFormat.format(tipValue));

        //use the tip example to do the same for the Total
        TotalLabel.setText(currencyFormat.format(billAmount));
    }

  /*  public void clear(){
        editTextBillAmount.setText("Enter Amount here");
        tipValue = 0.0;
        billAmount = 0.0;
    }*/
}
