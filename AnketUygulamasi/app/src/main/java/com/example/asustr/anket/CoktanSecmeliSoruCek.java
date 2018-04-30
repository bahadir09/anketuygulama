package com.example.asustr.anket;

/**
 * Created by ASUS TR on 27.04.2018.
 */

public class CoktanSecmeliSoruCek {

    private String soru;
    private String secenek1;
    private String secenek2;
    private String secenek3;
    private String secenek4;

    public CoktanSecmeliSoruCek(String soru, String secenek1, String secenek2, String secenek3, String secenek4) {
        this.soru = soru;
        this.secenek1 = secenek1;
        this.secenek2 = secenek2;
        this.secenek3 = secenek3;
        this.secenek4 = secenek4;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getSecenek1() {
        return secenek1;
    }

    public void setSecenek1(String secenek1) {
        this.secenek1 = secenek1;
    }

    public String getSecenek2() {
        return secenek2;
    }

    public void setSecenek2(String secenek2) {
        this.secenek2 = secenek2;
    }

    public String getSecenek3() {
        return secenek3;
    }

    public void setSecenek3(String secenek3) {
        this.secenek3 = secenek3;
    }

    public String getSecenek4() {
        return secenek4;
    }

    public void setSecenek4(String secenek4) {
        this.secenek4 = secenek4;
    }

}
