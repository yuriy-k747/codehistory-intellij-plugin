package com.github.yuriyk747.codehistoryintellijplugin.services

import com.intellij.openapi.project.Project
import com.github.yuriyk747.codehistoryintellijplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
