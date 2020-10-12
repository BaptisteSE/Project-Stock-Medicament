package project.sm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Passerelle {
Date date = new Date();
SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
Date uneDate = new Date();
java.sql.Date laDate = new java.sql.Date(uneDate.getTime());
    
    
    public Passerelle() {
    }

    // CONNECTION A LA BASE DE DONNEES
    public static Connection connexionBdd() throws SQLException {
        String url = "jdbc:postgresql://192.168.1.245:5432/slam2021_stockmedicaments_seret";
        String user = "seret";
        String passwd = "seret";
        Connection conn = (Connection) DriverManager.getConnection(url, user, passwd);
        Statement state = conn.createStatement();
        return conn;
    }
    public static ArrayList<Utilisateur> donnerTousLesUser() throws SQLException {    
        String requete = "select * from Utilisateur order by iduser";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete);
        ArrayList<Utilisateur> desUser = new ArrayList<>();
        while (jeuResultat.next()) {
            desUser.add(new Utilisateur(jeuResultat.getInt("iduser"),jeuResultat.getString("libelle"),jeuResultat.getString("mdp"),jeuResultat.getString("email"),jeuResultat.getInt("idfonction")));
        }
        return desUser;
    }
    public static Demande DonneLesDemandesParIdService(int id) throws SQLException{
        Demande uneDemande =null;
        String requete ="SELECT idd,datedujour,nbcommand,idservice,idm FROM demande WHERE idservice='"+id+"'";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        if (jeuResultat.next())
        {
            uneDemande = new Demande(jeuResultat.getInt("idd"),jeuResultat.getDate("datedujour"),jeuResultat.getInt("nbcommand"),jeuResultat.getString("idservice"),jeuResultat.getInt("idm")); 
        }
        return uneDemande;
    }
    public static boolean ConnexionUser(String email,String pwd) throws SQLException{
        boolean check = false;
            Utilisateur unUtilisateur = null;
            String requete ="SELECT email,mdp,iduser,libelle,idfonction FROM utilisateur WHERE email='"+email+"' AND mdp='"+pwd+"'";
            Statement state = connexionBdd().createStatement();
            ResultSet jeuResultat = state.executeQuery(requete); 
            if (jeuResultat.next())
            {           
                check = true;
                //System.out.println("email : " + jeuResultat.getString("email"));
                //System.out.println("mdp : " + jeuResultat.getString("mdp"));
                //System.out.println("Connexion établie !");
                donnerUser(email);
            }else{
                //System.out.println("Connexion impossible !");
            }              
    return check;  
    }
    public static Utilisateur donnerUser(String email) throws SQLException{
        Utilisateur unUtilisateur =null;
        String requete ="SELECT email,mdp,iduser,libelle,idfonction FROM utilisateur WHERE email='"+email+"'";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        if (jeuResultat.next())
        {
            unUtilisateur = new Utilisateur(jeuResultat.getInt("iduser"),jeuResultat.getString("libelle"),jeuResultat.getString("email"),jeuResultat.getString("mdp"),jeuResultat.getInt("idfonction")); 
        }
        return unUtilisateur;
    }
    public static Utilisateur donneUserId(int unId) throws SQLException{
        
        Utilisateur unUtilisateur = null;
        
        String requete = "SELECT * FROM utilisateur WHERE iduser="+unId;
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete);
        
        while(jeuResultat.next()){
            unUtilisateur = new Utilisateur(jeuResultat.getInt("iduser"),jeuResultat.getString("libelle"),jeuResultat.getString("email"),jeuResultat.getString("mdp"),jeuResultat.getInt("idfonction")); 
        }
        
        return unUtilisateur;
    }    
    // AJOUTER USER
    public static boolean ajouterUser(Utilisateur unUtilisateur) throws SQLException{
        boolean valeur;
        try{
            String requete = "INSERT INTO utilisateur(iduser, libelle, mdp, email, idfonction) VALUES("+unUtilisateur.getIduser()+",'"+unUtilisateur.getLibelle()+"','"+unUtilisateur.getMdp()+"','"+unUtilisateur.getEmail()+"',"+unUtilisateur.getIdfonction()+")";
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(requete);
            valeur = true;
        }catch(Exception e){
            System.out.println("Erreur : "+e.getMessage());
            valeur = false;
        }
        
        return valeur;
    }
    // MODIFIER USER
    public static boolean modifierUser(Utilisateur unUtilisateur) throws SQLException{
        boolean valeur;
        try{
            String requete = "UPDATE utilisateur SET libelle='"+unUtilisateur.getLibelle()+"', email='"+unUtilisateur.getEmail()+"', mdp='"+unUtilisateur.getMdp()+"', idfonction="+unUtilisateur.getIdfonction()+" WHERE id="+unUtilisateur.getIduser();
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(requete);
            valeur = true;
        }catch(Exception e){
            System.out.println("Erreur : "+e.getMessage());
            valeur = false;
        }
        
        return valeur;
    }
    // SUPPRIMER USER
    public static boolean supprimerUser(Utilisateur unUtilisateur) throws SQLException{
        boolean valeur;
        try{
            String requete = "DELETE FROM utilisateur WHERE iduser="+unUtilisateur.getIduser();
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(requete);
            valeur=true;
        }catch(Exception e){
            System.out.println("Erreur : "+e.getMessage());
            valeur = false;
        }
        
        return valeur;
    }
    
    // COMMANDES PHARMACIE FOURNISSEURS
    public void ajouterCommandeF(int idCommandes,int qtte) throws SQLException{
        Commande uneCommande = new Commande(idCommandes,laDate,qtte);// creation d'un objet commande
        lesCommandes.add(uneCommande);
        try{
            Statement stmt = connexionBdd().createStatement();
            PreparedStatement prep1 = connexionBdd().prepareStatement("insert into Commandes(idCommande,qtteCommande,dateCommande) values(id,qtte,NOW())"); //ajout de la commande dans la table commande
            prep1.setInt(1,idCommandes);
            prep1.setInt(2,qtte);
            prep1.executeUpdate();
            
            Statement prep2 = connexionBdd().createStatement();
            ResultSet res = stmt.executeQuery("SELECT qtteStock from Medicaments WHERE idM ='idCommandes' ");
            int laQtte = res.getInt(1); // recup le resultat
            laQtte=laQtte+qtte;
            
            PreparedStatement prep3 = connexionBdd().prepareStatement("UPDATE Medicament SET qtteStock = 'res' WHERE idM='idCommandes'"); //Ajout de la commande dans le stock pharmacie
            prep3.setInt(1,laQtte);
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    
    // envoie de la demande
    public void envoyerMedicament(int idMedicament,int nbCommande) throws SQLException{
        try{
            Statement stmt = connexionBdd().createStatement();
            PreparedStatement prep = connexionBdd().prepareStatement("insert into Demande(idD,nbCommande,dateDuJour) values(id,nbC,NOW())"); //Ajout de la demande dans la table demande
            prep.setInt(1,idMedicament);
            prep.setInt(2, nbCommande);
            prep.executeUpdate();
            }catch(SQLException e){
                    e.printStackTrace();
            }
    }
    
    
    /**
    public static String donneLibelleFonction(int unId) throws SQLException{
        
        String requete = "SELECT libelle FROM fonction WHERE idfonction="+unId;
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete);
        
        while(jeuResultat.next()){
            
        }
        
        return ;
    }
    **/

    
}
