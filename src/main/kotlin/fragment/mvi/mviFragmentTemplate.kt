package fragment.mvi

import com.android.tools.idea.wizard.template.*
import java.io.File

val mviFragmentTemplate
    get() = template {
        revision = 2
        name = "Fragment (with MVI Bundle)"
        description = "Creates a Fragment with its State, Intent, ViewModel, Reducer and PartialState"
        minApi = 20
        minBuildApi = 20
        category = Category.Other
        formFactor = FormFactor.Mobile
        screens = listOf(WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry)

        val fragmentClass = stringParameter {
            name = "Fragment Name"
            default = "BlankFragment"
            help = "The name of the fragment class to create"
            constraints = listOf(Constraint.CLASS, Constraint.NONEMPTY, Constraint.UNIQUE)
        }

        val layoutName = stringParameter {
            name = "Fragment Layout Name"
            default = "blank_fragment"
            help = "The name of the layout to create"
            constraints = listOf(Constraint.LAYOUT, Constraint.NONEMPTY, Constraint.UNIQUE)
            suggest = { "${classToResource(fragmentClass.value)}_fragment" }
        }

        val viewModelName = stringParameter {
            name = "ViewModel Name"
            default = "BlankViewModel"
            help = "The name of the ViewModel class to create"
            constraints = listOf(Constraint.CLASS, Constraint.NONEMPTY, Constraint.UNIQUE)
            suggest = { "${underscoreToCamelCase(classToResource(fragmentClass.value))}ViewModel" }
        }

        val intentName = stringParameter {
            name = "Intent Name"
            default = "BlankIntent"
            help = "The name of the Intent class to create"
            constraints = listOf(Constraint.CLASS, Constraint.NONEMPTY, Constraint.UNIQUE)
            suggest = { "${underscoreToCamelCase(classToResource(fragmentClass.value))}Intent" }
        }
        val stateName = stringParameter {
            name = "State Name"
            default = "BlankState"
            help = "The name of the State class to create"
            constraints = listOf(Constraint.CLASS, Constraint.NONEMPTY, Constraint.UNIQUE)
            suggest = { "${underscoreToCamelCase(classToResource(fragmentClass.value))}State" }
        }
        val partialStateName = stringParameter {
            name = "PartialState Name"
            default = "BlankPartialState"
            help = "The name of the PartialState class to create"
            constraints = listOf(Constraint.CLASS, Constraint.NONEMPTY, Constraint.UNIQUE)
            suggest = { "${underscoreToCamelCase(classToResource(fragmentClass.value))}PartialState" }
        }
        val reducerName =  stringParameter {
            name = "Reducer Name"
            default = "BlankReducer"
            help = "The name of the Reducer class to create"
            constraints = listOf(Constraint.CLASS, Constraint.NONEMPTY, Constraint.UNIQUE)
            suggest = { "${underscoreToCamelCase(classToResource(fragmentClass.value))}Reducer" }
        }

        widgets(
                TextFieldWidget(fragmentClass),
                TextFieldWidget(layoutName),
                TextFieldWidget(viewModelName),
                TextFieldWidget(stateName),
                TextFieldWidget(intentName),
                TextFieldWidget(partialStateName),
                TextFieldWidget(reducerName)
        )

        thumb { File("template_blank_fragment.png") }

        recipe = { data: TemplateData ->
            mviSetup(
                    data as ModuleTemplateData,
                    underscoreToCamelCase(classToResource(fragmentClass.value)),
                    fragmentClass.value,
                    layoutName.value,
                    viewModelName.value,
                    stateName.value,
                    intentName.value,
                    partialStateName.value,
                    reducerName.value
                    )
        }
    }