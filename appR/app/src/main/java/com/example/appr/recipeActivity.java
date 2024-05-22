package com.example.appr;

public class recipeActivity {
    private int id;
    private int idUser;
    private String name;
    private int time;
    private String ingredients;
    private String instructions;
    private String categories;


    public recipeActivity(){    }
    public recipeActivity(int id, int user, String name, int time, String ingredients, String categories, String instructions) {
        this.id=id;
        this.idUser=user;
        this.name = name;
        this.time = time;
        this.ingredients = ingredients;
        this.categories = categories;
        this.instructions= instructions;
    }

    public recipeActivity(int user, String name, int time, String ingredients, String categories, String instructions) {
        this.idUser=user;
        this.name = name;
        this.time = time;
        this.ingredients = ingredients;
        this.categories = categories;
        this.instructions= instructions;
    }

    public recipeActivity(int user, int i, String string, String string1, int i1, String string2, String string3) {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) { this.id = id;  }

    public int getidUser() {
        return idUser;
    }

    public void setuser(int user) { this.idUser = user;  }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstru() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

}