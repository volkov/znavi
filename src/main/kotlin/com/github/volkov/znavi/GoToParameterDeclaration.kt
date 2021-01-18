package com.github.volkov.znavi

import com.intellij.codeInsight.TargetElementUtil
import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction
import com.intellij.ide.util.EditSourceUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.pom.Navigatable

/**
 * User: serg-v
 * Date: 1/9/21
 * Time: 3:54 PM
 */
class GoToParameterDeclaration : GotoDeclarationAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val file = e.getData(CommonDataKeys.PSI_FILE)
        if (file != null && editor != null) {

            val targetElement = TargetElementUtil.getInstance()
                    .findTargetElement(editor, TargetElementUtil.REFERENCED_ELEMENT_ACCEPTED, editor.caretModel.offset)
            val element = targetElement!!.navigationElement
            val navigatable = if (element is Navigatable) element else EditSourceUtil.getDescriptor(element)
            if (navigatable != null && navigatable.canNavigate()) {
                navigatable.navigate(true)
            }

            println(targetElement)
            println("ok")
        }
    }
}