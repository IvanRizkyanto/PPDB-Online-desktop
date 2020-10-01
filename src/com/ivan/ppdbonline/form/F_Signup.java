/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivan.ppdbonline.form;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
/**
 *
 * @author ivanlubuntu
 */
public class F_Signup extends javax.swing.JFrame {

    /**
     * Creates new form F_Signup
     */
    public F_Signup() {
        setIcon();
        initComponents();
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        int x = screen.width /2 - this.getSize().width / 2;
        int y = screen.height /2 - this.getSize().height / 2;
        
        this.setLocation(x, y);
        
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo.png")));
    }
    
    Vector<String> signupDataString = new Vector<String>();
    Vector<Double> signupDataDouble = new Vector<Double>();
    C_Palette color = new C_Palette();
    C_Config config = new C_Config();
    
    
    
    int birthDateDay;
    String birthDateMonth;
    int birthDateYear;
    double examBonusZero = 0;
    double examTotal;
    
    private void getSignupData() {
        signupDataString.add(this.txtNoUN.getText());
        signupDataString.add(this.txtName.getText().toUpperCase());
        if(this.bgGenderMale.isSelected()) {
            signupDataString.add(this.bgGenderMale.getText());
        } else if (this.bgGenderFemale.isSelected()) {
            signupDataString.add(this.bgGenderFemale.getText());
        }
        signupDataString.add(this.txtBirthPlace.getText().toUpperCase());
        getBirthDate();
        signupDataString.add(Integer.toString(this.birthDateDay)+" "+this.birthDateMonth+" "+Integer.toString(this.birthDateYear));
        signupDataString.add(this.txtAddress.getText());
        signupDataString.add(this.txtCity.getText().toUpperCase());
        signupDataString.add(this.txtSchoolOrigin.getText().toUpperCase());
        signupDataString.add(this.txtReligion.getText().toUpperCase());
        
        signupDataDouble.add(Double.parseDouble(this.txtExam1.getText()));
        signupDataDouble.add(Double.parseDouble(this.txtExam2.getText()));
        signupDataDouble.add(Double.parseDouble(this.txtExam3.getText()));
        signupDataDouble.add(Double.parseDouble(this.txtExam4.getText()));
        if (this.txtExamBonus.getText().trim().isEmpty()) {
            signupDataDouble.add(examBonusZero);
        } else {
            signupDataDouble.add(Double.parseDouble(this.txtExamBonus.getText()));
        }
        signupDataDouble.add(getExamTotal());
        
        signupDataString.add(this.txtPassword.getText());
        
        
    }
    
    private void getBirthDate() {
        birthDateDay = Integer.parseInt(this.txtBirthDateDay.getText());
        switch (Integer.parseInt(this.txtBirthDateMonth.getText())) {
            case 1: birthDateMonth = "Januari";
                break;
            case 2: birthDateMonth = "Februari";
                break;
            case 3: birthDateMonth = "Maret";
                break;
            case 4: birthDateMonth = "April";
                break;
            case 5: birthDateMonth = "Mei";
                break;
            case 6: birthDateMonth = "Juni";
                break;
            case 7: birthDateMonth = "Juli";
                break;
            case 8: birthDateMonth = "Agustus";
                break;
            case 9: birthDateMonth = "September";
                break;
            case 10: birthDateMonth = "Oktober";
                break;
            case 11: birthDateMonth = "November";
                break;
            case 12: birthDateMonth = "Desember";
                break;
        }
        birthDateYear = Integer.parseInt(this.txtBirthDateYear.getText());
    }
    
    private double getExamTotal() {
        double exam1, exam2, exam3, exam4, examBonus;
        exam1 = Double.parseDouble(this.txtExam1.getText());
        exam2 = Double.parseDouble(this.txtExam2.getText());
        exam3 = Double.parseDouble(this.txtExam3.getText());
        exam4 = Double.parseDouble(this.txtExam4.getText());
        if (this.txtExamBonus.getText().trim().isEmpty()) {
            examBonus = examBonusZero;
        } else {
            examBonus = Double.parseDouble(this.txtExamBonus.getText());
        }
        examTotal = exam1 + exam2 + exam3 + exam4 + examBonus;
        return examTotal;
    }
    
