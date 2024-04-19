# language: de
Funktionalität: Das Plugin muss sicherstellen, dass die Spielerauswahl auf 2-4 Spieler limitiert ist.

  Szenario: Die Spieler beginnen ein Spiel
    Angenommen drei Spieler wollen das Spiel starten
    Wenn die Spielerauswahl getroffen wurde
    Dann wird das Spiel mit drei Spielern gestartet

  Szenariogrundriss:
    Angenommen <Spieler> Spieler wollen das Spiel starten
    Wenn die Spielerauswahl getroffen wurde
    Dann wird das Spiel mit <Spieler> Spielern gestartet

    Beispiele:
      | Spieler |
      | 1       |
      | 2       |
      | 3       |
      | 4       |
      | 5       |