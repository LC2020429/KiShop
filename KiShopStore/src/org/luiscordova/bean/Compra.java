package org.luiscordova.bean;

public class Compra {
    private int numeroDocumento;
    private String fechaDocumento;
    private String descripcion;
    private double totalDocumento;

    public Compra() {
    }

    public Compra(int numeroDocumento, String fechaDocumento, String descripcion, double totalDocumento) {
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

    public double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }

    @Override
    public String toString() {
        return "Compra: " + getNumeroDocumento() + ", fecha:  " + getFechaDocumento() +  "   " +  getDescripcion() + ", total " + getTotalDocumento() + '}';
    }

    
    
}