    private void resetField() {
        this.txtNoUN.setText(null);
        this.txtName.setText(null);
        this.bgGender.clearSelection();
        this.txtBirthPlace.setText(null);
        this.txtBirthDateDay.setText(null);
        this.txtBirthDateMonth.setText(null);
        this.txtBirthDateYear.setText(null);
        this.txtAddress.setText(null);
        this.txtCity.setText(null);
        this.txtSchoolOrigin.setText(null);
        this.txtReligion.setText(null);
        this.txtExam1.setText(null);
        this.txtExam2.setText(null);
        this.txtExam3.setText(null);
        this.txtExam4.setText(null);
        this.txtExamBonus.setText(null);
        this.txtPassword.setText(null);
        
        signupDataString.clear();
        signupDataDouble.clear();
    }
    
    private void setDataAccount() {
        getSignupData();
        String no_pendaftaran = new C_NoPendaftaran().C_NoPendaftaran();
        try {
            String sql1 = "INSERT INTO tbl_akunppdb "
                    + "VALUES "
                    + "('"+ signupDataString.get(0) +"',"
                    + "'"+ signupDataString.get(1) +"',"
                    + "'"+ signupDataString.get(2) +"',"
                    + "'"+ signupDataString.get(3) +"',"
                    + "'"+ signupDataString.get(4) +"',"
                    + "'"+ signupDataString.get(5) +"',"
                    + "'"+ signupDataString.get(6) +"',"
                    + "'"+ signupDataString.get(7) +"',"
                    + "'"+ signupDataString.get(8) +"',"
                    + ""+ signupDataDouble.get(0) +","
                    + ""+ signupDataDouble.get(1) +","
                    + ""+ signupDataDouble.get(2) +","
                    + ""+ signupDataDouble.get(3) +","
                    + ""+ signupDataDouble.get(4) +","
                    + ""+ signupDataDouble.get(5) +","
                    + "'"+ signupDataString.get(9) +"', 0)";
            
            java.sql.Connection conn1 = (Connection)config.configDB();
            java.sql.PreparedStatement pst1 = conn1.prepareStatement(sql1);
            pst1.execute();
            JOptionPane.showMessageDialog(null, "Akun berhasil dibuat, silahkan login");
            resetField();
            this.setVisible(false);
            new F_Login().setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgGender = new javax.swing.ButtonGroup();
        pnlHead = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        pnlBody = new javax.swing.JScrollPane();
        pnlBodyBackground = new javax.swing.JPanel();
        pnlStage1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lbl1 = new javax.swing.JLabel();
        line1 = new javax.swing.JPanel();
        txtNoUN = new javax.swing.JTextField();
        lbl2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        line2 = new javax.swing.JPanel();
        lbl3 = new javax.swing.JLabel();
        bgGenderMale = new javax.swing.JRadioButton();
        bgGenderFemale = new javax.swing.JRadioButton();
        lbl4 = new javax.swing.JLabel();
        line3 = new javax.swing.JPanel();
        txtBirthPlace = new javax.swing.JTextField();
        lbl5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtBirthDateDay = new javax.swing.JTextField();
        line4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtBirthDateMonth = new javax.swing.JTextField();
        line5 = new javax.swing.JPanel();
        txtBirthDateYear = new javax.swing.JTextField();
        line6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbl6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        lbl7 = new javax.swing.JLabel();
        line7 = new javax.swing.JPanel();
        txtCity = new javax.swing.JTextField();
        lbl8 = new javax.swing.JLabel();
        line8 = new javax.swing.JPanel();
        txtSchoolOrigin = new javax.swing.JTextField();
        lbl9 = new javax.swing.JLabel();
        line9 = new javax.swing.JPanel();
        txtReligion = new javax.swing.JTextField();
        pnlStage2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lbl19 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        line19 = new javax.swing.JPanel();
        txtExam1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        line20 = new javax.swing.JPanel();
        txtExam2 = new javax.swing.JTextField();
        txtExam3 = new javax.swing.JTextField();
        line21 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtExam4 = new javax.swing.JTextField();
        line22 = new javax.swing.JPanel();
        txtExamBonus = new javax.swing.JTextField();
        line23 = new javax.swing.JPanel();
        lbl20 = new javax.swing.JLabel();
        pnlStage3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        line28 = new javax.swing.JPanel();
        lbl22 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnFinish = new javax.swing.JPanel();
        lblFinish = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sign Up PPDB ONLINE");
        setMaximumSize(new java.awt.Dimension(620, 480));
        setMinimumSize(new java.awt.Dimension(620, 480));
        setUndecorated(true);
        setResizable(false);

        pnlHead.setBackground(new java.awt.Color(3, 4, 94));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sign Up");
        jLabel1.setToolTipText("");

        jPanel2.setBackground(new java.awt.Color(3, 4, 94));

        lblClose.setFont(new java.awt.Font("Dyuthi", 0, 36)); // NOI18N
        lblClose.setForeground(new java.awt.Color(255, 255, 255));
        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClose.setText("x");
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCloseMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCloseMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlHeadLayout = new javax.swing.GroupLayout(pnlHead);
        pnlHead.setLayout(pnlHeadLayout);
        pnlHeadLayout.setHorizontalGroup(
            pnlHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlHeadLayout.setVerticalGroup(
            pnlHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlBodyBackground.setBackground(new java.awt.Color(0, 119, 182));
        pnlBodyBackground.setMaximumSize(new java.awt.Dimension(600, 9999));
        pnlBodyBackground.setMinimumSize(new java.awt.Dimension(600, 480));
        pnlBodyBackground.setPreferredSize(new java.awt.Dimension(480, 1620));

        pnlStage1.setBackground(new java.awt.Color(204, 204, 204));
        pnlStage1.setPreferredSize(new java.awt.Dimension(522, 900));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        lbl1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl1.setText("No. UN SMP/MTs/Sederajat");

        line1.setBackground(new java.awt.Color(0, 0, 0));
        line1.setMinimumSize(new java.awt.Dimension(0, 1));
        line1.setPreferredSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line1Layout = new javax.swing.GroupLayout(line1);
        line1.setLayout(line1Layout);
        line1Layout.setHorizontalGroup(
            line1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line1Layout.setVerticalGroup(
            line1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtNoUN.setBorder(null);

        lbl2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl2.setText("Nama Lengkap");

        txtName.setBorder(null);

        line2.setBackground(new java.awt.Color(0, 0, 0));
        line2.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line2Layout = new javax.swing.GroupLayout(line2);
        line2.setLayout(line2Layout);
        line2Layout.setHorizontalGroup(
            line2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line2Layout.setVerticalGroup(
            line2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        lbl3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl3.setText("Jenis Kelamin");

        bgGender.add(bgGenderMale);
        bgGenderMale.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        bgGenderMale.setText("Laki-laki");

        bgGender.add(bgGenderFemale);
        bgGenderFemale.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        bgGenderFemale.setText("Perempuan");

        lbl4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl4.setText("Tempat Lahir");

        line3.setBackground(new java.awt.Color(0, 0, 0));
        line3.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line3Layout = new javax.swing.GroupLayout(line3);
        line3.setLayout(line3Layout);
        line3Layout.setHorizontalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line3Layout.setVerticalGroup(
            line3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtBirthPlace.setBorder(null);

        lbl5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl5.setText("Tanggal Lahir");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("Tanggal");

        txtBirthDateDay.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBirthDateDay.setBorder(null);

        line4.setBackground(new java.awt.Color(0, 0, 0));
        line4.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line4Layout = new javax.swing.GroupLayout(line4);
        line4.setLayout(line4Layout);
        line4Layout.setHorizontalGroup(
            line4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line4Layout.setVerticalGroup(
            line4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setText("Bulan");

        txtBirthDateMonth.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBirthDateMonth.setBorder(null);

        line5.setBackground(new java.awt.Color(0, 0, 0));
        line5.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line5Layout = new javax.swing.GroupLayout(line5);
        line5.setLayout(line5Layout);
        line5Layout.setHorizontalGroup(
            line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line5Layout.setVerticalGroup(
            line5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtBirthDateYear.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBirthDateYear.setBorder(null);

        line6.setBackground(new java.awt.Color(0, 0, 0));
        line6.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line6Layout = new javax.swing.GroupLayout(line6);
        line6.setLayout(line6Layout);
        line6Layout.setHorizontalGroup(
            line6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line6Layout.setVerticalGroup(
            line6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setText("Tahun");

        lbl6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl6.setText("Alamat");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        txtAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportView(txtAddress);

        lbl7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl7.setText("Kota/Kabupaten");

        line7.setBackground(new java.awt.Color(0, 0, 0));
        line7.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line7Layout = new javax.swing.GroupLayout(line7);
        line7.setLayout(line7Layout);
        line7Layout.setHorizontalGroup(
            line7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line7Layout.setVerticalGroup(
            line7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtCity.setBorder(null);

        lbl8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl8.setText("Asal Sekolah");

        line8.setBackground(new java.awt.Color(0, 0, 0));
        line8.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line8Layout = new javax.swing.GroupLayout(line8);
        line8.setLayout(line8Layout);
        line8Layout.setHorizontalGroup(
            line8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line8Layout.setVerticalGroup(
            line8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtSchoolOrigin.setBorder(null);

        lbl9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl9.setText("Agama");

        line9.setBackground(new java.awt.Color(0, 0, 0));
        line9.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line9Layout = new javax.swing.GroupLayout(line9);
        line9.setLayout(line9Layout);
        line9Layout.setHorizontalGroup(
            line9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line9Layout.setVerticalGroup(
            line9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtReligion.setBorder(null);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
            .addComponent(line2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(line3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
            .addComponent(line7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(line9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoUN, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBirthPlace, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCity)
                    .addComponent(txtSchoolOrigin)
                    .addComponent(txtReligion)))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl6)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bgGenderMale)
                            .addComponent(bgGenderFemale)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(line4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBirthDateDay, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                                .addGap(30, 30, 30)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(line5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBirthDateMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                                .addGap(30, 30, 30)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtBirthDateYear, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                    .addComponent(line6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(lbl1)
                    .addComponent(lbl2)
                    .addComponent(lbl3)
                    .addComponent(lbl4)
                    .addComponent(lbl5)
                    .addComponent(lbl7)
                    .addComponent(lbl8)
                    .addComponent(lbl9))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(lbl1)
                .addGap(8, 8, 8)
                .addComponent(txtNoUN, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl2)
                .addGap(8, 8, 8)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bgGenderMale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bgGenderFemale)
                .addGap(18, 18, 18)
                .addComponent(lbl4)
                .addGap(8, 8, 8)
                .addComponent(txtBirthPlace, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lbl5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtBirthDateDay, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(line4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtBirthDateMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(line5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBirthDateYear, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(line6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lbl6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl7)
                .addGap(8, 8, 8)
                .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl8)
                .addGap(8, 8, 8)
                .addComponent(txtSchoolOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl9)
                .addGap(8, 8, 8)
                .addComponent(txtReligion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlStage1Layout = new javax.swing.GroupLayout(pnlStage1);
        pnlStage1.setLayout(pnlStage1Layout);
        pnlStage1Layout.setHorizontalGroup(
            pnlStage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStage1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlStage1Layout.setVerticalGroup(
            pnlStage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStage1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlStage2.setBackground(new java.awt.Color(204, 204, 204));
        pnlStage2.setPreferredSize(new java.awt.Dimension(522, 900));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        lbl19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl19.setText("Nilai Bonus Prestasi");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Bahasa Indonesia");

        line19.setBackground(new java.awt.Color(0, 0, 0));
        line19.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line19Layout = new javax.swing.GroupLayout(line19);
        line19.setLayout(line19Layout);
        line19Layout.setHorizontalGroup(
            line19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line19Layout.setVerticalGroup(
            line19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtExam1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExam1.setBorder(null);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("Matematika");

        line20.setBackground(new java.awt.Color(0, 0, 0));
        line20.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line20Layout = new javax.swing.GroupLayout(line20);
        line20.setLayout(line20Layout);
        line20Layout.setHorizontalGroup(
            line20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line20Layout.setVerticalGroup(
            line20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtExam2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExam2.setBorder(null);

        txtExam3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExam3.setBorder(null);

        line21.setBackground(new java.awt.Color(0, 0, 0));
        line21.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line21Layout = new javax.swing.GroupLayout(line21);
        line21.setLayout(line21Layout);
        line21Layout.setHorizontalGroup(
            line21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line21Layout.setVerticalGroup(
            line21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setText("Bahasa Inggris");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel8.setText("Ilmu Pengetahuan Alam");

        txtExam4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExam4.setBorder(null);

        line22.setBackground(new java.awt.Color(0, 0, 0));
        line22.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line22Layout = new javax.swing.GroupLayout(line22);
        line22.setLayout(line22Layout);
        line22Layout.setHorizontalGroup(
            line22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        line22Layout.setVerticalGroup(
            line22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        txtExamBonus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExamBonus.setBorder(null);

        line23.setBackground(new java.awt.Color(0, 0, 0));
        line23.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line23Layout = new javax.swing.GroupLayout(line23);
        line23.setLayout(line23Layout);
        line23Layout.setHorizontalGroup(
            line23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        line23Layout.setVerticalGroup(
            line23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        lbl20.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl20.setText("Nilai Ujian Nasional");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(line19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtExam1)
                    .addComponent(line20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtExam2, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(txtExam3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(line21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtExam4, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(line22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(190, 190, 190))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl19)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(line23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtExamBonus)))
                    .addComponent(lbl20))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(lbl20)
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExam1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExam2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExam3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExam4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lbl19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtExamBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout pnlStage2Layout = new javax.swing.GroupLayout(pnlStage2);
        pnlStage2.setLayout(pnlStage2Layout);
        pnlStage2Layout.setHorizontalGroup(
            pnlStage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStage2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlStage2Layout.setVerticalGroup(
            pnlStage2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlStage3.setBackground(new java.awt.Color(204, 204, 204));
        pnlStage3.setPreferredSize(new java.awt.Dimension(522, 900));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        line28.setBackground(new java.awt.Color(0, 0, 0));
        line28.setMinimumSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout line28Layout = new javax.swing.GroupLayout(line28);
        line28.setLayout(line28Layout);
        line28Layout.setHorizontalGroup(
            line28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        line28Layout.setVerticalGroup(
            line28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        lbl22.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl22.setText("Buat password untuk akun anda");

        txtPassword.setBorder(null);

        btnFinish.setBackground(new java.awt.Color(0, 119, 182));
        btnFinish.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 119, 182), 3));

        lblFinish.setBackground(new java.awt.Color(255, 255, 255));
        lblFinish.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblFinish.setForeground(new java.awt.Color(255, 255, 255));
        lblFinish.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFinish.setText("Buat Akun");
        lblFinish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblFinishMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblFinishMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFinishMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblFinishMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblFinishMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout btnFinishLayout = new javax.swing.GroupLayout(btnFinish);
        btnFinish.setLayout(btnFinishLayout);
        btnFinishLayout.setHorizontalGroup(
            btnFinishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFinish, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        btnFinishLayout.setVerticalGroup(
            btnFinishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFinish, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl22, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(line28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 64, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnFinish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lbl22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnFinish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlStage3Layout = new javax.swing.GroupLayout(pnlStage3);
        pnlStage3.setLayout(pnlStage3Layout);
        pnlStage3Layout.setHorizontalGroup(
            pnlStage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStage3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlStage3Layout.setVerticalGroup(
            pnlStage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout pnlBodyBackgroundLayout = new javax.swing.GroupLayout(pnlBodyBackground);
        pnlBodyBackground.setLayout(pnlBodyBackgroundLayout);
        pnlBodyBackgroundLayout.setHorizontalGroup(
            pnlBodyBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBodyBackgroundLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlBodyBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlStage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlStage3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        pnlBodyBackgroundLayout.setVerticalGroup(
            pnlBodyBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBodyBackgroundLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pnlStage1, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(pnlStage2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(pnlStage3, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pnlBody.setViewportView(pnlBodyBackground);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlBody, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlBody, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered
        lblClose.setForeground(color.red);
    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited
        lblClose.setForeground(color.white);
    }//GEN-LAST:event_lblCloseMouseExited

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        this.setVisible(false);
        new F_Login().setVisible(true);
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblFinishMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFinishMousePressed
        btnFinish.setBackground(color.white);
    }//GEN-LAST:event_lblFinishMousePressed

    private void lblFinishMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFinishMouseReleased
        btnFinish.setBackground(color.blue4);
    }//GEN-LAST:event_lblFinishMouseReleased

    private void lblFinishMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFinishMouseExited
        btnFinish.setBackground(color.blue2);
        lblFinish.setForeground(color.blue4);
    }//GEN-LAST:event_lblFinishMouseExited

    private void lblFinishMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFinishMouseEntered
        btnFinish.setBackground(color.blue4);
        lblFinish.setForeground(color.blue2);
    }//GEN-LAST:event_lblFinishMouseEntered

    private void lblFinishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFinishMouseClicked
        if (
                this.txtNoUN.getText().trim().isEmpty() ||
                this.txtName.getText().trim().isEmpty() ||
                this.bgGenderMale.isSelected() == false && this.bgGenderFemale.isSelected() == false ||
                this.txtBirthPlace.getText().trim().isEmpty() ||
                this.txtBirthDateDay.getText().trim().isEmpty() ||
                this.txtBirthDateMonth.getText().trim().isEmpty() ||
                this.txtBirthDateYear.getText().trim().isEmpty() ||
                this.txtAddress.getText().trim().isEmpty() ||
                this.txtCity.getText().trim().isEmpty() ||
                this.txtSchoolOrigin.getText().trim().isEmpty() ||
                this.txtReligion.getText().trim().isEmpty() ||
                this.txtExam1.getText().trim().isEmpty() ||
                this.txtExam2.getText().trim().isEmpty() ||
                this.txtExam3.getText().trim().isEmpty() ||
                this.txtExam4.getText().trim().isEmpty() ||
                this.txtPassword.getText().trim().isEmpty()
                ) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!");
        } else {
            setDataAccount();
        }
    }//GEN-LAST:event_lblFinishMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgGender;
    private javax.swing.JRadioButton bgGenderFemale;
    private javax.swing.JRadioButton bgGenderMale;
    private javax.swing.JPanel btnFinish;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl19;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl20;
    private javax.swing.JLabel lbl22;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl7;
    private javax.swing.JLabel lbl8;
    private javax.swing.JLabel lbl9;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblFinish;
    private javax.swing.JPanel line1;
    private javax.swing.JPanel line19;
    private javax.swing.JPanel line2;
    private javax.swing.JPanel line20;
    private javax.swing.JPanel line21;
    private javax.swing.JPanel line22;
    private javax.swing.JPanel line23;
    private javax.swing.JPanel line28;
    private javax.swing.JPanel line3;
    private javax.swing.JPanel line4;
    private javax.swing.JPanel line5;
    private javax.swing.JPanel line6;
    private javax.swing.JPanel line7;
    private javax.swing.JPanel line8;
    private javax.swing.JPanel line9;
    private javax.swing.JScrollPane pnlBody;
    private javax.swing.JPanel pnlBodyBackground;
    private javax.swing.JPanel pnlHead;
    private javax.swing.JPanel pnlStage1;
    private javax.swing.JPanel pnlStage2;
    private javax.swing.JPanel pnlStage3;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtBirthDateDay;
    private javax.swing.JTextField txtBirthDateMonth;
    private javax.swing.JTextField txtBirthDateYear;
    private javax.swing.JTextField txtBirthPlace;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtExam1;
    private javax.swing.JTextField txtExam2;
    private javax.swing.JTextField txtExam3;
    private javax.swing.JTextField txtExam4;
    private javax.swing.JTextField txtExamBonus;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNoUN;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtReligion;
    private javax.swing.JTextField txtSchoolOrigin;
    // End of variables declaration//GEN-END:variables
}
