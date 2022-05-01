package com.github.yuriyk747.codehistory.intellij.history;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.serviceContainer.NonInjectable;
import dev.codehistory.core.commands.FilesHistoryResult;

public class HistoryManager {
  private final Project project;
  private final LiveHistoryCache liveHistoryCache;

  public HistoryManager(Project project) {
    this(project, new LiveHistoryCache(project));
  }

  @NonInjectable
  HistoryManager(Project project, LiveHistoryCache liveHistoryCache) {
    this.project = project;
    this.liveHistoryCache = liveHistoryCache;
  }

  public void insertNewHistoryResult(VirtualFile file, FilesHistoryResult historyResult) {
    liveHistoryCache.insertHistoryResult(file, historyResult);
  }
}
