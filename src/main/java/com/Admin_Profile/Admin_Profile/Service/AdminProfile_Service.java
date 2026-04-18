package com.Admin_Profile.Admin_Profile.Service;

import com.Admin_Profile.Admin_Profile.Entity.AdminProfile_Entity;
import com.Admin_Profile.Admin_Profile.Repository.AdminProfile_Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AdminProfile_Service {

    private final AdminProfile_Repository profileRepository;

    @Value("${app.upload.dir:uploads/profiles}")
    private String uploadDir;

    public AdminProfile_Service(AdminProfile_Repository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public AdminProfile_Entity saveProfile(String name, String email, String phone,
                                           String registerNo, String section,
                                           MultipartFile imageFile) throws IOException {

        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is required and cannot be empty");
        }

        // Create upload directory
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";

        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Save image to filesystem (good practice)
        Path filePath = uploadPath.resolve(uniqueFileName);
        imageFile.transferTo(filePath);

        // Create entity
        AdminProfile_Entity profile = new AdminProfile_Entity();
        profile.setName(name);
        profile.setEmail(email);
        profile.setPhone(phone);
        profile.setRegNo(registerNo);
        profile.setSection(section);

        // Store image as bytes in DB (as per your entity)
        profile.setImage(imageFile.getBytes());

        // Also store metadata (recommended)
        String imagePath = "/uploads/profiles/" + uniqueFileName;
        profile.setImageName(uniqueFileName);
        profile.setImagePath(imagePath);

        return profileRepository.save(profile);
    }
    
    public AdminProfile_Entity updateProfile(Long id, String name, String email, String phone,
            String registerNo, String section,
            MultipartFile newImageFile) throws IOException {

		// Fetch existing profile
		AdminProfile_Entity existingProfile = profileRepository.findById(id)
		.orElseThrow(() -> new IllegalArgumentException("Profile not found with id: " + id));
		
		// Update basic fields
		existingProfile.setName(name);
		existingProfile.setEmail(email);
		existingProfile.setPhone(phone);
		existingProfile.setRegNo(registerNo);
		existingProfile.setSection(section);
		
		// If user uploaded a new image, replace the old one
		if (newImageFile != null && !newImageFile.isEmpty()) {
			// Create upload directory if not exists
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			String fileExtension = getFileExtension(newImageFile.getOriginalFilename());
			String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
			
			// Save new image to filesystem
			Path filePath = uploadPath.resolve(uniqueFileName);
			newImageFile.transferTo(filePath);
			
			// Update image data in entity
			existingProfile.setImage(newImageFile.getBytes());
			existingProfile.setImageName(uniqueFileName);
			existingProfile.setImagePath("/uploads/profiles/" + uniqueFileName);
		}
		// If no new image is provided, keep the old image
		
		return profileRepository.save(existingProfile);
	}
		
    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty() || !filename.contains(".")) {
            return ".jpg";
        }

        String ext = filename.substring(filename.lastIndexOf(".")).toLowerCase().trim();

        switch (ext) {
            case ".jpg":
            case ".jpeg":
            case ".png":
                return ext;
            default:
                return ".jpg";
        }
    }
    
    //Get By Id
    public AdminProfile_Entity getProfileById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
    }

    //Get All
    public List<AdminProfile_Entity> getAllProfiles() {
        return profileRepository.findAll();
    }
}