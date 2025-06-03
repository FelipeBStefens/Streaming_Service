package persistence;

import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements MethodsDAO<User>{

    @Override
    public void insert(User user) {
        ConnectionMySql.openConnection();
        String sql = "INSERT INTO user VALUE (NULL, ?, ?, ?, ?);";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setString(1, user.getNickname());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPassword());
            st.setString(4, user.getImage());
            st.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void update(User user) {
        ConnectionMySql.openConnection();
        String sql = "UPDATE user SET nickname=?, email=?, password_user=?, image_user=? WHERE id_user=?;";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setString(1, user.getNickname());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPassword());
            st.setString(4, user.getImage());
            st.setLong(5, user.getIdUser());
            st.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void delete(User user) {
        ConnectionMySql.openConnection();
        String sql = "DELETE * FROM user WHERE id_user=?;";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, user.getIdUser());
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public User searchById(long id) {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM user WHERE id_user=?;";
        User user = null;
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                user = new User(rs.getLong("id_user"), (rs.getString("email")),
                        (rs.getString("password_user")), rs.getString("nickname"),
                        rs.getString("image_user"));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return user;
    }

    @Override
    public List<User> searchAll() {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM user;";
        List<User> listUser = new ArrayList<>();
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                User user = new User(rs.getLong("id_user"), (rs.getString("email")),
                        (rs.getString("password_user")), rs.getString("nickname"),
                        rs.getString("image_user"));

                listUser.add(user);

            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return listUser;
    }

}
