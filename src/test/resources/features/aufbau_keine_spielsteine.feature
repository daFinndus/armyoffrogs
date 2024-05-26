# language: de
Funktionalität: Das Plugin muss verhindern, dass die Spielsteine, die nicht genutzt werden, den Beutel nicht befüllen.

  Szenariogrundriss: Der Beutel ist leer und das Spiel wird vorbereitet
    Angenommen das Spiel wird vorbereitet
    Und es nehmen <Spieler> Spieler teil
    Wenn der Beutel befüllt wird
    Dann mit Fröschen der Farbe <erste_Farbe>
    Und mit Fröschen der Farbe <zweite_Farbe>
    Und mit Fröschen der Farbe <dritte_Farbe>
    Und mit Fröschen der Farbe <vierte_Farbe>

    Beispiele:
      | Spieler | erste_Farbe | zweite_Farbe | dritte_Farbe | vierte_Farbe |
      | 2       | Rot         | Grün         | null         | null         |
      | 3       | Rot         | Grün         | Blau         | null         |
      | 4       | Rot         | Grün         | Blau         | Weiss        |