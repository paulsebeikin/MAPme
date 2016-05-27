/*
Defines all the string literals for the help functionality.
The HelpActivity must implement this interface.
*/
package Classes;
public interface HelpLiterals {
    //region Help content
    String newRecHelp = "All new records have compulsory entries that you must fill out first before you can go onto the next page:\n\n" +
            "\t\u2022 Up to 3 images must be submitted either taken with the camera " +
            "tor chosen from your gallery\n\n" +
            "\u2022 The location of the specimen, either from GPS, the Google Maps Widget " +
            "or coordinates entered manually. This also includes the altitude\n\n" +
            "\u2022 The project that the specimen belongs to\n\n" +
            "\u2022 How the GPS coordinates were obtained\n\n" +
            "\u2022 The date of the submission\n\n" +
            "\t\u2022 The Country\n\n" +
            "\u2022 The Province/State\n\n" +
            "\u2022 The Nearest Town\n\n" +
            "\u2022 A Description. This could be the locality, scent and any other " +
            "information that you perceive to be useful\n\n" +
            "Once you have filled in the correct mandatory information, you can click on 'Next' or 'Submit' the entry to the Virtual Museum" +
            "'Next' will take you to the next page where optional information can be entered." +
            "This includes:\n\n" +
            "\u2022 The Environment selected from a dropdown list\n\n" +
            "\u2022 The species that you think the specimen is\n\n" +
            "\u2022 An optional sound recording\n\n" +
            "\u2022 Whether flowers or fruit are present if relevant\n\n" +
            "\u2022 The number of specimens observed selected from a dropdown\n\n" +
            "\u2022 Whether the specimen is natural or cultivated\n\n" +
            "\u2022 The growth form of the specimen\n\n" +
            "You can then submit this new record by clicking on 'Submit'\n\n" +
            "Note: You are not required to entered all the optional information." +
            "You can enter desired fields as necessary";
    String logRegHelp = "To use MAPme, you must have an account registered with the " +
            "ADU Virtual Museum. An account can be created by clicking the 'Register' link on the login page. " +
            "This will open browser on your device, taking you to the ADU website.\n\n" +
            "Your password can be retrieved by clicking 'Forgot Password'.\n\n" +
            "Simply enter your username, email address, ADU number and password to login into your " +
            "ADU account and MAPme";
    String profHelp = "The Profile sets your preferences and is saved" +
            " when you submit a new record.\n\n" +
            "You can choose the country and province in which you reside " +
            "or where most specimens are found. You can also select the nearest town and " +
            "default project that you want to submit to.\n\n" +
            "This information is saved by clicking the 'Save' button";
    String historyHelp = "You can view your previous submission history by clicking on" +
            " the History button on the home screen. Previous submissions are " +
            " summarised in a table. The summary details the Project, date, description" +
            " and country of the specimen. The number of images submitted are indicated by the number next to the Project name.\n\n" +
            "You can clear your 'History' by clicking the 'bin' icon on the row.\n\n" +
            "You can view further details about the submission by clicking on a row of interest. " +
            "This screen will display all the relevant information pertaining to the submission.\n\n" +
            "This history item can be deleted by clicking on the 'bin' icon at the bottom of the screen.";

    // Help descriptions:
    String newRecord = "Creating a New Record";
    String loginRegister = "How to register and login";
    String profile = "Using the Profile feature";
    String history = "Viewing your submission history";
}