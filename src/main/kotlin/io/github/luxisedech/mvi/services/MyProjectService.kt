package io.github.luxisedech.mvi.services

import com.intellij.openapi.project.Project
import io.github.luxisedech.mvi.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
