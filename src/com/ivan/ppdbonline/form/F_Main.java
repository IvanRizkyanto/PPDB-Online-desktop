/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivan.ppdbonline.form;

import java.util.Calendar;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
/**
 *
 * @author Ivankunn
 */
public class F_Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    
    public F_Main(String param) {
        
        setIcon();
        
        initComponents();
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        int x = screen.width /2 - this.getSize().width / 2;
        int y = screen.height /2 - this.getSize().height / 2;
        
        this.setLocation(x, y);
        
        config.config();
        conn = config.conn;
        stmt = config.stmt;
        
        cbSelectMajor.enable(false);
        
        txtNoUN.setText(param);
        getAccountInfo();
        content1Load();
        logoView();
        showSelectedSchool();
        loadTableSelection();
        modeBtnSelectAdd();
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    }
    
    Connection conn;
    Statement stmt;
    ResultSet rs;
    
    String sql = null;
    int selectionCount;
    
    Calendar calendar = Calendar.getInstance();
    
    C_Palette color = new C_Palette();
    C_Status status = new C_Status();
    C_Config config = new C_Config();
    
    F_Login login = new F_Login();
    
    private int hours = calendar.get(Calendar.HOUR);
   
    private void logoView() {
        ImageIcon imageView = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
        
        Image img1 = imageView.getImage();
        Image img2 = img1.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_SMOOTH);
        
        ImageIcon i = new ImageIcon(img2);
        
        lblLogo.setIcon(i);
    }
    
    private void modeBtnSelectAdd() {
        
        if (this.txtSchoolSelected.getText().equals("")) {
            this.btnSelectAdd.setText("Pilih"); 
        } else {
            this.btnSelectAdd.setText("Ganti");
        }
        
    }
    
    private void getAccountInfo() {
        try {
            String id = txtNoUN.getText();
            
            if (id.length() > 0) {
                
                String sql = "SELECT * FROM tbl_akunppdb WHERE no_un = '"+ id + "'";
                
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery(sql);
                
                if (rs.next()) {
                    Vector<String> userAccountStr = new Vector<String>();
                    Vector<Double> userAccountDbl = new Vector<Double>();
                    
                    userAccountStr.add(rs.getString(2)); //name
                    userAccountStr.add(rs.getString(3)); //gender
                    userAccountStr.add(rs.getString(4)); //b_place
                    userAccountStr.add(rs.getString(5)); //b_date
                    userAccountStr.add(rs.getString(6)); //address
                    userAccountStr.add(rs.getString(7)); //city
                    userAccountStr.add(rs.getString(8)); //schoolOrigin
                    userAccountStr.add(rs.getString(9)); //religion
                    
                    userAccountDbl.add(rs.getDouble(10)); //ex1
                    userAccountDbl.add(rs.getDouble(11)); //ex2
                    userAccountDbl.add(rs.getDouble(12)); //ex3
                    userAccountDbl.add(rs.getDouble(13)); //ex4
                    userAccountDbl.add(rs.getDouble(14)); //exb
                    userAccountDbl.add(rs.getDouble(15)); //ext
                    
                    txtUsername.setText(userAccountStr.get(0)); //2
                    txtGender.setText(userAccountStr.get(1)); //3
                    txtTTL.setText(userAccountStr.get(2) +", "+ userAccountStr.get(3)); //4 & 5
                    txtAddress.setText("<html><p>"+ userAccountStr.get(4) + "</p></html>"); //6
                    txtCity.setText(userAccountStr.get(5)); //7
                    txtSchoolOrigin.setText(userAccountStr.get(6)); //8
                    txtReligion.setText(userAccountStr.get(7)); //9*/
                    
                    txtEx1.setText(Double.toString(userAccountDbl.get(0)));
                    txtEx2.setText(Double.toString(userAccountDbl.get(1)));
                    txtEx3.setText(Double.toString(userAccountDbl.get(2)));
                    txtEx4.setText(Double.toString(userAccountDbl.get(3)));
                    txtBonus.setText(Double.toString(userAccountDbl.get(4)));
                    txtTotal.setText(Double.toString(userAccountDbl.get(5)));
                    
                    this.selectionCount = rs.getInt(16);
                }
            }
        } catch (Exception e) {
            
        }
    }
    
    private void showSelectedSchool(){
        try {
            String sql = "SELECT * FROM tbl_pendaftaran WHERE no_un_pendaftar = '"+ this.txtNoUN.getText() +"'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String acc;
                if (rs.getInt("is_accepted") == 1) {
                    acc = "Diterima";
                } else {
                    acc = "Tidak diterima";
                }
            
                this.txtSchoolSelected.setText(rs.getString("sekolah_pendaftaran"));
                this.txtMajorSelected.setText(rs.getString("jurusan_pendaftaran"));
                this.txtStatusSelected.setText(acc);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void loadTableSelection() {
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("Nilai");
        
        String school = (String) this.cbSelectionSchool.getSelectedItem();
        String major = (String) this.cbSelectionMajor.getSelectedItem();
        
        try {
            int no = 1;
            String sql = "SELECT * FROM tbl_pendaftaran WHERE sekolah_pendaftaran = '"+ school +"' AND jurusan_pendaftaran = '"+ major +"' ORDER BY nilai_ppdb DESC";
            java.sql.Connection conn = (Connection)config.configDB();
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet res = stmt.executeQuery(sql);
            
            while(res.next()) {
                model.addRow(new Object[]
                {no++, res.getString("name_pendaftar"), res.getString("nilai_ppdb")});
            }
            
            tblSelection.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void content1Load() {
        pnlContent1.setVisible(true);
        pnlContent2.setVisible(false);
        pnlContent3.setVisible(false);
        
        getAccountInfo();
        
        System.out.println("selection_count: " + this.selectionCount);
    }
    
    private void content2Load() {
        pnlContent1.setVisible(false);
        pnlContent2.setVisible(true);
        pnlContent3.setVisible(false);
    }
    
    private void content3Load() {
        pnlContent1.setVisible(false);
        pnlContent2.setVisible(false);
        pnlContent3.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSideBar = new javax.swing.JPanel();
        pnlProfile = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        btnBackMenu1 = new javax.swing.JPanel();
        btnForeMenu1 = new javax.swing.JPanel();
        lblMenu1 = new javax.swing.JLabel();
        btnBackMenu2 = new javax.swing.JPanel();
        btnForeMenu2 = new javax.swing.JPanel();
        lblMenu2 = new javax.swing.JLabel();
        btnBackMenu3 = new javax.swing.JPanel();
        btnForeMenu3 = new javax.swing.JPanel();
        lblMenu3 = new javax.swing.JLabel();
        btnAbout = new javax.swing.JPanel();
        lblAbout = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlContent1 = new javax.swing.JPanel();
        pnlContentTopbar1 = new javax.swing.JPanel();
        lblContentTitle4 = new javax.swing.JLabel();
        pnlContentBody1 = new javax.swing.JPanel();
        pnlContent1Login = new javax.swing.JPanel();
        lblTop = new javax.swing.JPanel();
        txtNoUN = new javax.swing.JLabel();
        txtUsername = new javax.swing.JLabel();
        btnLogout = new javax.swing.JPanel();
        lblLogout = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblContentTitle5 = new javax.swing.JLabel();
        txtGender = new javax.swing.JLabel();
        lblContentTitle9 = new javax.swing.JLabel();
        txtTTL = new javax.swing.JLabel();
        lblContentTitle11 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JLabel();
        lblContentTitle13 = new javax.swing.JLabel();
        txtCity = new javax.swing.JLabel();
        lblContentTitle15 = new javax.swing.JLabel();
        txtSchoolOrigin = new javax.swing.JLabel();
        lblContentTitle17 = new javax.swing.JLabel();
        txtReligion = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        lblContentTitle19 = new javax.swing.JLabel();
        txtEx1 = new javax.swing.JLabel();
        lblContentTitle21 = new javax.swing.JLabel();
        txtEx2 = new javax.swing.JLabel();
        lblContentTitle23 = new javax.swing.JLabel();
        txtEx3 = new javax.swing.JLabel();
        lblContentTitle25 = new javax.swing.JLabel();
        txtEx4 = new javax.swing.JLabel();
        lblContentTitle27 = new javax.swing.JLabel();
        txtBonus = new javax.swing.JLabel();
        lblContentTitle29 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        pnlContent2 = new javax.swing.JPanel();
        pnlContentTopbar2 = new javax.swing.JPanel();
        lblContentTitle8 = new javax.swing.JLabel();
        pnlContentBody2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        pnlSelectSekolah = new javax.swing.JPanel();
        cbSelectSchool = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        btnSelectAdd = new javax.swing.JButton();
        cbSelectMajor = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSchoolSelected = new javax.swing.JLabel();
        txtMajorSelected = new javax.swing.JLabel();
        txtStatusSelected = new javax.swing.JLabel();
        pnlContent3 = new javax.swing.JPanel();
        pnlContentTopbar3 = new javax.swing.JPanel();
        lblContentTitle3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cbSelectionSchool = new javax.swing.JComboBox<>();
        cbSelectionMajor = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSelection = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PPDB ONLINE DESKTOP");
        setMinimumSize(new java.awt.Dimension(960, 528));

        pnlSideBar.setBackground(new java.awt.Color(0, 102, 255));
        pnlSideBar.setMinimumSize(new java.awt.Dimension(200, 100));

        pnlProfile.setBackground(new java.awt.Color(0, 119, 182));

        lblLogo.setMaximumSize(new java.awt.Dimension(100, 100));
        lblLogo.setMinimumSize(new java.awt.Dimension(100, 100));
        lblLogo.setPreferredSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout pnlProfileLayout = new javax.swing.GroupLayout(pnlProfile);
        pnlProfile.setLayout(pnlProfileLayout);
        pnlProfileLayout.setHorizontalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlProfileLayout.setVerticalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProfileLayout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlMenu.setBackground(new java.awt.Color(0, 119, 182));

        btnBackMenu1.setBackground(new java.awt.Color(202, 240, 248));

        btnForeMenu1.setBackground(new java.awt.Color(0, 119, 182));

        lblMenu1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblMenu1.setForeground(new java.awt.Color(255, 255, 255));
        lblMenu1.setText("   Akun Saya");
        lblMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMenu1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblMenu1MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMenu1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMenu1MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMenu1MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout btnForeMenu1Layout = new javax.swing.GroupLayout(btnForeMenu1);
        btnForeMenu1.setLayout(btnForeMenu1Layout);
        btnForeMenu1Layout.setHorizontalGroup(
            btnForeMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMenu1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        );
        btnForeMenu1Layout.setVerticalGroup(
            btnForeMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout btnBackMenu1Layout = new javax.swing.GroupLayout(btnBackMenu1);
        btnBackMenu1.setLayout(btnBackMenu1Layout);
        btnBackMenu1Layout.setHorizontalGroup(
            btnBackMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBackMenu1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnForeMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnBackMenu1Layout.setVerticalGroup(
            btnBackMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnForeMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnBackMenu2.setBackground(new java.awt.Color(202, 240, 248));

        btnForeMenu2.setBackground(new java.awt.Color(0, 119, 182));

        lblMenu2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblMenu2.setForeground(new java.awt.Color(255, 255, 255));
        lblMenu2.setText("   Pilih Sekolah");
        lblMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMenu2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblMenu2MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMenu2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMenu2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMenu2MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout btnForeMenu2Layout = new javax.swing.GroupLayout(btnForeMenu2);
        btnForeMenu2.setLayout(btnForeMenu2Layout);
        btnForeMenu2Layout.setHorizontalGroup(
            btnForeMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMenu2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        );
        btnForeMenu2Layout.setVerticalGroup(
            btnForeMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout btnBackMenu2Layout = new javax.swing.GroupLayout(btnBackMenu2);
        btnBackMenu2.setLayout(btnBackMenu2Layout);
        btnBackMenu2Layout.setHorizontalGroup(
            btnBackMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBackMenu2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnForeMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnBackMenu2Layout.setVerticalGroup(
            btnBackMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnForeMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnBackMenu3.setBackground(new java.awt.Color(202, 240, 248));

        btnForeMenu3.setBackground(new java.awt.Color(0, 119, 182));

        lblMenu3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblMenu3.setForeground(new java.awt.Color(255, 255, 255));
        lblMenu3.setText("   Seleksi");
        lblMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblMenu3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblMenu3MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMenu3MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMenu3MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMenu3MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout btnForeMenu3Layout = new javax.swing.GroupLayout(btnForeMenu3);
        btnForeMenu3.setLayout(btnForeMenu3Layout);
        btnForeMenu3Layout.setHorizontalGroup(
            btnForeMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMenu3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        );
        btnForeMenu3Layout.setVerticalGroup(
            btnForeMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMenu3, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout btnBackMenu3Layout = new javax.swing.GroupLayout(btnBackMenu3);
        btnBackMenu3.setLayout(btnBackMenu3Layout);
        btnBackMenu3Layout.setHorizontalGroup(
            btnBackMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBackMenu3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnForeMenu3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnBackMenu3Layout.setVerticalGroup(
            btnBackMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnForeMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnAbout.setBackground(new java.awt.Color(0, 119, 182));

        lblAbout.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblAbout.setForeground(new java.awt.Color(255, 255, 255));
        lblAbout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAbout.setText("About");
        lblAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAboutMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAboutMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAboutMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout btnAboutLayout = new javax.swing.GroupLayout(btnAbout);
        btnAbout.setLayout(btnAboutLayout);
        btnAboutLayout.setHorizontalGroup(
            btnAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAboutLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btnAboutLayout.setVerticalGroup(
            btnAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAbout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBackMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBackMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBackMenu3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBackMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBackMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAbout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlSideBarLayout = new javax.swing.GroupLayout(pnlSideBar);
        pnlSideBar.setLayout(pnlSideBarLayout);
        pnlSideBarLayout.setHorizontalGroup(
            pnlSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlSideBarLayout.setVerticalGroup(
            pnlSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSideBarLayout.createSequentialGroup()
                .addComponent(pnlProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContent.setLayout(new javax.swing.OverlayLayout(pnlContent));

        pnlContentTopbar1.setBackground(new java.awt.Color(3, 4, 94));

        lblContentTitle4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblContentTitle4.setForeground(new java.awt.Color(255, 255, 255));
        lblContentTitle4.setText("Akun Saya");

        javax.swing.GroupLayout pnlContentTopbar1Layout = new javax.swing.GroupLayout(pnlContentTopbar1);
        pnlContentTopbar1.setLayout(pnlContentTopbar1Layout);
        pnlContentTopbar1Layout.setHorizontalGroup(
            pnlContentTopbar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentTopbar1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblContentTitle4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(445, 445, 445))
        );
        pnlContentTopbar1Layout.setVerticalGroup(
            pnlContentTopbar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentTopbar1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblContentTitle4)
                .addContainerGap())
        );

        pnlContentBody1.setLayout(new javax.swing.OverlayLayout(pnlContentBody1));

        pnlContent1Login.setBackground(new java.awt.Color(202, 240, 248));
        pnlContent1Login.setPreferredSize(new java.awt.Dimension(897, 475));

        lblTop.setBackground(new java.awt.Color(0, 119, 182));

        txtNoUN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNoUN.setForeground(new java.awt.Color(255, 255, 255));
        txtNoUN.setText("txtNoUN");

        txtUsername.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(255, 255, 255));
        txtUsername.setText("txtUsername");

        btnLogout.setBackground(new java.awt.Color(255, 51, 51));

        lblLogout.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblLogout.setForeground(new java.awt.Color(255, 255, 255));
        lblLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogout.setText("Logout");
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLogoutMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLogoutMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout btnLogoutLayout = new javax.swing.GroupLayout(btnLogout);
        btnLogout.setLayout(btnLogoutLayout);
        btnLogoutLayout.setHorizontalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
        );
        btnLogoutLayout.setVerticalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout lblTopLayout = new javax.swing.GroupLayout(lblTop);
        lblTop.setLayout(lblTopLayout);
        lblTopLayout.setHorizontalGroup(
            lblTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblTopLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(lblTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoUN, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 319, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        lblTopLayout.setVerticalGroup(
            lblTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblTopLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(lblTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblTopLayout.createSequentialGroup()
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(lblTopLayout.createSequentialGroup()
                        .addComponent(txtUsername)
                        .addGap(2, 2, 2)
                        .addComponent(txtNoUN)
                        .addGap(0, 0, 0))))
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        lblContentTitle5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle5.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle5.setText("Jenis Kelamin");

        txtGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGender.setForeground(new java.awt.Color(0, 0, 0));
        txtGender.setText("txtGender");

        lblContentTitle9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle9.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle9.setText("Tempat, Tanggal Lahir");

        txtTTL.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTTL.setForeground(new java.awt.Color(0, 0, 0));
        txtTTL.setText("txtTTL");

        lblContentTitle11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle11.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle11.setText("Alamat");

        txtAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(0, 0, 0));
        txtAddress.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtAddress.setText("txtAddress");
        txtAddress.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblContentTitle13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle13.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle13.setText("Kota/ Kabupaten");

        txtCity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCity.setForeground(new java.awt.Color(0, 0, 0));
        txtCity.setText("txtCity");

        lblContentTitle15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle15.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle15.setText("Asal Sekolah");

        txtSchoolOrigin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSchoolOrigin.setForeground(new java.awt.Color(0, 0, 0));
        txtSchoolOrigin.setText("txtSchoolOrigin");

        lblContentTitle17.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle17.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle17.setText("Agama");

        txtReligion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtReligion.setForeground(new java.awt.Color(0, 0, 0));
        txtReligion.setText("txtReligion");

        jPanel8.setBackground(new java.awt.Color(153, 153, 153));
        jPanel8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel8.setMinimumSize(new java.awt.Dimension(2, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblContentTitle19.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle19.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle19.setText("Bahasa Indonesia");

        txtEx1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEx1.setForeground(new java.awt.Color(0, 0, 0));
        txtEx1.setText("txtEx1");

        lblContentTitle21.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle21.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle21.setText("Matematika");

        txtEx2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEx2.setForeground(new java.awt.Color(0, 0, 0));
        txtEx2.setText("txtEx2");

        lblContentTitle23.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle23.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle23.setText("Bahasa Inggris");

        txtEx3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEx3.setForeground(new java.awt.Color(0, 0, 0));
        txtEx3.setText("txtEx3");

        lblContentTitle25.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle25.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle25.setText("Ilmu Pengetahuan Alam");

        txtEx4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEx4.setForeground(new java.awt.Color(0, 0, 0));
        txtEx4.setText("txtEx4");

        lblContentTitle27.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle27.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle27.setText("Bonus");

        txtBonus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBonus.setForeground(new java.awt.Color(0, 0, 0));
        txtBonus.setText("txtBonus");

        lblContentTitle29.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblContentTitle29.setForeground(new java.awt.Color(0, 0, 0));
        lblContentTitle29.setText("Total :");

        txtTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(0, 0, 0));
        txtTotal.setText("txtTotal");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblContentTitle13, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblContentTitle11, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblContentTitle9, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblContentTitle5, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtTTL, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblContentTitle15, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(lblContentTitle17, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(txtSchoolOrigin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtReligion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblContentTitle19, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEx1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblContentTitle21, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEx2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblContentTitle23, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEx3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblContentTitle25, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEx4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblContentTitle27, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblContentTitle29, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(lblContentTitle19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEx1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblContentTitle21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEx2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblContentTitle23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEx3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblContentTitle25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEx4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblContentTitle27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBonus)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContentTitle29)
                            .addComponent(txtTotal))
                        .addGap(0, 46, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(lblContentTitle5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGender)
                                .addGap(18, 18, 18)
                                .addComponent(lblContentTitle9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTTL))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(lblContentTitle15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSchoolOrigin)
                                .addGap(18, 18, 18)
                                .addComponent(lblContentTitle17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtReligion)))
                        .addGap(18, 18, 18)
                        .addComponent(lblContentTitle11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblContentTitle13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCity)
                        .addGap(20, 20, 20)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlContent1LoginLayout = new javax.swing.GroupLayout(pnlContent1Login);
        pnlContent1Login.setLayout(pnlContent1LoginLayout);
        pnlContent1LoginLayout.setHorizontalGroup(
            pnlContent1LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlContent1LoginLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );
        pnlContent1LoginLayout.setVerticalGroup(
            pnlContent1LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContent1LoginLayout.createSequentialGroup()
                .addComponent(lblTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContentBody1.add(pnlContent1Login);

        javax.swing.GroupLayout pnlContent1Layout = new javax.swing.GroupLayout(pnlContent1);
        pnlContent1.setLayout(pnlContent1Layout);
        pnlContent1Layout.setHorizontalGroup(
            pnlContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContentTopbar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlContentBody1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlContent1Layout.setVerticalGroup(
            pnlContent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContent1Layout.createSequentialGroup()
                .addComponent(pnlContentTopbar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlContentBody1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContent.add(pnlContent1);

        pnlContentTopbar2.setBackground(new java.awt.Color(3, 4, 94));

        lblContentTitle8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblContentTitle8.setForeground(new java.awt.Color(255, 255, 255));
        lblContentTitle8.setText("Pilih Sekolah");

        javax.swing.GroupLayout pnlContentTopbar2Layout = new javax.swing.GroupLayout(pnlContentTopbar2);
        pnlContentTopbar2.setLayout(pnlContentTopbar2Layout);
        pnlContentTopbar2Layout.setHorizontalGroup(
            pnlContentTopbar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentTopbar2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblContentTitle8, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addGap(470, 470, 470))
        );
        pnlContentTopbar2Layout.setVerticalGroup(
            pnlContentTopbar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentTopbar2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblContentTitle8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContentBody2.setBackground(new java.awt.Color(202, 240, 248));

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        pnlSelectSekolah.setBackground(new java.awt.Color(255, 255, 255));

        cbSelectSchool.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih sekolah", "SMK NEGERI 1 PEMALANG", "SMK NEGERI 1 RANDUDONGKAL", "SMK NEGERI 1 AMPELGADING", "SMK NEGERI 1 PETARUKAN" }));
        cbSelectSchool.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbSelectSchoolMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbSelectSchoolMouseClicked(evt);
            }
        });
        cbSelectSchool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSelectSchoolActionPerformed(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(0, 119, 182));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );

        btnSelectAdd.setText("Pilih");
        btnSelectAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSelectAddMouseEntered(evt);
            }
        });
        btnSelectAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAddActionPerformed(evt);
            }
        });

        cbSelectMajor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih jurusan" }));
        cbSelectMajor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbSelectMajorMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbSelectMajorMouseClicked(evt);
            }
        });
        cbSelectMajor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSelectMajorActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Pilihanmu:");

        txtSchoolSelected.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        txtMajorSelected.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSchoolSelected, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(txtMajorSelected, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtStatusSelected, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSchoolSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtMajorSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtStatusSelected, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSelectSekolahLayout = new javax.swing.GroupLayout(pnlSelectSekolah);
        pnlSelectSekolah.setLayout(pnlSelectSekolahLayout);
        pnlSelectSekolahLayout.setHorizontalGroup(
            pnlSelectSekolahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSelectSekolahLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(pnlSelectSekolahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbSelectMajor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbSelectSchool, 0, 250, Short.MAX_VALUE))
                .addGap(62, 62, 62)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
            .addGroup(pnlSelectSekolahLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(btnSelectAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSelectSekolahLayout.setVerticalGroup(
            pnlSelectSekolahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSelectSekolahLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(pnlSelectSekolahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlSelectSekolahLayout.createSequentialGroup()
                        .addComponent(cbSelectSchool, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(cbSelectMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46)
                .addComponent(btnSelectAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSelectSekolah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSelectSekolah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlContentBody2Layout = new javax.swing.GroupLayout(pnlContentBody2);
        pnlContentBody2.setLayout(pnlContentBody2Layout);
        pnlContentBody2Layout.setHorizontalGroup(
            pnlContentBody2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentBody2Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(80, 80, 80))
        );
        pnlContentBody2Layout.setVerticalGroup(
            pnlContentBody2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentBody2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlContent2Layout = new javax.swing.GroupLayout(pnlContent2);
        pnlContent2.setLayout(pnlContent2Layout);
        pnlContent2Layout.setHorizontalGroup(
            pnlContent2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContentTopbar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlContentBody2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlContent2Layout.setVerticalGroup(
            pnlContent2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContent2Layout.createSequentialGroup()
                .addComponent(pnlContentTopbar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlContentBody2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContent.add(pnlContent2);

        pnlContentTopbar3.setBackground(new java.awt.Color(3, 4, 94));

        lblContentTitle3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblContentTitle3.setForeground(new java.awt.Color(255, 255, 255));
        lblContentTitle3.setText("Seleksi");

        javax.swing.GroupLayout pnlContentTopbar3Layout = new javax.swing.GroupLayout(pnlContentTopbar3);
        pnlContentTopbar3.setLayout(pnlContentTopbar3Layout);
        pnlContentTopbar3Layout.setHorizontalGroup(
            pnlContentTopbar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentTopbar3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblContentTitle3, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addGap(468, 468, 468))
        );
        pnlContentTopbar3Layout.setVerticalGroup(
            pnlContentTopbar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentTopbar3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblContentTitle3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(202, 240, 248));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel4.setBackground(new java.awt.Color(0, 119, 182));

        cbSelectionSchool.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih sekolah", "SMK NEGERI 1 PEMALANG", "SMK NEGERI 1 RANDUDONGKAL", "SMK NEGERI 1 AMPELGADING", "SMK NEGERI 1 PETARUKAN" }));
        cbSelectionSchool.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbSelectionSchoolMousePressed(evt);
            }
        });
        cbSelectionSchool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSelectionSchoolActionPerformed(evt);
            }
        });

        cbSelectionMajor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih jurusan" }));
        cbSelectionMajor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbSelectionMajorMousePressed(evt);
            }
        });
        cbSelectionMajor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSelectionMajorActionPerformed(evt);
            }
        });

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbSelectionSchool, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbSelectionMajor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSelectionMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSelectionSchool, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblSelection.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblSelection);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(100, 100, 100))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );

        javax.swing.GroupLayout pnlContent3Layout = new javax.swing.GroupLayout(pnlContent3);
        pnlContent3.setLayout(pnlContent3Layout);
        pnlContent3Layout.setHorizontalGroup(
            pnlContent3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlContentTopbar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlContent3Layout.setVerticalGroup(
            pnlContent3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContent3Layout.createSequentialGroup()
                .addComponent(pnlContentTopbar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContent.add(pnlContent3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlSideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblMenu1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu1MouseEntered
        btnForeMenu1.setBackground(color.blue3);
        btnBackMenu1.setBackground(color.blue4);
    }//GEN-LAST:event_lblMenu1MouseEntered

    private void lblMenu1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu1MouseExited
        btnForeMenu1.setBackground(color.blue2);
        btnBackMenu1.setBackground(color.blue5);     
    }//GEN-LAST:event_lblMenu1MouseExited

    private void lblMenu1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu1MouseReleased
        btnForeMenu1.setBackground(color.blue3);
    }//GEN-LAST:event_lblMenu1MouseReleased

    private void lblMenu1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu1MousePressed
        btnForeMenu1.setBackground(color.blue4);
    }//GEN-LAST:event_lblMenu1MousePressed

    private void lblMenu2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu2MousePressed
        btnForeMenu2.setBackground(color.blue3);
    }//GEN-LAST:event_lblMenu2MousePressed

    private void lblMenu2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu2MouseReleased
        btnForeMenu2.setBackground(color.blue4);
    }//GEN-LAST:event_lblMenu2MouseReleased

    private void lblMenu2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu2MouseExited
        btnForeMenu2.setBackground(color.blue2);
        btnBackMenu2.setBackground(color.blue5);      
    }//GEN-LAST:event_lblMenu2MouseExited

    private void lblMenu2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu2MouseEntered
        btnForeMenu2.setBackground(color.blue3);
        btnBackMenu2.setBackground(color.blue4);
    }//GEN-LAST:event_lblMenu2MouseEntered

    private void lblMenu3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu3MousePressed
        btnForeMenu3.setBackground(color.blue3);
    }//GEN-LAST:event_lblMenu3MousePressed

    private void lblMenu3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu3MouseReleased
        btnForeMenu3.setBackground(color.blue4);
    }//GEN-LAST:event_lblMenu3MouseReleased

    private void lblMenu3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu3MouseExited
        btnForeMenu3.setBackground(color.blue2);
        btnBackMenu3.setBackground(color.blue5);
    }//GEN-LAST:event_lblMenu3MouseExited

    private void lblMenu3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu3MouseEntered
        btnForeMenu3.setBackground(color.blue3);
        btnBackMenu3.setBackground(color.blue4);
    }//GEN-LAST:event_lblMenu3MouseEntered

    private void lblMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu1MouseClicked
        content1Load();
    }//GEN-LAST:event_lblMenu1MouseClicked

    private void lblMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu2MouseClicked
        content2Load();
    }//GEN-LAST:event_lblMenu2MouseClicked

    private void lblMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMenu3MouseClicked
        content3Load();
    }//GEN-LAST:event_lblMenu3MouseClicked

    private void cbSelectSchoolMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbSelectSchoolMousePressed
        cbSelectSchool.removeItem("Pilih sekolah");
    }//GEN-LAST:event_cbSelectSchoolMousePressed

    private void cbSelectSchoolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbSelectSchoolMouseClicked

    }//GEN-LAST:event_cbSelectSchoolMouseClicked

    private void lblLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseEntered
        this.btnLogout.setBackground(color.red1);
    }//GEN-LAST:event_lblLogoutMouseEntered

    private void lblLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseExited
        this.btnLogout.setBackground(color.red);
    }//GEN-LAST:event_lblLogoutMouseExited

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
        this.setVisible(false);
        new F_Login().setVisible(true);
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void lblAboutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAboutMouseEntered
        this.btnAbout.setBackground(color.blue4);
    }//GEN-LAST:event_lblAboutMouseEntered

    private void lblAboutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAboutMouseExited
        this.btnAbout.setBackground(color.blue2);
    }//GEN-LAST:event_lblAboutMouseExited

    private void lblAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAboutMouseClicked
        new F_About().setVisible(true);
    }//GEN-LAST:event_lblAboutMouseClicked

    private void cbSelectMajorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbSelectMajorMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSelectMajorMousePressed

    private void cbSelectMajorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbSelectMajorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSelectMajorMouseClicked

    private void btnSelectAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAddActionPerformed
        String no_pendaftaran = new C_NoPendaftaran().C_NoPendaftaran();
        String noUn_pendaftar = this.txtNoUN.getText();
        String name_pendaftar = this.txtUsername.getText();
        double score_ppdb = Double.valueOf(this.txtTotal.getText());
        String time_ppdb = calendar.getTime().toString();
        String sekolah = (String) this.cbSelectSchool.getSelectedItem();
        String jurusan = (String) this.cbSelectMajor.getSelectedItem();
              
        if (cbSelectSchool.getSelectedItem().equals("Pilih sekolah") || cbSelectMajor.getSelectedItem().equals("Pilih jurusan")) {
            JOptionPane.showMessageDialog(null, "Pilihan error!");
        } else {
            try {
                String sql;
                if (this.btnSelectAdd.getText().equals("Pilih")) {
                    sql = "INSERT INTO tbl_pendaftaran VALUE ('"+ no_pendaftaran +"','"+ noUn_pendaftar +"','"+ name_pendaftar +"',"+ score_ppdb +",'"+ time_ppdb +"','"+ sekolah +"','"+ jurusan +"', 1)";      
                } else {
                    sql = "UPDATE tbl_pendaftaran SET sekolah_pendaftaran = '"+ sekolah +"', jurusan_pendaftaran = '"+ jurusan +"' WHERE no_un_pendaftar = '"+ noUn_pendaftar +"'";
                }
                java.sql.Connection conn = (Connection)config.configDB();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.execute();
                System.out.println("1her");
            } catch (Exception e) {System.out.println(e);} 
        }
        showSelectedSchool();
        modeBtnSelectAdd();
    }//GEN-LAST:event_btnSelectAddActionPerformed

    private void btnSelectAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelectAddMouseEntered
        
    }//GEN-LAST:event_btnSelectAddMouseEntered

    private void cbSelectSchoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSelectSchoolActionPerformed
        C_SchoolList list = new C_SchoolList();
        cbSelectMajor.enable(true);
        if (cbSelectSchool.getSelectedItem().equals(list.list[0][0])) {
            cbSelectMajor.removeAllItems();
            cbSelectMajor.addItem(list.list[0][1]);
            cbSelectMajor.addItem(list.list[0][2]);
            cbSelectMajor.addItem(list.list[0][3]);
            cbSelectMajor.addItem(list.list[0][4]);
            cbSelectMajor.addItem(list.list[0][5]);
            cbSelectMajor.addItem(list.list[0][6]);
        } else if (cbSelectSchool.getSelectedItem().equals(list.list[1][0])) {
            cbSelectMajor.removeAllItems();
            cbSelectMajor.addItem(list.list[1][1]);
            cbSelectMajor.addItem(list.list[1][2]);
            cbSelectMajor.addItem(list.list[1][3]);
            cbSelectMajor.addItem(list.list[1][4]);
            cbSelectMajor.addItem(list.list[1][5]);
            cbSelectMajor.addItem(list.list[1][6]);
        } else if (cbSelectSchool.getSelectedItem().equals(list.list[2][0])) {
            cbSelectMajor.removeAllItems();
            cbSelectMajor.addItem(list.list[2][1]);
            cbSelectMajor.addItem(list.list[2][2]);
            cbSelectMajor.addItem(list.list[2][3]);
            cbSelectMajor.addItem(list.list[2][4]);
            cbSelectMajor.addItem(list.list[2][5]);
            cbSelectMajor.addItem(list.list[2][6]);
            cbSelectMajor.addItem(list.list[2][7]);
            cbSelectMajor.addItem(list.list[2][8]);
            cbSelectMajor.addItem(list.list[2][9]);
        } else if (cbSelectSchool.getSelectedItem().equals(list.list[3][0])) {
            cbSelectMajor.removeAllItems();
            cbSelectMajor.addItem(list.list[3][1]);
            cbSelectMajor.addItem(list.list[3][2]);
            cbSelectMajor.addItem(list.list[3][3]);
            cbSelectMajor.addItem(list.list[3][4]);
            cbSelectMajor.addItem(list.list[3][5]);
            cbSelectMajor.addItem(list.list[3][6]);
        }
    }//GEN-LAST:event_cbSelectSchoolActionPerformed

    private void cbSelectMajorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSelectMajorActionPerformed

    }//GEN-LAST:event_cbSelectMajorActionPerformed

    private void cbSelectionMajorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSelectionMajorActionPerformed
        loadTableSelection();
    }//GEN-LAST:event_cbSelectionMajorActionPerformed

    private void cbSelectionSchoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSelectionSchoolActionPerformed
        C_SchoolList list = new C_SchoolList();
        if (cbSelectionSchool.getSelectedItem().equals(list.list[0][0])) {
            cbSelectionMajor.removeAllItems();
            cbSelectionMajor.addItem(list.list[0][1]);
            cbSelectionMajor.addItem(list.list[0][2]);
            cbSelectionMajor.addItem(list.list[0][3]);
            cbSelectionMajor.addItem(list.list[0][4]);
            cbSelectionMajor.addItem(list.list[0][5]);
            cbSelectionMajor.addItem(list.list[0][6]);
        } else if (cbSelectionSchool.getSelectedItem().equals(list.list[1][0])) {
            cbSelectionMajor.removeAllItems();
            cbSelectionMajor.addItem(list.list[1][1]);
            cbSelectionMajor.addItem(list.list[1][2]);
            cbSelectionMajor.addItem(list.list[1][3]);
            cbSelectionMajor.addItem(list.list[1][4]);
            cbSelectionMajor.addItem(list.list[1][5]);
            cbSelectionMajor.addItem(list.list[1][6]);
        } else if (cbSelectionSchool.getSelectedItem().equals(list.list[2][0])) {
            cbSelectionMajor.removeAllItems();
            cbSelectionMajor.addItem(list.list[2][1]);
            cbSelectionMajor.addItem(list.list[2][2]);
            cbSelectionMajor.addItem(list.list[2][3]);
            cbSelectionMajor.addItem(list.list[2][4]);
            cbSelectionMajor.addItem(list.list[2][5]);
            cbSelectionMajor.addItem(list.list[2][6]);
            cbSelectionMajor.addItem(list.list[2][7]);
            cbSelectionMajor.addItem(list.list[2][8]);
            cbSelectionMajor.addItem(list.list[2][9]);
        } else if (cbSelectionSchool.getSelectedItem().equals(list.list[3][0])) {
            cbSelectionMajor.removeAllItems();
            cbSelectionMajor.addItem(list.list[3][1]);
            cbSelectionMajor.addItem(list.list[3][2]);
            cbSelectionMajor.addItem(list.list[3][3]);
            cbSelectionMajor.addItem(list.list[3][4]);
            cbSelectionMajor.addItem(list.list[3][5]);
            cbSelectionMajor.addItem(list.list[3][6]);
        }
    }//GEN-LAST:event_cbSelectionSchoolActionPerformed

    private void cbSelectionMajorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbSelectionMajorMousePressed
        cbSelectionMajor.removeItem("Pilih jurusan");
    }//GEN-LAST:event_cbSelectionMajorMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadTableSelection();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbSelectionSchoolMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbSelectionSchoolMousePressed
        cbSelectionSchool.removeItem("Pilih sekolah");
    }//GEN-LAST:event_cbSelectionSchoolMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAbout;
    private javax.swing.JPanel btnBackMenu1;
    private javax.swing.JPanel btnBackMenu2;
    private javax.swing.JPanel btnBackMenu3;
    private javax.swing.JPanel btnForeMenu1;
    private javax.swing.JPanel btnForeMenu2;
    private javax.swing.JPanel btnForeMenu3;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JButton btnSelectAdd;
    private javax.swing.JComboBox<String> cbSelectMajor;
    private javax.swing.JComboBox<String> cbSelectSchool;
    private javax.swing.JComboBox<String> cbSelectionMajor;
    private javax.swing.JComboBox<String> cbSelectionSchool;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAbout;
    public javax.swing.JLabel lblContentTitle11;
    public javax.swing.JLabel lblContentTitle13;
    public javax.swing.JLabel lblContentTitle15;
    public javax.swing.JLabel lblContentTitle17;
    public javax.swing.JLabel lblContentTitle19;
    public javax.swing.JLabel lblContentTitle21;
    public javax.swing.JLabel lblContentTitle23;
    public javax.swing.JLabel lblContentTitle25;
    public javax.swing.JLabel lblContentTitle27;
    public javax.swing.JLabel lblContentTitle29;
    public javax.swing.JLabel lblContentTitle3;
    public javax.swing.JLabel lblContentTitle4;
    public javax.swing.JLabel lblContentTitle5;
    public javax.swing.JLabel lblContentTitle8;
    public javax.swing.JLabel lblContentTitle9;
    private javax.swing.JLabel lblLogo;
    public javax.swing.JLabel lblLogout;
    public javax.swing.JLabel lblMenu1;
    public javax.swing.JLabel lblMenu2;
    public javax.swing.JLabel lblMenu3;
    private javax.swing.JPanel lblTop;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlContent1;
    private javax.swing.JPanel pnlContent1Login;
    private javax.swing.JPanel pnlContent2;
    private javax.swing.JPanel pnlContent3;
    private javax.swing.JPanel pnlContentBody1;
    private javax.swing.JPanel pnlContentBody2;
    private javax.swing.JPanel pnlContentTopbar1;
    private javax.swing.JPanel pnlContentTopbar2;
    private javax.swing.JPanel pnlContentTopbar3;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlProfile;
    private javax.swing.JPanel pnlSelectSekolah;
    private javax.swing.JPanel pnlSideBar;
    private javax.swing.JTable tblSelection;
    public javax.swing.JLabel txtAddress;
    public javax.swing.JLabel txtBonus;
    public javax.swing.JLabel txtCity;
    public javax.swing.JLabel txtEx1;
    public javax.swing.JLabel txtEx2;
    public javax.swing.JLabel txtEx3;
    public javax.swing.JLabel txtEx4;
    public javax.swing.JLabel txtGender;
    private javax.swing.JLabel txtMajorSelected;
    public javax.swing.JLabel txtNoUN;
    public javax.swing.JLabel txtReligion;
    public javax.swing.JLabel txtSchoolOrigin;
    private javax.swing.JLabel txtSchoolSelected;
    private javax.swing.JLabel txtStatusSelected;
    public javax.swing.JLabel txtTTL;
    public javax.swing.JLabel txtTotal;
    public javax.swing.JLabel txtUsername;
    // End of variables declaration//GEN-END:variables
}