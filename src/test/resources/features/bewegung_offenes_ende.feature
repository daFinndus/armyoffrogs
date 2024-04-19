# language: de
Funktionalität: Das Plugin muss sicherstellen, dass kein Spielstein so bewegt wird, dass er eine Kette mit drei oder
  mehr Einzelverbindungen und einem offenen Ende hinterlässt.

  Szenario: Spieler möchte eine Bewegung machen
    Angenommen der erste Spieler ist am Zug
    Wenn er einen Frosch bewegen möchte
    Dann darf er keinen Frosch so bewegen, dass er eine Kette mit drei oder mehr Einzelverbindungen hinterlassen
    Und er darf kein offenes Ende hinterlassen
