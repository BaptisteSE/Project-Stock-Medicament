/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.sm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sio2020
 */
public class SessionInfirmier extends javax.swing.JFrame {

    static private Utilisateur _user; 
    
    /**
     * Creates new form SessionInfirmier
     */
    public SessionInfirmier(Utilisateur user) throws SQLException {
        initComponents();
        _user = user;
        System.out.println(_user.getIdservice());
        ArrayList<Service> desServices = new ArrayList<Service>();
        desServices = Passerelle.donnerServices();
        int idService = _user.getIdservice();
        String nomService = "";
        //String id_str = Integer.toString(_user.getIdservice());
        for(Service unService : desServices){
            if(unService.getIdservice()==_user.getIdservice()){
                nomService = unService.getLibelle();
            }
        }
        idservice.setText(nomService);
        idservice.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAfficherStock = new javax.swing.JButton();
        javax.swing.JButton btnFaireDemande = new javax.swing.JButton();
        btnFaireSupp = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        idservice = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAfficherStock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAfficherStock.setText("Afficher le stock du service");
        btnAfficherStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAfficherStockMouseClicked(evt);
            }
        });
        btnAfficherStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAfficherStockActionPerformed(evt);
            }
        });

        btnFaireDemande.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFaireDemande.setText("Faire une demande");
        btnFaireDemande.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFaireDemandeMouseClicked(evt);
            }
        });
        btnFaireDemande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaireDemandeActionPerformed(evt);
            }
        });

        btnFaireSupp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnFaireSupp.setText("Supprimer une quantité du stock");
        btnFaireSupp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFaireSuppMouseClicked(evt);
            }
        });
        btnFaireSupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaireSuppActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Service :");

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setText("SE DECONNECTER");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        idservice.setBackground(new java.awt.Color(0, 0, 0));
        idservice.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        idservice.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(362, 362, 362)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(idservice, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(idservice, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(252, 393, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFaireDemande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAfficherStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFaireSupp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(394, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(btnAfficherStock, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnFaireDemande, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnFaireSupp, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAfficherStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAfficherStockActionPerformed
        try {
            new AfficherStockService(_user.getIdservice()).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(SessionInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAfficherStockActionPerformed

    private void btnFaireDemandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFaireDemandeActionPerformed
        try {
            new DemandeService(_user.getIdservice()).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(SessionInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnFaireDemandeActionPerformed

    private void btnAfficherStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAfficherStockMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAfficherStockMouseClicked

    private void btnFaireDemandeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFaireDemandeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFaireDemandeMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
       dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnFaireSuppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFaireSuppMouseClicked
        try {
            new SupprimerStockService(_user.getIdservice()).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(SessionInfirmier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnFaireSuppMouseClicked

    private void btnFaireSuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFaireSuppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFaireSuppActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SessionInfirmier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SessionInfirmier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SessionInfirmier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SessionInfirmier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new SessionInfirmier(_user).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(SessionInfirmier.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfficherStock;
    private javax.swing.JButton btnFaireSupp;
    private javax.swing.JLabel idservice;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
