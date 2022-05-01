package com.github.yuriyk747.codehistory.intellij.core;

import com.github.yuriyk747.codehistory.intellij.common.CodeHistoryUtils;
import com.github.yuriyk747.codehistory.intellij.trigger.CodeHistorySubmitter;
import com.github.yuriyk747.codehistory.intellij.trigger.TriggerType;
import com.intellij.concurrency.JobScheduler;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CodeHistorySynchronizer implements Disposable {
  private final Project project;
  private ScheduledFuture<?> scheduledTask;
  
  public CodeHistorySynchronizer(Project project) {
    this.project = project;
  }
  
  public void init() {
    int syncPeriod = 3600;
    scheduledTask = JobScheduler.getScheduler().scheduleWithFixedDelay(this::syncHistory, 1, syncPeriod, TimeUnit.SECONDS);
  }
  
  private void syncHistory() {
    ProgressManager.getInstance()
        .run(new Task.Backgroundable(project, "CodeHistory Update") {
          public void run(@NotNull ProgressIndicator progressIndicator) {
            CodeHistorySynchronizer.this.syncHistory(progressIndicator);
          }
        });
  }
  
  void syncHistory(@NotNull ProgressIndicator progressIndicator) {
    CodeHistorySubmitter submitter = CodeHistoryUtils.getService(project, CodeHistorySubmitter.class);
    submitter.submitOpenFilesAuto(TriggerType.BINDING_UPDATE);
  }
  
  @Override
  public void dispose() {
    scheduledTask.cancel(true);
  }
}
