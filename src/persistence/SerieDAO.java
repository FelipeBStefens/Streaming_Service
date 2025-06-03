package persistence;

import models.Movie;
import models.Serie;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SerieDAO implements MethodsDAO<Serie>{

    @Override
    public void insert(Serie serie) {
        ConnectionMySql.openConnection();
        String sqlStream = "INSERT INTO streamTable VALUE (NULL, ?, ?, ?, ?);";
        String sqlSelect = "SELECT MAX(id_stream) FROM streamTable;";
        String sqlSerie = "INSERT INTO serie VALUE (?);";
        try{
            PreparedStatement stStream = ConnectionMySql.getConnection().prepareStatement(sqlStream);
            PreparedStatement stSerie = ConnectionMySql.getConnection().prepareStatement(sqlSerie);
            PreparedStatement stSelect = ConnectionMySql.getConnection().prepareStatement(sqlSelect);

            stStream.setString(1, serie.getTitle());
            stStream.setString(2, serie.getDescription());
            stStream.setString(3, serie.getPoster());
            stStream.setDate(4, Date.valueOf(serie.getReleaseDate()));
            stStream.executeUpdate();

            ResultSet rsSelect = stSelect.executeQuery();

            long idStream = 0;

            if(rsSelect.next()){
                idStream = rsSelect.getLong(1);
            }

            stSerie.setLong(1, idStream);

            stSerie.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void update(Serie serie) {
        ConnectionMySql.openConnection();
        String sql = "UPDATE streamTable SET title=?, description_stream=?, poster=?, release_date=? WHERE id_stream=?";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);

            st.setString(1, serie.getTitle());
            st.setString(2, serie.getDescription());
            st.setString(3, serie.getPoster());
            st.setDate(4, Date.valueOf(serie.getReleaseDate()));
            st.setLong(5,serie.getIdStream());
            st.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void delete(Serie serie) {
        ConnectionMySql.openConnection();
        String sql = "DELETE FROM streamTable WHERE id_stream=?;";
        String sqlMovie = "DELETE FROM serie WHERE id_serie=?;";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            PreparedStatement stSerie = ConnectionMySql.getConnection().prepareStatement(sqlMovie);
            st.setLong(1,serie.getIdStream());
            stSerie.setLong(1,serie.getIdSerie());

            stSerie.executeUpdate();
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public Serie searchById(long id) {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM serie m INNER JOIN streamTable s ON m.id_serie = s.id_stream WHERE id_serie=?";
        Serie serie = null;
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                serie = new Serie(rs.getLong("id_serie"), rs.getLong("id_stream"), rs.getString("title"),
                        rs.getString("description_stream"), rs.getString("poster"),
                        null);
                LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
                serie.setReleaseDate(releaseDate);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return serie;
    }

    @Override
    public List<Serie> searchAll() {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM serie m INNER JOIN streamTable s ON m.id_serie = s.id_stream";
        List<Serie> listSeries = new ArrayList<>();
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Serie serie = new Serie(rs.getLong("id_serie"), rs.getLong("id_stream"), rs.getString("title"),
                        rs.getString("description_stream"), rs.getString("poster"),
                        null);
                LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
                serie.setReleaseDate(releaseDate);

                listSeries.add(serie);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return listSeries;
    }

}
