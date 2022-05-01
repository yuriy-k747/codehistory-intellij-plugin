package com.github.yuriyk747.codehistory.intellij.common;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.Nullable;

import javax.annotation.CheckForNull;
import java.nio.file.Paths;

public class CodeHistoryAppUtils {
  @CheckForNull
  public static String getRelativePathForIndex(Module module, VirtualFile virtualFile) {
    var relativePathToProject = getPathRelativeToProjectBaseDir(module.getProject(), virtualFile);
    if (relativePathToProject != null) {
      return relativePathToProject;
    }

    var relativePathToModule = getPathRelativeToModuleBaseDir(module, virtualFile);
    if (relativePathToModule != null) {
      return relativePathToModule;
    }

    var strictRelativePathToContentRoot = getPathRelativeToContentRoot(module, virtualFile);
    if (strictRelativePathToContentRoot != null) {
      return strictRelativePathToContentRoot;
    }

    return getPathRelativeToCommonAncestorWithProjectBaseDir(module.getProject(), virtualFile);
  }

  @Nullable
  private static String getPathRelativeToCommonAncestorWithProjectBaseDir(Project project, VirtualFile virtualFile) {
    final var projectDir = ProjectUtil.guessProjectDir(project);
    if (projectDir == null) {
      return null;
    }
    var commonAncestor = VfsUtilCore.getCommonAncestor(projectDir, virtualFile);
    if (commonAncestor != null) {
      return VfsUtilCore.getRelativePath(virtualFile, commonAncestor);
    }
    return null;
  }

  @CheckForNull
  private static String getPathRelativeToProjectBaseDir(Project project, VirtualFile file) {
    final var projectDir = ProjectUtil.guessProjectDir(project);
    if (projectDir == null) {
      return null;
    }
    return VfsUtilCore.getRelativePath(file, projectDir);
  }

  @CheckForNull
  private static String getPathRelativeToModuleBaseDir(Module module, VirtualFile file) {
    var moduleFilePath = module.getModuleFilePath();
    if ("".equals(moduleFilePath)) {
      return null;
    }
    var baseDir = Paths.get(moduleFilePath).getParent();
    var filePath = Paths.get(file.getPath());
    if (!filePath.startsWith(baseDir)) {
      return null;
    }
    return PathUtil.toSystemIndependentName(baseDir.relativize(filePath).toString());
  }

  @CheckForNull
  private static String getPathRelativeToContentRoot(Module module, VirtualFile file) {
    var moduleRootManager = ModuleRootManager.getInstance(module);
    for (var root : moduleRootManager.getContentRoots()) {
      if (VfsUtilCore.isAncestor(root, file, true)) {
        return VfsUtilCore.getRelativePath(file, root);
      }
    }
    return null;
  }
}
