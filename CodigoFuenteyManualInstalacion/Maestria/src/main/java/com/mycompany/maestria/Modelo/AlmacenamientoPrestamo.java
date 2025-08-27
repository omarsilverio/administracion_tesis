/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maestria.Modelo;

import com.mycompany.maestria.Controlador.conectionBDD;
import java.sql.PreparedStatement;

/**
 *
 * @author omarsilverio
 */
public class AlmacenamientoPrestamo extends Almacenamiento{
    
    private boolean fisico;
    
    private PreparedStatement pstatement;
    private final conectionBDD CONEXION_BDD = new conectionBDD();

    public AlmacenamientoPrestamo(String idTesis, int usb, int cd, int tomo,boolean fisico){
        super(idTesis,usb,cd,tomo);
        this.fisico = fisico;
    }
    
    public boolean getStatus() {
        return getUsb() == 0 && getTomo() == 0 && getCd() == 0;
    }

    public void setFisico(boolean fisico) {
        this.fisico = fisico;
    }
    
}
