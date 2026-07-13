#include "DHT.h"
const byte DHTPIN = 32;
const byte LED1 = 23;
const byte LED2 = 22;
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(115200);
  pinMode(LED1,OUTPUT);
  pinMode(LED2,OUTPUT);
  dht.begin();
}

void loop() {
  float t = dht.readTemperature();
  if (isnan(t)) {
    Serial.println("Error"); 
  }
  Serial.println(t);
  if (Serial.available() > 0) {
    char comando = Serial.read();
    
    if (comando == 'H') {
      digitalWrite(LED2, HIGH);
      digitalWrite(LED1, LOW);
    } else if (comando == 'L') {
      digitalWrite(LED2, LOW);
      digitalWrite(LED1, HIGH);
    }
  }
  delay(2000); //Agregamos un delay para adquirir datos.
  }