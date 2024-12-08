**Java-Tutorial: Mit Visual Studio Code und javac**

Dieses Tutorial zeigt, wie man ein einfaches Java-Programm erstellt, mit Visual Studio Code (VSCode) arbeitet und das Programm mit dem Java-Compiler `javac` sowie dem Java-Laufzeitbefehl `java` ausführt. Außerdem erweitern wir das Tutorial um fortgeschrittene Konzepte wie die Arbeit mit mehreren Klassen, Paketen, Arrays und benutzerdefinierten Methoden.

---

### 1. Voraussetzungen

Bevor wir mit dem Schreiben und Ausführen von Java-Programmen beginnen, müssen einige grundlegende Softwarepakete installiert sein:

1. **Visual Studio Code (VSCode)**: Lade es von der offiziellen Website herunter und installiere es: [VSCode herunterladen](https://code.visualstudio.com/).
2. **Java Development Kit (JDK)**: Lade das JDK von Oracle oder OpenJDK herunter und installiere es: [Adoptium](https://adoptium.net/) oder [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html).
3. **Java Extension Pack für VSCode**: Suche im Extensions-Tab von VSCode nach dem Java Extension Pack und installiere es, um Unterstützung für Java-Entwicklung zu erhalten.

---

### 2. Erstellen eines einfachen Java-Programms

Wir beginnen mit einem einfachen Java-Programm, das den Text "Hallo, Java Welt!" ausgibt.

1. Erstelle ein neues Verzeichnis für dein Projekt, z.B. `MyJavaApp`.
2. Öffne das Verzeichnis in VSCode.
3. Erstelle eine neue Datei namens `Main.java` und füge folgenden Code ein:

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hallo, Java Welt!");
    }
}
```

Nun kompilieren wir das Programm und führen es aus:

1. Öffne das Terminal in VSCode und gib den folgenden Befehl ein, um das Programm zu kompilieren:

```bash
javac Main.java
```

2. Führe das Programm aus, indem du folgenden Befehl verwendest:

```bash
java Main
```

Du solltest die Ausgabe „Hallo, Java Welt!“ im Terminal sehen.

---

### 3. Arbeiten mit mehreren Java-Klassen

Um die Funktionalität zu erweitern, erstellen wir eine zusätzliche Klasse `Person`, die Informationen über eine Person speichert und ausgibt.

1. Erstelle eine neue Datei namens `Person.java` mit folgendem Inhalt:

```java
public class Person {
    String name;
    int alter;

    // Konstruktor
    public Person(String name, int alter) {
        this.name = name;
        this.alter = alter;
    }

    // Methode, die Informationen über die Person ausgibt
    public void vorstellen() {
        System.out.println("Hallo, mein Name ist " + name + " und ich bin " + alter + " Jahre alt.");
    }
}
```

Ändere nun `Main.java`, um die `Person`-Klasse zu verwenden:

```java
public class Main {
    public static void main(String[] args) {
        Person person = new Person("Max Mustermann", 25);
        person.vorstellen(); // Die Methode aus der Person-Klasse aufrufen
    }
}
```

Kompiliere nun beide Dateien und führe das Programm aus, um die Ausgabe zu sehen:

```bash
javac Main.java Person.java
java Main
```

Ausgabe:

```
Hallo, mein Name ist Max Mustermann und ich bin 25 Jahre alt.
```

---

### 4. Arbeiten mit Paketen

In größeren Projekten ist es üblich, Java-Klassen in Paketen zu organisieren. Hier zeigen wir, wie man Klassen in einem Paket speichert und auf sie zugreift.

1. Erstelle einen neuen Ordner `com/myapp`, um das Paket zu simulieren.
2. Verschiebe die Dateien `Main.java` und `Person.java` in diesen Ordner.
3. Füge am Anfang jeder Datei die Paketdeklaration hinzu:

```java
package com.myapp;
```

Nun musst du beim Kompilieren sicherstellen, dass du das Paket korrekt berücksichtigst:

1. Kompiliere die Dateien aus dem übergeordneten Verzeichnis (z.B. `C:\Users\Teilnehmer\documents\dumps`):

```bash
javac com/myapp/Main.java com/myapp/Person.java
```

2. Führe das Programm aus, indem du den Paketnamen angibst:

```bash
java com.myapp.Main
```

Ausgabe:

```
Hallo, mein Name ist Max Mustermann und ich bin 25 Jahre alt.
```

---

### 5. Arbeiten mit Arrays

Ein Array ist eine Sammlung von Objekten desselben Typs. In diesem Abschnitt erweitern wir das Programm, um ein Array von `Person`-Objekten zu verwenden.

1. Bearbeite `Main.java`, um ein Array von `Person`-Objekten zu erstellen:

```java
public class Main {
    public static void main(String[] args) {
        Person[] personen = new Person[3];
        personen[0] = new Person("Max Mustermann", 25);
        personen[1] = new Person("Erika Musterfrau", 30);
        personen[2] = new Person("John Doe", 40);

        for (Person p : personen) {
            p.vorstellen();
        }
    }
}
```

Kompiliere und führe das Programm aus:

```bash
javac com/myapp/Main.java com/myapp/Person.java
java com.myapp.Main
```

Ausgabe:

```
Hallo, mein Name ist Max Mustermann und ich bin 25 Jahre alt.
Hallo, mein Name ist Erika Musterfrau und ich bin 30 Jahre alt.
Hallo, mein Name ist John Doe und ich bin 40 Jahre alt.
```

---

### 6. Erstellen von Benutzerdefinierten Methoden

Jetzt erweitern wir die `Person`-Klasse um eine Methode, die überprüft, ob eine Person volljährig ist.

1. Bearbeite die `Person`-Klasse und füge folgende Methode hinzu:

```java
public boolean istVolljaehrig() {
    return alter >= 18;
}
```

Nun rufen wir die Methode in `Main.java` auf, um zu prüfen, ob eine Person volljährig ist:

```java
public class Main {
    public static void main(String[] args) {
        Person person = new Person("Max Mustermann", 25);
        person.vorstellen(); // Die Methode aus der Person-Klasse aufrufen

        if (person.istVolljaehrig()) {
            System.out.println(person.name + " ist volljährig.");
        } else {
            System.out.println(person.name + " ist noch nicht volljährig.");
        }
    }
}
```

Kompiliere und führe das Programm aus:

```bash
javac com/myapp/Main.java com/myapp/Person.java
java com.myapp.Main
```

Ausgabe:

```
Max Mustermann ist volljährig.
```

---

Damit hast du nun ein vollständiges Java-Programm erstellt, das mit VSCode entwickelt und mit `javac` sowie `java` ausgeführt wird!
