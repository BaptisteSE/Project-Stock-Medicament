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

import java.time.format.DateTimeFormatter;

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
            int nbcommand = uneDemande.getNbcommand();
            String values = Integer.toString(idm)
                    +",'"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    +"',"+Integer.toString(idService)
                    +","+Integer.toString(nbcommand);
            System.out.println(values);
            String requete = "INSERT INTO demande(idm, datedujour, idservice, nbcommand) VALUES("+values+")";
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(requete);
            valeur = true;
            
            boolean result = Passerelle.updateLeStockMedicament(idm, nbcommand);
            boolean result_service = Passerelle.ajouterDansLeStockService(idService, idm, nbcommand);
        }catch(Exception e){
            System.out.println("Erreur : "+e.getMessage());
            valeur = false;
        }
        
        return valeur;
    }
    
    public static boolean updateLeStockMedicament(int idm, int qtedde) throws SQLException{
        boolean valeur;
        try{
            int qttestock = Passerelle.getLeStockMedicament(idm);
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
    
    public static int getLeStockMedicament(int idm) throws SQLException{ 
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
    
    public static boolean checkLeStockMedicament(int idm, int qtedde) throws SQLException{
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
    
    public static int getLeStockService(int idservice, int idm) throws SQLException{ 
        int qttestockmedicament = 0;
        ArrayList<MedicamentService> desMedic = new ArrayList<>();
        String requete ="SELECT idm, idservice, qttestockmedicament FROM donner d WHERE d.idm="+idm+" and d.idservice="+idservice;
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        if (jeuResultat.next())
        {
            qttestockmedicament = jeuResultat.getInt("qttestockmedicament"); 
        }
        return qttestockmedicament;
    }
    
    public static boolean ajouterDansLeStockService(int idservice, int idm, int qtedde) throws SQLException{
        boolean valeur;
        try{
            int qttestock = Passerelle.getLeStockService(idservice, idm);
            int qttestock_new = qttestock + qtedde;
            String requete = "UPDATE donner SET qttestockmedicament="+qttestock_new+" WHERE idm="+idm+" and idservice="+idservice;
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(requete);
            valeur = true;
        }catch(Exception e){
            System.out.println("Erreur : "+e.getMessage());
            valeur = false;
        }
        
        return valeur;
    }
    
    public static ArrayList<MedicamentService> DonneLeStockDuService(int id) throws SQLException{
        ArrayList<MedicamentService> desMedic = new ArrayList<>();
        String requete ="SELECT m.idm, libelle, d.qttestockmedicament FROM medicament m JOIN Donner d ON d.idm = m.idm WHERE d.idservice='"+id+"' ORDER BY m.idm";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        while (jeuResultat.next())
        {
            desMedic.add(new MedicamentService(jeuResultat.getInt("idm"),jeuResultat.getString("libelle"),jeuResultat.getInt("qttestockmedicament"))); 
        }
        return desMedic;
    }
    public static boolean supprimerDansLeStockService(int idservice, int idm, int qtesupp) throws SQLException{
        boolean valeur = false;
        int qttestock = Passerelle.getLeStockService(idservice, idm);
        if (qttestock>=qtesupp){
            try{
                int qttestock_new = qttestock - qtesupp;
                String requete = "UPDATE donner SET qttestockmedicament="+qttestock_new+" WHERE idm="+idm+" and idservice="+idservice;
                Statement state = connexionBdd().createStatement();
                int nb = state.executeUpdate(requete);
                valeur = true;
            }catch(Exception e){
                System.out.println("Erreur : "+e.getMessage());
                valeur = false;
            }           
        }
        return valeur;
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
            unUtilisateur = new Utilisateur(jeuResultat.getInt("iduser"),jeuResultat.getString("libelle"),jeuResultat.getString("mdp"),jeuResultat.getString("email"),jeuResultat.getInt("idfonction"),jeuResultat.getInt("idservice")); 
        }
        return unUtilisateur;
    }
    public static ArrayList<Service> donnerServices() throws SQLException{
        ArrayList<Service> desServices = new ArrayList<>();
        String requete ="SELECT idservice, libelle, secteur FROM service";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        while (jeuResultat.next())
        {
            desServices.add(new Service(jeuResultat.getInt("idservice"),jeuResultat.getString("libelle"),jeuResultat.getString("secteur"))); 
        }
        return desServices;
    }
    public static ArrayList<Fonction> donnerFonction() throws SQLException{
        ArrayList<Fonction> desFonctions = new ArrayList<>();
        String requete ="SELECT * FROM fonction order by idfonction";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete); 
        while (jeuResultat.next())
        {
            desFonctions.add(new Fonction(jeuResultat.getInt("idfonction"),jeuResultat.getString("libelle"))); 
        }
        return desFonctions;
    }
    public static Utilisateur donneUserId(int unId) throws SQLException{
        
        Utilisateur unUtilisateur = null;
        
        String requete = "SELECT * FROM utilisateur WHERE iduser="+unId;
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete);
        
        while(jeuResultat.next()){
            unUtilisateur = new Utilisateur(jeuResultat.getInt("iduser"),jeuResultat.getString("libelle"),jeuResultat.getString("mdp"),jeuResultat.getString("email"),jeuResultat.getInt("idfonction"),jeuResultat.getInt("idservice")); 
        }
        
        return unUtilisateur;
    }    
    public static boolean verifUser(Utilisateur unUtilisateur) throws SQLException{
        boolean verif = false;
        String email = unUtilisateur.getEmail();
        //String libelle = unUtilisateur.getLibelle();
        String requete = "SELECT * FROM utilisateur WHERE email='"+email+"'";
        Statement state = connexionBdd().createStatement();
        ResultSet jeuResultat = state.executeQuery(requete);
        if(jeuResultat.next()){
             verif = true;
         }
        return verif;
    }
    // AJOUTER USER
    public static boolean ajouterUser(Utilisateur unUtilisateur) throws SQLException{
        boolean valeur;
        try{
            /**
            String values = unUtilisateur.getIduser()
                    +",'"+unUtilisateur.getLibelle()
                    +"','"+unUtilisateur.getMdp()
                    +"','"+unUtilisateur.getEmail()
                    +"',"+unUtilisateur.getIdfonction()
                    +","+unUtilisateur.getIdservice();
            System.out.println(values);
            **/
            String requete = "INSERT INTO utilisateur(iduser, libelle, mdp, email, idfonction, idservice) VALUES(?,?,?,?,?,?)";
            PreparedStatement state = connexionBdd().prepareStatement(requete);
            state.setInt(1,unUtilisateur.getIduser());  
            state.setString(2,unUtilisateur.getLibelle());  
            state.setString(3,unUtilisateur.getMdp());  
            state.setString(4,unUtilisateur.getEmail());  
            state.setInt(5,unUtilisateur.getIdfonction());  
            state.setInt(6,unUtilisateur.getIdservice()); 
            System.out.println(state.toString());
            state.executeUpdate();
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
            /**
            System.out.println("ajouterUserNullService");
            String values = unUtilisateur.getIduser()
                    +",'"+unUtilisateur.getLibelle()
                    +"','"+unUtilisateur.getMdp()
                    +"','"+unUtilisateur.getEmail()
                    +"',"+unUtilisateur.getIdfonction()+",null";
            System.out.println(values);
            **/
            String requete = "INSERT INTO utilisateur(iduser, libelle, mdp, email, idfonction, idservice) VALUES(?,?,?,?,?,null)";
            PreparedStatement state = connexionBdd().prepareStatement(requete);
            state.setInt(1,unUtilisateur.getIduser());  
            state.setString(2,unUtilisateur.getLibelle());  
            state.setString(3,unUtilisateur.getMdp());  
            state.setString(4,unUtilisateur.getEmail());  
            state.setInt(5,unUtilisateur.getIdfonction());  
            //state.setInt(6,unUtilisateur.getIdservice());  
            System.out.println(state.toString());
            state.executeUpdate();
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
            if(unUtilisateur.getIdfonction()==1){
                String requete = "UPDATE utilisateur SET libelle=?, mdp=?, email=?, idservice=null, idfonction=? WHERE iduser=?";
                System.out.println(requete);
                PreparedStatement state = connexionBdd().prepareStatement(requete);
                state.setString(1,unUtilisateur.getLibelle());  
                state.setString(2,unUtilisateur.getMdp());  
                state.setString(3,unUtilisateur.getEmail());  
                //state.setInt(4,unUtilisateur.getIdservice());  
                state.setInt(5,unUtilisateur.getIdfonction()); 
                state.setInt(6,unUtilisateur.getIduser()); 
                System.out.println(state.toString());
                state.executeUpdate();
            }else{
                String requete = "UPDATE utilisateur SET libelle=?, mdp=?, email=?, idservice=?, idfonction=? WHERE iduser=?";
                System.out.println(requete);
                PreparedStatement state = connexionBdd().prepareStatement(requete);
                state.setString(1,unUtilisateur.getLibelle());  
                state.setString(2,unUtilisateur.getMdp());  
                state.setString(3,unUtilisateur.getEmail());  
                state.setInt(4,unUtilisateur.getIdservice());  
                state.setInt(5,unUtilisateur.getIdfonction()); 
                state.setInt(6,unUtilisateur.getIduser()); 
                System.out.println(state.toString());
                state.executeUpdate();
            }
            
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
    public static boolean ajouterCommande(int idM,int qtte) throws SQLException{
        boolean verif = false;
        try{
            String prep1 = "insert into commande(idm,qtteCommande,dateCommande) values("+idM+","+qtte+",NOW())"; //ajout de la commande dans la table commande
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(prep1);
          
            String prep2 = ("SELECT qtteStock from Medicament WHERE idm ="+idM+" ");   
            ResultSet rs =state.executeQuery(prep2);
            rs.next();           
            int laQtte = rs.getInt(1); // recup le resultat
            laQtte=laQtte+qtte;
             
            
            String prep3 = ("UPDATE Medicament SET qtteStock = "+laQtte+" WHERE idM="+idM+""); //Ajout de la commande dans le stock pharmacie
            int nb2 = state.executeUpdate(prep3);           
            verif=true;
            
        }catch(SQLException e){
            verif=false;
            e.printStackTrace();
        }
        return(verif);
        
       
    }
    
    // envoie de la demande
    public static boolean envoyerMedicament(int idMedicament,int idService,int nbcommande) throws SQLException{
        boolean verif = false;
        try{           
            String prep1 = "insert into Demande(idM,idservice,nbcommand,dateDuJour) values("+idMedicament+","+idService+","+nbcommande+",NOW())"; //Ajout de la demande dans la table demande
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(prep1);   
            verif=true;
            }catch(SQLException e){
                    e.printStackTrace();
            }return(verif);
    }
    
    
// COMMANDES PHARMACIE FOURNISSEURS
    public static boolean ajouterCommandeF(int idCommandes,int qtte) throws SQLException{
        boolean verif = false;
        try{     
            String prep1 =("insert into (Commandes(idCommandes,qtteCommande,dateCommande) values("+idCommandes+","+qtte+",NOW())");
            Statement state = connexionBdd().createStatement();
            int nb = state.executeUpdate(prep1);
            
            String prep2 = ("SELECT qtteStock from Medicament where idm="+idM+"");
            ResultSet rs = state.executeQuery(prep2);
            rs.next();
            int laQtteStock = rs.getInt(1);
            laQtteStock=laQtteStock+qtte;
            
            String prep3 = ("UPDATE Medicament SET qtteStock = "+laQtteStock+"where idM="+idM+"");
            int nb2 = state.executeUpdate(prep3);
            verif=true;
                    
        }catch(SQLException e){
            e.printStackTrace();
        }return (verif);
        
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
