package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import utils.DBContext;

public class CategoriesDAO {

    // Lấy toàn bộ category từ DB
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, name, description FROM Categories";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name")); 
                category.setDescription(rs.getString("description"));
                list.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy category theo ID
    public Category getCategoryById(int id) {
        Category category = null;
        String sql = "SELECT category_id, name, description FROM Categories WHERE category_id = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    category = new Category();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setName(rs.getString("name"));
                    category.setDescription(rs.getString("description"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }
}
