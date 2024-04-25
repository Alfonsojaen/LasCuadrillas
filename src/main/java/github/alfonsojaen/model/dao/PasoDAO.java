package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasoDAO implements DAO<Paso>{

    private static final String FINDALL ="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a";
    private static final String FINDBYID ="SELECT a.id,a.brotherhood,a.capacity FROM paso AS a WHERE a.id=?";
    private static final String INSERT ="INSERT INTO paso (id,brotherhood,capacity) VALUES (?,?,?)";
    private static final String UPDATE ="UPDATE paso SET brotherhood=?,capacity=? WHERE id=?";
    private static final String DELETE ="DELETE FROM paso WHERE id=?";


    private Connection conn;
    public PasoDAO(){
        conn = ConnectionMariaDB.getConnection();
    }
    @Override
    public Paso save(Paso entity) {
        Paso result = entity;
        if (entity == null) return result;
        Paso p = findById(entity.getId());
        if (p.getId() == 0) {
            //INSERT
            try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getBrotherhood());
                pst.setInt(3, entity.getCapacity());
                pst.setInt(4, entity.getCuadrilla().getId());
                pst.executeUpdate();
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        entity.setId(generatedId);
                        result = entity;
                    }

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            //UPDATE
            try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getBrotherhood());
                pst.setInt(3, entity.getCapacity());
                pst.setString(4, entity.getCuadrilla().getName());
                pst.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
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
        Paso result = new Paso();
        if(key == 0) return result;

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)){
            pst.setInt(1,key);
            ResultSet res = pst.executeQuery();
                if(res.next()){
                    result.setId(res.getInt(1));
                    result.setBrotherhood(res.getString("brotherhood"));
                    result.setCapacity(res.getInt(1));

                }
                res.close();
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

    @Override
    public void close() throws IOException {

    }
    public static PasoDAO build(){
        return new PasoDAO();
    }
}
