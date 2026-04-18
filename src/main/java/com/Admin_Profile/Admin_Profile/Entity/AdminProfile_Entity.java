package com.Admin_Profile.Admin_Profile.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin_Profile")
public class AdminProfile_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "register_number", nullable = false, unique = true)
    private String regNo;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "section", nullable = false)
    private String section;

    // Store image as bytes in database (as per your current entity)
    @Lob
    @Column(name = "image", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] image;

    // New fields - Recommended for better image handling
    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_path")
    private String imagePath;   // e.g., "/uploads/profiles/xxx.jpg"

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}