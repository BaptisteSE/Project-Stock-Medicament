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
public class MedicamentService {
    private int idm;
    private String libelle;
    private int qttestock;

    public MedicamentService(int idm, String libelle, int qttestock) {
        this.idm = idm;
        this.libelle = libelle;
        this.qttestock = qttestock;
    }

    public int getIdm() {
        return idm;
    }

    public void setIdm(int idm) {
        this.idm = idm;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    

    

    public int getQttestock() {
        return qttestock;
    }

    public void setQttestock(int qttestock) {
        this.qttestock = qttestock;
    }

    @Override
    public String toString() {
        return "Medicament{" + "idm=" + idm + ", libelle=" + libelle + ", qttestock=" + qttestock + '}';
    }
    
    
}
