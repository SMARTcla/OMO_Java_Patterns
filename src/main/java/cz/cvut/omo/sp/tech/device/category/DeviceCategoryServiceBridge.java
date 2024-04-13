package cz.cvut.omo.sp.tech.device.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DeviceCategoryServiceBridge} class is a bridge implementation for managing device categories.
 * It provides methods to retrieve details, display the main category, add subcategories, and show available subcategories.
 */
public class DeviceCategoryServiceBridge implements DeviceCategoryBridge {

    private static final Logger LOGGER = LogManager.getLogger(DeviceCategoryServiceBridge.class);
    private DeviceCategory deviceCategory;
    private List<String> mainCategories = new ArrayList<>();

    public DeviceCategoryServiceBridge(DeviceCategory deviceCategory) {
        this.deviceCategory = deviceCategory;
        initializeMainCategories();
    }

    private void initializeMainCategories() {
        for (CategoryName categoryName : CategoryName.values()) {
            mainCategories.add(String.valueOf(categoryName));
        }
    }

    /**
     * Gets details about the device category.
     *
     * @return A string containing details about the device category.
     */
    @Override
    public String getDetails() {
        return "Device Details: " + deviceCategory.toString();
    }

    /**
     * Displays the main category of the device.
     */
    @Override
    public void showMainCategory() {
        LOGGER.info("Main Category: " + deviceCategory.getCategoryName());
    }

    /**
     * Adds a subcategory to the device category.
     *
     * @param subCategory The subcategory to be added.
     */
    @Override
    public void addSubCategory(String subCategory) {
        for (String mainCategory : mainCategories) {
            for (Brand brand : Brand.values()) {
                deviceCategory.addSubCategory(mainCategory + " " + brand + " " + subCategory);
            }
        }
        LOGGER.info("Added Sub Category: " + subCategory);
    }

    /**
     * Displays available subcategories of the device category.
     */
    @Override
    public void showSubCategory() {
        List<String> subCategories = deviceCategory.getSubCategories();
        if (subCategories.isEmpty()) {
            LOGGER.info("No Sub Categories available.");
        } else {
            LOGGER.info("Available Sub Categories:");
            for (String subCategory : subCategories) {
                LOGGER.info("- " + subCategory);
            }
        }
    }
}
