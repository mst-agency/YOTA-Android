package app.yota

import android.os.Bundle
import app.yota.activity.BaseActivity
import app.yota.fragment.servicemanagement.ServiceManagementFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, ServiceManagementFragment.newInstance()).commit()
        }
    }
}
