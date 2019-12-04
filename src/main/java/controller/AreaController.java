/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashMap;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author raulrivadeneyra
 */
public class AreaController {
    HashMap<String, String> areas;
    public AreaController(){
        areas = new HashMap<>();
    }
    private void getDataFromModel(){
        for (int i = 0; i < 10; i++) {
            areas.put(""+i, "Area#"+i);
        }
    }
    public void sendDataToModel(HashMap<String, String> temp_areas){
        
    }
    public HashMap<String, String> getDataFromController(){
        getDataFromModel();
        return areas;
    }
    
    public void delete (HashMap<String,String> nombre) throws NullPointerException {
        try {
            Set<String> llave = nombre.keySet();
            for (String key : llave){
                if(nombre.get(key).isEmpty()){
                    throw new NullPointerException();
                } else {
                   // modelx.importGroupData(nombre);
                }
            }
        } catch(NullPointerException ex) {
            JOptionPane.showMessageDialog(null,"error");
        } 
    }
    
    //confirma la eliminacion al usuario
    public void delete_confirmacion (boolean a){
        if(a == true){
            JOptionPane.showMessageDialog(null,"Se ha borrado exitosamente el area");
        } else {
            JOptionPane.showMessageDialog(null,"No se ha podido borrar el area por favor verifique los datos ingresados");
        }
    }
}
