package com.fgb.ventaya.Entity;

public class Publicacion {
    String title, image, description,precio, marca, modelo, telefono, tipo, idUsuario,estado;

    public Publicacion() {
    }

    public Publicacion(String title, String image, String description, String precio, String marca, String modelo, String telefono, String tipo,String idUsuario, String estado) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.precio = precio;
        this.marca = marca;
        this.modelo = modelo;
        this.telefono = telefono;
        this.tipo = tipo;
        this.idUsuario = idUsuario;
        this.estado = estado;
    }


    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTitle() {
        return title;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}

