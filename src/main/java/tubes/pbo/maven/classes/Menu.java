package tubes.pbo.maven.classes;

public class Menu {
  private int id;
  private String name;
  private int price;
  private String description;

  // Constructors, getters, and setters

  public Menu(int id, String name, int price, String description) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.description = description;
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

  public String getDescription() {
    return description;
  }
}
