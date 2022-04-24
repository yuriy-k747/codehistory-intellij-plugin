package com.github.yuriyk747.codehistory.intellij.index;

import com.github.yuriyk747.codehistory.intellij.common.CanceledException;
import com.github.yuriyk747.codehistory.intellij.common.ui.CodeHistoryConsole;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class IndexTask extends Task.Backgroundable {
  private final IndexRequest request;
  private final boolean modal;
  private final boolean background;
  private boolean finished = false;
  private boolean cancelled;

  public IndexTask(IndexRequest request, boolean background) {
    this(request, false, background);
  }

  public IndexTask(IndexRequest request, boolean modal, boolean background) {
    super(request.getProject(), "CodeHistory Index", true);
    this.request = request;
    this.modal = modal;
    this.background = background;
  }

  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    try {
      checkCanceled(indicator);

    } catch (CanceledException | ProcessCanceledException e1) {
      CodeHistoryConsole console = CodeHistoryConsole.get(request.getProject());
      console.info("Analysis canceled");
    } catch (Throwable e) {
      handleError(e, indicator);
    }
  }

  @Override
  public boolean shouldStartInBackground() {
    return background;
  }

  @Override
  public boolean isConditionalModal() {
    return modal;
  }

  public IndexRequest getRequest() {
    return request;
  }

  private void handleError(Throwable e, ProgressIndicator indicator) {
    if (!isCancelled(indicator)) {
      String message = "Error running CodeHistory indexing";
      CodeHistoryConsole console = CodeHistoryConsole.get(request.getProject());
      console.error(message, e);

      if (indicator.isShowing()) {
        var dialogMsg = "CodeHistory indexing failed: " + e.getMessage();
        ApplicationManager.getApplication().invokeAndWait(
            () -> Messages.showErrorDialog(dialogMsg, "Error Running CodeHistory Indexing"), ModalityState.defaultModalityState());
      }

      IndexCallback callback = request.callback();
      callback.onError(e);
    }
  }

  private void checkCanceled(ProgressIndicator indicator) {
    if (isCancelled(indicator)) {
      throw new CanceledException();
    }
  }

  private boolean isCancelled(ProgressIndicator indicator) {
    return cancelled || indicator.isCanceled() || myProject.isDisposed() || Thread.currentThread().isInterrupted() || IndexStatus.get(myProject).isCanceled();
  }
}
