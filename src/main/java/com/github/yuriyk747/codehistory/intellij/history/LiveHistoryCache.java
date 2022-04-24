package com.github.yuriyk747.codehistory.intellij.history;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import dev.codehistory.core.commands.FileHistoryResult;

import java.util.HashMap;
import java.util.Map;

public class LiveHistoryCache {
  private final Map<VirtualFile, FileHistoryResult> cache = new HashMap<>();
  private final Project project;

  public LiveHistoryCache(Project project) {
    this.project = project;
  }
}
