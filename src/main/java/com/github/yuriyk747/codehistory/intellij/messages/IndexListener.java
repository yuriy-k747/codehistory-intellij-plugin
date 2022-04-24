package com.github.yuriyk747.codehistory.intellij.messages;

import com.github.yuriyk747.codehistory.intellij.index.IndexRequest;
import com.intellij.util.messages.Topic;

public interface IndexListener {
  Topic<IndexListener> TOPIC = Topic.create("CodeHistory index start", IndexListener.class);

  void started(IndexRequest request);

  abstract class Adapter implements IndexListener {
    @Override
    public void started(IndexRequest request) {  }
  }
}
