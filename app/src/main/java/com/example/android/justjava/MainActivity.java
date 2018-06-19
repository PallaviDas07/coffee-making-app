
package com.example.android.justjava;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.CheckBox;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int price = 0;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        hasChocolate = chocolateCheckBox.isChecked();
        EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        int price = calculatePrice();
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for"+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        /*Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mail to:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for"+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);*/
        }
    

    public void increment(View view) {
        if(quantity<=100){

        quantity = quantity + 1;
        display(quantity);}
        else {

            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
    }

    private int calculatePrice() {
        if (hasWhippedCream == true && hasChocolate == false) {
            price = quantity * 6;
            return price;
        } else if (hasChocolate == true && hasWhippedCream == false) {
            price = quantity * 7;
            return price;
        } else if (hasChocolate == true && hasWhippedCream == true) {
            price = quantity * 8;
            return price;

        } else {
            price = quantity * 5;
            return price;
        }
    }

    public void decrement(View view) {
     if(quantity>0){
        quantity = quantity - 1;
        display(quantity);}
        else{
         Toast.makeText(this, "You cannot have less than 0 coffees", Toast.LENGTH_SHORT).show();
         // Exit this method early because there's nothing left to do
         return;
     }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    private String createOrderSummary(int number, boolean addWhippedCream, boolean addChocolate, String addname) {
        String priceMessage = "Name = " + addname;
        priceMessage = priceMessage + "\nAdd Whipped cream?" + addWhippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate?" + addChocolate;
        priceMessage = priceMessage + "\nQuantity" + quantity;
        priceMessage = priceMessage + "\nTotal $" + price;
        priceMessage = priceMessage + "\nThank You";
        return priceMessage;

    }
}
