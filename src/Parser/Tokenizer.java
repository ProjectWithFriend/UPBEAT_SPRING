package org.example;

public class Tokenizer {
    private int pos;
    private String str;
    public Tokenizer(String str) {
        this.pos = 0;
        this.str = str;
    }

    public boolean hasMoreTokens() {
        return false;
    }

    public String consume() {
        return null;
    }

    public String peek() {
        return null;
    }

    public boolean consume(String str) {
        return false;
    }

    public boolean peek(String str) {
        return false;
    }
}
