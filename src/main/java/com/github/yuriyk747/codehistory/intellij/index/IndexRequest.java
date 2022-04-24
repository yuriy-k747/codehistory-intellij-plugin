package com.github.yuriyk747.codehistory.intellij.index;

import com.github.yuriyk747.codehistory.intellij.trigger.TriggerType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.Collection;

public class IndexRequest {
  private final Project project;
  private final Collection<VirtualFile> files;
  private final TriggerType trigger;
  private final IndexCallback callback;

  public IndexRequest(Project project, Collection<VirtualFile> files, TriggerType trigger, IndexCallback callback) {
    this.project = project;
    this.files = files;
    this.trigger = trigger;
    this.callback = callback;
  }

  public Collection<VirtualFile> getFiles() {
    return files;
  }

  public Project getProject() {
    return project;
  }

  public IndexCallback callback() {
    return callback;
  }

}
