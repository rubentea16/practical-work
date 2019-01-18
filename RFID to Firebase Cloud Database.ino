#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <SPI.h>
#include <MFRC522.h>
#include <FirebaseArduino.h>
#include <Wire.h> // must be included here so that Arduino library object file references work
#include <uRTCLib.h>

#define RST_PIN 0  //RST-PIN für RC522 - RFID - SPI - Modul GPIO15 
#define SS_PIN  15  //SDA-PIN für RC522 - RFID - SPI - Modul GPIO2 

#define FIREBASE_HOST "rfid-d2376.firebaseio.com"
#define FIREBASE_AUTH "QzGNwx6vvMdOFwsMFpkPpXRRZLwy8e3T4gAKrReA"
#define WIFI_SSID "Ryx"
#define WIFI_PASS "ruben123"

uRTCLib rtc;


unsigned int a,b,c,d;
int x=0;
String satu,dua,tiga,empat,hasil,final,gabungan;
int i,day,month,year,hour,minute,second,DOW;
String day1,month1,year1,hour1,minute1,second1,RTC,DOW1;
String storagegabungan[100];



MFRC522 mfrc522(SS_PIN, RST_PIN); // Create MFRC522 instance

void setup() {
  Serial.begin(9600);    // Initialize serial communications
  delay(100);
  SPI.begin();
  mfrc522.PCD_Init();    // Init MFRC522
  WiFi.begin(WIFI_SSID, WIFI_PASS);
  Firebase.begin(FIREBASE_HOST,FIREBASE_AUTH);
  pinMode(0,OUTPUT);
  digitalWrite(0,LOW);
}


void loop() { 
  
  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent()) {
    delay(50);
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) {
    delay(50);
    return;
  }
  // Show some details of the PICC (that is: the tag/card)
  Serial.print(F("Card UID:"));
  delay(50);
 
  dump_byte_array(mfrc522.uid.uidByte, mfrc522.uid.size);
  RTCDS();
  gabungan = final+"#"+RTC;
  storagegabungan[x] = gabungan;
  Serial.println(storagegabungan[x]);
  delay(100);
  
  
  if(WiFi.status() == WL_CONNECTED )
  {   
     for(i=0;i<=x;i++)
     {
      
      Firebase.pushString("feeds",storagegabungan[i]);
      digitalWrite(0,HIGH);
      delay(1000);
      digitalWrite(0,LOW);
     }
     x=0;
  }

  else if(WiFi.status() != WL_CONNECTED)
  {
    x++;
  }
  
}

// Helper routine to dump a byte array as hex values to Serial
void dump_byte_array(byte *buffer, byte bufferSize) {
  for (byte i = 0; i < bufferSize; i++) {
    a=buffer[0];
    b=buffer[1];
    c=buffer[2];
    d=buffer[3];
    satu=String(a);
    dua=String(b);
    tiga=String(c);
    empat=String(d);
    hasil=satu+dua+tiga+empat;
    final=hasil;
    delay(20);
  }
}

void RTCDS()
{
  rtc.refresh();
  
  day = rtc.day(); 
  month = rtc.month();
  year = rtc.year();
  hour = rtc.hour();
  minute = rtc.minute();
  second = rtc.second();
  DOW = rtc.dayOfWeek();

  switch (DOW)
  {
    case 2: DOW1 = "Senin"; break;
    case 3: DOW1 = "Selasa"; break;
    case 4: DOW1 = "Rabu"; break;
    case 5: DOW1 = "Kamis"; break;
    case 6: DOW1 = "Jumat"; break;
    case 7: DOW1 = "Sabtu"; break;
    case 1: DOW1 = "Minggu"; break;
  }
  
  day1 = String (day);
  month1 = String (month);
  year1 = String (year);
  hour1 = String (hour);
  minute1 = String (minute);
  second1 = String (second);
  RTC = day1+"/"+month1+"/"+year1+"#"+hour1+":"+minute1+":"+second1+"#"+DOW1;
  
  
}

