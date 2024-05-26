# language: de
Funktionalität: Das System muss sicherstellen, dass der Spieler einen zufällig gezogenen Spielstein aus dem Beutel
  seinem Vorrat hinzufügt, solange der Spieler nicht bereits zwei Spielsteine hat.

  Szenariogrundriss: Spieler möchte einen Spielstein aus dem Beutel zu seinem Vorrat hinzufügen.
    Angenommen das Spiel hat mit <Spieler> Spielern begonnen.
    Und der erste Spieler ist am Zug
    Wenn er einen neuen Spielstein ziehen will
    Dann muss die Anzahl der sich im Vorrat befindenden Spielsteine 1 betragen.

    Beispiele:
      | Spieler |
      | 1       |
      | 2       |
      | 3       |
      | 4       |

