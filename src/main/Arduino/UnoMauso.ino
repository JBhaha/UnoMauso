const int SW_pin = 2;
const int X_pin = 0;
const int Y_pin = 1;

void setup() {
  pinMode(SW_pin, INPUT);
  digitalWrite(SW_pin, HIGH);
  pinMode(13, OUTPUT);
  Serial.begin(9600);

}

void loop() {
    Serial.println(digitalRead(SW_pin));
    if (analogRead(X_pin) == 1023){
      Serial.println(950);
    }else {
      Serial.println(analogRead(X_pin));
    }
    if (analogRead(Y_pin) == 1023){
      Serial.println(950);
    }else {
      Serial.println(analogRead(Y_pin));
    }
  if (digitalRead(SW_pin) == 0){
    digitalWrite(13, HIGH); 
  } else {
    digitalWrite(13, LOW); 
  }
  delay(45);
}