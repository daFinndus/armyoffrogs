# language: de
Funktionalität: Das Plugin muss sicherstellen, dass die Spieler zu Spielbeginn, im Uhrzeigersinn beginnend beim Startspieler,
  zwei zufällige Steine aus dem Beutel ihrem Vorrat zugefügt bekommen.

  Szenariogrundriss: Die Spieler bekommen ihre ersten zwei Steine zu Spielbeginn aus dem Beutel
    Angenommen Das Spiel ist initialisiert
    Und das Spiel hat <Spieler> Spieler
    Wenn die Spieler ihre ersten zwei Steine erhalten
    Dann muss jeder Spieler zwei zufällige Steine aus dem Beutel in ihren Vorrat bekommen
    Und der Beutel enthält jetzt <Anzahl> Steine weniger

    Beispiele:
      | Spieler | Anzahl |
      | 2       | 4      |
      | 3       | 6      |
      | 4       | 8      |
