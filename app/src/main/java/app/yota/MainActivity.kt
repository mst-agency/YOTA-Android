package app.yota

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.yota.view.appbar.MoneyAppBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appbar = findViewById<MoneyAppBarView>(R.id.appbar)

        appbar.setBalance(999.9f)
        appbar.setCardNumber(8888)
    }
}
