package com.trax.ems.elasticSearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trax.ems.elasticSearch.bean.MarketDepth;
import com.trax.ems.elasticSearch.bean.ProfileDocument;
import com.trax.ems.elasticSearch.service.ProfileService;

/*
 * Controller redirect as per action/uri
 */
@RestController
@RequestMapping
public class ProfileController {
    private ProfileService service;

    @Autowired
    public ProfileController(ProfileService service) {

        this.service = service;
    }

    @GetMapping("/{id}")
    public MarketDepth findById(@PathVariable String id) throws Exception {
        System.out.println("ProfileController findById id  " + id);
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity createProfile(@RequestBody MarketDepth document) throws Exception {
        System.out.println("ProfileController create Profile ProfileDocument  " + document);
        service.createProfileDocument(document);
        return new ResponseEntity<ProfileDocument>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateProfile(@RequestBody MarketDepth document) throws Exception {
        System.out.println("ProfileController updateProfile  " + document);
        return new ResponseEntity(service.updateProfile(document), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String deleteProfileDocument(@PathVariable String id) throws Exception {
        System.out.println("ProfileController deleteProfileDocument  " + id);
        return service.deleteProfileDocument(id);
    }

    @GetMapping("/all")
    public List<MarketDepth> findAll() throws Exception {
        System.out.println("ProfileController search all  --- ");
        return service.findAll();
    }

}
