package com.github.yuriyk747.codehistory.intellij.vcs;

import com.intellij.openapi.extensions.ExtensionPointName;

public interface ModuleVcsRepoProvider {
  ExtensionPointName<ModuleVcsRepoProvider> EP_NAME = ExtensionPointName.create("com.github.yuriyk747.idea.vcsProvider");
}
