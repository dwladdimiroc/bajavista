/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bajavista;

import conexionmysql.ConexionBD;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author daniel
 */
public class IndiceInvertido {

    String dirIndexES;
    String dirIndexNONES;

    public IndiceInvertido(String directorioES, String directorioNONES) {
        this.dirIndexES = directorioES;
        this.dirIndexNONES = directorioNONES;
    }

    public void crearIndiceInvertido() throws IOException {
        // 0. Specify the analyzer for tokenizing text.
        //    The same analyzer should be used for indexing and searching
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);

        // 1. Create the index 
        File indexDirES = new File(dirIndexES);
        Directory indexES = FSDirectory.open(indexDirES);
        File indexDirNONES = new File(dirIndexNONES);
        Directory indexNONES = FSDirectory.open(indexDirNONES);

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43, analyzer);

        IndexWriter wES = new IndexWriter(indexES, config);
        IndexWriter wNONES = new IndexWriter(indexNONES, config);

        ConexionBD db = new ConexionBD();
        try {
            try (PreparedStatement consulta = db.getConnection().prepareStatement("SELECT * FROM TweetES");
                    ResultSet res = consulta.executeQuery()) {
                while (res.next()) {
                    agregarDoc(wES, res.getString("Idioma"), res.getString("Timestamp"), res.getString("Texto"));
                }

            }

        } catch (Exception e) {
            System.out.print("No se pudo consultar a la base de datos\n" + e);
        }

//    try {
//            File f = new File(baseDatosNONES);
//            FileReader fr = new FileReader(f);
//            BufferedReader br = new BufferedReader(fr);
//            String linea = br.readLine();
//
//            while ((linea = br.readLine()) != null) {
//		StringTokenizer separarLinea = new StringTokenizer(linea, "|");
//		String next = separarLinea.nextToken();;
//                String next1 = separarLinea.nextToken();;
//                String next2 = separarLinea.nextToken();;
//                
//                addDoc(wNONES, next, next1,next2);
//            }
//
//            fr.close();
//        } catch (Exception e) {
//            System.out.println("Error en la lectura del archivo...");
//        }
        wES.close();
        wNONES.close();
    }

    private static void agregarDoc(IndexWriter w, String ES, String dateTime, String contenido) throws IOException {
        Document doc = new Document();

        doc.add(new StringField("es", ES, Field.Store.YES));
        doc.add(new StringField("datetime", dateTime, Field.Store.YES));
        doc.add(new TextField("contenido", contenido, Field.Store.YES));

        w.addDocument(doc);
    }

}
