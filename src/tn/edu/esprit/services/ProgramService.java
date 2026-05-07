package tn.edu.esprit.services;

import tn.edu.esprit.entities.Program;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ProgramService implements IService<Program>{

    Connection cnx = DataSource.getConnection();

    @Override
    public void ajouter(Program p){
        String sql="INSERT INTO program(name,level) VALUES (?,?)";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setString(1,p.getName());
            ps.setString(2,p.getLevel());
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    @Override
    public void modifier(Program p){
        String sql="UPDATE program SET name=?, level=? WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setString(1,p.getName());
            ps.setString(2,p.getLevel());
            ps.setInt(3,p.getId());
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    @Override
    public void supprimer(int id){
        String sql="DELETE FROM program WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(SQLException e){System.out.println(e.getMessage());}
    }

    @Override
    public Program getOne(Program p){
        String sql="SELECT * FROM program WHERE id=?";
        try{
            PreparedStatement ps=cnx.prepareStatement(sql);
            ps.setInt(1,p.getId());
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return new Program(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("level"));
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return null;
    }

    @Override
    public List<Program> getAll(Program t){
        List<Program> list=new ArrayList<>();
        String sql="SELECT * FROM program";
        try{
            Statement st=cnx.createStatement();
            ResultSet rs=st.executeQuery(sql);

            while(rs.next()){
                list.add(new Program(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("level")));
            }
        }catch(SQLException e){System.out.println(e.getMessage());}
        return list;
    }
}