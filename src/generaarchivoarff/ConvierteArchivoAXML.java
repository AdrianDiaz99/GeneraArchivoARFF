package generaarchivoarff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jesus Adrian Diaz Orozco
 * @fecha 13/12/2020
 */
public class ConvierteArchivoAXML {
    
    BufferedWriter bw;
    BufferedReader br;
    
    ConvierteArchivoAXML(){
        try{
            generaArchivo();
            escribeArchivo();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ConvierteArchivoAXML();
    }

    private boolean generaArchivo() throws IOException{
        String ruta = "C:\\archivos";
        File fileToWrite = new File(ruta + "\\covid.xml");
        File fileToRead = new File(ruta + "\\covid.arff");
        File carpeta = new File(ruta);
        FileWriter fw;
        FileReader fr;

        if (!carpeta.exists() || !fileToRead.exists()) {
            return false;
        }
        if(!fileToWrite.exists()){
            fileToWrite.createNewFile();
        }

        fw = new FileWriter(fileToWrite);
        bw = new BufferedWriter(fw);
        
        fr = new FileReader(fileToRead);
        br = new BufferedReader(fr);
        return false;
    }

    private void escribeArchivo() throws IOException{
        
        final int NO_ATRIBUTOS = 12, EDAD = 0, NIVEL = 1, DIA = 2, TURNO = 3, HORA = 4,
                EVENTO = 5, TIPO_LUGAR_EVENTO = 6, NO_PERSONAS = 7, SANA_DISTANCIA = 8,
                USO_CUBREBOCAS = 9, GENERO = 10, SE_CONTAGIO = 11;
        
        String linea = br.readLine();
        while(!linea.equals("@data")){
            System.out.println(linea);
            linea = br.readLine();
        }
        
        bw.write("<?xml version=\"1.0\"?>\n");
        bw.write("<!DOCTYPE analisis SYSTEM \"covid.dtd\">\n");
        bw.write("<Analisis>\n");
        
        linea = br.readLine();
        while(linea != null){
            int inicio = 0;
            String [] datos = new String[12];
            for(int i = 0; i < NO_ATRIBUTOS ; i++){
                if(linea.contains(",")){
                    datos[i] = linea.substring( 0, linea.indexOf(",") );
                    linea = linea.substring(linea.indexOf(",") + 1);
                }
                else
                    datos[i] = linea;
            }
            bw.write("\t<Ejemplo ");
            bw.write("Dia=\"" + datos[DIA] + "\" SanaDistancia=\"" + datos[SANA_DISTANCIA] + "\" UsoCubrebocas=\"" + datos[USO_CUBREBOCAS] + "\">\n");
            bw.write("\t\t<Persona Edad=\"" + datos[EDAD] + "\" Genero=\"" + datos[GENERO] + "\">\n");
            bw.write("\t\t\t<Nivel>" + datos[NIVEL] + "</Nivel>\n");
            bw.write("\t\t</Persona>\n");
            bw.write("\t\t<Turno Hora=\"" + datos[HORA] + "\">" + datos[TURNO] + "</Turno>\n");
            bw.write("\t\t</Evento TipoLugar=\"" + datos[TIPO_LUGAR_EVENTO] + "\" NoPersonas=\"" + datos[NO_PERSONAS] + "\">" + datos[EVENTO] + "</Evento>\n");
            bw.write("\t\t<SeContagio>" + datos[SE_CONTAGIO] + "</SeContagio>\n");
            bw.write("\t</Ejemplo>\n");
            linea = br.readLine();
        }
        
        bw.write("</Analisis>");
        bw.close();
    }
    /*
    <?xml version="1.0"?>
    <!DOCTYPE analisis SYSTEM "covid.dtd">
    <Analisis>
            <Ejemplo Dia="SabadoDomingo" SanaDistancia="Si" UsoCubreocas="Si">
                    <Persona Edad="28" Genero="Femenino">
                            <Nivel>Adulto</Nivel>
                    </Persona>
                    <Turno Hora="14">Nocturno</Turno>
                    <Evento TipoLugar="SemiAbierto" NoPersonas="24">Social</Evento>
                    <SeContagio>No</SeContagio>
            </Ejemplo>
    </Analisis>
    */
}
