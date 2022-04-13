package Restaurant.domain;

public class Restaurant {

    private float id;
    private String name;
    private float postal_code;
    private String adress;


    public Restaurant(String name, float postal_code, String adress) {
        this.name = name;
        this.postal_code = postal_code;
        this.adress = adress;
    }

    public Restaurant() {

    }


    public float getId() {return id;}

    public void setId(float id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name){this.name = name;}

    public float getPostal_code() {return postal_code;}

    public void setPostal_code(float postal_code) {this.postal_code = postal_code;}

    public String getAdress() {return adress;}

    public void setAdress(String adress) {this.adress = adress;}
}
