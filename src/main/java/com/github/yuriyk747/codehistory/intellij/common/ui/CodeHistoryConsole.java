package com.github.yuriyk747.codehistory.intellij.common.ui;

import com.github.yuriyk747.codehistory.intellij.common.CodeHistoryUtils;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CodeHistoryConsole {
  static CodeHistoryConsole get(@NotNull Project p) {
    return CodeHistoryUtils.getService(p, CodeHistoryConsole.class);
  }

  void debug(String msg);

  boolean debugEnabled();

  void info(String msg);

  void error(String msg);

  void error(String msg, @Nullable Throwable t);

  void clear();

  ConsoleView getConsoleView();
}
