package tn.edu.esprit.services;

import tn.edu.esprit.entities.Session;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class SessionService implements IService<Session>{

    Connection cnx = DataSource.getConnection();

    public void ajouter(Session s){
        String sql="INSERT INTO session(program_id,duration) VALUES (?,?)";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,s.getProgramId());
            ps.setInt(2,s.getDuration());
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    public void modifier(Session s){
        String sql="UPDATE session SET program_id=?, duration=? WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,s.getProgramId());
            ps.setInt(2,s.getDuration());
            ps.setInt(3,s.getId());
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    public void supprimer(int id){
        String sql="DELETE FROM session WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    public Session getOne(Session s){
        String sql="SELECT * FROM session WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,s.getId());
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return new Session(
                        rs.getInt("id"),
                        rs.getInt("program_id"),
                        rs.getInt("duration"));
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return null;
    }

    public List<Session> getAll(Session t){
        List<Session> list=new ArrayList<>();
        String sql="SELECT * FROM session";
        try{
            Statement st=cnx.createStatement();
            ResultSet rs=st.executeQuery(sql);

            while(rs.next()){
                list.add(new Session(
                        rs.getInt("id"),
                        rs.getInt("program_id"),
                        rs.getInt("duration")));
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return list;
    }
}