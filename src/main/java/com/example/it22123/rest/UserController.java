package com.example.it22123.rest;


import com.example.it22123.entity.Cooperative;
import com.example.it22123.entity.User;
import com.example.it22123.entity.role;
import com.example.it22123.repository.RoleRepository;
import com.example.it22123.repository.UserRepository;
import com.example.it22123.service.CooperativeService;
import com.example.it22123.service.UserDetailsImpl;
import com.example.it22123.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private CooperativeService cooperativeService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;
    //OK
    @Secured("ROLE_ADMIN")
    @GetMapping("")
    public List<User> showUsers() {
        return userService.getUsers();
    }


    //OK

    @PostMapping("/new/add")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    //OK
    @Secured("ROLE_ADMIN")
    @PutMapping("{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }
    //OK
    @PostMapping("/new")
    public List<User> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        List<User> users = userService.getUsers();
        for (User a : users) {
            a.addUser(user);
        }
        return userService.getUsers();
    }

    //OK
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/admin/{user_id}")
    public List<User> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return userService.getUsers();
    }



    //OK
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }


























    // OK
    @Secured("ROLE_MODERATOR")
    @GetMapping("/employee/{user_id}/processed_applications")
    public List<Cooperative> getProcessedApplications(@PathVariable Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow();
        return user.getApplications();
    }

    // OK
    @Secured("ROLE_MODERATOR")
    @GetMapping("/employee/{user_id}/unprocessed_applications")
    public List<Cooperative> getUnprocessedApplications(@PathVariable Long user_id) {
        return cooperativeService.getUnprocessedApplications();
    }


    // OK with indicating that the "cooperative is checked"
    @Secured("ROLE_MODERATOR")
    @GetMapping("/employee/{user_id}/{cooperative_id}/check")
    public String checkApplication(@PathVariable Long user_id, @PathVariable Integer cooperative_id) {
        // You can return a message or a specific view for checking applications
        return "Check application for employee_id: " + user_id + ", cooperative_id: " + cooperative_id;
    }


    @Secured("ROLE_MODERATOR")
    @PostMapping("/employee/{user_id}/{cooperative_id}/check/submit")
    public List<Cooperative> submitApplicationCheck(
            @PathVariable Long user_id,
            @PathVariable Integer cooperative_id,
            @RequestBody Map<String, String> requestBody
    ) {
        String notes = requestBody.get("notes");
        cooperativeService.approveApplication(user_id, cooperative_id, notes);
        return cooperativeService.getProcessedApplications(user_id);
    }




    @PostMapping("/employee/{user_id}/{cooperative_id}/check/submit-reject")
    public List<Cooperative> submitApplicationCheckReject(
            @PathVariable Long user_id,
            @PathVariable Integer cooperative_id,
            @RequestBody Map<String, String> requestBody
    ) {
        String notes = requestBody.get("notes");
        cooperativeService.rejectApplication(user_id, cooperative_id, notes);
        return cooperativeService.getProcessedApplications(user_id);
    }



    //ok
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/role/delete/{user_id}/{role_id}")
    public ResponseEntity<?> deleteRoleFromUser(@PathVariable Long user_id, @PathVariable Integer role_id) {
        User user = userService.getUser(user_id);
        role role = roleRepository.findById(role_id).orElse(null);

        if (user == null || role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
        }

        user.getRoles().remove(role);
        userService.updateUser(user_id, user);

        return ResponseEntity.ok(user);  // Return the updated user as JSON
    }

    //ok
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/role/add/{user_id}/{role_id}")
    public ResponseEntity<?> addRoleToUser(@PathVariable Long user_id, @PathVariable Integer role_id) {
        User user = userService.getUser(user_id);
        role role = roleRepository.findById(role_id).orElse(null);

        if (user == null || role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found");
        }

        user.getRoles().add(role);
        userService.updateUser(user_id, user);

        return ResponseEntity.ok(user);  // Return the updated user as JSON
    }






    ////11.1


    @GetMapping("/{userId}/roles")
    public ResponseEntity<Set<Integer>> getUserRoleIds(@PathVariable Long userId) {
        Set<Integer> roleIds = userService.getRoleIdsByUserId(userId);
        return ResponseEntity.ok(roleIds);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Integer>> getUserRoleIds(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        Set<Integer> roleIds = userService.getRoleIdsByUserId(userId);
        return ResponseEntity.ok(roleIds);
    }
}






















/*
ignore
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user_registration";
    }







    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, Model model){
        System.out.println("Roles: "+user.getRoles());
        Integer id = userService.saveUser(user);
        String message = "User '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "home";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Long user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "edit_user";
    }

    @PostMapping("/user/{user_id}")
    public String saveFarmer(@PathVariable Long user_id, @ModelAttribute("user") User user, Model model) {
        User the_user = (User) userService.getUser(user_id);
        the_user.setEmail(user.getEmail());
        the_user.setUsername(user.getUsername());
        userService.saveUser(the_user);
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        role role = roleRepository.findById(role_id).get();
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Long user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        role role = roleRepository.findById(role_id).get();
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";

    }
    */