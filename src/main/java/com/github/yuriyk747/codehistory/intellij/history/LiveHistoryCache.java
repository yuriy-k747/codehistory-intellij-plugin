package com.github.yuriyk747.codehistory.intellij.history;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import dev.codehistory.core.commands.FilesHistoryResult;

import java.util.HashMap;
import java.util.Map;

public class LiveHistoryCache {
  private final Map<VirtualFile, FilesHistoryResult> cache = new HashMap<>();
  private final Project project;

  public LiveHistoryCache(Project project) {
    this.project = project;
  }

  public synchronized void insertHistoryResult(VirtualFile virtualFile, FilesHistoryResult historyResult) {
    cache.put(virtualFile, historyResult);
  }

  public synchronized void clear() {
    cache.clear();
  }
}
