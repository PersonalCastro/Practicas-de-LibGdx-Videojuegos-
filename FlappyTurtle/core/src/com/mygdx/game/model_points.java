/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 *
 * @author PersonalCastro
 */
public class model_points {
    
    public model_points(){
        try{
            File ruta = new File("puntuaction.pab");
        }catch(Exception e){
            System.out.println("Fallo: " + e);
        }
    }
    
    
    
    public void addDataToDb(int nuevosDatos){
        
        File ruta = new File("puntuaction.pab");

        this.generarNuevoFicheroAleatorioDeTortugas(nuevosDatos, ruta);

    }
    
    public int getDataFromDb(){
        
        int puntos = getInfoTortugasFicheroDeAccesoSerializable();
        return puntos;
    }
    
    
    private static void generarNuevoFicheroAleatorioDeTortugas(int puntos, File ruta){
        
        try{
            
            boolean exists = ruta.exists();
            if(exists){
                ruta.delete();
            }

            FileOutputStream fileout = new FileOutputStream(ruta,true);
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);  
                        
            dataOS.writeObject(puntos);
            
            
            dataOS.close();
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    private static int getInfoTortugasFicheroDeAccesoSerializable(){
        int puntuacion = 0;       
        
        try{
            File fichero = new File("puntuaction.pab");
            
            ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(fichero));
            
            try {
                while (true) {
                        puntuacion = (int) dataIS.readObject();
                }
            } catch (EOFException eo) {
                System.out.println("FIN DE LECTURA.");
            } catch (StreamCorruptedException x) {
                System.out.println("exception: " + x);
            }

            dataIS.close(); // cerrar stream de entrada
        }catch (Exception e){
            System.out.println("Exception: " +e);
        }
        return puntuacion;
    }
    
    
}
