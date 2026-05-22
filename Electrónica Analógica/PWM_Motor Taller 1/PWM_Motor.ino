const byte pot_in = 36;
const byte PWM_out = 19;

//Definimos los paramateros para PWM
const int freq= 1000;
const int res = 12;

void setup() {
  pinMode(pot_in, INPUT);
  pinMode(PWM_out, OUTPUT);

  //Seteamos PWM
  ledcAttach(PWM_out,freq,res);

  //Iniciamos Serial monitor
  Serial.begin(115200);
  Serial.print("Iniciando monitor serial");
  Serial.println();

}

void loop() {
  //Leemos los valores del potenciometro
  float dutycicle;
  int val_pot = analogRead(pot_in);

  dutycicle = (val_pot*100.0/4095.0);  //Guardamos el valor de duty cicle en %

  //Serial.printf("El valor ADC del potenciometro es: %i\n", val_pot);
  Serial.printf("El duty cicle actual es: %.2f\n", dutycicle);
  delay(50);
  //Enviamos configuracion a PWM_OUT
  ledcWrite(PWM_out,val_pot);
  
}