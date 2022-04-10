package com.github.yuriyk747.codehistory.intellij.messages;

import com.github.yuriyk747.codehistory.intellij.index.IndexRequest;
import com.intellij.util.messages.Topic;

public interface CodeHistoryListener {
  Topic<CodeHistoryListener> TOPIC = Topic.create("CodeHistory index start", CodeHistoryListener.class);

  void started(IndexRequest request);

  abstract class Adapter implements CodeHistoryListener {
    @Override
    public void started(IndexRequest request) {  }
  }
}
