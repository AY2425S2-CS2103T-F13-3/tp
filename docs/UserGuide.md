---
layout: page
title: User Guide

---

# Welcome to HireHive!

HireHive is an **all-in-one desktop application** designed to streamline recruitment for small businesses and hiring managers, combining the efficiency of a [Command Line Interface (CLI)](#glossary) with the ease of a [Graphical User Interface (GUI)](#glossary). It simplifies tracking [applicants](#glossary), organizing interview notes, and managing hiring stages—all in one centralized platform. With **fast keyboard-driven commands**, HireHive lets you complete tasks quicker than traditional GUI apps, so you can focus less on administrative work and more on finding the best talent.

- [Quick start](#quick-start-)
- [Command Summary](#command-summary)
- [Features](#features-)
  - [Viewing help: `help`](#viewing-help--help)
  - [Adding a person: `add`](#adding-a-person-add)
  - [Listing all persons: `list`](#listing-all-persons--list)
  - [Editing a person: `edit`](#editing-a-person--edit)
  - [Tagging a person: `tag`](#tagging-a-person--tag)
  - [Filter persons by tag: `filter`](#filter-persons-by-tag--filter)
  - [Locating persons by name `find`](#locating-persons-by-name-find)
  - [Deleting a person: `delete`](#deleting-a-person--delete)
  - [Clearing all entries: `clear`](#clearing-all-entries--clear)
  - [Exiting the program: `exit`](#exiting-the-program--exit)
  - [Saving the data](#saving-the-data)
  - [Editing the data file](#editing-the-data-file)
- [FAQ](#faq)
- [Known Issues](#known-issues)
- [Glossary](#glossary)

--------------------------------------------------------------------------------------------------------------------


## Quick start
1. To use Hirehive, you need Java `17` or above installed in your computer.<br>
   - For **Mac users:** Ensure you have the precise [JDK](#glossary) version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest HireHive `.jar` file from [here](https://github.com/AY2425S2-CS2103T-F13-3/tp/releases).

3. Copy the downloaded file to a folder where you would like to store HireHive (e.g. "Documents" or "Desktop" or a new folder "HireHive") .

4. Run HireHive
   - Find the folder that you stored HireHive previously (e.g. "Documents" or "Desktop" or "HireHive").
   - Right-click on the folder and choose:  
     - For MAC: "New terminal at [folder-name]".
     - For Windows: "Open in terminal".
   - Type `java -jar hirehive.jar` command in the opened terminal and press Enter to run the HireHive application.<br>
   - A [GUI](#glossary) similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui-2.png)

5. To use HireHive, you can type a command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all applicants.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/intern`: Adds an applicant named `John Doe` to HireHive, with his relevant information

   * `delete n/John Doe` : Deletes the applicant `John Doe` from the current list.

   * `edit 3 n/Josef` : Edits the name of the 3rd applicant in the list to Josef

   * `find John Doe`: Searches for John Doe in the current list.

   * `tag n/John Doe t/interviewee` : Tags John Doe with the 'interviewee' tag

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. You can refer to the [Features](#features) section below for details of each command.

[Back to top](#welcome-to-hirehive)

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Help** | `help`
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/ROLE [i/INFO]` <br> e.g., `add n/James Ho p/87654321 e/jamesho@example.com a/123, Clementi Rd, 1234665 r/software engineer intern i/26 years old`
**List** | `list`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG] [i/NOTE] [d/DATE]`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Tag** | `tag n/NAME [t/TAG]…​`
**Filter** |`filter t/TAG` <br> e.g., `filter t/Applicant`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Delete** | `delete n/NAME`<br> e.g., `delete n/John Doe`
**Clear** | `clear`
**Exit** | `exit`

[Back to top](#welcome-to-hirehive)

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the  parameters to be supplied by you.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [i/INFO]` can be used as `n/John Doe i/27 years old` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/interviewee`, `t/shortlisted` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`, `list` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

[Back to top](#welcome-to-hirehive)

### Viewing help : `help`

Shows a message explaining how you can access the help page. The help page contains all the features of HireHive. 

![help message](images/helpMessage.png)

Format: `help`

[Back to top](#welcome-to-hirehive)


### Adding a person: `add`

Adds a person to HireHive.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS r/ROLE`

**:information_source: Note!**<br>
* Please input a phone number that **start with 9/8/6**, is **exactly 8 digits** long and do not use spaces.
* Please **do not use** dashes(-), commas(,), slashes(/) and periods(.) in names.
    - Example: `Doe, John` or `Doe/John` should be entered as `Doe John`.
* Names need to be in English!

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An 'Applicant' tag and an empty note is automatically assigned to the applicant when you add them to HireHive.
</div>

- The parameter NAME is **[unique](#glossary)** and **[case-insensitive](#glossary)**, and is displayed as how you type it.
- Applicants in HireHive can share the same phone number, email, address and role.

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/Software Engineer intern`
* `add n/Betsy Crowe e/betsycrowe@example.com a/Ang Mo Kio Street 22 p/87654321 r/senior consulting analyst i/32 years old`

![help message](images/Ui-AddCommand.png)

[Back to top](#welcome-to-hirehive)

### Listing all persons : `list`

Shows you a list of all the applicants in HireHive.

Format: `list`

[Back to top](#welcome-to-hirehive)

### Editing a person : `edit`

You can edit the information of an existing applicant in HireHive if needed!

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG] [r/ROLE] [i/NOTE] [d/DATE]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.


Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.

[Back to top](#welcome-to-hirehive)


### Tagging a person : `tag`

You can change or add tags to a specific person from HireHive to easily differentiate between all your applicants!

Format: `tag n/NAME t/TAG`

* Tags a person if the given name partially matches **exactly one person** in the list ([case-insensitive](#glossary)).
* If multiple matches are found, a list of all matching names will be displayed. You can then enter the **full name**
  of the person you want to delete.

Examples:
* `tag n/John Doe t/Applicant` will tag John Doe as "Applicant" in HireHive

[Back to top](#hirehive-user-guide)

### Filter persons by tag : `filter`

Lists down all your applicants of a certain tag. 

Format: `filter t/TAG`

* The search is [case-insensitive](#glossary). e.g `applicant` will match `Applicant`
* Only full words will be matched e.g. `Reject` will not match `Rejected`

[Back to top](#welcome-to-hirehive)

### Add notes to person: `newnote`

You can add information about the applicant into a note with this command.

Format: `newnote n/NAME i/NOTE`

* Adds the note if the given name partially matches **exactly one** person in the list ([case-insensitive](#glossary))
* If multiple matches are found, a list of all matching names will be displayed. You should then retry the command but using the **full name** of the desired person.
* NOTE has a limit of 500 characters.
* After adding, a popup window will appear containing the given input.
* If the input is empty or consists only of whitespace, then the popup window displays a default message "Currently empty..." in yellow.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can remove the contents of the note by leaving the NOTE field blank.
</div>

Examples:
* `newnote n/John Doe i/25 years old` adds a note to John Doe that says "25 years old"
* `newnote n/John Doe i/` removes any existing content in John Doe's note

[Back to top](#welcome-to-hirehive)

### Display note of person: `displaynote`

You can display the note of a person with this command.

Format: `displaynote n/NAME`

* Displays the note if the given name partially matches **exactly one** person in the list ([case-insensitive](#glossary))
* If multiple matches are found, a list of all matching names will be displayed. You should then retry the command but using the **full name** of the desired person.
* A popup window will appear containing the person's note.
* If the input is empty or consists only of whitespace, then the popup window displays a default message "Currently empty..." in yellow.

Examples:
* `displaynote n/John Doe` shows John Doe's note

[Back to top](#welcome-to-hirehive)

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is [case-insensitive](#glossary). e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

[Back to top](#welcome-to-hirehive)

### Deleting a person : `delete`

You can delete a specific person from HireHive.

Format: `delete n/NAME`

* Deletes the person if the given name partially matches **exactly one person** in the list ([case-insensitive](#glossary)).
* If multiple matches are found, a list of all matching names will be displayed. You can then enter the **full name** of the person you want to delete.

Examples:
* `delete n/John Doe` deletes John Doe's contact from HireHive

![delete message](images/Ui-DeleteCommand.png)

[Back to top](#welcome-to-hirehive)

### Clearing all entries : `clear`

Clears all entries from HireHive.

Format: `clear`

[Back to top](#welcome-to-hirehive)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

[Back to top](#welcome-to-hirehive)

### Saving the data

HireHive data is saved in the [hard disk](#glossary) automatically after any command that changes the data. If successfully saved, the output following the command will display the success message. There is no need to save manually.

[Back to top](#welcome-to-hirehive)

### Editing the data file

HireHive data are saved automatically as a [JSON](#glossary) file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, HireHive will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause HireHive to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

[Back to top](#welcome-to-hirehive)

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous HireHive home folder.

[Back to top](#welcome-to-hirehive)

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the [GUI](#glossary) will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. If you **minimize the Help Window and then run the `help` command** (or use the `Help` menu, or the keyboard [shortcut](#glossary) `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

[Back to top](#welcome-to-hirehive)

--------------------------------------------------------------------------------------------------------------------

## Glossary

Term | Description
--------|------------------
**Command Line Interface (CLI)** | A text-based interface where users interact with a computer by typing commands into a terminal, allowing for direct control and automation of tasks.
**Graphical User Interface (GUI)** | A form of user interface that allows users to interact with electronic devices through graphical icons and visual indicators such as secondary notation.
**Applicant** | A person who makes a formal application for something, especially a job.
**JDK** | Acronym for _Java Development Kit._ It is a distribution of Java technology by Oracle Corporation. It implements the Java Language Specification and the Java Virtual Machine Specification and provides the Standard Edition of the Java Application Programming Interface.
**Unique** | Only 1 specific instance of it exists.
**Case-insensitive** | Uppercase and lowercase letters are treated the same and hence equivalent.
**Hard disk** | The storage device used by a computer. These can be used as primary or secondary storage.
**JSON** | Acronym for _JavaScript Object Notation,_ an open standard file format and data interchange format that uses human-readable text to store and transmit data objects consisting of name–value pairs and arrays (or other serializable values).
**Shortcut** | A key or combination of keys that you can press on a computer keyboard to quickly perform a specific action.

[Back to top](#welcome-to-hirehive)

--------------------------------------------------------------------------------------------------------------------
