package generaarchivoarff;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalIconFactory;

/*
 * @author Adrian Diaz
 * @fecha 06-12-2020
 */
public class GeneraArchivoARFF {

    BufferedWriter bw;
    private final int TOTAL_LINEAS = 100000;
    Random r = new Random();
    final String [] 
            EVENTOS             = {"Social","Familiar","Negocio","Deportivo"},
            TIPOS_LUGAR_EVENTOS = {"Cerrado","AireLibre","SemiAbierto"},
            SI_O_NO             = {"Si","No"},
            DIAS                = {"LunesViernes","SabadoDomingo"},
            GENEROS             = {"Masculino","Femenino"};

    public static void main(String[] args) {
        new GeneraArchivoARFF();
    }
    
    GeneraArchivoARFF() {
        try {
            generaArchivo();
            defineEncabezado();
            escribeInformacion();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de entrada/salida",
                    JOptionPane.ERROR_MESSAGE, null);
        }

    }

    public void escribeInformacion() throws IOException {
        escribeLinea("@data");
        for (int i = 0; i < TOTAL_LINEAS; i++) {
            String info = generaInformacion();
            escribeLinea(info);
        }
    }

    public String generaInformacion() {
        String res = "";
        int edad = r.nextInt(100) + 1, hora = r.nextInt(24) + 1, noPersonas = r.nextInt(99) + 2;
        String nivel = "", dia = "", turno = "", evento = "", tipoLugarEvento = "", sanaDistancia = "", 
                usoCubrebocas = "", genero = "", seContagio = "";

        if (edad <= 12)
            nivel = "Niño";
        else if (edad <= 18)
            nivel = "Joven";
        else if (edad <= 70)
            nivel = "Adulto";
        else
            nivel = "Anciano";
        
        if(hora >= 5 && hora < 12)
                turno = "Matutino";
        if(hora >= 12 && hora < 19)
                turno = "Vespertino";
        if(hora >= 19 || hora < 5)
                turno = "Nocturno";

        evento          = elegirElementoRandom(EVENTOS);
        tipoLugarEvento = elegirElementoRandom(TIPOS_LUGAR_EVENTOS);
        sanaDistancia   = elegirElementoRandom(SI_O_NO);
        usoCubrebocas   = elegirElementoRandom(SI_O_NO);
        dia             = elegirElementoRandom(DIAS);
        genero          = elegirElementoRandom(GENEROS);
        seContagio      = calculaSeContagio(tipoLugarEvento, sanaDistancia, usoCubrebocas, dia);
        
        res = edad + "," + nivel + "," + dia + "," + turno + "," + hora + "," + 
                evento + "," + tipoLugarEvento + "," + noPersonas + "," + 
                sanaDistancia + "," + usoCubrebocas + "," + genero + "," + seContagio;
        
        return res;
    }
    
    private String calculaSeContagio(String tipoLugarEvento, String sanaDistancia, String usoCubrebocas, String dia) {
       ArrayList<String> data = new ArrayList();
       
       for(int i = 0; i < 20 ; i++){
           data.add("No"); 
           data.add("Si");
       }
       
       for(int i = 0; i < 60; i++){
           switch(tipoLugarEvento){
               case "Cerrado": data.add("Si"); break;
               case "AireLibre": data.add("No"); break;
               case "SemiAbierto": data.add(elegirElementoRandom(SI_O_NO)); break;
           }
       }
       for(int i = 0; i < 40; i++){
           data.add(sanaDistancia == "No"? "Si" : "No");
       }
       for(int i = 0; i < 80; i++){
           data.add(usoCubrebocas == "No"? "Si" : "No");
       }
       for(int i = 0; i < 10; i++){
           data.add(dia == "SabadoDomingo"? "Si" : "No");
       }
       
       return elegirElementoRandom( data.toArray() );
    }
    
    private String elegirElementoRandom(Object [] data){
        int index = r.nextInt(data.length);
        return (String)data[index];
    }

    public void defineEncabezado() throws IOException {
        escribeLinea("@relation covid");
        bw.newLine();
        escribeLinea("@attribute Edad numeric");
        escribeLinea("@attribute Nivel {Niño, Joven, Adulto, Anciano}");
        escribeLinea("@attribute Dia {LunesViernes, SabadoDomingo}");
        escribeLinea("@attribute Turno {Matutino, Vespertino, Nocturno}");
        escribeLinea("@attribute Hora numeric");
        escribeLinea("@attribute Evento {Social, Familiar, Negocio, Deportivo}");
        escribeLinea("@attribute TipoLugarEvento {Cerrado, AireLibre, SemiAbierto}");
        escribeLinea("@attribute NoPersonas numeric");
        escribeLinea("@attribute SanaDistancia {Si, No}");
        escribeLinea("@attribute UsoCubrebocas {Si, No}");
        escribeLinea("@attribute Genero {Masculino, Femenino}");
        escribeLinea("@attribute SeContagio {Si, No}");
        bw.newLine();
    }

    public void escribeLinea(String texto) throws IOException {
        bw.write(texto);
        bw.newLine();
    }

    public boolean generaArchivo() throws IOException {
        String ruta = "C:\\archivos";
        File file = new File(ruta + "\\covid.arff");
        File carpeta = new File(ruta);
        FileWriter fw;

        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
        return false;
    }


}
