package com.github.gfranks.dynamiccard.sample;

public class DynamicItem {

    private String mText;

    public DynamicItem(String text) {
        this.mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }
}
