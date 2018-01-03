package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
     public void submitOrder (View v) {
         CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checbox);
         boolean hasWhippedCreamCheckBox = whippedCreamCheckBox.isChecked();

         CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
         boolean hasChocolate = chocolateCheckBox.isChecked();

         EditText nameOfCustomer = (EditText) findViewById(R.id.name_of_customer);
         String nameOfOrder = nameOfCustomer.getText().toString();

         int price = calculatePrice(hasWhippedCreamCheckBox, hasChocolate);

         String priceMessage = createOrderSummary(price, hasWhippedCreamCheckBox, hasChocolate, nameOfOrder);
         /**
         displayMessage(priceMessage); */

         Intent intent = new Intent(Intent.ACTION_SENDTO);
         intent.setData(Uri.parse("mailto:")); // only email apps should handle this
         intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary for " +nameOfOrder);
         intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
         if (intent.resolveActivity(getPackageManager()) != null) {
             startActivity(intent);
         }
     }



    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */


   private int calculatePrice(boolean hasWhippedCreamCheckBox, boolean hasChocolate) {
       int basePrice = 5;

       if (hasWhippedCreamCheckBox) {
           basePrice = basePrice + 1;
       }

       if (hasChocolate) {
           basePrice = basePrice + 2;
       }

       return quantity * basePrice;
   }

        private String createOrderSummary(int fullPrice, boolean addWhippedCream, boolean addChocolate, String nameOfOrdery) {
        String priceMessage= "Zamówienie należy do: " + nameOfOrdery;
        priceMessage += "\nDodano śmietankę?: " + addWhippedCream;
        priceMessage += "\nCzekoladkę do tego?: " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + fullPrice;
        priceMessage += "\nThank You!";
        return priceMessage;
    }

    public void increment(View view) {


        if (quantity >= 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
   }


    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }

        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
/**
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    } */
}


