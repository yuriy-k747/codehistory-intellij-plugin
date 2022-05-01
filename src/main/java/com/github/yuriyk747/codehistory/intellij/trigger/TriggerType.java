package com.github.yuriyk747.codehistory.intellij.trigger;

public enum TriggerType {
  UPDATE("Update"),
  EDITOR_OPEN("Editor open"),
  BINDING_UPDATE("Binding update");

  private final String name;

  TriggerType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
