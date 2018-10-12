package mchehab.com.distancealgorithm;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import mchehab.com.distancealgorithm.distanceAlgorithm.DistanceAlgorithm;
import mchehab.com.distancealgorithm.distanceAlgorithm.EuclideanDistance;
import mchehab.com.distancealgorithm.distanceAlgorithm.ManhattenDistance;
import mchehab.com.distancealgorithm.distanceAlgorithm.MinkowskiDistance;

public class MainActivity extends AppCompatActivity {

    private final String MINKOWSKI = "Minkowski";
    private DistanceAlgorithm[] distanceAlgorithms =
            {new EuclideanDistance(), new ManhattenDistance(), new MinkowskiDistance(0)};

    private Spinner spinnerAlgorithm;
    private EditText editTextMinkowski;
    private EditText editTextX1;
    private EditText editTextY1;
    private EditText editTextX2;
    private EditText editTextY2;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        setSpinnerAlgorithmListener();
        setButtonCalculateListener();
    }

    private void setupUI(){
        spinnerAlgorithm = findViewById(R.id.spinnerAlgorithm);
        editTextMinkowski = findViewById(R.id.editTextMinkowski);
        editTextX1 = findViewById(R.id.editTextX1);
        editTextY1 = findViewById(R.id.editTextY1);
        editTextX2 = findViewById(R.id.editTextX2);
        editTextY2 = findViewById(R.id.editTextY2);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);
    }

    private void setSpinnerAlgorithmListener(){
        spinnerAlgorithm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String algorithm = (String)spinnerAlgorithm.getItemAtPosition(position);
                if (algorithm.equalsIgnoreCase(MINKOWSKI))
                    editTextMinkowski.setVisibility(View.VISIBLE);
                else
                    editTextMinkowski.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setButtonCalculateListener(){
        buttonCalculate.setOnClickListener(e -> {
            if (!canCalculate()){
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Please fill all of the fields")
                        .setPositiveButton("Ok", null)
                        .create()
                        .show();
                return;
            }
            String algorithmName = (String) spinnerAlgorithm.getSelectedItem();
            int spinnerIndex = spinnerAlgorithm.getSelectedItemPosition();
            int x1 = Integer.parseInt(getText(editTextX1));
            int y1 = Integer.parseInt(getText(editTextY1));
            int x2 = Integer.parseInt(getText(editTextX2));
            int y2 = Integer.parseInt(getText(editTextY2));

            if(spinnerIndex == distanceAlgorithms.length - 1){
                int p = Integer.parseInt(getText(editTextMinkowski));
                ((MinkowskiDistance)distanceAlgorithms[spinnerIndex]).setP(p);
            }
            double distance = distanceAlgorithms[spinnerIndex].calculateDistance(x1, y1, x2, y2);
            textViewResult.setText("Distance using " + algorithmName + " = " + distance);
        });
    }

    private int getSpinnerPosition(Spinner spinner){
        return spinner.getSelectedItemPosition();
    }

    private String getText(EditText editText){
        return editText.getText().toString().trim();
    }

    private boolean isEmpty(EditText editText){
        return getText(editText).length() == 0;
    }

    private boolean canCalculate(){
        if (isEmpty(editTextX1))
            return false;
        if (isEmpty(editTextY1))
            return false;
        if (isEmpty(editTextX2))
            return false;
        if (isEmpty(editTextY2))
            return false;
        if (getSpinnerPosition(spinnerAlgorithm) == distanceAlgorithms.length - 1)
            if (isEmpty(editTextMinkowski))
                return false;
        return true;
    }
}