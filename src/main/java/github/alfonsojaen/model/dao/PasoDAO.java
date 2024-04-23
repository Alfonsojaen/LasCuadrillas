package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PasoDAO implements DAO<Paso>{

    private static final String FINDALL ="SELECT b.id,b.brotherhood,b.capacity,b.id_cuadrilla FROM paso AS b";
    private static final String FINDBYID ="SELECT b.id,b.brotherhood,b.capacity,b.id_cuadrilla FROM paso AS b WHERE b.id=?";
    private static final String INSERT ="INSERT INTO paso (id,brotherhood,capacity,id_cuadrilla) VALUES (?,?,?,?)";
    private static final String UPDATE ="UPDATE paso SET brotherhood=?,capacity=? WHERE id=?";
    private static final String DELETE ="DELETE FROM paso WHERE id=?";
    private static final String FINDBYPASO ="SELECT b.id,b.brotherhood,b.capacity,b.id_cuadrilla FROM book AS b WHERE b.id_cuadrilla=?";


    private Connection conn;
    public PasoDAO(){
        conn = ConnectionMariaDB.getConnection();
    }
    @Override
    public Paso save(Paso entity)  {
        Paso result=entity;
        if(entity!=null){
            int id = entity.getId();
            if(id != 0){
                Paso isInDataBase = findById(id);
                if(isInDataBase ==null) {
                    //INSERT
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setInt(1, entity.getId());
                        pst.setString(2, entity.getBrotherhood());
                        pst.setInt(3, entity.getCapacity());
                        pst.setString(4, entity.getCuadrilla().getName());

                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    //UPDATE
                    try(PreparedStatement pst = conn.prepareStatement(UPDATE)){
                        pst.setInt(1, entity.getId());
                        pst.setString(2, entity.getBrotherhood());
                        pst.setInt(3, entity.getCapacity());
                        pst.setString(4, entity.getCuadrilla().getName());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Paso delete(Paso entity)  {
        if(entity!=null){
            try (PreparedStatement pst = conn.prepareStatement(DELETE)){
                pst.setInt(1,entity.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity=null;
            }
        }
        return entity;
    }

    @Override
    public Paso findById(int key) {
        Paso result = null;
        try(PreparedStatement pst = conn.prepareStatement(FINDBYID)){
            pst.setInt(1,key);
            try(ResultSet res = pst.executeQuery()){
                if(res.next()){
                    Paso p = new Paso();
                    p.setId(res.getInt(1));
                    //p.setCuadrilla(CuadrillaDAO.build().findById(res.getInt()));
                    p.setBrotherhood(res.getString("brotherhood"));
                    p.setCapacity(res.getInt(2));
                    result=p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Paso> findAll() {
        List<Paso> result = new ArrayList<>();
        try(PreparedStatement pst = conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Paso p = new Paso();
                    p.setId(res.getInt(1));
                    p.setBrotherhood(res.getString("brotherhood"));
                    p.setCapacity(res.getInt(2));
                    result.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Paso> findByPaso(Cuadrilla c){
        List<Paso> result = new ArrayList<>();
        if(c==null || c.getId()==0) return result;
        try(PreparedStatement pst = conn.prepareStatement(FINDBYPASO)){
            pst.setInt(1,c.getId());
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Paso paso = new Paso();
                    paso.setId(res.getInt(1));
                    paso.setBrotherhood(res.getString("brotherhood"));
                    paso.setCuadrilla(c);
                    paso.setCapacity(res.getInt(2));
                    result.add(paso);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void close() throws IOException {

    }
    public static PasoDAO build(){
        return new PasoDAO();
    }
}
