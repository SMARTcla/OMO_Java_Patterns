package cz.cvut.omo.sp.tech.device.content;

import java.time.LocalDateTime;

/**
 * The `DeviceContent` class represents the content associated with a device.
 * It includes information such as the type of content, content description, count,
 * content start and end dates, content country, and content language.
 */
public class DeviceContent {

    private TypeDeviceContent typeContent;
    private String contentDescription;
    private int count;
    private LocalDateTime contentStartDate;
    private LocalDateTime contentEndDate;
    private String contentCountry;
    private String contentLanguage;

    /**
     * Constructs a `DeviceContent` object with the specified attributes.
     *
     * @param typeContent       The type of content associated with the device.
     * @param contentDescription A description of the content.
     * @param count             The count or quantity of the content.
     * @param contentStartDate  The start date of the content availability.
     * @param contentEndDate    The end date of the content availability.
     * @param contentCountry    The country or region associated with the content.
     * @param contentLanguage   The language of the content.
     */
    public DeviceContent(TypeDeviceContent typeContent, String contentDescription, int count,
                         LocalDateTime contentStartDate, LocalDateTime contentEndDate,
                         String contentCountry, String contentLanguage) {
        this.typeContent = typeContent;
        this.contentDescription = contentDescription;
        this.count = count;
        this.contentStartDate = contentStartDate;
        this.contentEndDate = contentEndDate;
        this.contentCountry = contentCountry;
        this.contentLanguage = contentLanguage;
    }

    /**
     * Gets the type of content associated with the device.
     *
     * @return The type of content.
     */
    public TypeDeviceContent getTypeContent() {
        return typeContent;
    }

    /**
     * Sets the type of content associated with the device.
     *
     * @param typeContent The new type of content.
     */
    public void setTypeContent(TypeDeviceContent typeContent) {
        this.typeContent = typeContent;
    }

    /**
     * Gets the description of the content.
     *
     * @return The content description.
     */
    public String getContentDescription() {
        return contentDescription;
    }

    /**
     * Sets the description of the content.
     *
     * @param contentDescription The new content description.
     */
    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    /**
     * Gets the count or quantity of the content.
     *
     * @return The content count.
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count or quantity of the content.
     *
     * @param count The new content count.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets the start date of the content availability.
     *
     * @return The content start date.
     */
    public LocalDateTime getContentStartDate() {
        return contentStartDate;
    }

    /**
     * Sets the start date of the content availability.
     *
     * @param contentStartDate The new content start date.
     */
    public void setContentStartDate(LocalDateTime contentStartDate) {
        this.contentStartDate = contentStartDate;
    }

    /**
     * Gets the end date of the content availability.
     *
     * @return The content end date.
     */
    public LocalDateTime getContentEndDate() {
        return contentEndDate;
    }

    /**
     * Sets the end date of the content availability.
     *
     * @param contentEndDate The new content end date.
     */
    public void setContentEndDate(LocalDateTime contentEndDate) {
        this.contentEndDate = contentEndDate;
    }

    /**
     * Gets the country or region associated with the content.
     *
     * @return The content country or region.
     */
    public String getContentCountry() {
        return contentCountry;
    }

    /**
     * Sets the country or region associated with the content.
     *
     * @param contentCountry The new content country or region.
     */
    public void setContentCountry(String contentCountry) {
        this.contentCountry = contentCountry;
    }

    /**
     * Gets the language of the content.
     *
     * @return The content language.
     */
    public String getContentLanguage() {
        return contentLanguage;
    }

    /**
     * Sets the language of the content.
     *
     * @param contentLanguage The new content language.
     */
    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    /**
     * Returns a string representation of the `DeviceContent` object.
     *
     * @return A string containing information about the type of content, description, count,
     *         start and end dates, country, and language.
     */
    @Override
    public String toString() {
        return "DeviceContent{" +
                "typeContent=" + typeContent +
                ", contentDescription='" + contentDescription + '\'' +
                ", count=" + count +
                ", contentStartDate=" + contentStartDate +
                ", contentEndDate=" + contentEndDate +
                ", contentCountry='" + contentCountry + '\'' +
                ", contentLanguage='" + contentLanguage + '\'' +
                '}';
    }
}
