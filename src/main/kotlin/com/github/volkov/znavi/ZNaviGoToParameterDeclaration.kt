package com.github.volkov.znavi

import com.intellij.codeInsight.TargetElementUtil
import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction
import com.intellij.ide.util.EditSourceUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.pom.Navigatable
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil.getChildrenOfType
import com.intellij.psi.util.PsiTreeUtil.getParentOfType
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
            val underCursor = file.findElementAt(offset)!!
            println("### under cursor: $underCursor")

            val anyCall = getParentOfType(underCursor, PsiCallExpression::class.java) ?: return //should support other languages
            val anyOffset = anyCall.startOffset
            println("### any call $anyOffset")
            println(anyCall)

            val methodTarget = anyCall.resolveMethod()!!
            println("### method target")
            println(methodTarget)

            val parameterIndex = findParameterIndex(underCursor)
            println("### arg index " + parameterIndex)

            //TargetElementUtil.getInstance().findTargetElement(editor, TargetElementUtil.getInstance().allAccepted, anyOffset)

            val navigationElement = methodTarget.navigationElement
            val trueNavigationElement = TargetElementUtil.getInstance().getGotoDeclarationTarget(methodTarget, navigationElement)
            println("### true navigation elemnent")
            println(trueNavigationElement)
            val parameterElement = findParameter(trueNavigationElement, parameterIndex)
            println("### parameter element")
            println(parameterElement)

            val navigatable =
                    if (parameterElement is Navigatable)
                        parameterElement
                    else EditSourceUtil.getDescriptor(parameterElement)

            if (navigatable != null && navigatable.canNavigate()) {
                navigatable.navigate(true)
            }

            println("ok")
        }
    }

    private fun findParameter(element: PsiElement, index: Int): PsiElement {
        var paramInex = -1
        var lastParameter : PsiElement? = null
        println("### children")
        for (child in getChildrenOfType(element, PsiParameterList::class.java)[0].children) {
            println(child)
            if (child is PsiParameter) {
                lastParameter = child
                paramInex++
                if (paramInex == index) {
                    return child
                }
            }
        }
        return lastParameter!!
    }

    private fun findParameterIndex(element: PsiElement): Int {
        var reference: PsiElement? = getParentOfType(element, PsiReferenceExpression::class.java)
        var res = 0
        reference = reference?.prevSibling
        while (reference != null) {
            if (reference is PsiExpression) {
                res++
            }
            reference = reference.prevSibling
            if (res > 100) {
                println("### oops")
                return res
            }
        }
        return res
    }
}