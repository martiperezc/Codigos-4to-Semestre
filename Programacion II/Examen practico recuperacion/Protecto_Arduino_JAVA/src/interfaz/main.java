package interfaz;

import modelo.Temperatura;

//Importamos las librerias necesarias para establecer conexion mediante Serialport
import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;
import java.io.PrintWriter;

public class main {
    public static void main(String[] args) {
        // 1. Inicialización de la comunicación
        SerialPort puerto = SerialPort.getCommPort("COM5");
        puerto.setBaudRate(115200);
        puerto.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        if (!puerto.openPort()) {
            System.out.println("Error no se establecio conexion con el ESP32");
            return;
        }
        System.out.println("Iniciando datos de ESP32");

        //Instanciamos el modelo
        Temperatura gestorTemp = new Temperatura();

        Scanner scanner = new Scanner(puerto.getInputStream());
        PrintWriter escritor = new PrintWriter(puerto.getOutputStream(), true);

        // 3. Bucle infinito de procesamiento
        while (scanner.hasNextLine()) {
            try {
                // Lectura y limpieza del buffer entrante
                String datoRaw = scanner.nextLine().trim();
                float tempLeida = Float.parseFloat(datoRaw);

                //Mandamos datos a alos metodos de temperatur
                gestorTemp.setValorActual(tempLeida);
                String comandoAccion = gestorTemp.evaluarLogica();

                //Mandamos comando a ESP32
                escritor.print(comandoAccion);
                escritor.flush();

            } catch (NumberFormatException e) {
                //Agregamos excepciones para datos corruptos
                System.out.println("Error datos incorrectos o no validos" + e.getMessage() + ")");
            }
        }
        puerto.closePort();
    }
}