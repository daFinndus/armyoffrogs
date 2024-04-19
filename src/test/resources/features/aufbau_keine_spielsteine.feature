# language: de
Funktionalität: Das Plugin muss verhindern, dass die Spielsteine, die nicht genutzt werden, den Beutel füllen.

  Szenario: Der Beutel ist leer und das Spiel wird vorbereitet
    Angenommen das Spiel wird vorbereitet
    Und die Spielerfarben wurden vergeben
    Wenn der Beutel befüllt wird
    Dann darf er das nur mit den Spielsteinen der Spielerfarben tun

  Szenariogrundriss: Der Beutel ist leer und das Spiel wird vorbereitet
    Angenommen das Spiel wird vorbereitet
    Und es spielen <Spieler> Spieler
    Und die Spielerfarben <Farben> wurden vergeben
    Wenn der Beutel befüllt wird
    Dann darf er das nur mit den Spielsteinen der Farbe <Farben> tun

    Beispiele:
      | Spieler | Farben                  |
      | 2       | Blau, Rot               |
      | 3       | Blau, Gruen, Rot        |
      | 4       | Blau, Gruen, Rot, Weiss |