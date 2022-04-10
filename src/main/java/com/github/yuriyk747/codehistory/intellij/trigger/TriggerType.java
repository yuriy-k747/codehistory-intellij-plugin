package com.github.yuriyk747.codehistory.intellij.trigger;

public enum TriggerType {

  UPDATE("Update");

  private final String name;

  TriggerType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
