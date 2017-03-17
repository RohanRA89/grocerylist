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

import java.util.ArrayList;
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

        if (password.equals(userPassword)  && groceryItems.findByListName(listName) == null) {
            GroceryItem createItem = new GroceryItem();
            createItem.setItemName(itemName);
            createItem.setListName(listName);
            createItem.setCategory(category);
            createItem.setAisle(aisle);
            createItem.setPrice(price);
            groceryItems.save(createItem);

            List<GroceryItem> groceryList = new ArrayList<>();
            groceryList.add(createItem);

            found.setGroceryList(groceryList);
            groceryUsers.save(found);


            signInResponse = "Sign in successful " + foundName + "!\nYour item was created sucessfully and was added to your list "+listName;
            return signInResponse;
        }

            else if (password.equals(userPassword) && groceryItems.findByListName(listName) != null){
            GroceryItem createItem = new GroceryItem();
            createItem.setItemName(itemName);
            createItem.setCategory(category);
            createItem.setAisle(aisle);
            createItem.setPrice(price);
            groceryItems.save(createItem);


            List<GroceryItem> groceryList = found.getGroceryList();
            groceryList.add(createItem);

            found.setGroceryList(groceryList);
            groceryUsers.save(found);


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
        GroceryItem foundItems = groceryItems.findByListName(listName);

        String signInResponse = null;

        if (password.equals(userPassword) && foundItems == null) {
            signInResponse = "You have no items saved. Please go to /groceryList/createGroceryItem to create your grocery list.";
            return signInResponse;
        }
        else if (password.equals(userPassword) && foundItems != null) {
            List<GroceryItem> getList = found.getGroceryList();
            String showListName = foundItems.getListName();
//            Integer listSize = getList.size();
//            int x = 0;
//           while(x <= listSize){
//            String itemName = foundItems.getItemName();
//            String categoryName = foundItems.getCategory();
//            Integer aisleNumber = foundItems.getAisle();
//            Double price = foundItems.getPrice();
//
//            String displayList = String.format("Item Name: %s\nItem Category: %s\nItem Aisle: "+aisleNumber+"\nItem Price: "+price,itemName,categoryName);
//
//
//            signInResponse = "Welcome " + foundName + "!\nHere are your list of items:\n " + displayList;
//            listSize--;}
            signInResponse = "List Name: "+showListName+ getList.toString();
            return signInResponse;
        }
        {
            signInResponse = "Sign in failed for username: " + username;
            return signInResponse;
        }

    }
}



