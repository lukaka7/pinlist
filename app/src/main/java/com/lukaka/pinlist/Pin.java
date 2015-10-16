package com.lukaka.pinlist;

/**
 * Created by lujiawang on 10/16/15.
 */
public class Pin {
    private String title;
    private String note;

    public Pin() {

    }

    public Pin(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
