package com.github.yuriyk747.codehistory.intellij.index;

import com.intellij.openapi.vfs.VirtualFile;

import java.util.Set;

public interface IndexCallback {
  void onSuccess(Set<VirtualFile> failedFiles);

  void onError(Throwable e);
}
