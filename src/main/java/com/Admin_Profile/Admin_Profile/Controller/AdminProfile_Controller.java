package com.Admin_Profile.Admin_Profile.Controller;

import com.Admin_Profile.Admin_Profile.Entity.AdminProfile_Entity;
import com.Admin_Profile.Admin_Profile.Service.AdminProfile_Service;

import jakarta.persistence.criteria.Path;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/Admin/Admin_Profile")   // Add base path - good practice
@CrossOrigin(origins = "*")       // For development (change in production)
public class AdminProfile_Controller {

    private final AdminProfile_Service profileService;

    public AdminProfile_Controller(AdminProfile_Service profileService) {
        this.profileService = profileService;
    }
    
    
    @PostMapping("/AdminProfile")
    public ResponseEntity<AdminProfile_Entity> saveProfile(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("registerNo") String registerNo,
            @RequestParam("section") String section,
            @RequestParam("image") MultipartFile image) {

        try {
            AdminProfile_Entity savedProfile = profileService.saveProfile(
                    name, email, phone, registerNo, section, image);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
        } 
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AdminProfile_Entity> getProfileById(@PathVariable Long id){
    	try {
    		AdminProfile_Entity profile = profileService.getProfileById(id);
    		return ResponseEntity.ok(profile);
    	} catch(RuntimeException e) {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @GetMapping(value = "/image/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String fileName) {
        try {
            // Use the same uploadDir as in service
            java.nio.file.Path imagePath = Paths.get("uploads/profiles").resolve(fileName);

            if (!Files.exists(imagePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] imageBytes = Files.readAllBytes(imagePath);
            return ResponseEntity.ok()
                    .contentType(Files.probeContentType(imagePath) != null 
                        ? MediaType.parseMediaType(Files.probeContentType(imagePath)) 
                        : MediaType.IMAGE_JPEG)
                    .body(imageBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdminProfile_Entity> updateProfile(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("registerNo") String registerNo,
            @RequestParam("section") String section,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            AdminProfile_Entity updatedProfile = profileService.updateProfile(
                    id, name, email, phone, registerNo, section, image);

            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}