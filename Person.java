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
