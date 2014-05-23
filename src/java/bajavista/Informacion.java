/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bajavista;

/**
 *
 * @author daniel
 */
public class Informacion {

    String idioma;
    String dataTime;
    String texto;

    public Informacion(String idioma, String dataTime, String texto) {
        this.idioma = idioma;
        this.dataTime = dataTime;
        this.texto = texto;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
