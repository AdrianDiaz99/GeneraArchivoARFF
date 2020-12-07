package generaarchivoarff;

import java.io.*;
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
        Random r = new Random();
        int edad = r.nextInt(100) + 1, hora = r.nextInt(24) + 1, noPersonas = r.nextInt(99) + 2;
        String nivel = "", dia = "", turno = "", evento = "", tipoLugarEvento = "", sanaDistancia = "", 
                usoCubrebocas = "", genero = "", seContagio = "";

        if (edad <= 12) {
            nivel = "Niño";
        }
        if (edad > 12 && edad <= 18) {
            nivel = "Joven";
        }
        if (edad > 18 && edad <= 70) {
            nivel = "Adulto";
        }
        if (edad > 70 && edad <= 100) {
            nivel = "Anciano";
        }

        int control;

        control = r.nextInt(3);
        switch (control) {
            case 0:
                turno = "Matutino";
                break;
            case 1:
                turno = "Vespertino";
                break;
            case 2:
                turno = "Nocturno";
                break;
        }

        control = r.nextInt(4);
        switch (control) {
            case 0:
                evento = "Social";
                break;
            case 1:
                evento = "Familiar";
                break;
            case 2:
                evento = "Negocio";
                break;
            case 3:
                evento = "Deportivo";
                break;
        }

        control = r.nextInt(3);
        switch (control) {
            case 0:
                tipoLugarEvento = "Cerrado";
                break;
            case 1:
                tipoLugarEvento = "AireLibre";
                break;
            case 2:
                tipoLugarEvento = "SemiAbierto";
                break;
        }

        sanaDistancia = r.nextInt(2) == 0 ? "Si" : "No";
        usoCubrebocas = r.nextInt(2) == 0 ? "Si" : "No";
        seContagio = r.nextInt(2) == 0 ? "Si" : "No";
        dia = r.nextInt(2) == 0 ? "LunesViernes" : "SabadoDomingo";
        genero = r.nextInt(2) == 0 ? "Masculino" : "Femenino";
        
        res = edad + "," + nivel + "," + dia + "," + turno + "," + hora + "," + 
                evento + "," + tipoLugarEvento + "," + noPersonas + "," + 
                sanaDistancia + "," + usoCubrebocas + "," + genero + "," + seContagio;
        
        return res;
    }

    public void defineEncabezado() throws IOException {
        escribeLinea("@relation covid");
        bw.newLine();
        escribeLinea("@attribute Edad   numeric");
        escribeLinea("@attribute Nivel  {Niño, Joven, Adulto, Anciano}");
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

        if (!file.exists()) {
            file.createNewFile();
        }

        fw = new FileWriter(file);
        bw = new BufferedWriter(fw);
        return false;
    }

    public static void main(String[] args) {
        new GeneraArchivoARFF();
    }

}
