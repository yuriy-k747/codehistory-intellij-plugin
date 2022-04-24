package com.github.yuriyk747.codehistory.intellij.messages;

import com.github.yuriyk747.codehistory.intellij.index.IndexStatus;
import com.intellij.util.messages.Topic;

public interface StatusListener {
  Topic<StatusListener> CODE_HISTORY_INDEXER_STATUS = Topic.create("CodeHistory Indexer Status", StatusListener.class);
  
  void changed(IndexStatus.Status newStatus);
}
