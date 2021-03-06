package net.steveperkins.fitnessjiffy.domain;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FOOD_EATEN")
public class FoodEaten {

    @Id
    @Column(columnDefinition = "BYTEA", length = 16)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "FOOD_ID", nullable = false)
    private Food food;

    @Column(name = "DATE", nullable = false)
    private Date date;

    @Column(name = "SERVING_TYPE", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Food.ServingType servingType;

    @Column(name = "SERVING_QTY", nullable = false)
    private Double servingQty;

    public FoodEaten(UUID id, User user, Food food, Date date, Food.ServingType servingType,
                     double servingQty) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.user = user;
        this.food = food;
        this.date = date;
        this.servingType = servingType;
        this.servingQty = servingQty;
    }

    public FoodEaten() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Food.ServingType getServingType() {
        return servingType;
    }

    public void setServingType(Food.ServingType servingType) {
        this.servingType = servingType;
    }

    public Double getServingQty() {
        return servingQty;
    }

    public void setServingQty(Double servingQty) {
        this.servingQty = servingQty;
    }

    public int getCalories() {
        return (int) (food.getCalories() * getRatio());
    }

    public double getFat() {
        return food.getCalories() * getRatio();
    }

    public double getSaturatedFat() {
        return food.getSaturatedFat() * getRatio();
    }

    public double getSodium() {
        return food.getSodium() * getRatio();
    }

    public double getCarbs() {
        return food.getCarbs() * getRatio();
    }

    public double getFiber() {
        return food.getFiber() * getRatio();
    }

    public double getSugar() {
        return food.getSugar() * getRatio();
    }

    public double getProtein() {
        return food.getProtein() * getRatio();
    }

    public int getPoints() {
        return (int) (food.getPoints() * getRatio());
    }

	private double getRatio() {
        if(servingType.equals(food.getDefaultServingType())) {
            // Default serving type was used
            return servingQty / food.getServingTypeQty();
        } else {
            // Serving type needs conversion
            double ouncesInThisServingType = servingType.getValue();
            double ouncesInDefaultServingType = food.getDefaultServingType().getValue();
            return (ouncesInDefaultServingType * food.getServingTypeQty() != 0) ? (ouncesInThisServingType * servingQty) / (ouncesInDefaultServingType * food.getServingTypeQty()) : 0;
        }
    }
}
