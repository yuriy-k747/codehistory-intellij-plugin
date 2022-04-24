package com.github.yuriyk747.codehistory.intellij.index;

import com.github.yuriyk747.codehistory.intellij.common.CodeHistoryUtils;
import com.github.yuriyk747.codehistory.intellij.messages.StatusListener;
import com.intellij.openapi.project.Project;

public class IndexStatus {
  private final StatusListener statusListener;
  private Status status = Status.STOPPED;

  public IndexStatus(Project project) {
    this.statusListener = project.getMessageBus().syncPublisher(StatusListener.CODE_HISTORY_INDEXER_STATUS);
  }

  public enum Status {RUNNING, STOPPED, CANCELLING}

  public static IndexStatus get(Project project) {
    return CodeHistoryUtils.getService(project, IndexStatus.class);
  }

  public synchronized boolean isRunning() {
    return status == Status.RUNNING || status == Status.CANCELLING;
  }

  public synchronized boolean isCanceled() {
    return status == Status.CANCELLING;
  }

  public void stopRun() {
    Status callback = null;
    synchronized (this) {
      if (isRunning()) {
        status = Status.STOPPED;
        callback = status;
      }
    }

    if (callback != null) {
      statusListener.changed(callback);
    }
  }

  public void cancel() {
    Status callback = null;
    synchronized (this) {
      if (status == Status.RUNNING) {
        status = Status.CANCELLING;
        callback = Status.CANCELLING;
      }
    }

    if (callback != null) {
      statusListener.changed(callback);
    }
  }

  public boolean tryRun() {
    Status callback = null;
    synchronized (this) {
      if (!isRunning()) {
        status = Status.RUNNING;
        callback = Status.RUNNING;
      }
    }

    if (callback != null) {
      statusListener.changed(callback);
      return true;
    } else {
      return false;
    }
  }
}
