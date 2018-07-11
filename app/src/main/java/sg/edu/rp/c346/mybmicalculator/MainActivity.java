package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalc, btnReset;
    TextView tvDate, tvBMI, tvType;
    String dateTime = "";
    String message = "";
    String minute = "";
    float BMI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvType = findViewById(R.id.textViewType);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String convertWeight = etWeight.getText().toString();
                String convertHeight = etHeight.getText().toString();
                float weight = Float.parseFloat(convertWeight);
                float height = Float.parseFloat(convertHeight);
                BMI = weight / (height * height);
                if (BMI < 18.5){
                    message = "You are underweight";
                } else if (BMI > 18.5 && BMI < 24.9){
                    message = "Your BMI is normal";
                } else if (BMI > 25 && BMI < 29.9){
                    message = "You are overweight";
                } else {
                    message = "You are obese";
                }
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                if (now.get(Calendar.MINUTE) < 10){
                    minute = "0"+now.get(Calendar.MINUTE);
                } else {
                    minute = ""+now.get(Calendar.MINUTE);
                }
                dateTime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH) + 1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        minute;
                tvDate.setText(dateTime);
                tvBMI.setText(BMI + "");
                tvType.setText(message);
                etHeight.setText(null);
                etWeight.setText(null);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setText(null);
                tvBMI.setText(null);
                tvType.setText(null);
                etWeight.setText(null);
                etHeight.setText(null);
                message = null;
                dateTime = null;
                BMI = 0;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("theDateTime", dateTime);
        prefEdit.putFloat("theBMI", BMI);
        prefEdit.putString("theType", message);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String duration = prefs.getString("theDateTime", "");
        Float BMI = prefs.getFloat("theBMI",0);
        String type = prefs.getString("theType","");
        tvDate.setText(duration);
        tvBMI.setText(Float.toString(BMI));
        tvType.setText(type);
    }
}
