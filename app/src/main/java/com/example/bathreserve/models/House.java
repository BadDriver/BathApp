package com.example.bathreserve.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class House {
    private String name;
    private List<String> users;

    public House(String name, String uID) {
        this.name = name;
        this.users = new ArrayList<>();
        this.users.add(uID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
