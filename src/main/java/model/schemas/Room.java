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
public class Room {
    @Id
    @Column(name = "room_id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "area_id")
    private int area_id;

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAreaId() {
        return area_id;
    }
    public void setAreaId(int area_id) {
        this.area_id = area_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getInfo() {
        return "Room{" + "Id=" + id + ", Name='" + name + '}';
    }
}
