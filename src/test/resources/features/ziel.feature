# language: de
Funktionalität: Das Plugin muss sicherstellen, dass der Spieler gewonnen hat, der alle Steine seiner Farbe auf der Spielfläche miteinander verbunden hat. Ist diese Kette kleiner als sieben Steine, ist das Spiel nicht gewonnen.

  Szenario: Der Spieler hat eine Kette von 7 Steinen
    Angenommen das Spiel ist gestartet
    Und der Spieler hat eine Kette von 7 Steinen
    Wenn der Spieler keine weiteren Steine auf dem Spielfeld hat
    Dann hat der Spieler gewonnen

"""
  Szenariogrundriss: Der Spieler hat <Frösche> Frösche in einer Kette
    Angenommen das Spiel ist gestartet
    Und der Spieler hat <Frösche> Frösche in einer Kette
    Wenn der Spieler mindestens 7 Frösch in einer Kette hat
    Und alle Frösche derselben Farbe in einer Kette auf dem Spielfeld verbunden sind
    Dann hat der Spieler gewonnen
"""
