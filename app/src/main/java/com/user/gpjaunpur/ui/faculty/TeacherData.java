package com.user.gpjaunpur.ui.faculty;

public class TeacherData {
    private String name, post, image, joining,email, key;

    public TeacherData() {

    }

    public TeacherData(String name, String post, String image, String joining, String email, String key) {
        this.name = name;
        this.post = post;
        this.image = image;
        this.joining = joining;
        this.email = email;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJoining() {
        return joining;
    }

    public void setJoining(String joining) {
        this.joining = joining;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
