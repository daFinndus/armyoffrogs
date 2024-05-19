# language: de
Funktionalität: Das Plugin muss sicherstellen, dass die Spieler nacheinander im Uhrzeigersinn ihre Aktionen ausführen müssen.

  Szenariogrundriss: Das Spiel ist gestartet und die Aktionen eines Spielers wurden abgeschlossen
    Angenommen das Spiel läuft bereits
    Und es wurde mit <Spieler> Spielern gestartet
    Wenn der Spieler mit der Teamfarbe <Farbe> seine Aktionen abgeschlossen hat
    Dann muss der nächste Spieler die Teamfarbe <zweiteFarbe> haben

    Beispiele:
      | Spieler | Farbe |  | zweiteFarbe |
      | 3       | Rot   |  | Gruen       |
      | 3       | Gruen |  | Blau        |
      | 4       | Blau  |  | Weiss       |
      | 4       | Weiss |  | Rot         |

