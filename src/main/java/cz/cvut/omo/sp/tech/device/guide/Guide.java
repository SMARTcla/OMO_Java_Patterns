package cz.cvut.omo.sp.tech.device.guide;

import java.time.LocalDateTime;

/**
 * The `Guide` class represents a user guide or manual associated with a device. It includes information
 * such as the guide's description, start and end timestamps, language, link, and page count.
 */
public class Guide {

    private String guideDescription;
    private LocalDateTime guideStartDate;
    private LocalDateTime guideEndDate;
    private String guideLanguage;
    private String guideLink;
    private int pageCount;

    /**
     * Constructs a `Guide` object with the specified guide information.
     *
     * @param guideDescription The description of the user guide.
     * @param guideStartDate   The timestamp when the user guide becomes available.
     * @param guideEndDate     The timestamp when the user guide is no longer available.
     * @param guideLanguage    The language of the user guide.
     * @param guideLink        The link or URL to the user guide.
     * @param pageCount        The number of pages in the user guide.
     */
    public Guide(String guideDescription, LocalDateTime guideStartDate, LocalDateTime guideEndDate, String guideLanguage, String guideLink, int pageCount) {
        this.guideDescription = guideDescription;
        this.guideStartDate = guideStartDate;
        this.guideEndDate = guideEndDate;
        this.guideLanguage = guideLanguage;
        this.guideLink = guideLink;
        this.pageCount = pageCount;
    }

    /**
     * Gets the timestamp when the user guide becomes available.
     *
     * @return The start timestamp of the guide's availability.
     */
    public LocalDateTime getGuideStartDate() {
        return guideStartDate;
    }

    /**
     * Sets the timestamp when the user guide becomes available.
     *
     * @param guideStartDate The new start timestamp of the guide's availability.
     */
    public void setGuideStartDate(LocalDateTime guideStartDate) {
        this.guideStartDate = guideStartDate;
    }

    /**
     * Gets the timestamp when the user guide is no longer available.
     *
     * @return The end timestamp of the guide's availability.
     */
    public LocalDateTime getGuideEndDate() {
        return guideEndDate;
    }

    /**
     * Sets the timestamp when the user guide is no longer available.
     *
     * @param guideEndDate The new end timestamp of the guide's availability.
     */
    public void setGuideEndDate(LocalDateTime guideEndDate) {
        this.guideEndDate = guideEndDate;
    }

    /**
     * Gets the language of the user guide.
     *
     * @return The language of the user guide.
     */
    public String getGuideLanguage() {
        return guideLanguage;
    }

    /**
     * Sets the language of the user guide.
     *
     * @param guideLanguage The new language of the user guide.
     */
    public void setGuideLanguage(String guideLanguage) {
        this.guideLanguage = guideLanguage;
    }

    /**
     * Returns a string representation of the `Guide` object.
     *
     * @return A string containing information about the user guide, such as its description and page count.
     */
    @Override
    public String toString() {
        return "Guide{" +
                "guideDescription='" + guideDescription + '\'' +
                ", guideStartDate=" + guideStartDate +
                ", guideEndDate=" + guideEndDate +
                ", guideLanguage='" + guideLanguage + '\'' +
                ", guideLink='" + guideLink + '\'' +
                ", pageCount=" + pageCount +
                '}';
    }
}
