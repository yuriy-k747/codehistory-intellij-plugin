package com.github.yuriyk747.codehistory.intellij;

import com.github.yuriyk747.codehistory.intellij.actions.CodeHistoryToolWindow;
import com.github.yuriyk747.codehistory.intellij.common.CodeHistoryUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

public class CodeHistoryToolWindowFactory implements ToolWindowFactory {
  public static final String TOOL_WINDOW_ID = "CodeHistory";

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    CodeHistoryToolWindow codeHistoryToolWindow = CodeHistoryUtils.getService(project, CodeHistoryToolWindow.class);
    codeHistoryToolWindow.show();
  }
}
