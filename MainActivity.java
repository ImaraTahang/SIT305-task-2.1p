package com.example.unitconverter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Spinner conversionTypeSpinner, sourceUnitSpinner, destUnitSpinner;
    private EditText inputValueEditText;
    private TextView convertedValueTextView;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initializing views
        conversionTypeSpinner = findViewById(R.id.conversion_type_spinner);
        sourceUnitSpinner = findViewById(R.id.spinner2);
        destUnitSpinner = findViewById(R.id.spinner3);
        inputValueEditText = findViewById(R.id.editTextText);
        convertedValueTextView = findViewById(R.id.textView7);
        convertButton = findViewById(R.id.button2);

        //ArrayAdapter for conversion types spinner
        ArrayAdapter<CharSequence> conversionTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_types, android.R.layout.simple_spinner_item);
        conversionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionTypeSpinner.setAdapter(conversionTypeAdapter);

        //conversion type spinner listener
        conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update unit spinners based on selected conversion type
                String selectedConversionType = parent.getItemAtPosition(position).toString();
                switch (selectedConversionType) {
                    case "Length":
                        sourceUnitSpinner.setAdapter(ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.length_units, android.R.layout.simple_spinner_item));
                        destUnitSpinner.setAdapter(ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.length_units, android.R.layout.simple_spinner_item));
                        break;
                    case "Weight":
                        sourceUnitSpinner.setAdapter(ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.weight_units, android.R.layout.simple_spinner_item));
                        destUnitSpinner.setAdapter(ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.weight_units, android.R.layout.simple_spinner_item));
                        break;
                    case "Temperature":
                        sourceUnitSpinner.setAdapter(ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.temperature_units, android.R.layout.simple_spinner_item));
                        destUnitSpinner.setAdapter(ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.temperature_units, android.R.layout.simple_spinner_item));
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //does nothing
            }
        });

        //convertButton listener
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
                String destUnit = destUnitSpinner.getSelectedItem().toString();
                double inputValue = Double.parseDouble(inputValueEditText.getText().toString());

                // Convert units
                double convertedValue = convertUnits(sourceUnit, destUnit, inputValue);

                // Display the converted value
                convertedValueTextView.setText(String.valueOf(convertedValue));
            }
        });
    }
    private double convertUnits(String sourceUnit, String destUnit, double input) {
        //Length Conversions
        if (sourceUnit.equals("Inches")) {
            if (destUnit.equals("Centimeters")) {
                return input * 2.54;
            }
        } else if (sourceUnit.equals("Feet")) {
            if (destUnit.equals("Centimeters")) {
                return input * 30.48;
            }
        } else if (sourceUnit.equals("Yards")) {
            if (destUnit.equals("Centimeters")) {
                return input * 91.44;
            }
        } else if (sourceUnit.equals("Miles")) {
            if (destUnit.equals("Kilometers")) {
                return input * 1.60934;
            }
        }

        //Weight Conversions
        else if (sourceUnit.equals("Pounds")) {
            if (destUnit.equals("Kilograms")) {
                return input * 0.453592;
            }
        } else if (sourceUnit.equals("Ounces")) {
            if (destUnit.equals("Grams")) {
                return input * 28.3495;
            }
        } else if (sourceUnit.equals("Tons")) {
            if (destUnit.equals("Kilograms")) {
                return input * 907.185;
            }
        }

        //Temperature Conversions
        else if (sourceUnit.equals("Celsius")) {
            if (destUnit.equals("Fahrenheit")) {
                return (input * 1.8) + 32;
            } else if (destUnit.equals("Kelvin")) {
                return input + 273.15;
            }
        } else if (sourceUnit.equals("Fahrenheit")) {
            if (destUnit.equals("Celsius")) {
                return (input - 32) / 1.8;
            } else if (destUnit.equals("Kelvin")) {
                return (input - 32) / 1.8 + 273.15;
            }
        } else if (sourceUnit.equals("Kelvin")) {
            if (destUnit.equals("Celsius")) {
                return input - 273.15;
            } else if (destUnit.equals("Fahrenheit")) {
                return (input - 273.15) * 1.8 + 32;
            }
        }

        return Double.NaN;
    }
}