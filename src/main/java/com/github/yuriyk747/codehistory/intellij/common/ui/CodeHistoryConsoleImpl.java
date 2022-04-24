package com.github.yuriyk747.codehistory.intellij.common.ui;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.serviceContainer.NonInjectable;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CodeHistoryConsoleImpl implements CodeHistoryConsole, Disposable {

  private ConsoleView consoleView;
  private final Project myProject;

  public CodeHistoryConsoleImpl(Project project) {
    this.myProject = project;
  }

  @NonInjectable
  CodeHistoryConsoleImpl(Project project, ConsoleView consoleView) {
    this.consoleView = consoleView;
    this.myProject = project;
  }

  @Override
  public void debug(String msg) {
    if (debugEnabled()) {
      getConsoleView().print(msg + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
    }
  }

  @Override
  public boolean debugEnabled() {
    return true;
  }

  @Override
  public void info(String msg) {
    getConsoleView().print(msg + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
  }

  @Override
  public void error(String msg) {
    getConsoleView().print(msg + "\n", ConsoleViewContentType.ERROR_OUTPUT);
  }

  @Override
  public void error(String msg, @Nullable Throwable t) {
    error(msg);
    if (t != null) {
      var errors = new StringWriter();
      t.printStackTrace(new PrintWriter(errors));
      error(errors.toString());
    }
  }

  @Override
  public synchronized void clear() {
    if (consoleView != null) {
      consoleView.clear();
    }
  }

  @Override
  public synchronized ConsoleView getConsoleView() {
    if (consoleView == null) {
      consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(myProject).getConsole();
    }
    return this.consoleView;
  }

  @Override
  public void dispose() {
    if (consoleView != null) {
      Disposer.dispose(consoleView);
    }
  }
}
