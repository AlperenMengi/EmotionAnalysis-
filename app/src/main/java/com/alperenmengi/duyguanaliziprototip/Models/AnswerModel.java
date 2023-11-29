package com.alperenmengi.duyguanaliziprototip.Models;

public class AnswerModel {
    private String text;
    private String cevap; // veya char cevap; olarak tanımlayabilirsiniz

    public AnswerModel(String text, String cevap) {
        this.text = text;
        this.cevap = cevap;
    }

    public String getText() {
        return text;
    }

    public String getCevap() {
        return cevap;
    }
}