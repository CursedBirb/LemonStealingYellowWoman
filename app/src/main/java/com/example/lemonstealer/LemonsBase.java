package com.example.lemonstealer;


public class LemonsBase {

    private String lemons;
    private boolean check=false;

    public LemonsBase(String lemons, boolean check) {
        super();
        this.lemons = lemons;
        this.check = check;
    }

    public String getLemons() {
        return lemons;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void setNazwa(String lemons) {
        this.lemons = lemons;
    }
}
