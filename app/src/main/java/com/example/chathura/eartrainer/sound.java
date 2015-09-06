package com.example.chathura.eartrainer;

/**
 * Created by Chathura on 8/29/2015.
 */
public class sound {
    private String name;
    private String file;

    public sound(String name, String file) {
        this.name = name;
        this.file = file;
    }

    public sound() {
        this.name = "";
        this.file = "";

    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }
}
