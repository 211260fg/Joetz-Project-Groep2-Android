package groep2.joetz.com.joetz_project_groep2_test.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by floriangoeteyn on 26-May-16.
 */
public class Vacation {

    private String title;
    private String description;
    private String extra_info="";
    private String header_img="";
    private String startDate;
    private String endDate;
    private int minAge = 0;
    private int maxAge = 0;
    private String location;
    private Category category;
    private int price;



    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return parseStringToDate(startDate);
    }

    public Date getEndDate() {
        return parseStringToDate(endDate);
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

    private Date parseStringToDate(String datestr){
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getHeader_img() {
        return header_img;
    }
}
