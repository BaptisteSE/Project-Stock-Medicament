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
public class Fonction {
    private int idFonction;
    private String libelle;

    public Fonction(int idFonction, String libelle) {
        this.idFonction = idFonction;
        this.libelle = libelle;
    }

    public int getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(int idFonction) {
        this.idFonction = idFonction;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "idFonction=" + idFonction + ", libelle=" + libelle + '/';
    }
    
    
}
