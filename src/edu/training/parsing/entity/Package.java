package edu.training.parsing.entity;

import edu.training.parsing.type.PackageType;

/**
 * Created by Roman on 18.11.2016.
 */
public class Package {
    private int amount;
    private int price;
    private PackageType type = PackageType.NONE;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Package:\n" +
                "\t\t\tAmount: " + amount + '\n' +
                "\t\t\tPrice: " + price + '\n' +
                "\t\t\tType: " + type;
    }
}
