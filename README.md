Memory
======
## Installation unter Windows

* CMD öffnen und in einem leeren Verzeichnis folgende Befehl ausführen:
  * git clone "https://github.com/Monoblos/Memory.git"
  * cd Memory
  * create.cmd
  * java -jar Memory.jar
* Benötigt werden die Programme "Git", "Maven" und "Java"
* Alle Programme müssen in der System-Variable "PATH" eingetragen sein.

### 1. Zweck des Projektes
* Erzeugen eines Memory Spiels für das Fach Software Engineering.

### 2. Kunden und andere Beteiligte/Betroffene
* Frei benutzbar für jeden.
* Unter Leitung von Prof. Dr. Marco Boger

### 3. Nutzer des Produkts
* Frei benutzbar für jeden, Partyspiel.

### 4. Vorgegebene Randbedingungen für das Projekt
* Programmiersprache: Java oder Skala.
* Verwaltung mit Scrum und GitHub.
* Arbeiten in Teams bestehend aus 2 Personen.

### 5. Namenskonventionen und Definitionen
* Listener enthalten den Zusatz "Listener".
* Graphische und Textuelle Oberflächen haben den Namenzusatz "UI".
*  Adapter haben den Zusatz "Adapter".

### 6. Relevante Fakten und Annahmen
* Benutzung eigener Karten durch ein Savefile möglich

### 7. Arbeitsumfang
* Entwicklung des Memory Spiels.
* Produktdokumentation.
* Produktpräsentation.


### 8. Abgrenzung des Produkts
* - 

### 9. Anforderungen an Funktionen und Daten des Produkts
* Eigene Karten verwenden.
* Multiplayer.
* Skalierbare Oberfläche.
* Beliebige Feldgröße.
* Frei wählbare Größe der Kartensets.
* Neustartbar.


### 10. Designaspekte/ Look and Feel
* Skalierbares Spielbrett.
* Skalierbare Karten.

### 11. Benutzbarkeitsanforderungen
* Graphische Oberfläche zum Bedienen des Memory Spiels.
* Textuelle Oberfläche, falls keine graphischen Oberflächen unterstützt wird.

### 12. Performance
* Reaktionszeiten von < 1 sec.

### 13. Operationale Anforderungen
* 

### 14. Wartungs- und Portierungsanforderungen
* Testen auf anderen Betriebsystemen.

### 15. Zugriffsschutzanforderungen
* - 

### 16. Kulturelle und politische Anforderungen
* -

### 17. Rechtliche Anforderungen
* Lizenz

### 18. Offene Punkte
* Memory mit "nichtgleichen", zueinandergehörenden Karten.

### 19. Fertiglösungen
* -

### 20. Neue Probleme
* -

### 21. Aufgaben
* Verbesserung des Loggings.
* Umsetzung vom Punkt "Offene Punkte".
### 22. Inbetriebnahme und Migration

### 23. Risiken
* -

### 24. Kosten
* 

### 25. Benutzerdokumentation und Schulung
* -

### 26. Warteraum
* -

### 27. Lösungsideen
* -

### Aufbau des Spiels
* main 
  * Main
    * Enthält main Methode zur Ausführung der UI


* ui
  * VirtualConsole
    * Dient zur Darstellung der TUI.
  * TUI
    * Öffnet und Verwaltet die textbasierte Benutzeroberfläche. 
  * MemCardButton
    * Zur GUI zugehörige Klasse zur Darstellung der Memory Karte.
  * GUI
    * Öffnet und Verwaltet die graphische Benutzeroberfläche.  


* entities
  * MemoryCard
    * Beinhaltet alle Attribute einer Memory Karte.  
    * Enthält Methoden zum Abfragen und Ändern dieser Attribute sowie Methoden zum Vergleich von Memory Karten.
  * Board
    * Die Klasse Board setzt ein Spielbrett mit spezifischer Höhe und Breite und mit Memory Karten auf. 
    * Sie enthält alle Methoden, die zur Verwaltung der Karten des Spielbrettes, gebraucht werden.
  * MemoryCardEventListener
    * Interface dient zur Weiterleitung von Informationen der Klasse MemoryCard an die Klasse Board.
  * IMemoryCard
    * Interface beinhaltet Methode zum setzen der Sichtbarkeit einer Karte.
  
  
* logic
  * BoardEventListener
    * Interface enthält Methoden zur Weitergabe von Informationen der Klasse Board. 
  * BoardEventAdapter
    * Implementiert die Funktionen von BoardEventListener als leere Funktionen.
    * Erleichtert Anwendung des BoardEventListeners.
  * SettingUtil
    * Bündelt alle Einstellungen in einer Klasse.
  * Util 
    * Enthält allgemeine, keiner spezifischen Klasse zuordenbare, Methoden.
