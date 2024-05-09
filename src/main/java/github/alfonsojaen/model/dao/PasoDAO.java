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
    private static final String INSERT ="INSERT INTO paso (brotherhood,capacity) VALUES (?,?)";
    private static final String UPDATE ="UPDATE paso SET brotherhood=?,capacity=? WHERE id=?";
    private static final String DELETE ="DELETE FROM paso WHERE id=?";


    private Connection conn;
    public PasoDAO(){
        conn = ConnectionMariaDB.getConnection();
    }


    @Override
    public Paso save(Paso paso) throws SQLException{
        if (paso != null ) {
        Paso p = findById(paso.getId());
        if (p.getId() == 0) {
            //INSERT
            try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, paso.getBrotherhood());
                pst.setInt(2, paso.getCapacity());
                pst.executeUpdate();
                ResultSet r = pst.getGeneratedKeys();
                if (r.next()) {
                    int generatedId = r.getInt(1);
                    paso.setId(generatedId);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                paso = null;
            }
            }
        }
        return paso;
    }
public void update(Paso paso){
    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
        if(paso!=null){
        pst.setString(1, paso.getBrotherhood());
        pst.setInt(2, paso.getCapacity());
        pst.setInt(3, paso.getId());
        pst.executeUpdate();
        } else {
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    @Override
    public Paso delete(Paso paso)  {
        if(paso!=null){
            try (PreparedStatement pst = conn.prepareStatement(DELETE)){
                pst.setInt(1,paso.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                paso=null;
            }
        }
        return paso;
    }

    @Override
    public Paso findById(int key) {
        Paso result = new Paso();
        if(key != 0){

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)){
            pst.setInt(1,key);
            ResultSet res = pst.executeQuery();
                if(res.next()){
                    result.setId(res.getInt(1));
                    result.setBrotherhood(res.getString("brotherhood"));
                    result.setCapacity(res.getInt(3));

                }
                res.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
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
                    p.setCapacity(res.getInt(3));
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
