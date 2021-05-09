package fragment.mvi.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun blankPartialStateKt(
        partialStateClass: String,
        packageName: String
): String {

    return """package ${escapeKotlinIdentifier(packageName)}

sealed class $partialStateClass {
    object ButtonDisabled : $partialStateClass()
}
"""
}