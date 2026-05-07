package tn.edu.esprit.services;

import tn.edu.esprit.entities.Progress;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ProgressService implements IService<Progress>{

    Connection cnx = DataSource.getConnection();

    public void ajouter(Progress p){
        String sql="INSERT INTO progress(user_id,performance) VALUES (?,?)";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,p.getUserId());
            ps.setString(2,p.getPerformance());
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    public void modifier(Progress p){
        String sql="UPDATE progress SET user_id=?, performance=? WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,p.getUserId());
            ps.setString(2,p.getPerformance());
            ps.setInt(3,p.getId());
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    public void supprimer(int id){
        String sql="DELETE FROM progress WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    public Progress getOne(Progress p){
        String sql="SELECT * FROM progress WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,p.getId());
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return new Progress(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("performance"));
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return null;
    }

    public List<Progress> getAll(Progress t){
        List<Progress> list=new ArrayList<>();
        String sql="SELECT * FROM progress";
        try{
            Statement st=cnx.createStatement();
            ResultSet rs=st.executeQuery(sql);

            while(rs.next()){
                list.add(new Progress(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("performance")));
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return list;
    }
}