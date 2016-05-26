package groep2.joetz.com.joetz_project_groep2_test.model;

import java.util.Date;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public class Vakantie {

    private String title;
    private String description;
    private String extra_info="";
    private Date startDate;
    private Date endDate;
    private int minAge = 0;
    private int maxAge = 0;
    private String location;
    private Category category;
    private double price;

    public Vakantie(String title, String description, Date startDate, Date endDate, String location, Category category, double price) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getLocation() {
        return location;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getExtra_info() {
        return extra_info;
    }
}
