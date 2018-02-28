package rodrigodavy.com.github.lcoolougasolina

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.InvocationTargetException


class MainActivity : AppCompatActivity() {

    private val GAS = 0
    private val ETHANOL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun calculate(view: View) {
        val textView = findViewById<TextView>(R.id.result_message)
        val editGas = findViewById<EditText>(R.id.gas_edit_text)
        val editEthanol = findViewById<EditText>(R.id.ethanol_edit_text)

        var stringGas = editGas.text.toString()
        var stringEthanol = editEthanol.text.toString()

        if(stringGas.contains(",")) stringGas = stringGas.replace(",",".")
        if(stringEthanol.contains(",")) stringEthanol = stringEthanol.replace(",",".")

        try {
            val priceGas = stringGas.toFloat()
            val priceEthanol = stringEthanol.toFloat()

            when (gasOrEthanol(priceGas, priceEthanol)) {
                GAS -> {
                    textView.setText("Gasolina tem o melhor custo-benefício")
                    textView.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
                }
                ETHANOL ->{
                    textView.setText("Etanol tem o melhor custo-benefício")
                    textView.setTextColor(ContextCompat.getColor(this,R.color.colorAccent))
                }
            }
        }catch (exception: NumberFormatException) {
            textView.setText("Erro! Cheque se os valores preenchidos são válidos.")
            textView.setTextColor(Color.RED)
        }

        //Hides the keyboard
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun gasOrEthanol(priceGas: Float, priceEthanol: Float): Int {
        val maxPriceEthanol = priceGas * 0.7

        return if(priceEthanol >= maxPriceEthanol) {
            GAS
        }else{
            ETHANOL
        }
    }
}
