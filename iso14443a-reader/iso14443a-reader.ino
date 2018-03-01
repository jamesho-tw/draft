#include <PN532_HSU.h>
#include <PN532.h>

PN532_HSU pn532hsu(Serial1);
PN532 nfc(pn532hsu);

void setup(void) {
  Serial.begin(115200);
  nfc.begin();
  uint32_t ver = nfc.getFirmwareVersion();
  if (! ver) {
    while (1);
  }
  nfc.setPassiveActivationRetries(0xFF);
  nfc.SAMConfig();
}

void loop(void) {
  boolean success;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };
  uint8_t len;
  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, &uid[0], &len);
  if (success) {
    // Serial.print(len, HEX);
    long result = 0;
    if (len == 4) {
      for (int i = len - 1; i >= 0; i--) {
        result = (result << 8) + uid[i];
      }
    }
    if (result < 0) {
      result = result * -1;
    }
    Serial.println(result);
    delay(1000);
  }
}
