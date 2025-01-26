import android.content.Context
import androidx.lifecycle.ViewModel


fun saveDarkThemePreference(context: Context, isDark: Boolean) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("darkTheme", isDark)
    editor.apply()
}

fun getDarkThemePreference(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("darkTheme", false)
}

class SettingsViewModel(private val context: Context) : ViewModel() {

    fun saveDarkThemePreference(isDark: Boolean) {
        saveDarkThemePreference(context, isDark)
    }

    fun getDarkThemePreference(): Boolean {
        return getDarkThemePreference(context)
    }
}