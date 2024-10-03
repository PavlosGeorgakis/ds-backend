package com.example.it22123.rest;

import com.example.it22123.entity.Cooperative;
import com.example.it22123.entity.User;
import com.example.it22123.service.CooperativeService;
import com.example.it22123.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cooperative")
public class CooperativeRestController {

    @Autowired
    private CooperativeService cooperativeService;

    @Autowired
    private UserService userService;





    @GetMapping("")
    public List<Cooperative> showCooperatives() {


        return cooperativeService.getCooperatives();
    }




    @GetMapping("{cooperative_id}")
    public Cooperative getCooperative(@PathVariable Integer cooperative_id) {

        return cooperativeService.getCooperative(cooperative_id);
    }



    @GetMapping("/new")
    public Cooperative addCooperative() {
        return new Cooperative();
    }


    @PutMapping("{cooperative_id}")
    public Cooperative editCooperative(@PathVariable Integer cooperative_id, @RequestBody Cooperative updatedCooperative) {
        Cooperative existingCooperative = cooperativeService.getCooperative(cooperative_id);

        if (existingCooperative != null) {
            // Update the properties of the existing cooperative with the new values
            existingCooperative.setName(updatedCooperative.getName());
            existingCooperative.setVat(updatedCooperative.getVat());
            // Update other properties as needed

            cooperativeService.saveCooperative(existingCooperative);
            return existingCooperative;
        } else {
            // Handle the case where the cooperative with the given ID is not found
            return null; // or throw an exception, return a response with a 404 status, etc.
        }
    }



    @PostMapping("/temp")
    public String saveCooperative(@RequestBody Cooperative cooperative)  {
        cooperativeService.saveCooperative(cooperative);
        return "temp";
    }



    @PostMapping("/new")
    public List<Cooperative> saveCooperative(@RequestBody Cooperative cooperative, Model model) {
        // Retrieve the current user from the security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Retrieve the user by username using the new method
        User currentUser = userService.getUserByUsername(userDetails.getUsername());

        // Set the user for the cooperative
        cooperative.setUser(currentUser);

        // Save the cooperative
        cooperativeService.saveCooperative(cooperative);

        // Return the list of cooperatives
        return cooperativeService.getCooperatives();
    }








    //OK
    @DeleteMapping("{cooperative_id}")
    public List<Cooperative> deleteCooperative(@PathVariable Integer cooperative_id) {
        cooperativeService.deleteCooperative(cooperative_id);
        return cooperativeService.getCooperatives();
    }




    @GetMapping("/user/{user_id}")
    public ResponseEntity<Cooperative> getUserCooperative(@PathVariable Long user_id) {
        Cooperative cooperative = cooperativeService.getUserCooperative(user_id);

        if (cooperative != null) {
            return ResponseEntity.ok(cooperative);
        } else {
            return ResponseEntity.notFound().build();
        }
    }






    @GetMapping("{cooperative_id}/users")
    public List<User> getCooperativeUser(@PathVariable Integer cooperative_id) {
        return cooperativeService.getCooperative(cooperative_id).getUsers();
    }



    @PostMapping("/users/{cooperative_id}/{user_id}")
    public List<User> addUser(@PathVariable Integer cooperative_id, @PathVariable Long user_id,Model model) {
        Cooperative cooperative = cooperativeService.getCooperative(cooperative_id);
        User user = userService.getUser(user_id);
        cooperative.addUser(user);
        cooperativeService.saveCooperative(cooperative);
        user.setCooperative(cooperative);
        userService.saveUser(user);
        model.addAttribute((cooperative));
        model.addAttribute((userService.getUserWithoutCooperative()));
        return cooperative.getUsers();
    }


























    //

    @GetMapping("/user/{user_id}/cooperatives")
    public ResponseEntity<List<Cooperative>> getCooperativesForUser(@PathVariable Long user_id) {
        List<Cooperative> cooperatives = cooperativeService.getCooperativesForUser(user_id);

        if (!cooperatives.isEmpty()) {
            return ResponseEntity.ok(cooperatives);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{user_id}/cooperatives/details")
    public ResponseEntity<List<Cooperative>> getCooperativesDetailsForUser(@PathVariable Long user_id) {
        List<Cooperative> cooperatives = cooperativeService.getCooperativesDetailsForUser(user_id);

        if (!cooperatives.isEmpty()) {
            return ResponseEntity.ok(cooperatives);
        } else {
            return ResponseEntity.notFound().build();
        }
    }











}