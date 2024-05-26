# language: de
Funktionalität: Das Plugin muss verhindern, dass die Spielsteine, die nicht genutzt werden, den Beutel nicht befüllen.

  Szenario: Der Beutel ist leer und das Spiel wird vorbereitet
    Angenommen das Spiel wird vorbereitet
    Und es nehmen 2 Spieler teil
    Wenn der Beutel befüllt wird
    Dann darf er das nur mit den Spielsteinen der Spielerfarben tun

  Szenariogrundriss: Der Beutel ist leer und das Spiel wird vorbereitet
    Angenommen das Spiel wird vorbereitet
    Und es nehmen <Spieler> Spieler teil
    Wenn der Beutel befüllt wird
    Dann darf er das nur mit den Spielsteinen der Farbe <Farben> tun

    Beispiele:
      | Spieler | Farben                  |
      | 2       | Blau, Rot               |
      | 3       | Blau, Gruen, Rot        |
      | 4       | Blau, Gruen, Rot, Weiss |