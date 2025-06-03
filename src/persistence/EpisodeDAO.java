package persistence;

import models.Episode;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class EpisodeDAO implements MethodsDAO<Episode>{


    @Override
    public void insert(Episode objDAO) {
        ConnectionMySql.openConnection();
        String sql = "INSERT INTO episode VALUES (NULL, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setString(1, objDAO.getTitle());
            st.setString(2, objDAO.getDescription());
            st.setDate(3, Date.valueOf(objDAO.getReleaseDate()));
            st.setTime(4, Time.valueOf(objDAO.getDuration()));
            st.setLong(5, objDAO.getSeason().getIdSeason());
            st.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void update(Episode objDAO) {
        ConnectionMySql.openConnection();
        String sql = "UPDATE season SET title=?, description_stream=?, release_date=?, duration=?, id_season=? WHERE id_episode=?;";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setString(1, objDAO.getTitle());
            st.setString(2, objDAO.getDescription());
            st.setDate(3, Date.valueOf(objDAO.getReleaseDate()));
            st.setTime(4, Time.valueOf(objDAO.getDuration()));
            st.setLong(5, objDAO.getSeason().getIdSeason());
            st.setLong(6, objDAO.getIdEpisode());
            st.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void delete(Episode objDAO) {
        ConnectionMySql.openConnection();
        String sql = "DELETE FROM episode WHERE id_episode=?";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, objDAO.getIdEpisode());
            st.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public Episode searchById(long id) {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM episode WHERE id_episode=?";
        Episode episode = null;
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                episode = new Episode(rs.getLong("id_episode"),
                        rs.getString("title"), rs.getString("description_stream"),
                        null, null);
                episode.setReleaseDate(rs.getDate("release_date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                episode.setDuration(rs.getTime("duration").toLocalTime());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return episode;
    }

    @Override
    public List<Episode> searchAll() {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM episode";
        List<Episode> listEpisode = new ArrayList<>();
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                Episode episode = new Episode(rs.getLong("id_episode"),
                        rs.getString("title"), rs.getString("description_stream"),
                        null, null);
                episode.setReleaseDate(rs.getDate("release_date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                episode.setDuration(rs.getTime("duration").toLocalTime());

                listEpisode.add(episode);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionMySql.closeConnection();
        }
        return listEpisode;
    }
}
