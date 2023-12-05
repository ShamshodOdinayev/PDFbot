package com.company.repository;

import com.company.db.DataBase;
import com.company.dto.Profile;
import com.company.enums.ProfileRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Repository {

    public Integer createProfile(Profile profile) {
        Connection connection = null;
        try {
            connection = DataBase.getConnection();
            String sql = "insert into profile(fullName,chatId,phone,role) " + "values ('%s','%s','%s','%s');";
            sql = String.format(sql, profile.getFullName(), profile.getChatId(), profile.getPhone(), profile.getRole().toString());
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return 0;
    }

    public Integer saveProfile(Profile profile) {
        Connection connection = null;
        try {
            connection = DataBase.getConnection();
            String sql = "insert into profile(fullName,chatId,phone,role) " + "values ('%s','%s','%s','%s');";
            sql = String.format(sql, profile.getFullName(), profile.getChatId(), profile.getPhone(), profile.getRole().toString());
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return 0;
    }

    public Profile getProfileByChatId(String chatId) {
        Connection connection = null;
        try {
            connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            String sql = String.format("Select  * from profile where chatId= '%s';", chatId);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Profile profile = new Profile();
                profile.setId(resultSet.getInt("id"));
                profile.setFullName(resultSet.getString("fullName"));
                profile.setChatId(resultSet.getString("chatId"));
                profile.setPhone(resultSet.getString("phone"));
                profile.setRole(ProfileRole.valueOf(resultSet.getString("role")));
                return profile;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return null;
    }
    public List<Profile> getAll(){
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from profile");
            List<Profile> profileList = new LinkedList<>();
            while (resultSet.next()) {
                Profile profile = new Profile();
                profile.setId(resultSet.getInt("id"));
                profile.setFullName(resultSet.getString("fullName"));
                profile.setChatId(resultSet.getString("chatId"));
                profile.setPhone(resultSet.getString("phone"));
                profile.setRole(ProfileRole.valueOf(resultSet.getString("role")));
                profileList.add(profile);
            }
            return profileList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
