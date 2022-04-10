package com.github.yuriyk747.codehistory.intellij.actions;

import com.github.yuriyk747.codehistory.intellij.CodeHistoryToolWindowFactory;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.ContentManagerListener;

import javax.swing.*;
import java.awt.*;

public class CodeHistoryToolWindow implements ContentManagerListener {
  private final Project project;

  public CodeHistoryToolWindow(Project project) {
    this.project = project;
  }

  public void show() {
    if (getToolWindow() == null) {
      return;
    }

    bringIdeToFront(project);
  }

  public void openTab(String name) {
    ApplicationManager.getApplication().assertIsDispatchThread();
    ToolWindow toolWindow = getToolWindow();
    if (toolWindow != null) {
      toolWindow.show(() -> selectTab(toolWindow, name));
    }
  }

  private ToolWindow getToolWindow() {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    return toolWindowManager.getToolWindow(CodeHistoryToolWindowFactory.TOOL_WINDOW_ID);
  }

  private void bringIdeToFront(Project project) {
    JComponent component = getToolWindow().getComponent();
    IdeFocusManager.getInstance(project).requestFocus(component, true);
    Window window = SwingUtilities.getWindowAncestor(component);
    if (window != null) {
      window.toFront();
    }
  }

  private static void selectTab(ToolWindow toolWindow, String tabId) {
    ContentManager contentManager = toolWindow.getContentManager();
    Content content = contentManager.findContent(tabId);
    if (content != null) {
      contentManager.setSelectedContent(content);
    }
  }
}
