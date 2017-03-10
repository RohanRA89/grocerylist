package com.ironyard.data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by rohanayub on 3/9/17.
 */
@Entity
public class GroceryUser {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String realName;
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<GroceryItem> groceryList;

    public GroceryUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GroceryItem> getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(List<GroceryItem> groceryList) {
        this.groceryList = groceryList;
    }
}
