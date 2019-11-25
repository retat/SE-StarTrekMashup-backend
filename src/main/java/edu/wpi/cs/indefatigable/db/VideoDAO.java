package edu.wpi.cs.indefatigable.db;

import edu.wpi.cs.indefatigable.model.RemotelyAvailableVideo;
import edu.wpi.cs.indefatigable.model.Video;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class VideoDAO {
    java.sql.Connection conn;

    public VideoDAO() {
        try {
            conn = DatabaseUtil.connect();
        } catch (Exception e) {
            conn = null;
        }
    }

    public VideoDAO(java.sql.Connection conn) {
        this.conn = conn;
    }

    public Video getVideo(String vuid) throws Exception {
        try {
            Video video = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Video WHERE vuid=?;");
            ps.setString(1, vuid);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                video = generateVideo(resultSet);
            }
            resultSet.close();
            ps.close();

            return video;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in getting video: " + e.getMessage());
        }
    }

    public boolean deleteVideo(String vuid) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Video WHERE vuid=?;");
            ps.setString(1, vuid);
            int numAffected = ps.executeUpdate();
            ps.close();

            return (numAffected == 1);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in deleting video: " + e.getMessage());
        }
    }

    public boolean markVideoAsRemote(String vuid) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Video SET remoteAvailability=1 WHERE vuid=?");
            ps.setString(1, vuid);
            int numAffected = ps.executeUpdate();
            ps.close();
            return (numAffected == 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in marking video: " + e.getMessage());
        }
    }

    public boolean unmarkVideoAsRemote(String vuid) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Video SET remoteAvailability=0 WHERE vuid=?");
            ps.setString(1, vuid);
            int numAffected = ps.executeUpdate();
            ps.close();
            return (numAffected == 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in unmarking video: " + e.getMessage());
        }
    }

    private Video generateVideo(ResultSet resultSet) throws SQLException {
        String vuid = resultSet.getString("vuid");
        String url = resultSet.getString("url");
        boolean remoteAvailability = resultSet.getBoolean("remoteAvailability");
        boolean isRemote = resultSet.getBoolean("isRemote");
        String character = resultSet.getString("character");
        String transcript = resultSet.getString("transcript");
        String title = resultSet.getString("title");
        return new Video(vuid, url, remoteAvailability, isRemote, character, transcript, title);

    }

    private RemotelyAvailableVideo generateRAVideo(ResultSet resultSet) throws SQLException {
        String url = resultSet.getString("url");
        String character = resultSet.getString("character");
        String transcript = resultSet.getString("transcript");
        return new RemotelyAvailableVideo(url, character, transcript);
    }
    
    public ArrayList<Video> queryAllVideos(String character, String transcript) throws Exception {
        try {
            ArrayList<Video> aVideos = new ArrayList<>();
            PreparedStatement statement;
            
            // If character is blank
            if (transcript.contentEquals("")) {
            	statement = conn.prepareStatement("SELECT * FROM Video WHERE character LIKE ?;");
            	statement.setString(1, character);
            // If transcript is blank
            } else if (character.contentEquals("")) {
            	statement = conn.prepareStatement("SELECT * FROM Video WHERE transcript LIKE ?;");
            	statement.setString(1, transcript);
            // If both are not blank
            } else {
            	statement = conn.prepareStatement("SELECT * FROM Video WHERE transcript LIKE ? AND character LIKE ?;");
            	statement.setString(1, transcript);
            	statement.setString(2, character);
            }
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Video v = generateVideo(resultSet);
                aVideos.add(v);
            }
            
            resultSet.close();
            statement.close();
            return aVideos;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed in getting Videos: " + e.getMessage());
        }
    }
    
    

    public ArrayList<Video> getAllVideos() throws Exception {
        try {
            ArrayList<Video> aVideos = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Video");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Video v = generateVideo(resultSet);
                aVideos.add(v);
            }
            resultSet.close();
            statement.close();
            return aVideos;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed in getting Videos: " + e.getMessage());
        }
    }

    public ArrayList<RemotelyAvailableVideo> getAllRAVideos() throws Exception {
        try {
            ArrayList<RemotelyAvailableVideo> allVideos = new ArrayList<RemotelyAvailableVideo>();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Video WHERE remoteAvailability=1");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RemotelyAvailableVideo v = generateRAVideo(resultSet);
                allVideos.add(v);
            }
            resultSet.close();
            statement.close();
            return allVideos;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed in getting RA Videos: " + e.getMessage());
        }
    }


}
