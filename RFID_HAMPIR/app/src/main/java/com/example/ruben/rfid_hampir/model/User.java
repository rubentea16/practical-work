package com.example.ruben.rfid_hampir.model;

/**
 * Created by Daniel on 1/4/2017.
 */

public class User {
    private Long NRP;
    private String Nama;

    public User(Long NRP, String nama) {
        this.NRP = NRP;
        Nama = nama;
    }

    public User() {

    }

    public Long getNRP() {
        return NRP;
    }

    public void setNRP(Long NRP) {
        this.NRP = NRP;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }
}
