package com.github.volkov.znavi.services

import com.intellij.openapi.project.Project
import com.github.volkov.znavi.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
