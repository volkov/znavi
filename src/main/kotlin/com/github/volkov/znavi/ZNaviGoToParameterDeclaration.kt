package com.github.volkov.znavi

import com.intellij.codeInsight.TargetElementUtil
import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction
import com.intellij.ide.util.EditSourceUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiCallExpression
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.psi.psiUtil.startOffset

/**
 * User: serg-v
 * Date: 1/9/21
 * Time: 3:54 PM
 */
class ZNaviGoToParameterDeclaration : GotoDeclarationAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val file = e.getData(CommonDataKeys.PSI_FILE)
        if (file != null && editor != null) {

            val offset = editor.caretModel.offset
            val underCursor = file.findElementAt(offset)

            val anyCall = PsiTreeUtil.getParentOfType(underCursor, PsiCallExpression::class.java)
            val anyOffset = anyCall!!.startOffset
            println("### any call $anyOffset")
            println(anyCall.javaClass)
            println(anyCall)

            val methodTarget = anyCall.resolveMethod()
            println("### method target")
            println(methodTarget!!.javaClass)

            //TargetElementUtil.getInstance().findTargetElement(editor, TargetElementUtil.getInstance().allAccepted, anyOffset)

            val navigationElement = methodTarget.navigationElement
            val trueNavigationElement = TargetElementUtil.getInstance().getGotoDeclarationTarget(methodTarget, navigationElement)

            val navigatable =
                    if (trueNavigationElement is Navigatable)
                        trueNavigationElement
                    else EditSourceUtil.getDescriptor(trueNavigationElement)

            if (navigatable != null && navigatable.canNavigate()) {
                navigatable.navigate(true)
            }

            println(methodTarget)
            println("ok")
        }
    }
}