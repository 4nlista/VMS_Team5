/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.DonationDAO;

/**
 *
 * @author ADDMIN
 */
public class SumDisplayService {
    private DonationDAO donationDAO = new DonationDAO();

    public double getTotalDonations() {
        return donationDAO.getTotalDonationAmount();
    }
    
    public int getTotalApply() {
         return donationDAO.getTotalApply();
    }
}
