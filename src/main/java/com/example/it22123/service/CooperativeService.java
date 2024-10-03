package com.example.it22123.service;

import com.example.it22123.entity.Cooperative;
import com.example.it22123.entity.Status;
import com.example.it22123.entity.User;
import com.example.it22123.repository.CooperativeRepository;
import com.example.it22123.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CooperativeService {

    @Autowired
    private CooperativeRepository cooperativeRepository;

    @Autowired
    UserRepository userRepository;


    //@Secured("ROLE_USER")
    @Transactional
    public List<Cooperative> getCooperatives() {
        return cooperativeRepository.findAll();
    }


    @Transactional
    public void saveCooperative(Cooperative cooperative) {
        cooperativeRepository.save(cooperative);

    }


    @Transactional
    public void deleteCooperative(Integer cooperativeId) {
        cooperativeRepository.deleteById(cooperativeId);
    }

    @Transactional
    public Cooperative getCooperative(Integer cooperativeId) {
        return cooperativeRepository.findById(cooperativeId).get();
    }


    /*
        @Transactional
        public Cooperative getUserCooperative(Long userId) {
            User user = userRepository.findById(userId).get();
            return user.getCooperative();
        }
    */
    @Transactional
    public Cooperative getUserCooperative(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getCooperative();
    }


    @Transactional
    public List<Cooperative> getProcessedApplications(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getApplications();
    }


    @Transactional
    public List<Cooperative> getUnprocessedApplications() {
        List<Cooperative> cooperatives = cooperativeRepository.findAll();
        cooperatives.removeIf(cooperative -> cooperative.getEstatus() != Status.PROCESSING);
        return cooperatives;
    }







    /*
    @Transactional
    public void approveApplication(Integer cooperativeId, Long userId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        cooperative.setUser(user);
        cooperative.setNotes(notes);
        cooperative.setEstatus(Status.APPROVED);
        cooperativeRepository.save(cooperative);
        user.approveApplication(cooperative);
        userRepository.save(user);
    }























    @Transactional
    public void rejectApplication(Integer cooperativeId, Long userId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        cooperative.setUser(user);
        cooperative.setNotes(notes);
        cooperative.setEstatus(Status.REJECTED);
        cooperativeRepository.save(cooperative);
        user.rejectApplication(cooperative);
        userRepository.save(user);
    }
}
*/


    @Transactional
    public void approveApplication(Long userId, Integer cooperativeId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        // Set notes and change status without updating user_id
        cooperative.setNotes(notes);
        cooperative.setEstatus(Status.APPROVED);


        cooperativeRepository.save(cooperative);
        user.approveApplication(cooperative);

    }






    /*

    @Transactional
    public void approveApplication(Long userId, Integer cooperativeId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();

        // Set notes and change status without updating user_id
        cooperative.setNotes(notes);
        cooperative.setEstatus(Status.APPROVED);


        cooperativeRepository.save(cooperative);

    }
  */

    @Transactional
    public void rejectApplication(Long userId, Integer cooperativeId, String notes) {
        Cooperative cooperative = cooperativeRepository.findById(cooperativeId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        // Set notes and change status without updating user_id
        cooperative.setNotes(notes);
        cooperative.setEstatus(Status.REJECTED);


        cooperativeRepository.save(cooperative);
        user.rejectApplication(cooperative);
    }




















//
    @Transactional
    public List<Cooperative> getCooperativesForUser(Long userId) {

        return cooperativeRepository.findByUserId(userId);
    }


    @Transactional
    public List<Cooperative> getCooperativesDetailsForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getApplications();
    }



}
