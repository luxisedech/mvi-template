package fragment.mvi.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun blankStateKt(
        stateClass: String,
        packageName: String
): String {

    return """package ${escapeKotlinIdentifier(packageName)}

data class $stateClass(
    val isButtonActive : Boolean = true
)
"""
}