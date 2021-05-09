package fragment.mvi.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun blankViewModelKt(
        basePackage: String,
        viewModelClass: String,
        stateClass: String,
        intentClass: String,
        partialStateClass: String,
        reducerClass: String,
        packageName: String
): String {

    return """package ${escapeKotlinIdentifier(packageName)}

import $basePackage.common.viewmodels.MVIViewModel
import javax.inject.Inject

class $viewModelClass @Inject constructor() :
    MVIViewModel<$stateClass, $intentClass, $partialStateClass>
        ($reducerClass()) {

    override fun createInitialState() = $stateClass()

    override suspend fun intentToAction(intent: $intentClass) =
        when (intent) {
            is $intentClass.ClickedOnButton -> disableButton()
        }

    private suspend fun disableButton() = $partialStateClass.ButtonDisabled.reduceIt()
}

"""
}