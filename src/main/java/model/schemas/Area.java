/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.schemas;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author raulrivadeneyra
 */
public class Area {
    @Id
    @Column(name = "area_id")
    private int id;
    @Column(name = "name")
    private String name;
    
    public Area(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }   
    public String getInfo() {
        return "Rooms{" + "id=" + id + ", name='" + name +'}';
    }
}
