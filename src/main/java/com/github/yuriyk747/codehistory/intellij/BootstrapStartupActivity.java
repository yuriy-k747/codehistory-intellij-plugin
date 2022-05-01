package com.github.yuriyk747.codehistory.intellij;

import com.github.yuriyk747.codehistory.intellij.common.CodeHistoryUtils;
import com.github.yuriyk747.codehistory.intellij.core.CodeHistorySynchronizer;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

public class BootstrapStartupActivity implements StartupActivity {
  
  @Override
  public void runActivity(@NotNull Project project) {
    if (ApplicationManager.getApplication().isUnitTestMode()) {
      return;
    }
    
    CodeHistoryUtils.getService(project, CodeHistorySynchronizer.class).init();
  }
}
