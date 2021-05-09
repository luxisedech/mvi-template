package fragment.mvi.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun blankIntentKt(
        intentClass: String,
        packageName: String
): String {

    return """package ${escapeKotlinIdentifier(packageName)}

sealed class $intentClass {
    object ClickedOnButton: $intentClass()
}
"""
}