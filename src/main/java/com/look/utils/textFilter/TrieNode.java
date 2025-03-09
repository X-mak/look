package com.look.utils.textFilter;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private boolean isEnd;
    private Map<Character, TrieNode> children = new HashMap<>();

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public TrieNode getChildren(Character c) {
        return children.get(c);
    }

    public void addChildren(Character c, TrieNode node) {
        children.put(c, node);
    }
}
