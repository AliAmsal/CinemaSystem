
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinemasystem;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.align;
import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.align;
import java.awt.Dimension;
import static java.awt.SystemColor.text;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import oracle.net.aso.m;

/**
 *
 * @author Dell
 */
public class foodReceipt extends javax.swing.JFrame {

    private boolean tempvalue[][];
    private int c;
    private movie mov;
    String snos = "";
    double total_amount = 0;
    
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public foodReceipt(boolean[][] array,movie m, customer cus) throws IOException, ClassNotFoundException, SQLException, AddressException, MessagingException {
        
    }

    public static void sendPlainTextEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
// *** BEGIN CHANGE
        properties.put("mail.smtp.user", userName);

        // creates a new session, no Authenticator (will connect() later)
        Session session = Session.getDefaultInstance(properties);
// *** END CHANGE

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setContent(message, "text/html");

// *** BEGIN CHANGE
        // sends the e-mail
        Transport t = session.getTransport("smtp");
        t.connect(userName, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
// *** END CHANGE

    }

    foodReceipt(movie mov, customer cus) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    foodReceipt(boolean[][] array, movie m, customer cus, int items, double snack_amount) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, MessagingException {
        initComponents();
        RandomAccessFile file = new RandomAccessFile("hall.dat","rw");
        
        boolean found = false;
        while(!found && (file.getFilePointer() < file.length()) ) {
           
            if(file.readInt() == m.getId()){
                found = true;
                file.readInt();        
                
                for(int i=0;i<array.length;i++){
                    for(int j=0;j<array[0].length;j++){            
                        if(array[i][j]) {                    
                            file.writeBoolean(true);
                        }
                        else {
                            file.readBoolean();
                        }
                    }
                }
            }
            else{
                int skip = file.readInt();
                file.seek(file.getFilePointer() + skip);
            }
        }
        
        for(int i = 0; i < array.length; i++) {
            for(int j = array[0].length - 1; j >= 0; j--) {
                if(array[i][j]) {
                    c++;
                    seatsTF.addItem( (char)(i+65) + "" + (array[0].length - j) );
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb", "cinema", "cinema");
                    pst = con.prepareStatement("insert into SEAT(name,cnic,show_id,seat_no)values(?,?,?,?)");
                    pst.setString(1, cus.getName());
                    pst.setString(2, cus.getCnic());
                    pst.setInt(3, m.getId());
                    pst.setString(4,(char)(i+65) + "" + (array[0].length - j));
                    pst.executeUpdate();
                    snos = snos + " " + (char)(i+65) + "" + (array[0].length - j);
                }
            }
        }
        idTF.setEditable(false);
        titleTF.setEditable(false);
        hallTF.setEditable(false);
        dateTF.setEditable(false);
        timeTF.setEditable(false);
        nameTF.setEditable(false);
        numberTF.setEditable(false);
        ticketamountTF.setEditable(false);
        cnicTF.setEditable(false);
        
        itemsTF.setEditable(false);
        snackamountTF.setEditable(false);
        totalamountTF.setEditable(false);
        
        
        idTF.setText(Integer.toString(m.getId()));
        titleTF.setText(m.getTitle());
        hallTF.setText(m.getHall());
        dateTF.setText(m.getDate());
        timeTF.setText(m.getTime());
        nameTF.setText(cus.getName());
        cnicTF.setText(cus.getCnic());
        numberTF.setText(Integer.toString(c));
        ticketamountTF.setText(Double.toString(cus.getAmount(c, m.getHall())));
        itemsTF.setText(Integer.toString(items));
        snackamountTF.setText(Double.toString(snack_amount));
        
        
        
        double a = cus.getAmount(c, m.getHall());
        
        total_amount = a + snack_amount;
        
        totalamountTF.setText(Double.toString(total_amount));
        
        Double amount = new Double(a);
       
        
        
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb", "cinema", "cinema");
                pst = con.prepareStatement("insert into Booking(name,cnic,show_id,title,hall_category,day,month,time,number_of_tickets,total_amount)values(?,?,?,?,?,?,?,?,?,?) ");
                pst.setString(1, nameTF.getText());
                pst.setString(2, cnicTF.getText());
                pst.setInt(3, Integer.parseInt(idTF.getText()));
                pst.setString(4, titleTF.getText());
                pst.setString(5, hallTF.getText());
                String[] date_array = dateTF.getText().split("-");
                pst.setInt(6, Integer.parseInt(date_array[0]));
                pst.setString(7, date_array[1]);
                pst.setString(8, timeTF.getText());
                pst.setInt(9, Integer.parseInt(numberTF.getText()));
                pst.setInt(10, amount.intValue());
           
                pst.executeUpdate();
                
                
                
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orclpdb", "cinema", "cinema");
                pst = con.prepareStatement("insert into orders(name,cnic,items,amount)values(?,?,?,?) ");
                pst.setString(1, cus.getName());
                pst.setString(2, cnicTF.getText());
                pst.setInt(3, items);
                pst.setInt(4, (int) snack_amount);
           
                pst.executeUpdate();
        
                
                
        
        
        /*try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                con = DriverManager.getConnection("jdbc:ucanaccess://customers.accdb");
                pst = con.prepareStatement("insert into customer(Namee,CNIC,Address,Phone,showID,tickets,amount)values(?,?,?,?,?,?,?) ");
                pst.setString(1, cus.getName());
                pst.setString(2, cus.getCnic());
                pst.setString(3, cus.getAddress());
                pst.setString(4, cus.getPhone());
                pst.setString(5, Integer.toString(m.getId()));
                pst.setString(6, Integer.toString(c));
                pst.setString(7, Double.toString(cus.getAmount(c, m.getHall())));
                
                pst.executeUpdate();
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(customerDetails.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(customerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        /////// emailing
        String host = "smtp.gmail.com";
        String port = "587";
        final String username = "bookingcinema3109@gmail.com";
        final String password = "cinema3109";
        String to = cus.getAddress();
        String subject = "BOOKING DETAILS";
        String message
                = "<h1 style=color:green; text-align:center>BOOKING CONFIRMATION</h1>\n\n"
                + "Thank you for booking tickets with E-Cinema! You can see the summary of your booking below." + "\n\n\n\n"
                + "<h3>TICKET DETAILS</h3>" + "\n"
                + "<b>Customer Name: </b>" + cus.getName()
                + "<br><b>Customer CNIC: </b>" + cus.getCnic()
                + "<br><b>Movie Show ID: </b>" + m.getId()
                + "<br><b>Movie Title: </b>" + m.getTitle()
                + "<br><b>Hall Category: </b>" + m.getHall()
                + "<br><b>Show Date: </b>" + m.getDate()
                + "<br><b>Show Timing: </b>" + m.getTime()
                + "<br><b>Number of Tickets: </b>" + c
                + "<br><b>Seat Numbers: </b>" + snos
                + "<br><b>Ticket Amount: </b>" + a
                + "<br><b>------------------------------- </b>"
                + "<h3>SNACK ORDER DETAILS</h3>" + "\n"
                + "<b>Snack Items: </b>" + items
                + "<br><b>Snack Amount: </b>" + snack_amount
                + "<br><b>------------------------------- </b>"
                + "<br><b>Total Amount: </b>" + total_amount
                + "<br><b>------------------------------- </b>";

        sendPlainTextEmail(host, port, username, password, to, subject, message);
        jLabel13.setText("Booking Details have been emailed at:  " + cus.getAddress());

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idTF = new javax.swing.JTextField();
        titleTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        hallTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dateTF = new javax.swing.JTextField();
        timeTF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        numberTF = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        seatsTF = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        ticketamountTF = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        btnBackToMovies = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cnicTF = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        itemsTF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        snackamountTF = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        totalamountTF = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Receipt");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        idTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTFActionPerformed(evt);
            }
        });
        getContentPane().add(idTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 211, -1));

        titleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleTFActionPerformed(evt);
            }
        });
        getContentPane().add(titleTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 211, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setText("Receipt");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 129, 10));

        jLabel2.setText("Show ID:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        jLabel3.setText("Movie Title:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, -1, -1));

        jLabel4.setText("Hall Category:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));
        getContentPane().add(hallTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 211, -1));

        jLabel5.setText("Show Date:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, -1));

        jLabel6.setText("Show Time:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, -1, -1));
        getContentPane().add(dateTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 211, -1));
        getContentPane().add(timeTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 211, -1));

        jLabel7.setText("Customer Name:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));
        getContentPane().add(nameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 211, -1));

        jLabel8.setText("Number of Tickets:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));
        getContentPane().add(numberTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 211, -1));

        jLabel9.setText("Seat Numbers:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, -1));

        getContentPane().add(seatsTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 211, -1));

        jLabel10.setText("Ticket Amount:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, -1, -1));

        ticketamountTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ticketamountTFActionPerformed(evt);
            }
        });
        getContentPane().add(ticketamountTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 211, -1));

        btnExit.setBackground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        getContentPane().add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 660, 120, 30));

        btnBackToMovies.setBackground(new java.awt.Color(255, 255, 255));
        btnBackToMovies.setText("Back to Movies");
        btnBackToMovies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackToMoviesActionPerformed(evt);
            }
        });
        getContentPane().add(btnBackToMovies, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 660, -1, 30));
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 440, -1));

        jLabel12.setText("CNIC:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, -1));
        getContentPane().add(cnicTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 210, -1));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("jLabel13");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 440, -1));

        jLabel14.setText("Snack Items:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, -1, -1));

        itemsTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemsTFActionPerformed(evt);
            }
        });
        getContentPane().add(itemsTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 500, 210, -1));

        jLabel15.setText("Snacks Amount:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, -1, -1));
        getContentPane().add(snackamountTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 540, 210, -1));

        jLabel16.setText("Total Amount:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, -1, -1));

        totalamountTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalamountTFActionPerformed(evt);
            }
        });
        getContentPane().add(totalamountTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 580, 210, -1));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 730));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void titleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleTFActionPerformed

    private void idTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idTFActionPerformed

    private void ticketamountTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ticketamountTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ticketamountTFActionPerformed

    private void btnBackToMoviesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackToMoviesActionPerformed
        try {
            new bookMovies().setVisible(true);
            this.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(foodReceipt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(foodReceipt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnBackToMoviesActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void itemsTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemsTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemsTFActionPerformed

    private void totalamountTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalamountTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalamountTFActionPerformed

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
            java.util.logging.Logger.getLogger(foodReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(foodReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(foodReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(foodReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                boolean a[][] = null;
                movie m = null;
                customer c = null;
                try {
                    new foodReceipt(a,m,c).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(foodReceipt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(foodReceipt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(foodReceipt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(foodReceipt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackToMovies;
    private javax.swing.JButton btnExit;
    private javax.swing.JTextField cnicTF;
    private javax.swing.JTextField dateTF;
    private javax.swing.JTextField hallTF;
    private javax.swing.JTextField idTF;
    private javax.swing.JTextField itemsTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField nameTF;
    private javax.swing.JTextField numberTF;
    private javax.swing.JComboBox<String> seatsTF;
    private javax.swing.JTextField snackamountTF;
    private javax.swing.JTextField ticketamountTF;
    private javax.swing.JTextField timeTF;
    private javax.swing.JTextField titleTF;
    private javax.swing.JTextField totalamountTF;
    // End of variables declaration//GEN-END:variables
}
