/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo;


/**
 *
 * @author omarsilverio
 */
public class Adeudo{
    
    private int numero_prestamo;
    private String idTesis;
    private int usb , cd, tomo;

    public Adeudo(int numero_prestamo,String idTesis, int usb, int cd, int tomo) {
        this.numero_prestamo = numero_prestamo;
        this.idTesis = idTesis;
        this.usb = usb;
        this.cd = cd;
        this.tomo = tomo;
    }    

    public int getNumero_prestamo() {
        return numero_prestamo;
    }

    public void setNumero_prestamo(int numero_prestamo) {
        this.numero_prestamo = numero_prestamo;
    }
   
    
    public String getIdTesis() {
        return idTesis;
    }

    public void setIdTesis(String idTesis) {
        this.idTesis = idTesis;
    }

    public int getUsb() {
        return usb;
    }

    public void setUsb(int usb) {
        this.usb = usb;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public int getTomo() {
        return tomo;
    }

    public void setTomo(int tomo) {
        this.tomo = tomo;
    }
}
