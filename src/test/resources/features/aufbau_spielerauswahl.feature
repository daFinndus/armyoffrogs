# language: de
Funktionalität: Das Plugin muss sicherstellen, dass die Spielerauswahl auf 2-4 Spieler limitiert ist.

  Szenariogrundriss:
    Angenommen es nehmen <Spieler> Spieler teil
    Wenn die Spielerauswahl getroffen wurde
    Und nicht zwei unterschreitet
    Und nicht vier überschreitet
    Dann wird das Spiel mit <Spieler> Spielern gestartet

    Beispiele:
      | Spieler |
      | 2       |
      | 3       |
      | 4       |