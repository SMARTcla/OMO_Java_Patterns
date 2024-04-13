package cz.cvut.omo.sp.tech.device.category;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The `DeviceCategory` class represents a category of electronic devices with specific attributes,
 * such as brand, category name, icon, label, and warranty duration.
 */
public class DeviceCategory {

    private Brand brand;
    private CategoryName categoryName;
    private Image icon;
    private String label;
    private int warranty;
    private List<String> subCategories;

    /**
     * Constructs a `DeviceCategory` object with the specified attributes.
     *
     * @param brand        The brand or manufacturer of the device category.
     * @param categoryName The name or category of the electronic devices.
     * @param icon         The icon representing the device category.
     * @param label        A label or description of the device category.
     * @param warranty     The warranty duration for devices in this category.
     */
    public DeviceCategory(Brand brand, CategoryName categoryName, Image icon, String label, int warranty) {
        this.brand = brand;
        this.categoryName = categoryName;
        this.icon = icon;
        this.label = label;
        this.warranty = warranty;
        this.subCategories = new ArrayList<>();

    }

    /**
     * Gets the brand or manufacturer of the device category.
     *
     * @return The brand of the device category.
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Sets the brand or manufacturer of the device category.
     *
     * @param brand The new brand for the device category.
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    /**
     * Gets the name or category of the electronic devices.
     *
     * @return The category name of the electronic devices.
     */
    public CategoryName getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name or category of the electronic devices.
     *
     * @param categoryName The new category name for the electronic devices.
     */
    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }

    /**
     * Gets the icon representing the device category.
     *
     * @return The icon representing the device category.
     */
    public Image getIcon() {
        return icon;
    }

    /**
     * Sets the icon representing the device category.
     *
     * @param icon The new icon for the device category.
     */
    public void setIcon(Image icon) {
        this.icon = icon;
    }

    /**
     * Gets the label or description of the device category.
     *
     * @return The label or description of the device category.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label or description of the device category.
     *
     * @param label The new label for the device category.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the warranty duration for devices in this category.
     *
     * @return The warranty duration for devices in this category.
     */
    public int getWarranty() {
        return warranty;
    }

    /**
     * Sets the warranty duration for devices in this category.
     *
     * @param warranty The new warranty duration for devices in this category.
     */
    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public void addSubCategory(String subCategory) {
        subCategories.add(subCategory);
    }

    public List<String> getSubCategories() {
        return subCategories;
    }
    /**
     * Returns a string representation of the `DeviceCategory` object.
     *
     * @return A string containing the brand, category name, icon, label, and warranty information.
     */
    @Override
    public String toString() {
        return "DeviceCategory{" +
                "brand='" + brand + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", icon=" + icon +
                ", label='" + label + '\'' +
                ", warranty=" + warranty +
                ", subCategories=" + subCategories +
                '}';
    }
}
