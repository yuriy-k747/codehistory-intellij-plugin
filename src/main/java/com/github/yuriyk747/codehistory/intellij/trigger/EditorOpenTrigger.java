package com.github.yuriyk747.codehistory.intellij.trigger;

import com.github.yuriyk747.codehistory.intellij.common.CodeHistoryUtils;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EditorOpenTrigger implements FileEditorManagerListener, StartupActivity {
  @Override
  public void runActivity(@NotNull Project project) {
    VirtualFile[] openFiles = FileEditorManager.getInstance(project).getOpenFiles();
    if (openFiles.length > 0) {
      submit(List.of(openFiles), project);
    }
  }

  @Override
  public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
    submit(Collections.singleton(file), source.getProject());
  }

  private void submit(@NotNull Collection<VirtualFile> files, @NotNull Project project) {
    CodeHistorySubmitter submitter = CodeHistoryUtils.getService(project, CodeHistorySubmitter.class);
    submitter.submitFiles(files, TriggerType.EDITOR_OPEN, true);
  }
}
