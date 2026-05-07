package tn.edu.esprit.services;

import tn.edu.esprit.entities.Exercise;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ExerciseService implements IService<Exercise>{

    Connection cnx = DataSource.getConnection();

    public void ajouter(Exercise e){
        String sql="INSERT INTO exercise(name,muscle_group) VALUES (?,?)";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setString(1,e.getName());
            ps.setString(2,e.getMuscleGroup());
            ps.executeUpdate();
        }catch(SQLException ex){System.out.println(ex.getMessage());}
    }

    public void modifier(Exercise e){
        String sql="UPDATE exercise SET name=?, muscle_group=? WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setString(1,e.getName());
            ps.setString(2,e.getMuscleGroup());
            ps.setInt(3,e.getId());
            ps.executeUpdate();
        }catch(SQLException ex){System.out.println(ex.getMessage());}
    }

    public void supprimer(int id){
        String sql="DELETE FROM exercise WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(SQLException ex){System.out.println(ex.getMessage());}
    }

    public Exercise getOne(Exercise e){
        String sql="SELECT * FROM exercise WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,e.getId());
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return new Exercise(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("muscle_group"));
            }
        }catch(SQLException ex){System.out.println(ex.getMessage());}
        return null;
    }

    public List<Exercise> getAll(Exercise t){
        List<Exercise> list=new ArrayList<>();
        String sql="SELECT * FROM exercise";
        try{
            Statement st=cnx.createStatement();
            ResultSet rs=st.executeQuery(sql);

            while(rs.next()){
                list.add(new Exercise(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("muscle_group")));
            }
        }catch(SQLException ex){System.out.println(ex.getMessage());}
        return list;
    }
}