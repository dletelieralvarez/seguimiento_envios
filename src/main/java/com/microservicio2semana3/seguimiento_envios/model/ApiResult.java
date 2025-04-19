package com.microservicio2semana3.seguimiento_envios.model;
public class ApiResult<T> {
   
    private String mensaje;
    private T data;
    private int codigoEstado;

    public ApiResult(String mensaje, T data, int codigoEstado) {
        this.mensaje = mensaje;
        this.data = data;
        this.codigoEstado = codigoEstado;
    }

    // Getters and setters

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(int codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
}
