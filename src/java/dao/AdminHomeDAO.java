/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import utils.DBContext;

/**
 *
 * @author Admin
 */
public class AdminHomeDAO {

    private Connection conn;

    public AdminHomeDAO() {
        try {
            DBContext db = new DBContext();
            this.conn = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public List<Account> getTotalAccount() {
        List<Account> list = new ArrayList<>();
        return list;
    }
        
}
