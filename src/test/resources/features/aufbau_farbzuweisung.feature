# language: de
Funktionalität: Das Plugin muss garantieren, dass die Spieler nach der Spielerauswahl eine zufällige Farbe zugewiesen bekommen.

  Szenario: Die Spieler benötigen eine Farbe
    Angenommen die Spielerauswahl ist abgeschlossen
    Und es gibt 4 Spieler
    Dann wird jedem Spieler eine andere zufällige Farbe zugewiesen

  Szenariogrundriss: Die Spieler benötigen eine Farbe
    Angenommen die Spielerauswahl ist abgeschlossen
    Und es gibt <Spieler> Spieler
    Dann wird jedem Spieler eine andere zufällige Farbe zugewiesen

    Beispiele:
      | Spieler |
      | 2       |
      | 3       |
      | 4       |