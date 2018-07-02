
package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 2;
    int pricePerCup = 5;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        pricePerCup = 5;
        displayQuantity(quantity);
        String whippedCreamToppings;
        String chocolateToppings;
        String message;


        if (checkWhippedCreamCheckbox()) {
            whippedCreamToppings = getString(R.string.yes);
            pricePerCup += 1;
        } else {
            whippedCreamToppings = getString(R.string.no);
        }

        if (checkChocolateCheckbox()) {
            chocolateToppings = getString(R.string.yes);
            pricePerCup += 2;
        } else {
            chocolateToppings = getString(R.string.no);
        }

        int price = calculatePrice();
        message = createOrderSummary(price, quantity, whippedCreamToppings, chocolateToppings);
        displayMessage(message);

        composeEmail(getString(R.string.email_subject)+" " + getName(), message);
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice() {
        return quantity * pricePerCup;
    }

    /**
     * Creates order summary
     *
     * @param price                total price of the coffee ordered
     * @param quantity             amount of coffee ordered
     * @param whippedCreamToppings type of toppings added
     * @param chocolateToppings    type of toppings added
     */


    private String createOrderSummary(int price, int quantity, String whippedCreamToppings, String chocolateToppings) {
        String message = getString(R.string.name_message)+" " + getName();
        message += "\n"+getString(R.string.add_whipped_cream)+" " + whippedCreamToppings;
        message += "\n"+getString(R.string.add_chocolate)+" " + chocolateToppings;
        message += "\n"+getString(R.string.quantity_ordered1)+" " + quantity+" " + getString(R.string.quantity_ordered2);
        message += "\n"+getString(R.string.total_price) + price;
        message += "\n"+getString(R.string.thank_you);
        return message;
    }


    /**
     * This method is called when the + (plus) button is clicked.
     */
    public void increment(View view) {


        if (quantity == 100) {
            Toast.makeText(getApplicationContext(), getString(R.string.increment_toast_msg), Toast.LENGTH_SHORT).show();
            return;

        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - (minus) button is clicked.
     */
    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(getApplicationContext(), getString(R.string.decrement_toast_msg), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(getString(R.string.placeholder) + number);
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayMessage(String text) {
        TextView orderSummaryView = findViewById(R.id.order_summary_view);
        orderSummaryView.setText(getString(R.string.placeholder) + text);
    }

    /**
     * This method gets the text from the EditText view and store it to a String value
     */

    private String getName() {
        EditText nameValue = findViewById(R.id.name);
        return nameValue.getText().toString();
    }


    /**
     * This method checks if whipped cream is added and returns a boolean value.
     */

    public boolean checkWhippedCreamCheckbox() {
        // Is the view now checked?
        CheckBox whippedCream = findViewById(R.id.whipped_cream_checkbox);

        return whippedCream.isChecked();
    }

    /**
     *
     * This method checks if chocolate is added and returns a boolean value.
     *
     */

    public boolean checkChocolateCheckbox() {
        // Is the view now checked?
        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);

        return chocolate.isChecked();
   }

    /**
     * This method compose an email
     */
    public void composeEmail(String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
