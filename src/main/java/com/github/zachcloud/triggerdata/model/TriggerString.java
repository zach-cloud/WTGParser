package com.github.zachcloud.triggerdata.model;

public class TriggerString {

    private String description;
    private String editorData;
    private String hint;

    private boolean hasSetDescription;
    private boolean hasSetEditorData;
    private boolean hasSetHint;

    public TriggerString() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEditorData() {
        return editorData;
    }

    public void setEditorData(String editorData) {
        this.editorData = editorData;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean isHasSetDescription() {
        return hasSetDescription;
    }

    public void setHasSetDescription(boolean hasSetDescription) {
        this.hasSetDescription = hasSetDescription;
    }

    public boolean isHasSetEditorData() {
        return hasSetEditorData;
    }

    public void setHasSetEditorData(boolean hasSetEditorData) {
        this.hasSetEditorData = hasSetEditorData;
    }

    public boolean isHasSetHint() {
        return hasSetHint;
    }

    public void setHasSetHint(boolean hasSetHint) {
        this.hasSetHint = hasSetHint;
    }
}
