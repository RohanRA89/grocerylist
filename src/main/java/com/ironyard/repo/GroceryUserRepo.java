package com.ironyard.repo;

import com.ironyard.data.GroceryUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rohanayub on 3/9/17.
 */
public interface GroceryUserRepo extends CrudRepository<GroceryUser,Long>{
    public GroceryUser findByUsername(String username);
}
