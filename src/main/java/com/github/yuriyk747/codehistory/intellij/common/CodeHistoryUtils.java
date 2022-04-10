package com.github.yuriyk747.codehistory.intellij.common;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class CodeHistoryUtils {

  private CodeHistoryUtils() {
    throw new IllegalStateException();
  }

  private static final Logger LOG = Logger.getInstance(CodeHistoryUtils.class);

  public static <T> T getService(@NotNull Project project, Class<T> clazz) {
    var t = ServiceManager.getService(project, clazz);
    logAndThrowIfServiceNotFound(t, clazz.getName());

    return t;
  }

  private static <T> void logAndThrowIfServiceNotFound(T t, String name) {
    if (t == null) {
      LOG.error("Could not find service: " + name);
      throw new IllegalArgumentException("Class not found: " + name);
    }
  }
}
