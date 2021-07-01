@file:Suppress("UnstableApiUsage")

package io.noties.stopship.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement

val StopShipLintIssueJava = Issue.create(
  "stop-ship-lint",
  "Checks if StopShip functionality is used",
  "",
  Category.CORRECTNESS,
  10,
  Severity.FATAL,
  Implementation(StopShipLintIssueJavaImpl::class.java, Scope.JAVA_FILE_SCOPE)
)

class StopShipLintIssueJavaImpl : Detector(), Detector.UastScanner {

  companion object {
    const val JAVA = "io.noties.stopship.StopShip"
    const val KOTLIN = "io.noties.stopship.StopShipKt"
  }

  override fun getApplicableUastTypes(): List<Class<out UElement>> {
    return listOf(UCallExpression::class.java)
  }

  override fun createUastHandler(context: JavaContext): UElementHandler {

    fun isStopShipClassMember(method: PsiMethod): Boolean = context.evaluator.let {
      it.isMemberInClass(method, JAVA) || it.isMemberInClass(method, KOTLIN)
    }

    return object : UElementHandler() {
      override fun visitCallExpression(node: UCallExpression) {
        val psiMethod = node.resolve() ?: return
        if (isStopShipClassMember(psiMethod) && !psiMethod.name.startsWith("_")) {
          context.report(
            StopShipLintIssueJava,
            node,
            context.getLocation(node),
            "StopShip functionality is used"
          )
        }
      }
    }
  }
}