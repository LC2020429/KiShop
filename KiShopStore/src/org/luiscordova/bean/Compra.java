package org.luiscordova.bean;

public class Compra {
    private int numeroDocumento;
    private String fechaDocumento;
    private String descripcion;
    private String totalDocumento;

    public Compra() {
    }

    public Compra(int numeroDocumento, String fechaDocumento, String descripcion, String totalDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.descripcion = descripcion;
        this.totalDocumento = totalDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(String totalDocumento) {
        this.totalDocumento = totalDocumento;
    }
    
    
}