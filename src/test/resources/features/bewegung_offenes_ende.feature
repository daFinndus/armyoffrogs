# language: de
Funktionalität: Das Plugin muss sicherstellen, dass kein Spielstein so bewegt wird, dass er eine Kette mit drei oder
  mehr Einzelverbindungen und einem offenen Ende hinterlässt.

  Szenario: Spieler möchte eine Bewegung machen
    Angenommen der Spieler mit der Teamfarbe Grün ist am Zug
    Wenn er einen Frosch bewegen möchte
    Dann darf er keinen Frosch so bewegen, dass er eine Kette mit drei oder mehr Einzelverbindungen hinterlassen würde
    Und er darf kein offenes Ende hinterlassen
