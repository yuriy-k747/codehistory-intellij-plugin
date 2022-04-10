package com.github.yuriyk747.codehistory.intellij.index;

import com.github.yuriyk747.codehistory.intellij.common.CodeHistoryUtils;
import com.github.yuriyk747.codehistory.intellij.common.ui.CodeHistoryConsole;
import com.github.yuriyk747.codehistory.intellij.messages.CodeHistoryListener;
import com.github.yuriyk747.codehistory.intellij.trigger.TriggerType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBus;

import java.util.Collection;

public class IndexManager {
  private final MessageBus messageBus;
  private final Project project;

  public IndexManager(Project project) {
    this.messageBus = project.getMessageBus();
    this.project = project;
  }

  public IndexTask submitBackground(Collection<VirtualFile> files, TriggerType trigger, IndexCallback callback) {
    IndexRequest analysisRequest = new IndexRequest(this.project, files, trigger, callback);

    CodeHistoryConsole console = CodeHistoryUtils.getService(this.project, CodeHistoryConsole.class);
    console.debug(String.format("[%s] %d file(s) submitted", trigger.getName(), analysisRequest.getFiles().size()));

    IndexTask task = new IndexTask(analysisRequest, true);
    notifyStart(task.getRequest());
    task.queue();

    return task;
  }

  private void notifyStart(IndexRequest request) {
    messageBus.syncPublisher(CodeHistoryListener.TOPIC).started(request);
  }
}
