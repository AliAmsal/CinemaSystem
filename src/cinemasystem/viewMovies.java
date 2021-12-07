/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemasystem;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import oracle.net.aso.f;

/**
 *
 * @author Dell
 */
public class viewMovies extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    public viewMovies() throws SQLException, ClassNotFoundException {
        initComponents();
        table.getTableHeader().setBackground(Color.orange);
        table.getTableHeader().setForeground(Color.black);
        Font font = new Font("Candara", Font.BOLD, 18);
        table.getTableHeader().setFont(font);
        table.getTableHeader().setSize(40, 40);
        
        tableUpdate();
 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("View Movies");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Candara", 3, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("MOVIE SHOWS");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 310, -1));

        jSeparator1.setBackground(new java.awt.Color(255, 153, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 338, 10));

        table.setBackground(new java.awt.Color(0, 0, 0));
        table.setFont(new java.awt.Font("Agency FB", 1, 20)); // NOI18N
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "SHOW ID", "MOVIE TITLE", "GENRE", "RATING", "DURATION", "DATE", "SHOW TIME", "HALL CATEGORY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setOpaque(false);
        table.setRowHeight(25);
        table.setSelectionBackground(new java.awt.Color(255, 153, 0));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 1110, 430));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 385, -1, -1));

        backButton.setBackground(new java.awt.Color(255, 0, 0));
        backButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\newac\\Downloads\\previous (2) (1).png")); // NOI18N
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        getContentPane().add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 70, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cinemasystem/movies.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        DefaultTableModel model = (DefaultTableModel)table.getModel();
      
        int index = table.getSelectedRow();
        
        
    }//GEN-LAST:event_tableMouseClicked

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        new Home().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_backButtonActionPerformed

    public void tableUpdate() throws SQLException, ClassNotFoundException
    {
        
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb", "cinema", "cinema");
            pst = con.prepareStatement("select * from movies order by show_id");
            rs = pst.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            DefaultTableModel dft = (DefaultTableModel)table.getModel();
            dft.setRowCount(0);
            while(rs.next())
            {
                Object[] array = new Object[8];
                
                    array[0] = rs.getInt("show_ID");
                    array[1] = rs.getString("title");
                    array[2] = rs.getString("genre");                   
                    array[3] = rs.getString("rating");
                    
                    //Object[] duration = rs.getString("duration").split(" ");
                    array[4] = rs.getString("hours") + " hours " + rs.getString("minutes") + " mins";
                    //Object[] date = rs.getString("datee").split(" ");
                    array[5] = rs.getInt("day") + "-" + rs.getString("month") + "-2021";
                    array[6] = rs.getString("time");
                    array[7] = rs.getString("hall_category");
               
                dft.addRow(array);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(customerDetails.class.getName()).log(Level.SEVERE, null, ex);
        }

 
        /*try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            con = DriverManager.getConnection("jdbc:ucanaccess://Movies.accdb");
            pst = con.prepareStatement("select * from movies");
            rs = pst.executeQuery();
            
            ResultSetMetaData rsd = rs.getMetaData();
            DefaultTableModel dft = (DefaultTableModel)table.getModel();
            dft.setRowCount(0);
            while(rs.next())
            {
                Object[] array = new Object[8];
                
                    array[0] = rs.getString("showID");
                    array[1] = rs.getString("title");
                    array[2] = rs.getString("genre");                   
                    array[3] = rs.getString("rating");
                    Object[] duration = rs.getString("duration").split(" ");
                    array[4] = duration[0] + " hours " + duration[1] + " mins";
                    Object[] date = rs.getString("datee").split(" ");
                    array[5] = date[0];
                    array[6] = rs.getString("time");
                    array[7] = rs.getString("hall");
               
                dft.addRow(array);
            }
        } catch (SQLException ex) {
            Logger.getLogger(viewMovies.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            
      
        
    }
    
    
    
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
            java.util.logging.Logger.getLogger(viewMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new viewMovies().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(viewMovies.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(viewMovies.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
