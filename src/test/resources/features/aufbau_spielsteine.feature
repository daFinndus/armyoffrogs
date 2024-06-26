# language: de
Funktionalität: Das Plugin muss gewährleisten, dass sich zu Spielbeginn je 10 Spielsteine der Spielerfarbe im Beutel befinden.

  Szenario: Das Spiel wird vorbereitet und der Beutel muss gefüllt werden
    Angenommen das Spiel wird vorbereitet
    Und es nehmen 3 Spieler teil
    Wenn der Beutel befüllt wird
    Dann sollten 30 Spielsteine im Beutel liegen

  Szenariogrundriss: Das Spiel wird vorbereitet und der Beutel muss gefüllt werden
    Angenommen das Spiel wird vorbereitet
    Und es nehmen <Spieler> Spieler teil
    Wenn der Beutel befüllt wird
    Dann sollten <Spielsteine> Spielsteine im Beutel liegen

    Beispiele:
      | Spieler | Spielsteine |
      | 2       | 20          |
      | 3       | 30          |
      | 4       | 40          |