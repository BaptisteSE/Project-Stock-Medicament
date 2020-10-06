/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.sm;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author sio2020
 */
public class Demande {
    private int idd;
    private Date datedujour;
    private int nbcommand;
    private ArrayList<Medicament> DemandeS;
    private ArrayList<Medicament> EnvoiM;

    public Demande(int idd, Date datedujour, int nbcommand) {
        this.idd = idd;
        this.datedujour = datedujour;
        this.nbcommand = nbcommand;
        DemandeS = new ArrayList<Medicament>();
        EnvoiM = new ArrayList<Medicament>();
    }
    public void ajouterMedicament(Medicament unMedicament){
        DemandeS.add(unMedicament);
    }
    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public Date getDatedujour() {
        return datedujour;
    }

    public void setDatedujour(Date datedujour) {
        this.datedujour = datedujour;
    }

    public int getNbcommand() {
        return nbcommand;
    }

    public void setNbcommand(int nbcommand) {
        this.nbcommand = nbcommand;
    }

    public ArrayList<Medicament> getDemandeS() {
        return DemandeS;
    }

    public void setDemandeS(ArrayList<Medicament> DemandeS) {
        this.DemandeS = DemandeS;
    }

    public ArrayList<Medicament> getEnvoiM() {
        return EnvoiM;
    }

    public void setEnvoiM(ArrayList<Medicament> EnvoiM) {
        this.EnvoiM = EnvoiM;
    }

    @Override
    public String toString() {
        return "Demande{" + "idd=" + idd + ", datedujour=" + datedujour + ", nbcommand=" + nbcommand + ", DemandeS=" + DemandeS + ", EnvoiM=" + EnvoiM + '}';
    }
    
    
}
