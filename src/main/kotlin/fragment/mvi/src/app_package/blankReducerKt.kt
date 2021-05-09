package fragment.mvi.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun blankReducerKt(
        basePackage:String,
        partialStateClass: String,
        stateClass: String,
        reducerClass: String,
        packageName: String
): String {

    return """package ${escapeKotlinIdentifier(packageName)}

import $basePackage.common.viewmodels.IReducer

class $reducerClass : IReducer<$stateClass, $partialStateClass> {
    override fun reduce(state: $stateClass, partialState: $partialStateClass): $stateClass {
        return when (partialState) {
            is $partialStateClass.ButtonDisabled -> state.copy(
                isButtonActive = false
            )
        }
    }
}
"""
}