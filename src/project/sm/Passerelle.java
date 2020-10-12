package project.sm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Passerelle {

    
    
    public Passerelle() {
    }

    // CONNECTION A LA BASE DE DONNEES
    public static Connection connexionBdd() throws SQLException {
        /**
        String url = "jdbc:postgresql://192.168.1.245:5432/slam2021_stockmedicaments_seret";
        String user = "seret";
        String passwd = "seret";
        **/
        String url = "jdbc:postgresql://127.0.0.1:5432/slam2021_stockmedicaments_seret";
        String user = "postgres";
        String passwd = "root";
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
            desUser.add(new Utilisateur(jeuResultat.getInt("iduser"),jeuResultat.getString("libelle"),jeuResultat.getString("mdp"),jeuResultat.getString("email"),jeuResultat.getInt("idfonction"),jeuResultat.getInt("idservice"))); 
        }
        return desUser;
    }
    public static ArrayList<Medicament> donnerTousLesMedicaments() throws SQLException {    
        String requete = "select * from Medicament order by idm";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete);
        ArrayList<Medicament> desMedoc = new ArrayList<>();
        while (jeuResultat.next()) {
            desMedoc.add(new Medicament(jeuResultat.getInt("idm"),jeuResultat.getString("libelle"),jeuResultat.getInt("prixm"),jeuResultat.getInt("qttestock"))); 
        }
        return desMedoc;
    }
    
    public static boolean creerDemande(Demande uneDemande,int idService) throws SQLException{
        boolean valeur;
        try{
            int idm = uneDemande.getDemandeS().get(0).getIdm(); 
            String values = Integer.toString(idm)
                    +",'"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    +"',"+Integer.toString(idService)
                    +","+Integer.toString(uneDemande.getNbcommand());
            System.out.println(values);
            String requete = "INSERT INTO demande(idm, datedujour, idservice, nbcommand) VALUES("+values+")";
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(requete);
            valeur = true;
            
            boolean result = Passerelle.UpdateLeStockMedicament(idm, uneDemande.getNbcommand());
        }catch(Exception e){
            System.out.println("Erreur : "+e.getMessage());
            valeur = false;
        }
        
        return valeur;
    }
    
    public static boolean UpdateLeStockMedicament(int idm, int qtedde) throws SQLException{
        boolean valeur;
        try{
            int qttestock = Passerelle.GetLeStockMedicament(idm);
            int qttestock_new = qttestock - qtedde;
            String requete = "UPDATE medicament SET qttestock="+qttestock_new+" WHERE idm="+idm;
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(requete);
            valeur = true;
        }catch(Exception e){
            System.out.println("Erreur : "+e.getMessage());
            valeur = false;
        }
        
        return valeur;
    }
    
    public static int GetLeStockMedicament(int idm) throws SQLException{ 
        int qttestock = 0;
        ArrayList<MedicamentService> desMedic = new ArrayList<>();
        String requete ="SELECT m.idm, libelle, m.qttestock FROM medicament m WHERE m.idm='"+idm+"'";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        if (jeuResultat.next())
        {
            qttestock = jeuResultat.getInt("qttestock"); 
        }
        return qttestock;
    }
    
    public static boolean CheckLeStockMedicament(int idm, int qtedde) throws SQLException{
        boolean result = false;
        int qttestock = 0;
        ArrayList<MedicamentService> desMedic = new ArrayList<>();
        String requete ="SELECT m.idm, libelle, m.qttestock FROM medicament m WHERE m.idm='"+idm+"'";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        if (jeuResultat.next())
        {
            qttestock = jeuResultat.getInt("qttestock");
            if (qttestock >= qtedde) {
                result = true;
            }
        }
        return result;
    }
    
    public static ArrayList<MedicamentService> DonneLeStockDuService(int id) throws SQLException{
        ArrayList<MedicamentService> desMedic = new ArrayList<>();
        String requete ="SELECT m.idm, libelle, d.qtteStockMedicament FROM medicament m JOIN Donner d ON d.idm = m.idm WHERE d.idservice='"+id+"'";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        while (jeuResultat.next())
        {
            desMedic.add(new MedicamentService(jeuResultat.getInt("idm"),jeuResultat.getString("libelle"),jeuResultat.getInt("qtteStockMedicament"))); 
        }
        return desMedic;
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
        String requete ="SELECT email,mdp,iduser,libelle,idfonction,idservice FROM utilisateur WHERE email='"+email+"'";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        if (jeuResultat.next())
        {
            unUtilisateur = new Utilisateur(jeuResultat.getInt("iduser"),jeuResultat.getString("libelle"),jeuResultat.getString("email"),jeuResultat.getString("mdp"),jeuResultat.getInt("idfonction"),jeuResultat.getInt("idservice")); 
        }
        return unUtilisateur;
    }
    public static Utilisateur donneUserId(int unId) throws SQLException{
        
        Utilisateur unUtilisateur = null;
        
        String requete = "SELECT * FROM utilisateur WHERE iduser="+unId;
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete);
        
        while(jeuResultat.next()){
            unUtilisateur = new Utilisateur(jeuResultat.getInt("iduser"),jeuResultat.getString("libelle"),jeuResultat.getString("email"),jeuResultat.getString("mdp"),jeuResultat.getInt("idfonction"),jeuResultat.getInt("idservice")); 
        }
        
        return unUtilisateur;
    }    
    // AJOUTER USER
    public static boolean ajouterUser(Utilisateur unUtilisateur) throws SQLException{
        boolean valeur;
        try{
            String values = unUtilisateur.getIduser()
                    +",'"+unUtilisateur.getLibelle()
                    +"','"+unUtilisateur.getMdp()
                    +"','"+unUtilisateur.getEmail()
                    +"',"+unUtilisateur.getIdfonction()
                    +","+unUtilisateur.getIdservice();
            System.out.println(values);
            String requete = "INSERT INTO utilisateur(iduser, libelle, mdp, email, idfonction, idservice) VALUES("+values+")";
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(requete);
            valeur = true;
        }catch(Exception e){
            System.out.println("Erreur : "+e.getMessage());
            valeur = false;
        }
        
        return valeur;
    }
    // AJOUTER USER SERVICE NULL
    public static boolean ajouterUserNullService(Utilisateur unUtilisateur) throws SQLException{
        boolean valeur;
        try{
            System.out.println("ajouterUserNullService");
            String values = unUtilisateur.getIduser()
                    +",'"+unUtilisateur.getLibelle()
                    +"','"+unUtilisateur.getMdp()
                    +"','"+unUtilisateur.getEmail()
                    +"',"+unUtilisateur.getIdfonction()+",null";
            System.out.println(values);
            String requete = "INSERT INTO utilisateur(iduser, libelle, mdp, email, idfonction, idservice) VALUES("+values+")";
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
    
    
    /**
    public static String donneLibelleFonction(int unId) throws SQLException{
        
        String requete = "SELECT libelle FROM fonction WHERE idfonction="+unId;
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete);
        
        while(jeuResultat.next()){
            
        }0
        
        return ;
    }
    **/

    
}
