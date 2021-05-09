package fragment.mvi

import com.android.ide.common.util.toPathString
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import fragment.mvi.res.layout.blankFragmentXml
import fragment.mvi.src.app_package.*
import io.github.luxisedech.mvi.listeners.MyProjectManagerListener.Companion.projectInstance
import org.jetbrains.kotlin.idea.KotlinLanguage

fun RecipeExecutor.mviSetup(
        moduleData: ModuleTemplateData,
        fragmentName: String,
        fragmentClass: String,
        layoutName: String,
        viewModelClass: String,
        stateClass: String,
        intentClass: String,
        partialStateClass: String,
        reducerClass: String
) {

    fun getBasePackage(moduleBasePackage : String) = moduleBasePackage.split('.').toMutableList().apply {
        removeAt(lastIndex)
    }.toString().replace(" ","",true).replace("]","",true).replace("[","",true).replace(',','.')

    fun formatToPath(path:String) =
            path.replace('\\','/')


    val fullPathSrc = formatToPath(moduleData.srcDir.toPathString().toString())
    val fullPathRes = formatToPath(moduleData.resDir.toPathString().toString())



    val (projectData, srcOut, resOut) = moduleData
    val project = projectInstance ?: return
    //addAllKotlinDependencies(moduleData)
    val currentPackage = moduleData.packageName
    val moduleBasePackage = projectData.applicationPackage
    val basePackage = moduleBasePackage?.let { getBasePackage(it) }?: "error.package"

    val virtualFiles = ProjectRootManager.getInstance(project).contentSourceRoots
    val virtSrc = virtualFiles.filter { it.path.contains("src") }.first { fullPathSrc.contains(it.path) }
    val virtRes = virtualFiles.filter { it.path.contains("res") }.first { fullPathRes.contains(it.path) }

    val directorySrc = PsiManager.getInstance(project).findDirectory(virtSrc)!!
    val directoryRes = PsiManager.getInstance(project).findDirectory(virtRes)!!

    //+++++++ Build layout  +++++++
    blankFragmentXml(fragmentClass,viewModelClass ,currentPackage).save(directoryRes,"layout","${layoutName}.xml")

    //+++++++ Build fragment  +++++++
    val blankFragment =
            blankFragmentKt(basePackage,moduleBasePackage,fragmentName ,fragmentClass, stateClass,intentClass,layoutName, currentPackage, viewModelClass,
            virtualFiles.map { it.path }.filter { it.contains("src") }.toString() + "fullPathSrc : $fullPathSrc fullPathRes : $fullPathRes"
            + virtSrc + virtRes )

    //save(blankFragment, moduleData.srcDir.resolve("${fragmentClass}.kt"))

    blankFragment.save(directorySrc,currentPackage,"${fragmentClass}.kt")

    //+++++++ Build viewModel  +++++++
    val blankViewModel = blankViewModelKt(basePackage,viewModelClass,stateClass,intentClass,partialStateClass,reducerClass,currentPackage)
    blankViewModel.save(directorySrc,currentPackage,"${viewModelClass}.kt")

    //+++++++ Build state  +++++++
    val blankState = blankStateKt(stateClass,currentPackage)
    blankState.save(directorySrc,currentPackage,"${stateClass}.kt")

    //+++++++ Build intent  +++++++
    val blankIntent = blankIntentKt(intentClass,currentPackage)
    blankIntent.save(directorySrc,currentPackage,"${intentClass}.kt")

    //+++++++ Build partialState  +++++++
    val blankPartialState = blankPartialStateKt(partialStateClass,currentPackage)
    blankPartialState.save(directorySrc,currentPackage,"${partialStateClass}.kt")

    //+++++++ Build reducer  +++++++
    val blankReducer = blankReducerKt(basePackage,partialStateClass,stateClass,reducerClass,currentPackage)
    blankReducer.save(directorySrc,currentPackage,"${reducerClass}.kt")

    open(moduleData.srcDir.resolve("${fragmentClass}.kt"))
    open(moduleData.resDir.resolve("layout/${layoutName}.xml"))
}


fun String.save(srcDir: PsiDirectory, subDirPath: String, fileName: String) {
    try {
        val destDir = subDirPath.split(".").toDir(srcDir)
        val psiFile = PsiFileFactory
                .getInstance(srcDir.project)
                .createFileFromText(fileName, KotlinLanguage.INSTANCE, this)
        destDir.add(psiFile)
    }catch (exc: Exception) {
        exc.printStackTrace()
    }
}

fun List<String>.toDir(srcDir: PsiDirectory): PsiDirectory {
    var result = srcDir
    forEach {
        result = result.findSubdirectory(it) ?: result.createSubdirectory(it)
    }
    return result
}
