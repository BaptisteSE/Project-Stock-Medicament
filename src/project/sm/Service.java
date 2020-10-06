/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.sm;

/**
 *
 * @author sio2020
 */
public class Service {
    private int idservice;
    private String libelle;
    private String secteur;

    public Service(int idservice, String libelle, String secteur) {
        this.idservice = idservice;
        this.libelle = libelle;
        this.secteur = secteur;
    }

    public int getIdservice() {
        return idservice;
    }

    public void setIdservice(int idservice) {
        this.idservice = idservice;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    @Override
    public String toString() {
        return "Service{" + "idservice=" + idservice + ", libelle=" + libelle + ", secteur=" + secteur + '}';
    }
    
    
}
