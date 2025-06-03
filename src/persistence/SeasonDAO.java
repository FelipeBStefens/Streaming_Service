package persistence;

import models.Season;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeasonDAO implements MethodsDAO<Season>{

    @Override
    public void insert(Season objDAO) {
        ConnectionMySql.openConnection();
        String sql = "INSERT INTO season VALUES (NULL, ?, ?)";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setInt(1, objDAO.getNumber());
            st.setLong(2, objDAO.getSerie().getIdSerie());
            st.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void update(Season objDAO) {
        ConnectionMySql.openConnection();
        String sql = "UPDATE season SET number_season=? WHERE id_season=?;";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setInt(1, objDAO.getNumber());
            st.setLong(2, objDAO.getIdSeason());
            st.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void delete(Season objDAO) {
        ConnectionMySql.openConnection();
        String sql = "DELETE FROM season WHERE id_season=?;";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, objDAO.getIdSeason());
            st.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public Season searchById(long id) {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM season WHERE id_season=?;";
        Season season = null;
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                season = new Season(rs.getLong("id_season"), rs.getInt("number_season"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return season;
    }

    @Override
    public List<Season> searchAll() {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM season;";
        List<Season> listSeason = new ArrayList<>();
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Season season = new Season(rs.getLong("id_season"), rs.getInt("number_season"));
                listSeason.add(season);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return listSeason;
    }
}
