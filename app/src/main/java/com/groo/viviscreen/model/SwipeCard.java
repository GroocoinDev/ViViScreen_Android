package com.groo.viviscreen.model;

public class SwipeCard {
    private String text1,text2;
    private int background;
    private int adType; // 0:광고형(내부 웹뷰), 1:참여형(미션, 외부 브라우저), 2:설치형(미션, 앱스토어)
    private String url;
    private int grooPoint; // 지급되는 그루포인트

    public SwipeCard(String text1, String text2, int background, int adType, String url, int grooPoint) {
        this.text1 = text1;
        this.text2 = text2;
        this.background = background;
        this.adType = adType;
        this.url = url;
        this.grooPoint = grooPoint;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public int getGrooPoint() {
        return grooPoint;
    }

    public void setGrooPoint(int grooPoint) {
        this.grooPoint = grooPoint;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
