package com.ironyard.repo;

import com.ironyard.data.GroceryItem;
import com.ironyard.data.GroceryUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rohanayub on 3/9/17.
 */
public interface GroceryItemRepo extends CrudRepository<GroceryItem, Long>{
    public GroceryItem findByListName(String listName);
}
