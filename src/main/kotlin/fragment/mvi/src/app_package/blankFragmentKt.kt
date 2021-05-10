package fragment.mvi.src.app_package

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun blankFragmentKt(
        basePackage: String,
        applicationPackage: String?,
        fragmentName: String,
        fragmentClass: String,
        stateClass: String,
        intentClass: String,
        layoutName: String,
        packageName: String,
        viewModelName: String,
        aled:String
): String {

    return """package ${escapeKotlinIdentifier(packageName)}
import $basePackage.common.mvi.MVIFragment

${renderIf(applicationPackage != null) { "import ${applicationPackage}.R" }}
${renderIf(applicationPackage != null) { "import ${applicationPackage}.BR" }}
${renderIf(applicationPackage != null) { "import ${applicationPackage}.databinding.${fragmentClass}Binding" }}

class $fragmentClass :
    MVIFragment<$stateClass, $intentClass, ${fragmentClass}Binding, $viewModelName>(
        R.layout.$layoutName, BR.viewmodel,${viewModelName}::class) {

    override fun setupViews() {

    }
    /*
        $aled
    
    */

    override fun renderState(viewState: $stateClass) {


    }
}
"""
}