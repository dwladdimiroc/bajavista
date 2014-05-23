/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bajavista;

import conexionmysql.ConexionBD;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author daniel
 */
public class Actualizar {

    String dirBD;
    ArrayList<Informacion> datos = new ArrayList();

    public Actualizar(String dirBD) {
        this.dirBD = dirBD;
    }

    public void actualizarBaseDato() throws SQLException, FileNotFoundException, IOException {
        ConexionBD bd = new ConexionBD();

        Statement consulta = bd.getConnection().createStatement();
        parsearTexto(dirBD);
        for (int i = 0; i < datos.size() ; i++) {
            consulta.executeUpdate("INSERT INTO TweetES (idioma, timestamp, texto) VALUES ('"+datos.get(i).getIdioma()+"','"+datos.get(i).getDataTime()+"','"+datos.get(i).getTexto()+"')");
        }
        consulta.close();

        bd.desconectar();
    }

    public void parsearTexto(String file) throws FileNotFoundException, IOException {
        Informacion info;
        
        File f = new File(file);
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        
        String linea = br.readLine();

        while ((linea = br.readLine()) != null) {
            StringTokenizer separarLinea = new StringTokenizer(linea, "|");
            String idioma = separarLinea.nextToken();;
            String timestamp = separarLinea.nextToken();;
            String texto = separarLinea.nextToken();;

            info = new Informacion(idioma, timestamp, texto);
            datos.add(info);
        }
    }
}
