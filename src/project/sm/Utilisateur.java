/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.sm;

/**
 *
 * @author WQ59
 */
public class Utilisateur {
    private int iduser;
    private String libelle;
    private String mdp;
    private String email;
    private int idfonction;
    private Integer idservice;

    public Utilisateur(int iduser, String libelle, String mdp, String email, int idfonction, Integer idservice) {
        this.iduser = iduser;
        this.libelle = libelle;
        this.mdp = mdp;
        this.email = email;
        this.idfonction = idfonction;
        this.idservice = idservice;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdfonction() {
        return idfonction;
    }

    public void setIdfonction(int idfonction) {
        this.idfonction = idfonction;
    }

    public Integer getIdservice() {
        return idservice;
    }

    public void setIdservice(Integer idservice) {
        this.idservice = idservice;
    }

    @Override
    public String toString() {
        return "Utilisateur n°" + iduser + " | nom : " + libelle + " | mdp : " + mdp + " | email : " + email + " | idfonction : " + idfonction + " | idservice : " + idservice;
    }
    

    
}
