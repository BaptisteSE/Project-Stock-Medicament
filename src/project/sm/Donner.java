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
public class Donner {
    private int idm;
    private int idservice;
    private int qtte;

    public Donner(int idm, int idservice, int qtte) {
        this.idm = idm;
        this.idservice = idservice;
        this.qtte = qtte;
    }

    public int getIdm() {
        return idm;
    }

    public void setIdm(int idm) {
        this.idm = idm;
    }

    public int getIdservice() {
        return idservice;
    }

    public void setIdservice(int idservice) {
        this.idservice = idservice;
    }

    public int getQtte() {
        return qtte;
    }

    public void setQtte(int qtte) {
        this.qtte = qtte;
    }

    @Override
    public String toString() {
        return "Donner{" + "idm=" + idm + ", idservice=" + idservice + ", qtte=" + qtte + '}';
    }
    
}
