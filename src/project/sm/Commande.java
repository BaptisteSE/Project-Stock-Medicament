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
public class Commande {
    private ArrayList<Commande> lesCommandes = new ArrayList();
    private int idCommande;
    private Date dateCommande;
    private int qtteCommande;

    public Commande(int pIdCommande,Date pDateCommande, int pQtteCommande) {
        idCommande = pIdCommande;
        dateCommande = pDateCommande;
        qtteCommande = pQtteCommande;
   
    }    
    
    public void ajouterCommande(Commande uneCommande){
        lesCommandes.add(uneCommande);
    }
        
}
    
