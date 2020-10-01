/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivan.ppdbonline.form;

import java.util.Calendar;
import java.util.Random;
/**
 *
 * @author ivanlubuntu
 */
public class C_NoPendaftaran {
    
    private String getLastNumberOfThisYear() {
        return lastNumberOfThisYear;
    }

    private void setLastNumberOfThisYear() {
        String lastNumberOfThisYear;
        char[] a = {
                (char)Integer.toString(calendar.get(Calendar.YEAR))
                .charAt(Integer.toString(calendar.get(Calendar.YEAR)).length() -2),
                (char)Integer.toString(calendar.get(Calendar.YEAR))
                .charAt(Integer.toString(calendar.get(Calendar.YEAR)).length() -1)
        };
        
        String b = new String(a);
        lastNumberOfThisYear = b;
        this.lastNumberOfThisYear = lastNumberOfThisYear;
    }

    private String getNumberOfThisMonth() {
        return numberOfThisMonth;
    }

    private void setNumberOfThisMonth() {
        String numberOfThisMonth;
        int a = calendar.get(Calendar.MONTH);
        if (a < 10) {
            numberOfThisMonth = "0"+Integer.toString(calendar.get(Calendar.MONTH));
        } else {
            numberOfThisMonth = Integer.toString(calendar.get(Calendar.MONTH));
        }
        
        this.numberOfThisMonth = numberOfThisMonth;
    }

    private String getNumberOfThisDate() {
        return numberOfThisDate;
    }

    private void setNumberOfThisDate() {
        String numberOfThisDate;
        int a = calendar.get(Calendar.DATE);
        if (a < 10) {
            numberOfThisDate = "0"+Integer.toString(calendar.get(Calendar.DATE));
        } else {
            numberOfThisDate = Integer.toString(calendar.get(Calendar.DATE));
        }
        this.numberOfThisDate = numberOfThisDate;
    }
    
    public String getNumberOfThisSecond() {
        return numberOfThisSecond;
    }
    
    public void setNumberOfThisSecond() {
        String numberOfThisSecond;
        int a = calendar.get(Calendar.DATE);
        if (a < 10) {
            numberOfThisSecond = "0"+Integer.toString(calendar.get(Calendar.SECOND));
        } else {
            numberOfThisSecond = Integer.toString(calendar.get(Calendar.SECOND));
        }
        this.numberOfThisSecond = numberOfThisSecond;
    }

    private String getNumberOfRandom() {
        return numberOfRandom;
    }

    private void setNumberOfRandom() {
        String numberOfRandom;
        numberOfRandom = Integer.toString(random.nextInt(10));
        this.numberOfRandom = numberOfRandom;
    }
    
    Calendar calendar = Calendar.getInstance();
    Random random = new Random();
    
    private String lastNumberOfThisYear;
    private String numberOfThisMonth;
    private String numberOfThisDate;
    private String numberOfThisSecond;
    private String numberOfRandom;
    private String noPendaftaran;
    
    public String C_NoPendaftaran() {
        setLastNumberOfThisYear();
        setNumberOfThisMonth();
        setNumberOfThisDate();
        setNumberOfRandom();
        setNumberOfThisSecond();
        
        this.noPendaftaran =
                getLastNumberOfThisYear()+
                getNumberOfThisMonth()+
                getNumberOfThisDate()+
                getNumberOfThisSecond()+
                getNumberOfRandom();
        
        return noPendaftaran;
    }
    
}
