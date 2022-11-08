package com.prodyna.movieapp.dto;

public class ActorDTO {

    private String firstName;
    private String lastName;

    private String biography;

    public ActorDTO(){

    }

    public ActorDTO(String firstName, String lastName,String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
