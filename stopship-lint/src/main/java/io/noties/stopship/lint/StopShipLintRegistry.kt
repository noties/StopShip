package io.noties.stopship.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

@Suppress("UnstableApiUsage")
class StopShipLintRegistry : IssueRegistry() {

  override val issues: List<Issue>
    get() = listOf(StopShipLintIssueJava)

  override val api: Int
    get() = CURRENT_API
}