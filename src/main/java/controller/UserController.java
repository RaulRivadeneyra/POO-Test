/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.EmptyException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import model.crud.UserCRUD;
import model.schemas.User;
import view.Login;
import view.Register;

/**
 *
 * @author raulrivadeneyra
 */
public class UserController {
    public boolean addNewUser(Register view) throws EmptyException{
        User tempUser = null;
        User nuser = new User();
        UserCRUD model = new UserCRUD();

        nuser.setName(view.getNombre());
        nuser.setEmail(view.getCorreo());
        nuser.setPassword(view.getContrasena());
        nuser.setQuestion(view.getQuestion());
        nuser.setAnswer(view.getAnswer());

        Map<String, String> data = new HashMap<>();
        data.put("nombre", view.getNombre());
        data.put("correo", view.getCorreo());
        data.put("contrasena", view.getContrasena());
        data.put("conf_contrasena", view.getConfcontrasena());
        data.put("question", ""+view.getQuestion());
        data.put("answer", view.getAnswer());

        int campos = 0; //Verificacion campos
        int correo = 0; //Verificacion correo
        int contra = 0; //Verificacion contraseña

        int debounce = 0; //Mostrar solo un mensaje
        //Aunque no muestre el mensaje aun asi retorna la excepcion

        //confirmacion de todos los espacios fueron rellenados

        try{
            Set<String> llave = data.keySet();
             for (String key : llave){
                if(data.get(key).isEmpty()){
                    throw new NullPointerException();
                }else{
                    campos = 1;
                }
            }
        }catch(NullPointerException ex){
            if (debounce == 0){
                debounce = 1;
                JOptionPane.showMessageDialog(null,"Por favor, rellene todos los espacios");
            }
        }

        //verificacion de que es un correo
        int verif = 0;
        verif = data.get("correo").indexOf("@");

        if (verif != -1){
            correo = 1;
        }else{
            if (debounce == 0){
                debounce = 1;
                JOptionPane.showMessageDialog(null, "No se reconoce el correo, por favor intente de nuevo");
            }
        }
        if (this.checkIfUserExists(data.get("correo"))){
            JOptionPane.showMessageDialog(null, "Ya existe un usuario registrado con ese correo");
            return false;
        }

        //confirmacion de contrase?a
        if (data.get("contrasena").equals(data.get("conf_contrasena"))){
            contra = 1;
        }else{
            if (debounce == 0){
                debounce = 1;
                JOptionPane.showMessageDialog(null,"Las contraseñas no coinciden");
                
            }
        }

        //envia la informacion si todo es correcto
        if(campos == 1 && correo == 1 && contra == 1 && debounce == 0){
            model.crud.UserCRUD crudnu = new model.crud.UserCRUD();
             crudnu.createUser(nuser);
             return true;
        }
        return false;
    }
    public boolean validateSecurityQuestion(String email, int question, String answer){
        UserCRUD model = new UserCRUD();
        User tempUser = model.getUser(email);
        if ((tempUser.getQuestion() == question) && (tempUser.getAnswer().equals(answer))){
            return true;
        }
        return false;
    }
    public void updatePassword(String email, String password){
        UserCRUD model = new UserCRUD();
        User user = model.getUser(email);
        User tempUser = new User(user.getName(), user.getEmail(), password, user.getQuestion(), user.getAnswer());
        model.updateUser(user, tempUser);
  
    }
    public boolean checkIfUserExists(String email){
        UserCRUD model = new UserCRUD();
        User tempUser = null;
        try{
            tempUser = model.getUser(email);
            if (tempUser != null){    
                return true;
            }
        } catch(Exception ex) {
        }
        return false;
    }
    //Validates the input and send it to the model
    public boolean LoginUser(Login view) throws EmptyException {

        User user;
        UserCRUD model = new UserCRUD();

        Map<String, String> data = new HashMap<>();
        data.put("email", view.getEmailText());
        data.put("password", view.getPasswordText());

        if (!validCompleteness(data))
        {
            throw new EmptyException();
        }
        else
        {
            try{
                user = model.getUser(data.get("email"));
                validateLogin(data, user, view);
            }catch(Exception ex){
                showError(ex, view);
                return false;
            }

        }
        return true;
    }

    //Verify the password
    public void validateLogin(Map<String,String> data, User user, Login view){
        if(user.getPassword().equals(data.get("password"))){
            JOptionPane.showMessageDialog(
                    view, "Login success" , "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //Display an OptionPane in the view with the error
    public void showError(Exception ex, Login view){
        if(ex instanceof EmptyException){
            JOptionPane.showMessageDialog(
                    view, "You must fill every text field" , "ERROR", JOptionPane.ERROR_MESSAGE);
        }else if(ex instanceof SQLException){
            JOptionPane.showMessageDialog(
                    view, "Database error" , "ERROR", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(
                    view, "Unexpected error", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Validate that there is no empty information
    public boolean validCompleteness(Map<String,String> data){
        boolean isComplete = false;
        Set<String> keys = data.keySet();
        for(String key: keys){
            if(!data.get(key).isEmpty()){ //Checks that there is no empty information
                isComplete = true;
            }
        }

        return isComplete;
    }
}
