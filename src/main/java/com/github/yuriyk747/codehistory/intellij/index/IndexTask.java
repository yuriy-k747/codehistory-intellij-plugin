package com.github.yuriyk747.codehistory.intellij.index;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import org.jetbrains.annotations.NotNull;

public class IndexTask extends Task.Backgroundable {
  private final IndexRequest request;
  private final boolean modal;
  private final boolean background;
  private boolean finished = false;

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
}
