# language: de
Funktionalität: (AoF-L-240) Das Plugin muss der GUI die Möglichkeit bieten die Anzahl der Frösche im Beutel abzufragen.
  Das Plugin muss der GUI jederzeit die Funktion 'getFrogsInBag' sinnvoll auszuführen.

Szenario: Kai möchte vor einem Spiel die Anzahl der Frösche im Beutel wissen
  (Testfall 24-1)
  Angenommen  es läuft kein Spiel
  Wenn der Beutelinhalt abgefragt wird
  Dann sind 40 Frösche im Beutel

Szenario: Eike möchte im Spiel die Anzahl der Frösche im Beutel wissen
  (Testfall 24-2)
    Angenommen das Spiel ist mit 2 Spielern gestartet
    Und es läuft 3 Züge
    Und der erste Spieler hat 2 Frösche gezogen
    Und der zweite Spieler hat 1 Frosch gezogen
    Wenn der Beutelinhalt abgefragt wird
    Dann sind 13 Frösche im Beutel

Szenariogrundriss: Jens möchte im Spiel die Anzahl der Frösche im Beutel wissen
  (Testfall 24-2 bis 24-70)
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Und es läuft <Züge> Züge
    Und der erste Spieler hat <erster Spieler> Frösche gezogen
    Und der zweite Spieler hat <zweiter Spieler> Frosch gezogen
    Und der dritter Spieler hat <dritter Spieler> Frosch gezogen
    Und der vierter Spieler hat <vierter Spieler> Frosch gezogen
    Wenn der Beutelinhalt abgefragt wird
    Dann sind <Frösche im Beutel> Frösche im Beutel

  Beispiele:
    | Spieler | Züge | erster Spieler | zweiter Spieler | dritter Spieler | vierter Spieler | Frösche im Beutel |
    | 2       | 3    | 2              | 1               | 0               | 0               | 13                |
    | 2       | 30   | 8              | 8               | 0               | 0               | 0                 |
    | 4       | 10   | 2              | 3               | 1               | 1               | 25                |

