# Business Requirements:
======

## What to implement:
- Write a JavaFX program that lets users RENAME image files based on tags.

## How the tag names are going to be identified per image:
- Tag names are separated by spaces and are marked with the letter "@". 
- For instance, the image file name "image @Aunt Suzanne.png"

## What the program should do:
- Choose a directory
- View a list of all the image files anywhere in that directory
- Show a list of ALL the image files in that directory
- Move to different directories
- Each time a tag is removed/added to an image, that image has a new file name with those new tag names
- Make logs***

## Why is the image names have the tag names?
- It is so that anyone without the program can search people up in images.

## Storage and State Saving:
- The program should be able to get a list of available tags even after it has been closed.

## Data Recovery:
- When someone messes up the configuration files, the program should be able to fix it by
  recreating it.

## [***] Logging Mechanism:
- The user should be able to "go back to older sets" of tags for a particular file.
- Something to note that ^ will work given that the image has not moved to a different directory.
- The user should be able to rename the file to the older sets of tags AND
  the user should be able to rename the file to its original file name.





