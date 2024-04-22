# language: de
Funktionalität: Das Plugin muss es dem Spieler verbieten, seine Bewegung zu beenden, wenn dieser das Spielfeld
  aufreißen, also einen Spielstein ohne Verbindung hinterlassen würde.

  Szenario: Der Spieler versucht, das Spielfeld aufzureißen
    Angenommen der zweite Spieler ist am Zug
    Wenn der Spieler seinen Frosch bewegen möchte
    Und er infolgedessen einen Spielstein ohne Verbindung hinterlässt
    Dann muss die Bewegung nicht erlaubt sein