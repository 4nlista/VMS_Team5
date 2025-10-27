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
public class PageNewService {

    private int RECORDS_PER_PAGE = 3; // số tin tức mỗi trang
    private NewDAO newDAO = new NewDAO();
    
    // Lấy danh sách new theo trang
    public List<New> getNewsByPage(int page) {
        int offset = (page - 1) * RECORDS_PER_PAGE; // vị trí bắt đầu
        return newDAO.getPublishNewsPaged(offset, RECORDS_PER_PAGE);
    }

    // Tính tổng số trang
    public int getTotalPages() {
        int totalRecords = newDAO.getTotalPublishNews();
        return (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
    }
}
