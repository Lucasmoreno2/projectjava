/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lucas
 */
public class conexao {

    private static Connection connection;
    private static Componente Componente;
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException, InstantiationException {
        if (connection == null) {
            try {
                Class.forName("org.apache.derby.jdbc.EmbrddedDriver").newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            }
            String url = "jdbc:derby://localhost:1527/dmemo";
            connection = DriverManager.getConnection("jdbc:derby:dmemo","memo","memo");
        }

        return connection;
    }

    public static ArrayList<Componente> getComponentes() throws ClassNotFoundException, SQLException, InstantiationException {
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM componentes");
        ResultSet rs = ps.executeQuery();
        ArrayList<Componente> componentes = new ArrayList();
        while (rs.next()) {
            Componente componente = new Componente();
            componente.setId(rs.getString("id"));
            componente.setNome(rs.getString("nome"));
            componente.setTipo(rs.getString("tipo"));
            componente.setModelo(rs.getString("modelo"));
            componente.setInformacao(rs.getString("informacao"));
            componente.setPreco(rs.getString("preco"));
            componente.setQuantidade(rs.getString("quantidade"));
            componente.setLocalizacao(rs.getString("localizacao"));

            componentes.add(componente);
        }

        return componentes;
    }

    public static void setComponentes() throws ClassNotFoundException, SQLException, InstantiationException {
        int chave = 0;
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT id FROM componentes");
        ResultSet id = ps.executeQuery();
        while (id.next()) {
            chave = Integer.parseInt(id.getString("id"));
        }
        chave++;
        Statement statement = con.createStatement();
        String query = "Insert into componentes values(" + chave + "," + Adicionar.nome + "," + Adicionar.tipo + "," + Adicionar.modelo + "," + Adicionar.info + "," + Adicionar.preco + "," + Adicionar.localizacao + "," + Adicionar.quantidade + ")";
        int rs = statement.executeUpdate(query);

    }

    public static void delete() throws ClassNotFoundException, SQLException, InstantiationException {
        for (int i = 0; i < View.ids.length; i++) {
            Connection con = conexao.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM componentes WHERE id ="+View.ids[i]);
            ps.execute();
        }
    }
    public static ArrayList<Componente> reposicao() throws ClassNotFoundException, SQLException, InstantiationException {
        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM componentes where quantidade <= 3");
        ResultSet rs = ps.executeQuery();
        ArrayList<Componente> componentes = new ArrayList();
        while (rs.next()) {
            Componente componente = new Componente();
            componente.setId(rs.getString("id"));
            componente.setNome(rs.getString("nome"));
            componente.setTipo(rs.getString("tipo"));
            componente.setModelo(rs.getString("modelo"));
            componente.setInformacao(rs.getString("informacao"));
            componente.setPreco(rs.getString("preco"));
            componente.setQuantidade(rs.getString("quantidade"));
            componente.setLocalizacao(rs.getString("localizacao"));

            componentes.add(componente);
        }

        return componentes;
    }
}
