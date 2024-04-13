package cz.cvut.omo.sp.tech.device.documentation;

import java.time.LocalDateTime;

/**
 * The `Documentation` class represents documentation associated with a device.
 * It includes information such as author, issuance and expiration dates, device description,
 * device model, document ID, installation guide, device language, safety instructions, title,
 * troubleshooting details, URL, user manual, version, and warranty information.
 */
public class Documentation {

    private String author;
    private LocalDateTime dateIssued;
    private LocalDateTime dateExpired;
    private String deviceDescription;
    private String deviceModel;
    private String docId;
    private String installationGuide;
    private String deviceLanguage;
    private String safetyInstructions;
    private String title;
    private String troubleshooting;
    private String url;
    private String userManual;
    private int version;
    private LocalDateTime warrantyInfo;

    /**
     * Constructs a `Documentation` object with the specified attributes.
     *
     * @param author             The author of the documentation.
     * @param dateIssued         The date when the documentation was issued.
     * @param dateExpired        The date when the documentation expires.
     * @param deviceDescription  The description of the associated device.
     * @param deviceModel        The model of the associated device.
     * @param docId              The unique identifier of the documentation.
     * @param installationGuide  The installation guide for the device.
     * @param deviceLanguage     The language of the documentation.
     * @param safetyInstructions The safety instructions for using the device.
     * @param title              The title of the documentation.
     * @param troubleshooting    Troubleshooting details for the device.
     * @param url                The URL to access the documentation.
     * @param userManual         The user manual for the device.
     * @param version            The version of the documentation.
     * @param warrantyInfo       Warranty information related to the device.
     */
    public Documentation(String author, LocalDateTime dateIssued, LocalDateTime dateExpired, String deviceDescription,
                         String deviceModel, String docId, String installationGuide, String deviceLanguage,
                         String safetyInstructions, String title, String troubleshooting, String url,
                         String userManual, int version, LocalDateTime warrantyInfo) {
        this.author = author;
        this.dateIssued = dateIssued;
        this.dateExpired = dateExpired;
        this.deviceDescription = deviceDescription;
        this.deviceModel = deviceModel;
        this.docId = docId;
        this.installationGuide = installationGuide;
        this.deviceLanguage = deviceLanguage;
        this.safetyInstructions = safetyInstructions;
        this.title = title;
        this.troubleshooting = troubleshooting;
        this.url = url;
        this.userManual = userManual;
        this.version = version;
        this.warrantyInfo = warrantyInfo;
    }

    /**
     * Gets the date when the documentation was issued.
     *
     * @return The issuance date of the documentation.
     */
    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    /**
     * Sets the date when the documentation was issued.
     *
     * @param dateIssued The new issuance date of the documentation.
     */
    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    /**
     * Gets the date when the documentation expires.
     *
     * @return The expiration date of the documentation.
     */
    public LocalDateTime getDateExpired() {
        return dateExpired;
    }

    /**
     * Sets the date when the documentation expires.
     *
     * @param dateExpired The new expiration date of the documentation.
     */
    public void setDateExpired(LocalDateTime dateExpired) {
        this.dateExpired = dateExpired;
    }

    /**
     * Gets the language of the documentation.
     *
     * @return The language of the documentation.
     */
    public String getDeviceLanguage() {
        return deviceLanguage;
    }

    /**
     * Sets the language of the documentation.
     *
     * @param deviceLanguage The new language for the documentation.
     */
    public void setDeviceLanguage(String deviceLanguage) {
        this.deviceLanguage = deviceLanguage;
    }

    /**
     * Returns a string representation of the `Documentation` object.
     *
     * @return A string containing information about the author, dates, device description,
     *         model, document ID, guides, language, safety instructions, title, troubleshooting,
     *         URL, user manual, version, and warranty information.
     */
    @Override
    public String toString() {
        return "Documentation{" +
                "author='" + author + '\'' +
                ", dateIssued=" + dateIssued +
                ", dateExpired=" + dateExpired +
                ", deviceDescription='" + deviceDescription + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", docId='" + docId + '\'' +
                ", installationGuide='" + installationGuide + '\'' +
                ", deviceLanguage='" + deviceLanguage + '\'' +
                ", safetyInstructions='" + safetyInstructions + '\'' +
                ", title='" + title + '\'' +
                ", troubleshooting='" + troubleshooting + '\'' +
                ", url='" + url + '\'' +
                ", userManual='" + userManual + '\'' +
                ", version=" + version +
                ", warrantyInfo=" + warrantyInfo +
                '}';
    }
}
