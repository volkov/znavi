package com.github.volkov.znavi

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory

/**
 * User: serg-v
 * Date: 12/19/20
 * Time: 9:05 AM
 */
class PopupAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        JBPopupFactory.getInstance().createPopupChooserBuilder(listOf("a", "b", "c")).createPopup().showInFocusCenter()
    }
}