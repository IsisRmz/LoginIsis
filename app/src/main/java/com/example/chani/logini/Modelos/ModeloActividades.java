package com.example.chani.logini.Modelos;

/*
Modelo actividades
Clase objeto
Nos servirá para manejar los datos de la base de datos con los siguientes campos
 */
public class ModeloActividades {

    private String id;
    private String group;
    private String lecture;
    private String activity;
    //constructor para un objeto vacio
    public ModeloActividades(){

        //empty

    }
    //Constructor recibiendo todos los datos
    public ModeloActividades(String id, String group, String lecture, String activity){

        this.id=id;
        this.group=group;
        this.lecture=lecture;
        this.activity=activity;

    }
    //cosntructor recibiendo todos los datos menos el id
    public ModeloActividades(String group, String lecture, String activity){

        this.group=group;
        this.lecture=lecture;
        this.activity=activity;

    }
    /*
    Setters y getters para los campos
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
