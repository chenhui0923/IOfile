package com.example.demo.Iostart.ios5;

import java.io.Serializable;

public class Clazz implements Serializable {
    private static final long serialVersionUID = -7758998193415831795L;
    private int cno;
    private String cname;

    public Clazz(int cno, String cname) {
        this.cno = cno;
        this.cname = cname;
    }
    
    public  Clazz(){

    }

    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "cno=" + cno +
                ", cname='" + cname + '\'' +
                '}';
    }
}
