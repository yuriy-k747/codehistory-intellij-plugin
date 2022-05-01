package com.github.yuriyk747.codehistory.intellij.trigger;

import com.github.yuriyk747.codehistory.intellij.common.CodeHistoryUtils;
import com.github.yuriyk747.codehistory.intellij.common.ui.CodeHistoryConsole;
import com.github.yuriyk747.codehistory.intellij.index.IndexCallback;
import com.github.yuriyk747.codehistory.intellij.index.IndexManager;
import com.github.yuriyk747.codehistory.intellij.index.IndexTask;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.annotation.CheckForNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CodeHistorySubmitter {
  private final Project myProject;

  static final IndexCallback EMPTY = new IndexCallback() {
    @Override
    public void onSuccess(Set<VirtualFile> failedFiles) { }

    @Override
    public void onError(Throwable e) { }
  };

  public CodeHistorySubmitter(Project myProject) {
    this.myProject = myProject;
  }
  
  public void submitOpenFilesAuto(TriggerType trigger) {
    FileEditorManager editorManager = FileEditorManager.getInstance(myProject);
    VirtualFile[] openFiles = editorManager.getOpenFiles();
    submitFiles(List.of(openFiles), trigger, true);
  }
  
  @CheckForNull
  public IndexTask submitFiles(Collection<VirtualFile> files, TriggerType trigger, boolean startInBackground) {
    return submitFiles(files, trigger, EMPTY, startInBackground);
  }

  @CheckForNull
  public IndexTask submitFiles(Collection<VirtualFile> files, TriggerType trigger, IndexCallback callback, boolean startInBackground) {
    if (!files.isEmpty()) {
      CodeHistoryConsole console = CodeHistoryUtils.getService(myProject, CodeHistoryConsole.class);
      console.debug("Trigger: " + trigger);

      IndexManager indexManager = CodeHistoryUtils.getService(myProject, IndexManager.class);
      if (startInBackground) {
        return indexManager.submitBackground(files, trigger, callback);
      } else {
        // implement manual run
      }
    }

    return null;
  }
}
