// Definimos parametros de blink

#define BLYNK_PRINT Serial
#define BLYNK_TEMPLATE_ID "TMPL2IQ40xHgA"
#define BLYNK_TEMPLATE_NAME "PotVirtual"
#define BLYNK_AUTH_TOKEN "HjWxd4VnXabhij5qZjtNrppUw2r6Qaba"

#include <WiFi.h>
#include <WiFiClient.h>
#include <BlynkSimpleEsp32.h>

char ssid[] = "NETLIFE-JHONNY";
char pass[] = "1754280269";

const byte pot_in = 36;
const byte PWM_out = 19;

//Definimos los paramateros para PWM
const int freq= 250;
const int res = 12;

//Variable globales de control
int val_pot = 0; 
float dutycicle = 0;

//Variables para logica de IoT
int last_val_pot = 0;

//Leemos data de BLINK
BLYNK_WRITE(V0) {
  val_pot = param.asInt(); //Actualizamos Valor ADC al seteado en blink
}

void setup() {
  pinMode(pot_in, INPUT);
  pinMode(PWM_out, OUTPUT);

  //Seteamos PWM
  ledcAttach(PWM_out,freq,res);
  
  //Iniciamos Serial monitor
  Serial.begin(115200);
  Serial.print("Iniciando monitor serial");
  Serial.println();

  
  //Seteamos server blynk
  Blynk.begin(BLYNK_AUTH_TOKEN, ssid, pass);
  
  //Lectura inicial de pot real
  last_val_pot = analogRead(pot_in);
}

void loop() {

  Blynk.run(); 

  //Leemos potenciometro fisico
  int v_adc = analogRead(pot_in);
  //Validamos si hay un cambio significativo, para actualizar Dutycicle

  if (abs(v_adc - last_val_pot) > 30) {
    last_val_pot = v_adc;
    val_pot = v_adc;           
    //Mueve la perilla de blink
    Blynk.virtualWrite(V0, val_pot); 
  }

  dutycicle = (val_pot*100.0/4095.0);  //Guardamos el valor de duty cicle en %

  Serial.printf("El duty cicle actual es: %.2f\n", dutycicle);
  delay(50);
  //Enviamos configuracion a PWM_OUT
  ledcWrite(PWM_out,val_pot);
}