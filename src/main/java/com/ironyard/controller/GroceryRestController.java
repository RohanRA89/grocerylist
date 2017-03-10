package com.ironyard.controller;

import com.ironyard.data.GroceryItem;
import com.ironyard.data.GroceryUser;
import com.ironyard.repo.GroceryItemRepo;
import com.ironyard.repo.GroceryUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by rohanayub on 3/9/17.
 */
@RestController
public class GroceryRestController {

    @Autowired
    private GroceryUserRepo groceryUsers;
    @Autowired
    private GroceryItemRepo groceryItems;

    @RequestMapping(path = "/groceryList/createProfile", method = RequestMethod.POST)
    public String createUser(@RequestParam String username, @RequestParam String password, @RequestParam String realName) {
        String accountCreationMessage = null;
        GroceryUser createdUser = new GroceryUser();
        createdUser.setUsername(username);
        createdUser.setRealName(realName);
        createdUser.setPassword(password);

        GroceryUser findUsernames = groceryUsers.findByUsername(username);

        if (findUsernames != null) {
            accountCreationMessage = "Username: " + username + " is already taken. Please choose a new username.";
            return accountCreationMessage;
        }
        {
            groceryUsers.save(createdUser);

            accountCreationMessage = "Thank you " + realName + "!\nYour account was created with username: " + username;

            return accountCreationMessage;
        }

    }

    @RequestMapping(path = "/groceryList/createGroceryItem", method = RequestMethod.POST)
    public String createdItem(@RequestParam String username, @RequestParam String password, @RequestParam String itemName,
                              @RequestParam String category, @RequestParam Integer aisle, @RequestParam Double price, @RequestParam String listName) {
        GroceryUser found = groceryUsers.findByUsername(username);
        String userPassword = found.getPassword();
        String foundName = found.getRealName();
        String signInResponse = null;

        if (password.equals(userPassword)) {
            GroceryItem createItem = new GroceryItem();
            createItem.setItemName(itemName);
            createItem.setListName(listName);
            createItem.setCategory(category);
            createItem.setAisle(aisle);
            createItem.setPrice(price);
            groceryItems.save(createItem);

            signInResponse = "Sign in successful " + foundName + "!\nYour item was created sucessfully and was added to your list "+listName;
            return signInResponse;
        }
        signInResponse = "Sign in failed for: " + username + ".\nPlease check your password.";
        return signInResponse;
    }

    @RequestMapping(path = "/groceryList/listAllItems", method = RequestMethod.GET)
    public String listItmes(@RequestParam String username, @RequestParam String password, @RequestParam String listName) throws Exception{
        GroceryUser found = groceryUsers.findByUsername(username);
        String userPassword = found.getPassword();
        String foundName = found.getRealName();
        Iterable<GroceryItem> foundItems = (Iterable<GroceryItem>) groceryItems.findByListName(listName);

        String signInResponse = null;

        if (password.equals(userPassword) && foundItems == null) {
            signInResponse = "You have no items saved. Please go to /groceryList/createGroceryItem to create your grocery list.";
            return signInResponse;
        }
        else if (password.equals(userPassword) && foundItems != null) {
            String displayList = foundItems.toString();

            signInResponse = "Welcome " + foundName + "!\nHere are your list of items:\n " + displayList;
            return signInResponse;
        }
        {
            signInResponse = "Sign in failed for username: " + username;
            return signInResponse;
        }

    }
}



