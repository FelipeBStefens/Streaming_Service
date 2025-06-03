package persistence;

import models.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements MethodsDAO<Movie>{

    @Override
    public void insert(Movie movie) {
        ConnectionMySql.openConnection();
        String sqlStream = "INSERT INTO streamTable VALUE (NULL, ?, ?, ?, ?);";
        String sqlSelect = "SELECT MAX(id_stream) FROM streamTable;";
        String sqlMovie = "INSERT INTO movie VALUE (?, ?);";
        try{
            PreparedStatement stStream = ConnectionMySql.getConnection().prepareStatement(sqlStream);
            PreparedStatement stMovie = ConnectionMySql.getConnection().prepareStatement(sqlMovie);
            PreparedStatement stSelect = ConnectionMySql.getConnection().prepareStatement(sqlSelect);

            stStream.setString(1, movie.getTitle());
            stStream.setString(2, movie.getDescription());
            stStream.setString(3, movie.getPoster());
            stStream.setDate(4, Date.valueOf(movie.getReleaseDate()));
            stStream.executeUpdate();

            ResultSet rsSelect = stSelect.executeQuery();

            long idStream = 0;

            if(rsSelect.next()){
                idStream = rsSelect.getLong(1);
            }

            stMovie.setLong(1, idStream);
            Time duration = Time.valueOf(movie.getDuration());
            stMovie.setTime(2, duration);

            stMovie.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void update(Movie movie) {
        ConnectionMySql.openConnection();
        String sql = "UPDATE streamTable SET title=?, description_stream=?, poster=?, release_date=? WHERE id_stream=?";
        String sqlMovie = "UPDATE movie SET duration=? WHERE id_movie=?";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            PreparedStatement stMovie = ConnectionMySql.getConnection().prepareStatement(sqlMovie);

            st.setString(1, movie.getTitle());
            st.setString(2, movie.getDescription());
            st.setString(3, movie.getPoster());
            st.setDate(4, Date.valueOf(movie.getReleaseDate()));
            st.setLong(5,movie.getIdStream());
            st.executeUpdate();

            Time duration = Time.valueOf(movie.getDuration());
            stMovie.setTime(1, duration);
            stMovie.setLong(2,movie.getIdMovie());

            stMovie.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public void delete(Movie movie) {
        ConnectionMySql.openConnection();
        String sql = "DELETE FROM streamTable WHERE id_stream=?;";
        String sqlMovie = "DELETE FROM movie WHERE id_movie=?;";
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            PreparedStatement stMovie = ConnectionMySql.getConnection().prepareStatement(sqlMovie);
            st.setLong(1,movie.getIdStream());
            stMovie.setLong(1,movie.getIdMovie());

            stMovie.executeUpdate();
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
    }

    @Override
    public Movie searchById(long id) {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM movie AS m INNER JOIN streamTable AS s ON m.id_movie = s.id_stream WHERE id_movie=?";
        Movie movie = null;
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                movie = new Movie(rs.getLong("id_movie"), null,
                        rs.getLong("id_stream"), rs.getString("title"),
                        rs.getString("description_stream"), rs.getString("poster"),
                        null);
                LocalTime duration = rs.getTime("duration").toLocalTime();
                movie.setDuration(duration);
                LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
                movie.setReleaseDate(releaseDate);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return movie;
    }

    @Override
    public List<Movie> searchAll() {
        ConnectionMySql.openConnection();
        String sql = "SELECT * FROM movie AS m INNER JOIN streamTable AS s ON m.id_movie = s.id_stream";
        List<Movie> listMovies = new ArrayList<>();
        try{
            PreparedStatement st = ConnectionMySql.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Movie movie = new Movie(rs.getLong("id_movie"), null,
                        rs.getLong("id_stream"), rs.getString("title"),
                        rs.getString("description_stream"), rs.getString("poster"),
                        null);
                LocalTime duration = rs.getTime("duration").toLocalTime();
                movie.setDuration(duration);
                LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
                movie.setReleaseDate(releaseDate);

                listMovies.add(movie);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            ConnectionMySql.closeConnection();
        }
        return listMovies;
    }

}
