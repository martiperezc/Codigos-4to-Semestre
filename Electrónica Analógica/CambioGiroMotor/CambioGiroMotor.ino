const byte p1 = 32;
const byte p2 = 33;
const byte n1 = 19;
const byte n2 = 18;

void setup() {
  Serial.begin(115200);
  Serial.println("Iniciando Serial...");
  Serial.println();
  pinMode(p1, OUTPUT);
  pinMode(p2, OUTPUT);
  pinMode(n1, OUTPUT);
  pinMode(n2, OUTPUT);

  // Inicialmente motor apagado
  digitalWrite(p1, HIGH);
  digitalWrite(n1, LOW);
  digitalWrite(p2, HIGH);
  digitalWrite(n2, LOW);
}

void loop() {
  if (Serial.available()) {
    char comando = Serial.read();

    // Opción 1: Giro en un sentido
    if (comando == 'f') {
      digitalWrite(p2, LOW);
      digitalWrite(n1, HIGH);
      digitalWrite(p1, HIGH);
      digitalWrite(n2, LOW);
      Serial.println("Motor en fordward 1");
      delay(50);
    }

    // Opción 0: Giro en sentido contrario
    else if (comando == 'r') {
      digitalWrite(p2, HIGH);
      digitalWrite(n1, LOW);
      digitalWrite(p1, LOW);
      digitalWrite(n2, HIGH);

      Serial.println("Motor en reverse");
      delay(50);
    }
    else if (comando == 's'){
        digitalWrite(p1, HIGH);
        digitalWrite(n1, LOW);
        digitalWrite(p2, HIGH);
        digitalWrite(n2, LOW);
        Serial.println("STOP");
        delay(50);
    }
  }
}