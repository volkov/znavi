package com.github.volkov.znavi

import com.intellij.find.actions.ShowUsagesAction
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.psi.util.PsiTreeUtil

import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.popup.JBPopupFactory

import com.intellij.psi.*
import com.intellij.ui.popup.PopupPositionManager
import org.jetbrains.kotlin.psi.KtFunction

/**
 * User: serg-v
 * Date: 1/2/21
 * Time: 7:58 PM
 */
class ZNaviLeftAction : AnAction() {
    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val editor: Editor? = anActionEvent.getData(CommonDataKeys.EDITOR)
        val psiFile: PsiFile? = anActionEvent.getData(CommonDataKeys.PSI_FILE)
        if (editor == null || psiFile == null) {
            return
        }
        val offset = editor.caretModel.offset

        val infoBuilder = StringBuilder()
        val element = psiFile.findElementAt(offset)
        infoBuilder.append("Element at caret: ").append(element).append("\n")
        if (element != null) {
            val containingMethod: PsiMethod? = PsiTreeUtil.getParentOfType(element, PsiMethod::class.java)
            infoBuilder
                    .append("Containing method: ")
                    .append(containingMethod?.name ?: "none")
                    .append("\n")
            if (containingMethod != null) {
                startFindUsages(containingMethod, anActionEvent, editor)


                val containingClass: PsiClass? = containingMethod.containingClass
                infoBuilder
                        .append("Containing class: ")
                        .append(if (containingClass != null) containingClass.name else "none")
                        .append("\n")
            }
            val kotlinFunction = PsiTreeUtil.getParentOfType(element, KtFunction::class.java)
            if (kotlinFunction != null) {
                startFindUsages(kotlinFunction, anActionEvent, editor)
            }
        }
        //Messages.showMessageDialog(anActionEvent.project, infoBuilder.toString(), "PSI Info", null)
    }

    private fun startFindUsages(containingMethod: PsiElement, anActionEvent: AnActionEvent, editor: Editor?) {
        ShowUsagesAction.startFindUsages(
                containingMethod,
                JBPopupFactory.getInstance().guessBestPopupLocation(anActionEvent.dataContext),
                editor
        )
    }
}