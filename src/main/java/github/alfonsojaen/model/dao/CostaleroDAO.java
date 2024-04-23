package github.alfonsojaen.model.dao;

import github.alfonsojaen.model.connection.ConnectionMariaDB;
import github.alfonsojaen.model.entity.Costalero;
import github.alfonsojaen.model.entity.Cuadrilla;
import github.alfonsojaen.model.entity.Paso;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CostaleroDAO implements DAO<Costalero> {
    private final static String INSERT = "INSERT INTO costalero (id,nickname,height,age) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE costalero SET nickname=?, height=?, age=? WHERE id=?";
    private final static String FINDALL = "SELECT  a.id,a.nickname,a.height,a.age FROM costalero AS a";
    private final static String FINDBYID = "SELECT a.id,a.nickname,a.height,a.age FROM costalero AS a WHERE a.id=?";
    private final static String DELETE = "DELETE FROM costalero AS a WHERE a.id=?";
    private static final String FINDBYCUADRILLA = "SELECT b.id,b.nickname,b.height,a.ageb,a.id_cuadrilla FROM book AS b WHERE b.id_cuadrilla=?";

    private Connection conn;

    public CostaleroDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Costalero save(Costalero entity) {
        Costalero result = entity;
        if (entity != null || entity.getId() == 0) return result;
        Costalero c = findById(entity.getId());
        if (c.getId() == 0) {
            //INSERT
            try (PreparedStatement pst = this.conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getNickname());
                pst.setInt(3, entity.getHeight());
                pst.setInt(4, entity.getAge());
                pst.executeUpdate();

                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }

                if (entity.getCuadrillas() != null) {
                    for (Cuadrilla cuadrilla : entity.getCuadrillas()) {
                        CuadrillaDAO.build().save(cuadrilla);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                pst.setInt(1, entity.getId());
                pst.setString(2, entity.getNickname());
                pst.setInt(3, entity.getHeight());
                pst.setInt(4, entity.getAge());
                pst.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    @Override
    public Costalero delete(Costalero entity) throws SQLException {
        if (entity == null || entity.getId() == 0) return entity;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
        }
        return entity;
    }

    @Override
    public Costalero findById(int key) {
        Costalero result = new CostaleroLazy();
        if (key == 0) return result;

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, key);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                result.setId(res.getInt(1));
                result.setNickname(res.getString("nickname"));
                result.setHeight(res.getInt(2));
                result.setAge(res.getInt(3));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Costalero> findAll() {
        List<Costalero> result = new ArrayList<>();

        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Costalero costalero = new CostaleroLazy();
                costalero.setId(res.getInt(1));
                costalero.setNickname(res.getString("nickname"));
                costalero.setHeight(res.getInt(2));
                costalero.setAge(res.getInt(3));
                result.add(costalero);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public List<Costalero> findByCuadrilla(Cuadrilla c) {
        List<Costalero> result = new ArrayList<>();
        if (c == null || c.getId() == 0) return result;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYCUADRILLA)) {
            pst.setInt(1, c.getId());
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Costalero cst = new Costalero();
                    c.setId(res.getInt(1));
                    c.setName(res.getString("name"));
                    cst.setCuadrillas(c);
                    c.setOverseer(res.getString("overseer"));
                    c.setDescription(res.getString("descripcion"));
                    result.add(cst);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static CostaleroDAO build() {
        return new CostaleroDAO();
    }

    class CostaleroLazy extends Costalero {
        @Override
        public List<Cuadrilla> getCuadrillas() {
            if (super.getCuadrillas() == null) {
                setCuadrillas(CuadrillaDAO.build().f(this));
            }
            return super.getCuadrillas();
        }
    }
}
