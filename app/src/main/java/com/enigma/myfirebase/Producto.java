package com.enigma.myfirebase;

/**
 * Created by ramir on 5/19/2018.
 */

public class Producto {
    String nombre;
    String precio;
    String descripcion;
    String imagen;
    String imagentok;

    public Producto(){

    }

    public Producto(String nombre, String precio, String descripcion, String imagen, String imagentok) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.imagentok = imagentok;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagentok() {
        return imagentok;
    }

    public void setImagentok(String imagentok) {
        this.imagentok = imagentok;
    }
}
