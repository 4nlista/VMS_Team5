/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.NewDAO;
import java.util.List;
import model.New;

/**
 *
 * @author ADDMIN
 */
public class DisplayNewService {
     private NewDAO newsDAO;

    public DisplayNewService() {
        newsDAO = new NewDAO();
    }
    
    // trả về danh sách các bài viết đang published [đã xuất bản]
    public List<New> getAllPostNews() {
        return newsDAO.getAllPostNews();
    }

    // trả về danh sách 3 bài viết mới nhất đang published [đã xuất bản]
    public List<New> getTop3PostNews() {
        return newsDAO.getTop3PostNews();
    }
    
    
}
