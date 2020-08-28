package app.yota

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import app.yota.di.AppComponent
import app.yota.di.AppModule
import app.yota.di.DaggerAppComponent

class YotaInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        appComponent = initDagger(context!!.applicationContext as YotaApplication)
        return false
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null
}

private fun initDagger(app: YotaApplication): AppComponent =
    DaggerAppComponent.builder()
        .appModule(AppModule(app))
        .build()

private lateinit var appComponent: AppComponent

fun getAppComponent(): AppComponent {
    return appComponent
}
