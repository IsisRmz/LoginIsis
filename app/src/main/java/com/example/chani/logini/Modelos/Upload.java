package com.example.chani.logini.Modelos;

/*
Clase modelo Upload
Usada para subir y descargar imagenes
 */
public class Upload {

    private String name;
    private String imageUrl;

    public Upload() {
    }
    /*
    Constructor con los datos name y imageUrl
     */
    public Upload(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    /*
    setters y getters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
