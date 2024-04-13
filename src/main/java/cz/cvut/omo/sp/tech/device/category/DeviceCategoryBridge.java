package cz.cvut.omo.sp.tech.device.category;

/**
 * An interface representing a device category bridge.
 *
 * This interface defines methods for getting details about the device category, displaying its main category,
 * displaying its subcategory, and adding a subcategory. Implementing classes will provide specific functionality
 * for managing device categories.
 */
public interface DeviceCategoryBridge {
    /**
     * Get details about the device category.
     *
     * @return A string containing details about the device category.
     */
    String getDetails();

    /**
     * Show the main category of the device.
     */
    void showMainCategory();

    /**
     * Show the subcategory of the device.
     */
    void showSubCategory();

    /**
     * Add a subcategory to the device category.
     *
     * @param subCategory The subcategory to add.
     */
    void addSubCategory(String subCategory);
}
