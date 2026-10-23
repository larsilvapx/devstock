package br.com.devstock.devstock.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "notebook")
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patrimonio")
    private String patrimonio;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "processador")
    private String processador;

    @Column(name = "memoria_ram")
    private String memoria_ram;

    @Column(name = "armazenamento")
    private String armazenamento;

    @Column(name = "sistemaOperacional")
    private String sistemaOperacional;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "data_aquisicao")
    private LocalDate dataUltimaComunicacao;

    public Notebook(){

    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public String getMemoria_ram() {
        return memoria_ram;
    }

    public void setMemoria_ram(String memoria_ram) {
        this.memoria_ram = memoria_ram;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public void setArmazenamento(String armazenamento) {
        this.armazenamento = armazenamento;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataUltimaComunicacao() {
        return dataUltimaComunicacao;
    }

    public void setDataUltimaComunicacao(LocalDate dataUltimaComunicacao) {
        this.dataUltimaComunicacao = dataUltimaComunicacao;
    }
}
