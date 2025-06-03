package persistence;

import models.Rating;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO implements MethodsDAO<Rating>{

    @Override
    public void insert(Rating objDAO) {
        ConnectionMySql.openConnection();
        String sql = "INSERT INTO rating VALUES (NULL, ?, ?, ?);";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setInt(1, objDAO.getRating());
            st.setLong(2, objDAO.getStream().getIdStream());
            st.setLong(3, objDAO.getUser().getIdUser());
            st.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void update(Rating objDAO) {
        ConnectionMySql.openConnection();
        String sql = "UPDATE rating SET rate=?, id_stream=?, id_user=? WHERE id_rating=?";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setInt(1, objDAO.getRating());
            st.setLong(2, objDAO.getStream().getIdStream());
            st.setLong(3, objDAO.getUser().getIdUser());
            st.setLong(4, objDAO.getIdRating());
            st.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void delete(Rating objDAO) {
        ConnectionMySql.openConnection();
        String sql = "DELETE FROM rating WHERE id_rating=?";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, objDAO.getIdRating());
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public Rating searchById(long id) {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM rating WHERE id_rating=?";
        Rating rating = null;
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            if(rs.next()){
                rating = new Rating(rs.getLong("id_rating"), rs.getInt("rate"));
                rating.getUser().setIdUser(rs.getLong("id_user"));
                rating.getStream().setIdStream(rs.getLong("id_stream"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return rating;
    }

    @Override
    public List<Rating> searchAll() {
        String sql = "SELECT * FROM rating";
        List<Rating> listRating = new ArrayList<>();
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Rating rating = new Rating(rs.getLong("id_rating"), rs.getInt("rate"));
                rating.getUser().setIdUser(rs.getLong("id_user"));
                rating.getStream().setIdStream(rs.getLong("id_stream"));

                listRating.add(rating);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return listRating;
    }
}
