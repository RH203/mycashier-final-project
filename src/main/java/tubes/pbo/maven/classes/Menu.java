package tubes.pbo.maven.classes;

public class Menu {
  private int id;
  private String name;
  private int price;
  private String category;

  // Constructors, getters, and setters MENU

  public Menu(int id, String name, int price, String category) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.category = category;
  }
  public Menu(int id, String name, int price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public String getCategory() {
    return category;
  }
}
