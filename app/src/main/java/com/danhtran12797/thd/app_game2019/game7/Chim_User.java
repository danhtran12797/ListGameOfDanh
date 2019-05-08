package com.danhtran12797.thd.app_game2019.game7;

public class Chim_User {
    public String idChim;
    public int tienCuoc;
    public String chim;

    public Chim_User(String idChim, int tienCuoc, String chim) {
        this.idChim = idChim;
        this.tienCuoc = tienCuoc;
        this.chim = chim;
    }

    public String getIdChim() {
        return idChim;
    }

    public void setIdChim(String idChim) {
        this.idChim = idChim;
    }

    public int getTienCuoc() {
        return tienCuoc;
    }

    public void setTienCuoc(int tienCuoc) {
        this.tienCuoc = tienCuoc;
    }

    public String getChim() {
        return chim;
    }

    public void setChim(String chim) {
        this.chim = chim;
    }
}

