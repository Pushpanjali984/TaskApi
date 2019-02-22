package com.andrtech.taskapi;

public class UserData
{
    private String image;

    private String city;

    private String phone;

    private String name;

    private String id;

    public UserData(String image, String city, String phone, String name, String id) {
        this.image = image;
        this.city = city;
        this.phone = phone;
        this.name = name;
        this.id = id;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", city = "+city+", phone = "+phone+", name = "+name+", id = "+id+"]";
    }
}
