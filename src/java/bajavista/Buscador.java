/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bajavista;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author daniel
 */
public class Buscador {

    String dirIndexES;
    String dirIndexNONES;

    ArrayList<Informacion> listaInfo = new ArrayList();
    Informacion info;

    public Buscador(String directorioIndexES, String directorioIndexNONES) {
        this.dirIndexES = directorioIndexES;
        this.dirIndexNONES = directorioIndexNONES;
    }

    public ArrayList<Informacion> buscarContenido(String busqueda) throws IOException, ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);

        File indexDirES = new File(dirIndexES);
        Directory indexES = FSDirectory.open(indexDirES);
        File indexDirNONES = new File(dirIndexNONES);
        Directory indexNONES = FSDirectory.open(indexDirNONES);

        // 2. Query
        String querystr = busqueda;

        Query q = new QueryParser(Version.LUCENE_43, "contenido", analyzer).parse(querystr);
        //Query qNONES = new QueryParser(Version.LUCENE_43, "contenido", analyzer).parse(querystr);

        // 3. Search
        int hitsPerPage = 2048;
        IndexReader reader = DirectoryReader.open(indexES);
        IndexSearcher searcher = new IndexSearcher(reader);

        IndexReader readerNONES = DirectoryReader.open(indexNONES);
        IndexSearcher searcherNONES = new IndexSearcher(readerNONES);

        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        TopScoreDocCollector collectorNONES = TopScoreDocCollector.create(hitsPerPage, true);

        searcher.search(q, collector);
        searcherNONES.search(q, collectorNONES);

        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        // ScoreDoc[] hitsNONES = collectorNONES.topDocs().scoreDocs;

        // 4. Display results
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get("contenido"));
            info = new Informacion(d.get("es"), d.get("datetime"), d.get("contenido"));
            listaInfo.add(info);
        }

        /*System.out.println("No ES Found " + hitsNONES.length + " hits.");
         for(int i=0;i<hitsNONES.length;++i) {
         int docId = hitsNONES[i].doc;
         Document d = searcherNONES.doc(docId);
         System.out.println((i + 1) + ". " + d.get("es") + "\t" + d.get("contenido"));
         }*/
        
        reader.close();
        readerNONES.close();

        return listaInfo;
    }
    
    public ArrayList<Informacion> filtrarContenido(ArrayList<Informacion> listaInfo) {
        for(int i=0; i< listaInfo.size() ; i++){
            for(int j=0; j< (listaInfo.size()-1); j++){
                if(listaInfo.get(i).getTexto().compareTo(listaInfo.get(j).getTexto()) == 0){
                    listaInfo.remove(j);
                }
            }
        }
        
        return listaInfo;
    }
}
