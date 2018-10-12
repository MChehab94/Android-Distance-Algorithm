package mchehab.com.kotlin

import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import mchehab.com.kotlin.distanceAlgorithm.EuclideanDistance
import mchehab.com.kotlin.distanceAlgorithm.ManhattenDistance
import mchehab.com.kotlin.distanceAlgorithm.MinkowskiDistance

class MainActivity : AppCompatActivity() {

    private val MINKOWSKI = "Minkowski"
    private val distanceAlgorithms = arrayOf(EuclideanDistance(), ManhattenDistance(), MinkowskiDistance(0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSpinnerAlgorithmListener()
        setButtonCalculateListener()
    }

    private fun setSpinnerAlgorithmListener() {
        spinnerAlgorithm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val algorithm = spinnerAlgorithm.getItemAtPosition(position) as String
                if (algorithm.equals(MINKOWSKI, ignoreCase = true))
                    editTextMinkowski.visibility = View.VISIBLE
                else
                    editTextMinkowski.visibility = View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setButtonCalculateListener() {
        buttonCalculate.setOnClickListener { e ->
            if (!canCalculate()) {
                AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Please fill all of the fields")
                        .setPositiveButton("Ok", null)
                        .create()
                        .show()
                return@setOnClickListener
            }
            val algorithmName = spinnerAlgorithm.selectedItem as String
            val spinnerIndex = spinnerAlgorithm.selectedItemPosition
            val x1 = Integer.parseInt(getText(editTextX1))
            val y1 = Integer.parseInt(getText(editTextY1))
            val x2 = Integer.parseInt(getText(editTextX2))
            val y2 = Integer.parseInt(getText(editTextY2))

            if (spinnerIndex == distanceAlgorithms.size - 1) {
                val p = Integer.parseInt(getText(editTextMinkowski))
                (distanceAlgorithms[spinnerIndex] as MinkowskiDistance).p = p
            }
            val distance = distanceAlgorithms[spinnerIndex].calculateDistance(x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble())
            textViewResult.text = "Distance using $algorithmName = $distance"
        }
    }

    private fun getSpinnerPosition(spinner: Spinner): Int {
        return spinner.selectedItemPosition
    }

    private fun getText(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    private fun isEmpty(editText: EditText): Boolean {
        return getText(editText).isEmpty()
    }

    private fun canCalculate(): Boolean {
        if (isEmpty(editTextX1))
            return false
        if (isEmpty(editTextY1))
            return false
        if (isEmpty(editTextX2))
            return false
        if (isEmpty(editTextY2))
            return false
        if (getSpinnerPosition(spinnerAlgorithm) == distanceAlgorithms.size - 1)
            if (isEmpty(editTextMinkowski))
                return false
        return true
    }
}
