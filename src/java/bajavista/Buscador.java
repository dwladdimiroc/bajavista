/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bajavista;

import conexionmysql.ConexionBD;
import eda.Hamming;
import eda.Informacion;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    //String dirIndexNONES;

    ArrayList<Informacion> listaInfo = new ArrayList();
    Informacion info;

    public Buscador(String directorioIndexES) {
        this.dirIndexES = directorioIndexES;
        //this.dirIndexNONES = directorioIndexNONES;
    }

    public ArrayList<Informacion> buscarContenido(String busqueda) throws IOException, ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);

        File indexDirES = new File(dirIndexES);
        Directory indexES = FSDirectory.open(indexDirES);
        //File indexDirNONES = new File(dirIndexNONES);
        //Directory indexNONES = FSDirectory.open(indexDirNONES);

        // 2. Query
        String querystr = busqueda;

        Query q = new QueryParser(Version.LUCENE_43, "text", analyzer).parse(querystr);
        //Query qNONES = new QueryParser(Version.LUCENE_43, "contenido", analyzer).parse(querystr);

        // 3. Search
        int hitsPage = 1024;
        IndexReader reader = DirectoryReader.open(indexES);
        IndexSearcher searcher = new IndexSearcher(reader);

        //IndexReader readerNONES = DirectoryReader.open(indexNONES);
        //IndexSearcher searcherNONES = new IndexSearcher(readerNONES);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPage, true);
        //TopScoreDocCollector collectorNONES = TopScoreDocCollector.create(hitsPage, true);

        searcher.search(q, collector);
        //searcherNONES.search(q, collectorNONES);

        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        // ScoreDoc[] hitsNONES = collectorNONES.topDocs().scoreDocs;

        // 4. Return results
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document data = searcher.doc(docId);
            info = new Informacion(Integer.parseInt(data.get("idUser")), Long.parseLong(data.get("timestamp")), data.get("text"), Double.parseDouble(data.get("objective")), Double.parseDouble(data.get("subjective")), Double.parseDouble(data.get("positive")), Double.parseDouble(data.get("negative")), Integer.parseInt(data.get("need")));
            listaInfo.add(info);
        }

        /*System.out.println("No ES Found " + hitsNONES.length + " hits.");
         for(int i=0;i<hitsNONES.length;++i) {
         int docId = hitsNONES[i].doc;
         Document d = searcherNONES.doc(docId);
         System.out.println((i + 1) + ". " + d.get("es") + "\t" + d.get("contenido"));
         }*/
        reader.close();
        //readerNONES.close();

        return listaInfo;
    }

    //5. Clear tweets
    public ArrayList<Informacion> filtrarContenido(ArrayList<Informacion> listaInfo) {
        for (int i = 0; i < listaInfo.size(); i++) {
            for (int j = 1; j < listaInfo.size(); j++) {
                //if (listaInfo.get(i).getText().compareTo(listaInfo.get(j).getText()) == 0) {
                if (distanciaHamming(listaInfo.get(i).getText(), listaInfo.get(j).getText())) {
                    listaInfo.get(i).setRetweets(listaInfo.get(i).getRetweets() + 1);
                    listaInfo.remove(j);
                }
            }
        }

        return listaInfo;
    }

    public ArrayList<Informacion> rankingContenido(ArrayList<Informacion> listaInfo, String preferencia) {

        double P = 0.7;
        
        double Pn;
        double n;

        double Pi;
        double i;
        int iUser = 0;
        int iMax = calcularInfluenciaMaxima();
        int r;
        int rMax = calcularRetweetsMaximo(listaInfo);

        double Pt;
        double t;

        long tFinal = calcularTimeFinal();
        
        double Pni = 0.3;
        double ni;
        int niMax = calcularNeedMaxima();
        System.out.println("niMax" + niMax);

//        System.out.println("iMax: "+iMax);
//        System.out.println("rMax: "+rMax);
//        System.out.println("tFinal: "+tFinal);
        double ranking = 0;

        if (preferencia.compareTo("tiempo") == 0) {
            
            //System.out.println("Tiempo");
            
            Pn = 0.1;
            Pi = 0.1;
            Pt = 0.8;

        } else if (preferencia.compareTo("influencia") == 0) {
            
            //System.out.println("Influencia");
            
            Pn = 0.1;
            Pi = 0.8;
            Pt = 0.1;

        } else {                               //Mallet
            
            //System.out.println("Objetividad");
            
            Pn = 0.8;
            Pi = 0.1;
            Pt = 0.1;
        }

        for (int j = 0; j < listaInfo.size(); j++) {

            //Rankear valores de mallet
            if (listaInfo.get(j).getObjective() < listaInfo.get(j).getSubjective()) {
                if (listaInfo.get(j).getPositive() < listaInfo.get(j).getNegative()) {
                    n = (listaInfo.get(j).getPositive() * 0.25 + listaInfo.get(j).getSubjective()) / 2;
                } else {
                    n = (listaInfo.get(j).getPositive() * 0.5 + listaInfo.get(j).getSubjective()) / 2;
                }
            } else {
                n = listaInfo.get(j).getObjective();
            }

            //Rankear valores de influencia de usuario
            iUser = rankingUser(listaInfo.get(j).getId());
            r = listaInfo.get(j).getRetweets();
            //System.out.println("R: " + r + " RMax: " + rMax);
            
            //System.out.println("ID: "+j+" iUser: "+iUser+" iMax: "+iMax+" r: "+r+" rMax: "+rMax);
            i = (((double) iUser / (double) iMax) + ((double) r / (double) rMax)) / 2;
            //System.out.println("ID: "+j+" %i "+i);

            //Rankear valores del tiempo
            t = listaInfo.get(j).getTimestamp() / tFinal;
            
            //Rankear necesidad del tweet
            ni = (double) listaInfo.get(j).getNeed() / (double) niMax;

            ranking = P*(Pn * n + Pi * i + Pt * t) + Pni*ni;

            System.out.println("ID: "+j+" Ranking: "+ranking);
            listaInfo.get(j).setRanking(ranking);
        }

        return listaInfo;
    }

    private boolean distanciaHamming(String textoOriginal, String textoComparado) {

        Hamming distancia = new Hamming(textoOriginal, textoComparado);

        if ((distancia.getHammingDistance() > 1) && (distancia.getHammingDistance() < 5)) {

            return true;
        }

        return false;
    }

    private int calcularRetweetsMaximo(ArrayList<Informacion> listaInfo) {
        int maxTweet = 1;

        for (int i = 0; i < listaInfo.size(); i++) {
            //System.out.println("ID: "+listaInfo.get(i).getId()+" Retweets: "+listaInfo.get(i).getRetweets());
            if (maxTweet < listaInfo.get(i).getRetweets()) {
                maxTweet = listaInfo.get(i).getRetweets();
            }
        }

        return maxTweet;
    }

    private int calcularInfluenciaMaxima() {
        int influenciaMax = 0;

        ConexionBD db = new ConexionBD();
        try {
            try (PreparedStatement consulta = db.getConnection().prepareStatement("SELECT MAX(cantTweet) AS cantTweet  FROM User");
                    ResultSet res = consulta.executeQuery()) {
                while (res.next()) {
                    influenciaMax = Integer.parseInt(res.getString("cantTweet"));
                }

            }

        } catch (Exception e) {
            System.out.print("No se pudo consultar a la base de datos respecto a la influencia máxima\n" + e);
        }
        db.desconectar();

        return influenciaMax;
    }
    
    private int calcularNeedMaxima() {
        int needMax = 0;

        ConexionBD db = new ConexionBD();
        try {
            try (PreparedStatement consulta = db.getConnection().prepareStatement("SELECT MAX(need) AS need  FROM Tweet");
                    ResultSet res = consulta.executeQuery()) {
                while (res.next()) {
                    needMax = Integer.parseInt(res.getString("need"));
                }

            }

        } catch (Exception e) {
            System.out.print("No se pudo consultar a la base de datos respecto a la influencia máxima\n" + e);
        }
        db.desconectar();

        return needMax;
    }

    private int rankingUser(int id) {
        int rankingUser = 0;

        ConexionBD db = new ConexionBD();
        try {
            try (PreparedStatement consulta = db.getConnection().prepareStatement("SELECT * FROM User WHERE idUser='" + id + "'");
                    ResultSet res = consulta.executeQuery()) {
                while (res.next()) {
                    rankingUser = Integer.parseInt(res.getString("cantTweet"));
                }

            }

        } catch (Exception e) {
            System.out.print("No se pudo consultar a la base de datos a la cantidad de tweets\n" + e);
        }
        db.desconectar();

        return rankingUser;
    }

    private long calcularTimeFinal() {
        long timeFinal = 0;

        ConexionBD db = new ConexionBD();
        try {
            try (PreparedStatement consulta = db.getConnection().prepareStatement("SELECT MAX(timestamp) AS timestamp FROM Tweet");
                    ResultSet res = consulta.executeQuery()) {
                while (res.next()) {
                    timeFinal = Long.parseLong(res.getString("timestamp"));
                }

            }

        } catch (Exception e) {
            System.out.print("No se pudo consultar a la base de datos respecto al tiempo \n" + e);
        }
        db.desconectar();

        return timeFinal;
    }

    public ArrayList<Informacion> ordenarContenido(ArrayList<Informacion> listaInfo) {

        Collections.sort(listaInfo, new Comparator<Informacion>() {
            @Override
            public int compare(Informacion info1, Informacion info2) {
                return Double.compare(info2.getRanking(), info1.getRanking());
            }
        });

        return listaInfo;
    }
}
